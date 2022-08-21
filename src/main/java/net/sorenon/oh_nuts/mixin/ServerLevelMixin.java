package net.sorenon.oh_nuts.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.sorenon.oh_nuts.OhNutsMod;
import net.sorenon.oh_nuts.fake_player.FakeConnection;
import net.sorenon.oh_nuts.fake_player.FakePlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {

	@Shadow
	@Final
	private MinecraftServer server;

	protected ServerLevelMixin(WritableLevelData writableLevelData,
							   ResourceKey<Level> resourceKey,
							   Holder<DimensionType> holder,
							   Supplier<ProfilerFiller> supplier,
							   boolean bl,
							   boolean bl2, long l, int i) {
		super(writableLevelData, resourceKey, holder, supplier, bl, bl2, l, i);
	}

	@Unique
	private static boolean gjgjjg = false;

	@Inject(method = "addPlayer", at = @At("HEAD"))
	void onAddPlayer(ServerPlayer player, CallbackInfo ci) {
		if (this.dimension() == OhNutsMod.LAYER && !gjgjjg) {
			gjgjjg = true;
			var fakeplayer = new FakePlayer(this.server, this.server.overworld(), player);
			new ServerGamePacketListenerImpl(this.server, new FakeConnection(player.connection.connection), fakeplayer);


			player.server.overworld().addFreshEntity(fakeplayer);
		}
	}
}
