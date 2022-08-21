package net.sorenon.cake_world.fake_player;

import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NullConnection extends Connection {

	public NullConnection(PacketFlow packetFlow) {
		super(packetFlow);
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void send(@NotNull Packet<?> packet, @Nullable PacketSendListener listener) {

	}

	@Override
	public void tick() {

	}
}
