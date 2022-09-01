package net.sorenon.cake_world.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.sorenon.cake_world.CakeWorldMod;
import net.sorenon.cake_world.ServerPlayerExt;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

	@Inject(method = "remove", at = @At("HEAD"))
	void onRemove(Entity.RemovalReason reason, CallbackInfo ci) {
		if (this instanceof ServerPlayerExt ext && ext.getFakePlayer() != null) {
			ext.getFakePlayer().remove(Entity.RemovalReason.DISCARDED);
			ext.setFakePlayer(null);
			ServerPlayNetworking.send((ServerPlayer)ext, CakeWorldMod.S2C_CLEANUP, PacketByteBufs.empty());
		}
	}
}
