package firemuffin303.muffinssongkran.client;

import firemuffin303.muffinssongkran.Muffinssongkran;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final ModelIdentifier model2d;
    private final ModelIdentifier model3d;

    public ModItemRenderer(Identifier model2d, Identifier model3d){
        this.model2d = new ModelIdentifier(model2d,"") ;
        this.model3d = new ModelIdentifier(model3d,"") ;
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(!stack.isEmpty()){
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            matrices.push();
            boolean gui = mode == ModelTransformationMode.GUI || mode == ModelTransformationMode.GROUND || mode == ModelTransformationMode.FIXED;
            BakedModel model;
            if(gui){
                model = itemRenderer.getModels().getModelManager().getModel(model2d);
            }else{
                model = itemRenderer.getModels().getModelManager().getModel(model3d);
            }
            itemRenderer.renderItem(stack,mode,false,matrices,vertexConsumers,light,overlay,model);
            matrices.pop();

        }
    }
}
