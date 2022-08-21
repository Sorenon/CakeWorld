package net.sorenon.oh_nuts.mixin.client;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.sorenon.oh_nuts.client.OhNutsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRenderDispatcher.RenderChunk.class)
public class RenderChunkMixin {

//	@Inject(method = "doesChunkExistAt", at = @At("RETURN"), cancellable = true)
//	void checkRootWorld(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
//		if (cir.getReturnValue()) {
//			var rootLevel = OhNutsClient.rootWorld;
//			if (rootLevel == null) return;
//			cir.setReturnValue(rootLevel.getChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()), ChunkStatus.FULL, false) != null);
//		}
//	}
}
