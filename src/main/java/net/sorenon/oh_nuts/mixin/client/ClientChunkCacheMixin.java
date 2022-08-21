package net.sorenon.oh_nuts.mixin.client;

import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.sorenon.oh_nuts.OhNutsMod;
import net.sorenon.oh_nuts.client.OhNutsClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientChunkCache.class)
public class ClientChunkCacheMixin {

	@Shadow
	@Final
	ClientLevel level;

	@Shadow
	@Final
	private LevelChunk emptyChunk;

//	@Inject(method = "getChunk(IILnet/minecraft/world/level/chunk/ChunkStatus;Z)Lnet/minecraft/world/level/chunk/LevelChunk;", at = @At("HEAD"), cancellable = true)
//	void checkLayerChunkReady(int x,
//							  int z,
//							  ChunkStatus chunkStatus,
//							  boolean create,
//							  CallbackInfoReturnable<LevelChunk> cir) {
//		if (this.level.dimension() != OhNutsMod.LAYER) return;
//
//		var root = OhNutsClient.rootWorld;
//		if (root == null || root.getChunk(x, z, ChunkStatus.FULL, false) == null) {
//			cir.setReturnValue(create ? this.emptyChunk : null);
//		}
//	}
}
