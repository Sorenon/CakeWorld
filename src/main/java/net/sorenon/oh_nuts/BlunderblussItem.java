package net.sorenon.oh_nuts;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Predicate;

public class BlunderblussItem extends ProjectileWeaponItem {
	public BlunderblussItem() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return stack -> stack.is(Items.SUGAR);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 0;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);

		var lookDir = user.getLookAngle();
		var startPos = user.getEyePosition();
		var endPos = startPos.add(lookDir.scale(40));
		var aabb = new AABB(startPos.x, startPos.y, startPos.z, endPos.x, endPos.y, endPos.z).inflate(2);

		double e = 40;
		Entity entity2 = null;
		Vec3 vec33 = null;
		for (Entity entity3 : level.getEntities(user, aabb, entity -> entity.getType() == (EntityType.ITEM))) {
			AABB aABB2 = entity3.getBoundingBox().inflate(entity3.getPickRadius() +
					0.25
			);
			Optional<Vec3> optional = aABB2.clip(startPos, endPos);
			if (aABB2.contains(startPos)) {
				if (e >= 0.0) {
					entity2 = entity3;
					vec33 = (Vec3) optional.orElse(startPos);
					e = 0.0;
				}
			} else if (optional.isPresent()) {
				if (entity3 instanceof ItemEntity itemEntity) {
					itemEntity.setItem(new ItemStack(Items.DIAMOND));
					itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(lookDir.scale(0.5f)));
				}

				Vec3 vec34 = (Vec3) optional.get();
				double f = startPos.distanceToSqr(vec34);
				if (f < e || e == 0.0) {
					if (entity3.getRootVehicle() == user.getRootVehicle()) {
						if (e == 0.0) {
							entity2 = entity3;
							vec33 = vec34;
						}
					} else {
						entity2 = entity3;
						vec33 = vec34;
						e = f;
					}
				}
			}


		}


		return InteractionResultHolder.consume(itemStack);

//		if (isCharged(itemStack)) {
//			performShooting(world, user, hand, itemStack, getShootingPower(itemStack), 1.0F);
//			setCharged(itemStack, false);
//			return InteractionResultHolder.consume(itemStack);
//		} else if (!user.getProjectile(itemStack).isEmpty()) {
//			if (!isCharged(itemStack)) {
////				this.startSoundPlayed = false;
////				this.midLoadSoundPlayed = false;
//				user.startUsingItem(hand);
//			}
//
//			return InteractionResultHolder.consume(itemStack);
//		} else {
//			return InteractionResultHolder.fail(itemStack);
//		}
	}

