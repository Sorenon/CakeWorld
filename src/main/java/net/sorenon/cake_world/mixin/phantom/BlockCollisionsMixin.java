package net.sorenon.cake_world.mixin.phantom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.sorenon.cake_world.CakeWorldMod;
import net.sorenon.cake_world.client.CakeWorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.sorenon.cake_world.CakeWorldMod.isLayer;

@Mixin(BlockCollisions.class)
public class BlockCollisionsMixin {

	/**
	 * Easiest way to overwrite the `hasLargeCollisionShape` call
	 */
	@Redirect(method = "computeNext()Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BlockGetter;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
	BlockState swapBlockState(BlockGetter instance, BlockPos blockPos) {
		var blockState = instance.getBlockState(blockPos);
		if (hasParent(instance, blockState)) {
			return getRootLevel(instance).getBlockState(blockPos);
		}
		return blockState;
	}

	@Unique
	private boolean hasParent(BlockGetter blockGetter, BlockState blockState) {
		if (!blockState.is(CakeWorldMod.SUPER_BLOCK)) return false;
		var level = getLevel(blockGetter);
		if (level == null) return false;
		return isLayer(level.dimension());
	}

	@Unique
	private Level getLevel(BlockGetter blockGetter) {
		if (blockGetter instanceof Level level) {
			return level;
		} else if (blockGetter instanceof PathNavigationRegionAcc pathNavigationRegion) {
			return pathNavigationRegion.getLevel();
		} else if (blockGetter instanceof LevelChunk chunk) {
			return chunk.getLevel();
		}
		return null;
	}

	@Unique
	private Level getRootLevel(BlockGetter level) {
		if (getLevel(level) instanceof ServerLevel serverLevel) {
			return serverLevel.getServer().overworld();
		} else {
			return CakeWorldClient.rootWorld;
		}
	}
}
