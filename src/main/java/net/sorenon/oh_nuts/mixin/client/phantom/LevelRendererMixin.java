package net.sorenon.oh_nuts.mixin.client.phantom;

import net.minecraft.client.renderer.LevelRenderer;
import net.sorenon.oh_nuts.client.OhNutsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;pollLightUpdates()V"))
	void preLight(CallbackInfo ci) {
		OhNutsClient.isRendering = false;
	}

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;runUpdates(IZZ)I"))
	void postLight(CallbackInfo ci) {
		OhNutsClient.isRendering = true;
	}
}
