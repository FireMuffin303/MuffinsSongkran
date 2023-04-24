package firemuffin303.muffinssongkran.common.recipe;

import firemuffin303.muffinssongkran.common.item.WaterGunItem;
import firemuffin303.muffinssongkran.registry.ModRecipesSerializer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WaterGunColoringRecipe extends SpecialCraftingRecipe {
    public WaterGunColoringRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int i = 0;
        int j = 0;

        for(int k = 0; k < craftingInventory.size(); ++k) {
            ItemStack itemStack = craftingInventory.getStack(k);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof WaterGunItem) {
                    ++i;
                } else {
                    if (!(itemStack.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    ++j;
                }

                if (j > 1 || i > 1) {
                    return false;
                }
            }
        }

        return i == 1 && j == 1;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory, DynamicRegistryManager registryManager) {
        ItemStack itemStack = ItemStack.EMPTY;
        DyeItem dyeItem = (DyeItem) Items.WHITE_DYE;

        for(int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof WaterGunItem) {
                    itemStack = itemStack2;
                } else if (itemStack2.getItem() instanceof DyeItem) {
                    dyeItem = (DyeItem)itemStack2.getItem();
                }
            }
        }

        ItemStack itemStack3 = WaterGunItem.getItemStack(dyeItem.getColor());
        if (itemStack.hasNbt()) {
            itemStack3.setNbt(itemStack.getNbt().copy());
        }

        return itemStack3;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipesSerializer.WATER_GUN_COLORING;
    }
}
