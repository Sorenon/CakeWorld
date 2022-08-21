package net.sorenon.cake_world.mixin.phantom;

import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LayerLightEngine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerLightEngine.class)
public class LayerLightEngineMixin {
	@Shadow
	@Final
	protected LightChunkGetter chunkSource;

//	@Redirect(method = "getStateAndOpacity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BlockGetter;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
//	BlockState getBlockState(BlockGetter instance, BlockPos blockPos) {
//		var state = instance.getBlockState(blockPos);
//		if (state.is(OhNutsMod.SUPER_BLOCK) && instance instanceof LevelChunk levelChunk && levelChunk.getLevel().dimension() == OhNutsMod.LAYER) {
//			if (levelChunk.getLevel() instanceof ServerLevel serverLevel) {
//				return serverLevel.getServer().overworld().getBlockState(blockPos);
//			} else {
//				return OhNutsClient.rootWorld.getBlockState(blockPos);
//			}
//
//		} else {
//			return state;
//		}
//	}
}
