package net.sorenon.cake_world;

import net.sorenon.cake_world.fake_player.FakePlayer;

public interface ServerPlayerExt {

	void setFakePlayer(FakePlayer fakePlayer);

	FakePlayer getFakePlayer();
}
