package net.sorenon.oh_nuts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;

public class AirborneRecipeEntity extends Entity {
	public AirborneRecipeEntity(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {

	}

	@Override
	public void tick() {
		super.tick();


		var vel = this.getDeltaMovement();
		vel = vel.multiply(0.98, 1, 0.98);
		vel = vel.add(0, -0.01, 0);
		this.setDeltaMovement(vel);

		this.move(MoverType.SELF, this.getDeltaMovement());

		if (this.onGround && this.level instanceof ServerLevel) {
			this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}
}
