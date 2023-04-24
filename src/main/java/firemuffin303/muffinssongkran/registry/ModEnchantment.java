package firemuffin303.muffinssongkran.registry;

import firemuffin303.muffinssongkran.Muffinssongkran;
import firemuffin303.muffinssongkran.common.enchantment.FluidCapacityEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantment {
    public static final Enchantment FLUID_CAPACITY = register("fluid_capacity",new FluidCapacityEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));

    public static void init(){}

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Muffinssongkran.MODID,name), enchantment);
    }
}
