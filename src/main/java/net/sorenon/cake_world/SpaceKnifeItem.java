package net.sorenon.cake_world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpaceKnifeItem extends Item {
	public SpaceKnifeItem() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world,
												  Player user,
												  InteractionHand hand) {
		if (world.dimension() == Level.OVERWORLD || world.dimension() == CakeWorldMod.LAYER) {
			if (world instanceof ServerLevel serverLevel) {
				var server = serverLevel.getServer();

				if (world.dimension() == Level.OVERWORLD) {
					((ServerPlayer)user).teleportTo(server.getLevel(CakeWorldMod.LAYER), user.getX(), user.getY(), user.getZ(), user.getYRot(), user.getXRot());
				} else {
					((ServerPlayer)user).teleportTo(server.overworld(), user.getX(), user.getY(), user.getZ(), user.getYRot(), user.getXRot());
				}
			}

			return InteractionResultHolder.success(user.getItemInHand(hand));
		}
		return super.use(world, user, hand);
	}
}
