package firemuffin303.muffinssongkran.common.enchantment;

import firemuffin303.muffinssongkran.common.item.WaterGunItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class FluidCapacityEnchantment extends Enchantment {
    public FluidCapacityEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, EnchantmentTarget.CROSSBOW, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof WaterGunItem;
    }
}
