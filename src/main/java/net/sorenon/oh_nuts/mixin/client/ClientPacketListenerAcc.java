package net.sorenon.oh_nuts.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPacketListener.class)
public interface ClientPacketListenerAcc {

	@Accessor
	int getServerChunkRadius();

	@Accessor
	int getServerSimulationDistance();

	@Accessor
	void setLevel(ClientLevel level);
}
