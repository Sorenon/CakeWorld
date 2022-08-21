package net.sorenon.cake_world.client;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.sorenon.cake_world.WrappedPacket;
import net.sorenon.cake_world.fake_player.NullConnection;
import net.sorenon.cake_world.mixin.client.ClientPacketListenerAcc;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.io.IOException;
import java.util.UUID;

public class CakeWorldClient implements ClientModInitializer {

	public static boolean isRendering = false;

	public static ClientLevel rootWorld = null;
	private static ClientPacketListener rootPl = null;


	private static boolean needsCleanup = false;
	public static ResourceLocation S2C_WRAPPED_PACKET = new ResourceLocation("layer", "wrapped");
	public static ResourceLocation S2C_CLEANUP = new ResourceLocation("layer", "cleanup");

	public static ImmutableSet<Class<?>> BLACKLIST = ImmutableSet.of(
			ClientboundCustomPayloadPacket.class
	);

	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientPlayNetworking.registerGlobalReceiver(S2C_CLEANUP, (client, handler, buf, responseSender) -> {
			client.execute(() -> {
				needsCleanup = true;
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(S2C_WRAPPED_PACKET, (client, handler, buf, responseSender) -> {
			var packet = WrappedPacket.decode(buf);
//			System.out.println(packet);

			if (BLACKLIST.contains(packet.getClass())) {
				return;
			}

			client.execute(() -> {
				if (rootWorld == null) {
					initFake((ClientPacketListenerAcc) handler, client);
				} else if (needsCleanup) {
					try {
						rootWorld.close();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						rootWorld = null;
						rootPl = null;
					}
					initFake((ClientPacketListenerAcc) handler, client);
				}
				needsCleanup = false;

				var player = client.player;
				var level = client.level;
				client.player = null;
				client.level = rootWorld;

				genericsFtw(packet, rootPl);

				client.player = player;
				client.level = level;
			});
		});
	}

	private static <T extends PacketListener> void genericsFtw(Packet<T> packet, PacketListener listener) {
		packet.handle((T)listener);
	}

	private void initFake(ClientPacketListenerAcc handlerAcc, Minecraft client) {
		rootPl = new ClientPacketListener(
				client,
				null,
				new NullConnection(PacketFlow.SERVERBOUND),
				new GameProfile(UUID.randomUUID(), "cl-fake-player"),
				null
		);

		rootWorld = new ClientLevel(
				rootPl,
				new ClientLevel.ClientLevelData(Difficulty.NORMAL, false, false),
				Level.OVERWORLD,
				BuiltinRegistries.DIMENSION_TYPE.getHolderOrThrow(BuiltinDimensionTypes.OVERWORLD),
				handlerAcc.getServerChunkRadius(),
				handlerAcc.getServerSimulationDistance(),
				client::getProfiler,
				client.levelRenderer,
				false,
				0 //packet.getSeed()
		);

		((ClientPacketListenerAcc)rootPl).setLevel(rootWorld);
	}
}
