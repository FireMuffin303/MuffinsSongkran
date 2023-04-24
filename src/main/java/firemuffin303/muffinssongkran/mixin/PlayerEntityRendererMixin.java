package firemuffin303.muffinssongkran.mixin;

import firemuffin303.muffinssongkran.common.item.WaterGunItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method ="getArmPose",at = @At("TAIL"), cancellable = true)
    private static void muffinssongkran$ArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir){
        ItemStack itemStack = player.getStackInHand(hand);
        if(!itemStack.isEmpty()){
            if(!player.handSwinging && itemStack.getItem() instanceof WaterGunItem && WaterGunItem.isLoaded(itemStack)){
                cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
            }
        }

    }
}
