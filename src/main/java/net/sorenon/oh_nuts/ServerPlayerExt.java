package net.sorenon.oh_nuts;

import net.sorenon.oh_nuts.fake_player.FakePlayer;

public interface ServerPlayerExt {

	void setFakePlayer(FakePlayer fakePlayer);

	FakePlayer getFakePlayer();
}
