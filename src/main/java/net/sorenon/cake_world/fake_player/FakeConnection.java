package net.sorenon.cake_world.fake_player;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.*;
import net.sorenon.cake_world.WrappedPacket;
import net.sorenon.cake_world.client.CakeWorldClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class FakeConnection extends Connection {

	private final Connection parent;
	public final ImmutableList<Class<?>> PACKETS = ImmutableList.of(ClientboundLevelChunkWithLightPacket.class,
			ClientboundSectionBlocksUpdatePacket.class,
			ClientboundBlockUpdatePacket.class,
			ClientboundLightUpdatePacket.class,
			ClientboundForgetLevelChunkPacket.class,
			ClientboundSetChunkCacheCenterPacket.class
	);

	public FakeConnection(Connection parent) {
		super(PacketFlow.CLIENTBOUND);
		this.parent = parent;
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void send(@NotNull Packet<?> packet, @Nullable PacketSendListener listener) {
		if (PACKETS.contains(packet.getClass())) {
			var buf = PacketByteBufs.create();
			WrappedPacket.encode(packet, buf);
			this.parent.send(ServerPlayNetworking.createS2CPacket(CakeWorldClient.S2C_WRAPPED_PACKET, buf), listener);
		}
	}

	@Override
	public void tick() {

	}
}
