package net.sorenon.cake_world;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;

public class WrappedPacket {

	public static Packet<?> decode(FriendlyByteBuf buf) {
		int id = buf.readVarInt();
		return ConnectionProtocol.PLAY.createPacket(PacketFlow.CLIENTBOUND, id, buf);
	}

	public static void encode(Packet<?> packet, FriendlyByteBuf buf) {
		var id = ConnectionProtocol.PLAY.getPacketId(PacketFlow.CLIENTBOUND, packet);
		assert id != null;
		buf.writeVarInt(id);
		packet.write(buf);
	}
}
