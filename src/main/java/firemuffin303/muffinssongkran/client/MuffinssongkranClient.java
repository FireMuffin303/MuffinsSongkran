package firemuffin303.muffinssongkran.client;

import firemuffin303.muffinssongkran.Muffinssongkran;
import firemuffin303.muffinssongkran.client.render.entity.FluidSprayEntityRenderer;
import firemuffin303.muffinssongkran.common.item.WaterGunItem;
import firemuffin303.muffinssongkran.registry.ModEntities;
import firemuffin303.muffinssongkran.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MuffinssongkranClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.FLUID_PROJECTILE, FluidSprayEntityRenderer::new);
        /*BuiltinItemRendererRegistry.INSTANCE.register(ModItems.WHITE_WATER_GUN.asItem(),new ModItemRenderer(
                new Identifier(Muffinssongkran.MODID,"white_water_gun_in_hand"), new Identifier(Muffinssongkran.MODID,"white_water_gun")));
            */
       /* for(Item item : ModItems.WATER_GUNS){
            Identifier identifier = Registries.ITEM.getId(item);

            BuiltinItemRendererRegistry.INSTANCE.register(item,((stack, mode, matrices, vertexConsumers, light, overlay) -> {
                if(!stack.isEmpty()){
                    if(mode == ModelTransformationMode.GUI || mode == ModelTransformationMode.GROUND || mode == ModelTransformationMode.FIXED){
                        matrices.pop();
                        matrices.push();
                        MinecraftClient mc = MinecraftClient.getInstance();
                        BakedModel bakedModel = mc.getBakedModelManager().getModel(new ModelIdentifier(identifier, "inventory"));
                        mc.getItemRenderer().renderItem(stack,mode,false,matrices,vertexConsumers,light,overlay, bakedModel);
                    }else{
                        matrices.push();
                        MinecraftClient mc = MinecraftClient.getInstance();
                        BakedModel bakedModel = mc.getBakedModelManager().getModel(new ModelIdentifier(new Identifier(identifier.getNamespace(),identifier.getPath()+"_in_hand"), "inventory"));
                        mc.getItemRenderer().renderItem(stack,mode,false,matrices,vertexConsumers,light,overlay, bakedModel);
                        matrices.pop();
                    }
                }

            }));

            ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> tintIndex == 1 ? PotionUtil.getColor(stack) : -1),item);
            FabricModelPredicateProviderRegistry.register(item,new Identifier(Muffinssongkran.MODID,"amount"),((stack, world, entity, seed) -> {
                return (float)(WaterGunItem.getLoadAmmo(stack) / WaterGunItem.getMaxAmmo(stack));
            }));
            FabricModelPredicateProviderRegistry.register(item,new Identifier(Muffinssongkran.MODID,"loaded"),((stack, world, entity, seed) -> {
                return WaterGunItem.isLoaded(stack) ? 1:0;
            }));
            ModelLoadingRegistry.INSTANCE.registerModelProvider(((manager, out) -> out.accept(new ModelIdentifier(new Identifier(identifier.getNamespace(),identifier.getPath()+"_in_hand"),"inventory"))));
        }*/

    }
}
