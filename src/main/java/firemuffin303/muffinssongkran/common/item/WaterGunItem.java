package firemuffin303.muffinssongkran.common.item;

import firemuffin303.muffinssongkran.common.entity.FluidSprayEntity;
import firemuffin303.muffinssongkran.registry.ModEnchantment;
import firemuffin303.muffinssongkran.registry.ModItems;
import firemuffin303.muffinssongkran.registry.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class WaterGunItem extends RangedWeaponItem {
    private static final int max_ammo = 2;

    private boolean charged = false;
    private static boolean loaded = false;
    float firingSpeedSecond;


    public WaterGunItem(Settings settings) {
        super(settings.maxCount(1).maxDamage(362));
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(isLoaded(itemStack)){
            shoot(user, world,itemStack);
            setLoadedAmmo(itemStack,getLoadAmmo(itemStack)-1);
            if(getLoadAmmo(itemStack) <= 0){
                setLoaded(itemStack,false);
                PotionUtil.setPotion(itemStack, Potions.EMPTY);
            }
            user.getItemCooldownManager().set(this, 10);
            itemStack.damage(1, user,(e) ->{
                e.sendToolBreakStatus(hand);
            });
            return TypedActionResult.consume(itemStack);
        }else if(!user.getProjectileType(itemStack).isEmpty()){
            if(!isLoaded(itemStack)){
                this.charged = false;
                loaded = false;
                user.setCurrentHand(hand);
            }
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.fail(itemStack);


    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return (itemStack -> itemStack.isOf(Items.POTION));
    }


    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(!world.isClient){
            float f = (float)(stack.getMaxUseTime() - remainingUseTicks) / (float)getLoadingTime();
            if(f < 0.2f){
                this.charged = false;
                loaded = false;
            }

            if(f >= 0.2f && !this.charged){
                this.charged = true;
                world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.ITEM_WATER_GUN_FILLING, SoundCategory.PLAYERS, 0.5F, 1.0F);

            }

            if(f >=0.5f && !loaded){
                loaded = true;
            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        float f = (float)(i / getLoadingTime());
        if(f > 1.0f){
            f = 1.0F;
        }
        if (f >= 1.0F && !isLoaded(stack) && isValidAmmo(user,stack)) {
            setLoaded(stack,true);
            SoundCategory soundCategory = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.ITEM_WATER_GUN_LOADED, soundCategory, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return WaterGunItem.getLoadingTime() + 3;
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.isOf(this);
    }

    public void shoot(PlayerEntity user, World world, ItemStack waterGun){
        user.playSound(SoundEvents.ENTITY_SNOWBALL_THROW,0.8f,0);
        if(!world.isClient){
            Vec3d vec3d = user.getRotationVec(1.0f);
            FluidSprayEntity fluidSprayEntity = new FluidSprayEntity(world);
            fluidSprayEntity.setOwner(user);
            fluidSprayEntity.setPos(user.getX(),user.getEyeY(),user.getZ());
            fluidSprayEntity.setVelocity(vec3d);
            fluidSprayEntity.initPotionFromStack(waterGun);

            world.spawnEntity(fluidSprayEntity);
        }
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(isLoaded(stack)){
            tooltip.add(Text.translatable("item.muffinssongkran.watergun.fluid")
                    .append(ScreenTexts.SPACE)
                    .append(Text.translatable(PotionUtil.getPotion(stack).finishTranslationKey("item.minecraft.potion.effect.")).formatted(Formatting.GRAY)));
            tooltip.add(Text.translatable("item.muffinssongkran.watergun.amount", getLoadAmmo(stack), getMaxAmmo(stack)).formatted(Formatting.GRAY));
        }

    }

    private boolean isValidAmmo(LivingEntity shooter,ItemStack watergun){
        if(watergun.isEmpty()){
            return false;
        }

        ItemStack itemStack = shooter.getProjectileType(watergun);
        boolean creative = shooter instanceof PlayerEntity player && player.getAbilities().creativeMode;

        if(!creative && itemStack.getItem() == Items.POTION){
            if (shooter instanceof PlayerEntity player) {
                player.getInventory().insertStack(ItemUsage.exchangeStack(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
            }
        }

        if(creative && itemStack.getItem() != Items.POTION){
            itemStack = new ItemStack(Items.POTION);
            PotionUtil.setPotion(itemStack,Potions.WATER);
        }

        setLoadedAmmo(watergun,getMaxAmmo(watergun));
        PotionUtil.setPotion(watergun,PotionUtil.getPotion(itemStack));
        return true;
    }

    public static int getLoadingTime(){
        return 15;
    }


    public void setLoadedAmmo(ItemStack watergun, int ammo) {
        NbtCompound nbtCompound = watergun.getOrCreateNbt();
        nbtCompound.putInt("LoadedAmmo", ammo);
    }

    public static int getLoadAmmo(ItemStack itemStack){
        NbtCompound nbtCompound = itemStack.getOrCreateNbt();
        if(nbtCompound.contains("LoadedAmmo")){
            return nbtCompound.getInt("LoadedAmmo");
        }
        return 0;
    }

    public static int getMaxAmmo(ItemStack itemStack){
        int a = EnchantmentHelper.getLevel(ModEnchantment.FLUID_CAPACITY,itemStack);
        int b = max_ammo;
        if(a > 0){
            b = b + (int) (max_ammo *(0.5 * a));
        }
        return b;
    }

    public void setLoaded(ItemStack watergun,boolean bl) {
        NbtCompound nbtCompound = watergun.getOrCreateNbt();
        nbtCompound.putBoolean("isLoaded",bl);
    }

    public static boolean isLoaded(ItemStack watergun) {
        NbtCompound nbtCompound = watergun.getOrCreateNbt();
        return nbtCompound != null && nbtCompound.getBoolean("isLoaded");
    }

    public static ItemStack getItemStack(DyeColor color) {
        return new ItemStack(get(color));
    }

    public static Item get(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return ModItems.WHITE_WATER_GUN;
        } else {
            switch(dyeColor) {
                case WHITE: default:
                    return ModItems.WHITE_WATER_GUN;
                case ORANGE:
                    return ModItems.ORANGE_WATER_GUN;
                case MAGENTA:
                    return ModItems.MAGENTA_WATER_GUN;
                case LIGHT_BLUE:
                    return ModItems.LIGHT_BLUE_WATER_GUN;
                case YELLOW:
                    return ModItems.YELLOW_WATER_GUN;
                case LIME:
                    return ModItems.LIME_WATER_GUN;
                case PINK:
                    return ModItems.PINK_WATER_GUN;
                case GRAY:
                    return ModItems.GRAY_WATER_GUN;
                case LIGHT_GRAY:
                    return ModItems.LIGHT_GRAY_WATER_GUN;
                case CYAN:
                    return ModItems.CYAN_WATER_GUN;
                case PURPLE:
                    return ModItems.PURPLE_WATER_GUN;
                case BLUE:
                    return ModItems.BLUE_WATER_GUN;
                case BROWN:
                    return ModItems.BROWN_WATER_GUN;
                case GREEN:
                    return ModItems.GREEN_WATER_GUN;
                case RED:
                    return ModItems.RED_WATER_GUN;
                case BLACK:
                    return ModItems.BLACK_WATER_GUN;
            }
        }
    }

}
