package net.sorenon.cake_world.mixin;

import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.sorenon.cake_world.CakeWorldMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientboundCustomPayloadPacket.class)
public class ClientboundCustomPayloadPacketMixin {

	@Shadow
	@Final
	private ResourceLocation identifier;

	@ModifyConstant(method = "<init>*",constant = @Constant(intValue = 1048576))
	private int shutUp(int i) {
		if (this.identifier.equals(CakeWorldMod.S2C_WRAPPED_PACKET)) {
			return Integer.MAX_VALUE;
		} else {
			return i;
		}
	}
}
