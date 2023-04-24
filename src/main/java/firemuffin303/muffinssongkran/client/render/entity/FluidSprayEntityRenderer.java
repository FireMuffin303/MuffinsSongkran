package firemuffin303.muffinssongkran.client.render.entity;

import firemuffin303.muffinssongkran.Muffinssongkran;
import firemuffin303.muffinssongkran.common.entity.FluidSprayEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class FluidSprayEntityRenderer extends EntityRenderer<FluidSprayEntity> {
    private static final Identifier TEXTURE = new Identifier(Muffinssongkran.MODID,"textures/particle/fluidspray.png");
    public FluidSprayEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FluidSprayEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        matrices.multiply(this.dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.scale(0.8f,0.8f,0.8f);
        float red, green, blue;


        float re = (entity.getColor() >> 16 & 255) / 255.0f;
        float g = (entity.getColor() >> 8 & 255) / 255.0f;
        float b = (entity.getColor() & 255) / 255.0f;
        red =  re >= 1 ? 1 : re;
        green = g >= 1 ? 1 : g;
        blue = b >= 1 ? 1 : b;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getItemEntityTranslucentCull(TEXTURE));

        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        vertex(vertexConsumer,matrix4f,matrix3f,0F, 0F,red,green,blue,0,1,light);
        vertex(vertexConsumer,matrix4f,matrix3f,1F, 0F,red,green,blue,1,1,light);
        vertex(vertexConsumer,matrix4f,matrix3f,1F, 1F,red,green,blue,1,0,light);
        vertex(vertexConsumer,matrix4f,matrix3f,0F, 1F,red,green,blue,0,0,light);
        matrices.pop();

    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, float red, float green, float blue, float u, float v, int light) {
        vertexConsumer.vertex(positionMatrix, x - 0.5F, y-0.25F, 0.0F).color(Math.round(red*255), Math.round(green*255), Math.round(blue*255), 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }

    @Override
    public Identifier getTexture(FluidSprayEntity entity) {
        return null;
    }
}
