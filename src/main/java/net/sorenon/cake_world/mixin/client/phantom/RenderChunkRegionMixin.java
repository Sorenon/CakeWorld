package net.sorenon.cake_world.mixin.client.phantom;

import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.sorenon.cake_world.CakeWorldMod;
import net.sorenon.cake_world.client.CakeWorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderChunkRegion.class)
public abstract class RenderChunkRegionMixin {

	@Inject(at = @At("RETURN"), method = "getBlockState", cancellable = true)
	void getBlockState(BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
		if (CakeWorldClient.rootWorld != null && cir.getReturnValue().is(CakeWorldMod.SUPER_BLOCK)) {
			cir.setReturnValue(CakeWorldClient.rootWorld.getBlockState(pos));
		}
	}

//    TODO @Inject(at = @At("HEAD"), method = "getFluidState", cancellable = true)
//    void getFluidState(BlockPos pos, CallbackInfoReturnable<FluidState> cir) {
//		int i = SectionPos.blockToSectionCoord(pos.getX()) - this.centerX;
//		int j = SectionPos.blockToSectionCoord(pos.getZ()) - this.centerZ;
//		return this.chunks[i][j].getBlockState(pos);
//
//		if (OhNutsClient.rootWorld != null && getBlockState(pos).is(OhNutsMod.SUPER_BLOCK)) {
//			cir.setReturnValue(OhNutsClient.rootWorld.getBlockState(pos));
//		}
//    }
}
