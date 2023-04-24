package firemuffin303.muffinssongkran;

import firemuffin303.muffinssongkran.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Muffinssongkran implements ModInitializer {
    public static final String MODID = "muffinssongkran";
    public static final ItemGroup MUFFINSSONGKRAN = FabricItemGroup.builder(new Identifier(MODID,"main")).icon(() -> new ItemStack(ModItems.WHITE_WATER_GUN)).build();

    @Override
    public void onInitialize() {
        ModRecipesSerializer.init();
        ModEnchantment.init();
        ModEntities.init();
        ModSoundEvents.init();
        ModItems.init();
    }
}