//	@Override
//	public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
//		int i = this.getUseDuration(stack) - remainingUseTicks;
//		float f = getPowerForTime(i, stack);
//		if (f >= 1.0F && !isCharged(stack) && true) {
//			setCharged(stack, true);
//			SoundSource soundSource = user instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
//			world.playSound(
//					null,
//					user.getX(),
//					user.getY(),
//					user.getZ(),
//					SoundEvents.CROSSBOW_LOADING_END,
//					soundSource,
//					1.0F,
//					1.0F / (world.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F
//			);
//		}
//
//	}
//
//	private static float getShootingPower(ItemStack stack) {
//		return containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
//	}
//
//	public static boolean isCharged(ItemStack stack) {
//		CompoundTag compoundTag = stack.getTag();
//		return compoundTag != null && compoundTag.getBoolean("Charged");
//	}
//
//	public static void setCharged(ItemStack stack, boolean charged) {
//		CompoundTag compoundTag = stack.getOrCreateTag();
//		compoundTag.putBoolean("Charged", charged);
//	}
//
//	private static List<ItemStack> getChargedProjectiles(ItemStack crossbow) {
//		List<ItemStack> list = Lists.<ItemStack>newArrayList();
//		CompoundTag compoundTag = crossbow.getTag();
//		if (compoundTag != null && compoundTag.contains("ChargedProjectiles", 9)) {
//			ListTag listTag = compoundTag.getList("ChargedProjectiles", 10);
//			if (listTag != null) {
//				for(int i = 0; i < listTag.size(); ++i) {
//					CompoundTag compoundTag2 = listTag.getCompound(i);
//					list.add(ItemStack.of(compoundTag2));
//				}
//			}
//		}
//
//		return list;
//	}
//
//	private static void clearChargedProjectiles(ItemStack crossbow) {
//		CompoundTag compoundTag = crossbow.getTag();
//		if (compoundTag != null) {
//			ListTag listTag = compoundTag.getList("ChargedProjectiles", 9);
//			listTag.clear();
//			compoundTag.put("ChargedProjectiles", listTag);
//		}
//
//	}
//
//	public static boolean containsChargedProjectile(ItemStack crossbow, Item projectile) {
//		return getChargedProjectiles(crossbow).stream().anyMatch(s -> s.is(projectile));
//	}
//
//	private static void shootProjectile(
//			Level world,
//			LivingEntity shooter,
//			InteractionHand hand,
//			ItemStack crossbow,
//			ItemStack projectile,
//			float soundPitch,
//			boolean creative,
//			float speed,
//			float divergence,
//			float simulated
//	) {
//		if (!world.isClientSide) {
//			boolean bl = projectile.is(Items.FIREWORK_ROCKET);
//			Projectile projectile2;
//			if (bl) {
//				projectile2 = new FireworkRocketEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
//			} else {
//				projectile2 = getArrow(world, shooter, crossbow, projectile);
//				if (creative || simulated != 0.0F) {
//					((AbstractArrow)projectile2).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
//				}
//			}
//
//			if (shooter instanceof CrossbowAttackMob crossbowAttackMob) {
//				crossbowAttackMob.shootCrossbowProjectile(crossbowAttackMob.getTarget(), crossbow, projectile2, simulated);
//			} else {
//				Vec3 vec3 = shooter.getUpVector(1.0F);
//				Quaternion quaternion = new Quaternion(new Vector3f(vec3), simulated, true);
//				Vec3 vec32 = shooter.getViewVector(1.0F);
//				Vector3f vector3f = new Vector3f(vec32);
//				vector3f.transform(quaternion);
//				projectile2.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), speed, divergence);
//			}
//
//			crossbow.hurtAndBreak(bl ? 3 : 1, shooter, e -> e.broadcastBreakEvent(hand));
//			world.addFreshEntity(projectile2);
//			world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, soundPitch);
//		}
//	}
//
//	private static AbstractArrow getArrow(Level world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
//		ArrowItem arrowItem = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
//		AbstractArrow abstractArrow = arrowItem.createArrow(world, arrow, entity);
//		if (entity instanceof Player) {
//			abstractArrow.setCritArrow(true);
//		}
//
//		abstractArrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
//		abstractArrow.setShotFromCrossbow(true);
//		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbow);
//		if (i > 0) {
//			abstractArrow.setPierceLevel((byte)i);
//		}
//
//		return abstractArrow;
//	}
//
//	public static void performShooting(Level world, LivingEntity entity, InteractionHand hand, ItemStack stack, float speed, float divergence) {
//		List<ItemStack> list = getChargedProjectiles(stack);
//		float[] fs = getShotPitches(entity.getRandom());
//
//		for(int i = 0; i < list.size(); ++i) {
//			ItemStack itemStack = (ItemStack)list.get(i);
//			boolean bl = entity instanceof Player && ((Player)entity).getAbilities().instabuild;
//			if (!itemStack.isEmpty()) {
//				if (i == 0) {
//					shootProjectile(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 0.0F);
//				} else if (i == 1) {
//					shootProjectile(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, -10.0F);
//				} else if (i == 2) {
//					shootProjectile(world, entity, hand, stack, itemStack, fs[i], bl, speed, divergence, 10.0F);
//				}
//			}
//		}
//
//		onCrossbowShot(world, entity, stack);
//	}
//
//	private static float[] getShotPitches(RandomSource random) {
//		boolean bl = random.nextBoolean();
//		return new float[]{1.0F, getRandomShotPitch(bl, random), getRandomShotPitch(!bl, random)};
//	}
//
//	private static float getRandomShotPitch(boolean flag, RandomSource random) {
//		float f = flag ? 0.63F : 0.43F;
//		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
//	}
//
//	private static void onCrossbowShot(Level world, LivingEntity entity, ItemStack stack) {
//		if (entity instanceof ServerPlayer serverPlayer) {
//			if (!world.isClientSide) {
//				CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayer, stack);
//			}
//
//			serverPlayer.awardStat(Stats.ITEM_USED.get(stack.getItem()));
//		}
//
//		clearChargedProjectiles(stack);
//	}
//
//	@Override
//	public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
////		if (!world.isClientSide) {
////			int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
////			SoundEvent soundEvent = this.getStartSound(i);
////			SoundEvent soundEvent2 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
////			float f = (float)(stack.getUseDuration() - remainingUseTicks) / (float)getChargeDuration(stack);
////			if (f < 0.2F) {
////				this.startSoundPlayed = false;
////				this.midLoadSoundPlayed = false;
////			}
////
////			if (f >= 0.2F && !this.startSoundPlayed) {
////				this.startSoundPlayed = true;
////				world.playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent, SoundSource.PLAYERS, 0.5F, 1.0F);
////			}
////
////			if (f >= 0.5F && soundEvent2 != null && !this.midLoadSoundPlayed) {
////				this.midLoadSoundPlayed = true;
////				world.playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent2, SoundSource.PLAYERS, 0.5F, 1.0F);
////			}
////		}
//
//	}
//
//	@Override
//	public int getUseDuration(ItemStack stack) {
//		return getChargeDuration(stack) + 3;
//	}
//
//	public static int getChargeDuration(ItemStack stack) {
//		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
//		return i == 0 ? 25 : 25 - 5 * i;
//	}
//
//	@Override
//	public UseAnim getUseAnimation(ItemStack stack) {
//		return UseAnim.CROSSBOW;
//	}
//
//	private SoundEvent getStartSound(int stage) {
//		switch(stage) {
//			case 1:
//				return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
//			case 2:
//				return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
//			case 3:
//				return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
//			default:
//				return SoundEvents.CROSSBOW_LOADING_START;
//		}
//	}
//
//	private static float getPowerForTime(int useTicks, ItemStack stack) {
//		float f = (float)useTicks / (float)getChargeDuration(stack);
//		if (f > 1.0F) {
//			f = 1.0F;
//		}
//
//		return f;
//	}
//
//	@Override
//	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
//		List<ItemStack> list = getChargedProjectiles(stack);
//		if (isCharged(stack) && !list.isEmpty()) {
//			ItemStack itemStack = (ItemStack)list.get(0);
//			tooltip.add(Component.translatable("item.minecraft.crossbow.projectile").append(" ").append(itemStack.getDisplayName()));
//			if (context.isAdvanced() && itemStack.is(Items.FIREWORK_ROCKET)) {
//				List<Component> list2 = Lists.<Component>newArrayList();
//				Items.FIREWORK_ROCKET.appendHoverText(itemStack, world, list2, context);
//				if (!list2.isEmpty()) {
//					for(int i = 0; i < list2.size(); ++i) {
//						list2.set(i, Component.literal("  ").append((Component)list2.get(i)).withStyle(ChatFormatting.GRAY));
//					}
//
//					tooltip.addAll(list2);
//				}
//			}
//
//		}
//	}
//
//	@Override
//	public boolean useOnRelease(ItemStack stack) {
//		return stack.is(this);
//	}
}
