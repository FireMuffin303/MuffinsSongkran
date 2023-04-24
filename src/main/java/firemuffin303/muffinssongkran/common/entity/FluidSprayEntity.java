package firemuffin303.muffinssongkran.common.entity;

import com.google.common.collect.Sets;
import firemuffin303.muffinssongkran.registry.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class FluidSprayEntity extends ProjectileEntity {

    private static final TrackedData<Integer> COLOR;

    private Potion potion;
    private final Set<StatusEffectInstance> effects;
    private boolean colorSet;

    public FluidSprayEntity(World world) {
        super(ModEntities.FLUID_PROJECTILE, world);
        this.effects = Sets.newHashSet();
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(COLOR, -1);
    }

    public void initPotionFromStack(ItemStack stack) {
        this.potion = PotionUtil.getPotion(stack);
        Collection<StatusEffectInstance> collection = PotionUtil.getCustomPotionEffects(stack);
        if (!collection.isEmpty()) {
            for (StatusEffectInstance statusEffectInstance : collection) {
                this.effects.add(new StatusEffectInstance(statusEffectInstance));
            }
        }
        int i = getCustomPotionColor(stack);
        if (i == -1) {
            this.initColor();
        } else {
            this.setColor(i);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(world.isClient){
            int m = this.getColor();
            float n = (float)(m >> 16 & 0xFF) / 255.0f;
            float o = (float)(m >> 8 & 0xFF) / 255.0f;
            float p = (float)(m & 0xFF) / 255.0f;
            for (int i = 0 ; i < 2 ;i++){
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT,this.getX(),this.getY(),this.getZ(),n,o,p);
            }
        }
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        boolean bl = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            BlockState blockState = this.world.getBlockState(blockPos);
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockPos);
                bl = true;
            } else if (blockState.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.world, blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
                }

                bl = true;
            }
        }

        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.onCollision(hitResult);
        }

        this.checkBlockCollision();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float h;
        if (this.isTouchingWater()) {
            this.discard();
        }
        h = 0.99F;


        this.setVelocity(vec3d.multiply(h));
        if (!this.hasNoGravity()) {
            Vec3d vec3d2 = this.getVelocity();
            this.setVelocity(vec3d2.x, vec3d2.y - 0.03f, vec3d2.z);
        }

        this.setPosition(d, e, f);
    }

    public static int getCustomPotionColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null && nbtCompound.contains("CustomPotionColor", 99) ? nbtCompound.getInt("CustomPotionColor") : -1;
    }

    private void initColor() {
        this.colorSet = false;
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.dataTracker.set(COLOR, -1);
        } else {
            this.dataTracker.set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
        }

    }


    public void addEffect(StatusEffectInstance effect) {
        this.effects.add(effect);
        this.getDataTracker().set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
    }

    private void setColor(int color) {
        this.colorSet = true;
        this.dataTracker.set(COLOR, color);
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.potion != Potions.EMPTY) {
            nbt.putString("Potion", Registries.POTION.getId(this.potion).toString());
        }

        if (this.colorSet) {
            nbt.putInt("Color", this.getColor());
        }

        if (!this.effects.isEmpty()) {
            NbtList nbtList = new NbtList();

            for (StatusEffectInstance statusEffectInstance : this.effects) {
                nbtList.add(statusEffectInstance.writeNbt(new NbtCompound()));
            }

            nbt.put("CustomPotionEffects", nbtList);
        }

    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Potion", 8)) {
            this.potion = PotionUtil.getPotion(nbt);
        }

        for (StatusEffectInstance statusEffectInstance : PotionUtil.getCustomPotionEffects(nbt)) {
            this.addEffect(statusEffectInstance);
        }

        if (nbt.contains("Color", 99)) {
            this.setColor(nbt.getInt("Color"));
        } else {
            this.initColor();
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(!world.isClient){
            Entity entity = entityHitResult.getEntity();
            if(entity instanceof LivingEntity living){
                Iterator<StatusEffectInstance> var3 = this.potion.getEffects().iterator();
                StatusEffectInstance statusEffectInstance;
                while(var3.hasNext()) {
                    statusEffectInstance = var3.next();
                    living.addStatusEffect(new StatusEffectInstance(statusEffectInstance.getEffectType(), Math.max(statusEffectInstance.mapDuration((i) -> i / 8), 1), statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles()), getEffectCause());
                }

                if (!this.effects.isEmpty()) {
                    var3 = this.effects.iterator();

                    while(var3.hasNext()) {
                        statusEffectInstance = var3.next();
                        living.addStatusEffect(statusEffectInstance, getEffectCause());
                    }

                }

            }
            if(this.potion == Potions.WATER){
                if(entity.isOnFire()){
                    entity.extinguishWithSound();
                }

                if(entity instanceof AxolotlEntity axolotlEntity){
                    axolotlEntity.hydrateFromPotion();
                }
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.discard();
    }

    static{
        COLOR = DataTracker.registerData(FluidSprayEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
