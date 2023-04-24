package firemuffin303.muffinssongkran.registry;

import firemuffin303.muffinssongkran.Muffinssongkran;
import firemuffin303.muffinssongkran.common.recipe.WaterGunColoringRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipesSerializer {
    public static final RecipeSerializer<WaterGunColoringRecipe> WATER_GUN_COLORING = register("crafting_special_waterguncoloring", new SpecialRecipeSerializer<>(WaterGunColoringRecipe::new));

    public static void init(){}

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return (S) Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Muffinssongkran.MODID,id), serializer);
    }




}
