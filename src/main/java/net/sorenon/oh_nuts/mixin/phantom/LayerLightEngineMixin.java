package net.sorenon.oh_nuts.mixin.phantom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.sorenon.oh_nuts.OhNutsMod;
import net.sorenon.oh_nuts.client.OhNutsClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
