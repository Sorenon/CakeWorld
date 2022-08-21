package net.sorenon.oh_nuts.fake_player;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.sorenon.oh_nuts.OhNutsMod;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FakePlayer extends ServerPlayer {

	private ServerPlayer parent;

	public FakePlayer(MinecraftServer minecraftServer, ServerLevel serverLevel, ServerPlayer parent) {
		super(minecraftServer, serverLevel, new GameProfile(UUID.randomUUID(), "fake-player-" + parent.getGameProfile().getName()), null);
		this.parent = parent;

		this.setPosRaw(parent.getX(), parent.getY(), parent.getZ());
		this.setRot(parent.getYRot(), parent.getXRot());
		this.setDeltaMovement(parent.getDeltaMovement());
		this.setBoundingBox(parent.getBoundingBox());
		this.noPhysics = true;
	}

	@Override
	public boolean isSpectator() {
		return true;
	}

	@Override
	public boolean isCreative() {
		return false;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	protected boolean canRide(Entity entity) {
		return false;
	}

	@Nullable
	@Override
	public Entity changeDimension(ServerLevel destination) {
		OhNutsMod.LOGGER.warn("attempted to teleport fake player");
		return this;
	}

	@Override
	public void tick() {
		this.absMoveTo(parent.getX(), parent.getY(), parent.getZ(), parent.getYRot(), parent.getXRot());
		this.setDeltaMovement(parent.getDeltaMovement());
		this.setBoundingBox(parent.getBoundingBox());
		this.noPhysics = true;

		this.getLevel().getChunkSource().move(this);
	}

	@Override
	public void kill() {

	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	@Override
	public void move(MoverType movementType, Vec3 movement) {

	}
}
