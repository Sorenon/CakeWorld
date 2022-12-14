package net.sorenon.cake_world.mixin;

import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.sorenon.cake_world.ServerPlayerExt;
import net.sorenon.cake_world.fake_player.FakePlayer;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin {

	@Shadow
	protected abstract void playerLoadedChunk(ServerPlayer player,
											  MutableObject<ClientboundLevelChunkWithLightPacket> mutableObject,
											  LevelChunk chunk);

	@Unique
	private static final ThreadLocal<Boolean> EVIL = ThreadLocal.withInitial(() -> true);

	@Inject(method = "playerLoadedChunk", at = @At("HEAD"), cancellable = true)
	void onStartTracking(ServerPlayer player,
						 MutableObject<ClientboundLevelChunkWithLightPacket> mutableObject,
						 LevelChunk chunk, CallbackInfo ci) {
		if (player instanceof FakePlayer && EVIL.get()) ci.cancel();
		if (!(player instanceof FakePlayer)) {
			ServerPlayerExt ext = (ServerPlayerExt) player;
			var fakePlayer = ext.getFakePlayer();
			if (fakePlayer == null) return;

			EVIL.set(false);
			MutableObject<ClientboundLevelChunkWithLightPacket> mutableObject2 = new MutableObject<>();
			((ChunkMapMixin)(Object)((ServerChunkCache)fakePlayer.level.getChunkSource()).chunkMap).playerLoadedChunk(fakePlayer, mutableObject2, fakePlayer.level.getChunk(chunk.getPos().x, chunk.getPos().z));
			EVIL.set(true);
		}

	}
}
