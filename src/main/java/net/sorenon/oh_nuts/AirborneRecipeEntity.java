package net.sorenon.oh_nuts;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class AirborneRecipeEntity extends Entity {

	public static final EntityDataAccessor<Optional<BlockState>> RENDER_BLOCK_STATE = SynchedEntityData.defineId(AirborneRecipeEntity.class, EntityDataSerializers.BLOCK_STATE);

	public float xAngularVel = 0;
	public float yAngularVel = 0;

	public AirborneRecipeEntity(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(RENDER_BLOCK_STATE, Optional.of(OhNutsMod.DOUGH_BLOCK.defaultBlockState()));
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
		vel = vel.multiply(0.99F, 0.98F, 0.99F);
		vel = vel.add(0, -0.06, 0);
		this.setDeltaMovement(vel);

		this.move(MoverType.SELF, this.getDeltaMovement());

		if (this.onGround && this.level instanceof ServerLevel serverLevel) {
			this.remove(RemovalReason.KILLED);
			serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 4, 0.0, 0.0, 0.0, 0.1);
			this.playSound(SoundEvents.GENERIC_EXPLODE, 10.0f, 0.95f);

			this.getEntityData().get(RENDER_BLOCK_STATE).ifPresent(blockState -> {
				if (blockState.is(OhNutsMod.DOUGHNUT_BLOCK)) {
					level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(OhNutsMod.DOUGHNUT_ITEM, 12)));
				}
			});
		}
	}

	public void onShot() {

	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}
}
