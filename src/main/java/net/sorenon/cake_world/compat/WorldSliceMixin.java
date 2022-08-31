package net.sorenon.cake_world.compat;

import me.jellysquid.mods.sodium.client.world.WorldSlice;
import me.jellysquid.mods.sodium.client.world.cloned.ClonedChunkSection;
import me.jellysquid.mods.sodium.client.world.cloned.palette.ClonedPalette;
import me.jellysquid.mods.sodium.client.world.cloned.palette.ClonedPalleteArray;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.sorenon.cake_world.CakeWorldMod;
import net.sorenon.cake_world.client.CakeWorldClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.jellysquid.mods.sodium.client.world.WorldSlice.getLocalBlockIndex;

@Mixin(WorldSlice.class)
public class WorldSliceMixin {

	@Shadow
	@Final
	private Level world;

	@Inject(method = "unpackBlockData([Lnet/minecraft/world/level/block/state/BlockState;Lme/jellysquid/mods/sodium/client/world/cloned/ClonedChunkSection;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;)V", cancellable = true, at = @At("HEAD"))
	void overwriteBlockStates(BlockState[] states, ClonedChunkSection section, BoundingBox box, CallbackInfo ci) {
		if (CakeWorldMod.isLayer(this.world.dimension())) {
			SimpleBitStorage intArray = section.getBlockData();
			ClonedPalette<BlockState> palette = section.getBlockPalette();

//			if (palette instanceof ClonedPalleteArray<BlockState> palleteArray
//			&& palleteArray.) {
//
//			}

			SectionPos pos = section.getPosition();
			int minBlockX = Math.max(box.minX(), pos.minBlockX());
			int maxBlockX = Math.min(box.maxX(), pos.maxBlockX());
			int minBlockY = Math.max(box.minY(), pos.minBlockY());
			int maxBlockY = Math.min(box.maxY(), pos.maxBlockY());
			int minBlockZ = Math.max(box.minZ(), pos.minBlockZ());
			int maxBlockZ = Math.min(box.maxZ(), pos.maxBlockZ());

			for(int y = minBlockY; y <= maxBlockY; ++y) {
				for(int z = minBlockZ; z <= maxBlockZ; ++z) {
					for(int x = minBlockX; x <= maxBlockX; ++x) {
						int blockIdx = getLocalBlockIndex(x & 15, y & 15, z & 15);
						int value = intArray.get(blockIdx);
						var state = palette.get(value);

						if (state.is(CakeWorldMod.SUPER_BLOCK)) {
							states[blockIdx] = CakeWorldClient.rootWorld.getBlockState(new BlockPos(x, y, z));
						} else {
							states[blockIdx] = state;
						}
					}
				}
			}
			ci.cancel();
		}
	}
}
