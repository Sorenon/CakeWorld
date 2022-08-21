package net.sorenon.oh_nuts.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.sorenon.oh_nuts.client.OhNutsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(method = "renderLevel", at = @At("HEAD"))
	void startRender(float f, long l, PoseStack poseStack, CallbackInfo ci) {
		OhNutsClient.isRendering = true;
	}

	@Inject(method = "renderLevel", at = @At("RETURN"))
	void endRender(float f, long l, PoseStack poseStack, CallbackInfo ci) {
		OhNutsClient.isRendering = false;
	}
}
