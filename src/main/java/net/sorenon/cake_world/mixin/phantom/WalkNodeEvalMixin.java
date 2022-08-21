package net.sorenon.cake_world.mixin.phantom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.sorenon.cake_world.CakeWorldMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.sorenon.cake_world.CakeWorldMod.isLayer;

@Mixin(WalkNodeEvaluator.class)
public abstract class WalkNodeEvalMixin {

	@Shadow
	protected static BlockPathTypes getBlockPathTypeRaw(BlockGetter world, BlockPos pos) {
		throw new UnsupportedOperationException();
	}

	@Inject(method = "getBlockPathTypeRaw", at = @At("HEAD"), cancellable = true)
	private static void override(BlockGetter world, BlockPos pos, CallbackInfoReturnable<BlockPathTypes> cir) {
		var level = getServerLevel(world);
		if (level == null) return;

		if (isLayer(level.dimension()) && world.getBlockState(pos).is(CakeWorldMod.SUPER_BLOCK)) {
			cir.setReturnValue(getBlockPathTypeRaw(level.getServer().overworld(), pos));
		}
	}

	@Unique
	private static ServerLevel getServerLevel(BlockGetter world) {
		if (world instanceof ServerLevel level) {
			return level;
		} else if (world instanceof PathNavigationRegionAcc pathNavigationRegion) {
			return (ServerLevel) pathNavigationRegion.getLevel();
		} else if (world instanceof LevelChunk chunk) {
			return (ServerLevel) chunk.getLevel();
		}
		return null;
	}
}
