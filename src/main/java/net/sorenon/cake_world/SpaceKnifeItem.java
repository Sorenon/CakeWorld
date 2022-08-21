package net.sorenon.cake_world;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static net.minecraft.resources.ResourceLocation.validPathChar;
import static net.sorenon.cake_world.CakeWorldMod.MODID;
import static net.sorenon.cake_world.CakeWorldMod.isLayer;

public class SpaceKnifeItem extends Item {
	public SpaceKnifeItem() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world,
												  Player user,
												  InteractionHand hand) {
		if (world.dimension() == Level.OVERWORLD || isLayer(world.dimension())) {
			var stack = user.getItemInHand(hand);
			if (world instanceof ServerLevel serverLevel) {
				var server = serverLevel.getServer();

				if (world.dimension() == Level.OVERWORLD) {
					var level = server.getLevel(getLevel(stack));
					if (level != null) {
						((ServerPlayer) user).teleportTo(level, user.getX(), user.getY(), user.getZ(), user.getYRot(), user.getXRot());
					}
				} else {
					((ServerPlayer) user).teleportTo(server.overworld(), user.getX(), user.getY(), user.getZ(), user.getYRot(), user.getXRot());
				}
			}

			return InteractionResultHolder.success(stack);
		}
		return super.use(world, user, hand);
	}

	public static ResourceKey<Level> getLevel(ItemStack stack) {
		var tag = stack.getTag();
		if (tag == null) return CakeWorldMod.LAYER;
		if (!tag.contains("layer", NbtType.STRING)) return CakeWorldMod.LAYER;
		var id = tag.getString("layer");

		if (!isValidPath(id)) {
			tag.remove("layer");
			return CakeWorldMod.LAYER;
		}

		return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(MODID, id));
	}

	private static boolean isValidPath(String path) {
		for(int i = 0; i < path.length(); ++i) {
			if (!validPathChar(path.charAt(i))) {
				return false;
			}
		}

		return true;
	}
}
