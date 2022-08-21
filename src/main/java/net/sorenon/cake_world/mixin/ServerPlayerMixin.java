package net.sorenon.cake_world.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.sorenon.cake_world.ServerPlayerExt;
import net.sorenon.cake_world.fake_player.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements ServerPlayerExt {

	@Unique
	private FakePlayer fakePlayer;

	@Override
	public void setFakePlayer(FakePlayer fakePlayer) {
		this.fakePlayer = fakePlayer;
	}

	@Override
	public FakePlayer getFakePlayer() {
		return fakePlayer;
	}
}
