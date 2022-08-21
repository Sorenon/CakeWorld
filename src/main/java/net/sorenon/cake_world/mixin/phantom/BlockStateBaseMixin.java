package net.sorenon.cake_world.mixin.phantom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sorenon.cake_world.CakeWorldMod;
import net.sorenon.cake_world.client.CakeWorldClient;
import net.sorenon.cake_world.mixin.UseOnContextAcc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.sorenon.cake_world.CakeWorldMod.isLayer;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

	@Shadow
	public abstract boolean is(Block block);

	@Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("HEAD"), cancellable = true)
	void overrideCollisionShape(BlockGetter world,
								BlockPos pos,
								CallbackInfoReturnable<VoxelShape> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).getCollisionShape(level, pos));
		}
	}

	@Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("HEAD"), cancellable = true)
	void overrideCollisionShape(BlockGetter world,
								BlockPos pos,
								CollisionContext context,
								CallbackInfoReturnable<VoxelShape> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).getCollisionShape(level, pos, context));
		}
	}

	@Inject(method = "getVisualShape", at = @At("HEAD"), cancellable = true)
	void overrideVisualShape(BlockGetter world,
							 BlockPos pos,
							 CollisionContext context,
							 CallbackInfoReturnable<VoxelShape> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).getVisualShape(level, pos, context));
		}
	}

	@Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("HEAD"), cancellable = true)
	void overrideShape(BlockGetter world,
					   BlockPos pos,
					   CollisionContext context,
					   CallbackInfoReturnable<VoxelShape> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).getShape(level, pos, context));
		}
	}

	@Inject(method = "isCollisionShapeFullBlock", at = @At("HEAD"), cancellable = true)
	public void overrideIsCollisionShapeFullBlock(BlockGetter world,
												  BlockPos pos,
												  CallbackInfoReturnable<Boolean> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).isCollisionShapeFullBlock(level, pos));
		}
	}

	@Inject(method = "isViewBlocking", at = @At("HEAD"), cancellable = true)
	public void overrideIsViewBlocking(BlockGetter world,
									   BlockPos pos,
									   CallbackInfoReturnable<Boolean> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).isViewBlocking(level, pos));
		}
	}

	@Inject(method = "isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/SupportType;)Z", at = @At("HEAD"), cancellable = true)
	public void overrideIsFaceSturdy(BlockGetter world,
									 BlockPos pos,
									 Direction direction,
									 SupportType shapeType,
									 CallbackInfoReturnable<Boolean> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).isFaceSturdy(level, pos, direction, shapeType));
		}
	}

	@Inject(method = "canBeReplaced(Lnet/minecraft/world/item/context/BlockPlaceContext;)Z", at = @At("HEAD"), cancellable = true)
	public void overrideCanBeReplaced(BlockPlaceContext context, CallbackInfoReturnable<Boolean> cir) {
		var world = context.getLevel();
		var pos = context.getClickedPos();
		if (isFunny(world)) {
			var level = getLevel(world);
			var player = context.getPlayer();
			player.level = level;
			cir.setReturnValue(level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context.getPlayer(), context.getHand(), context.getItemInHand(), ((UseOnContextAcc) context).getHitResult())));
			player.level = world;
		}
	}

//	@Inject(method = "getLightBlock", at = @At("HEAD"), cancellable = true)
//	void overrideGetLightBlock(BlockGetter world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
//		if (isFunny(world)) {
////			var level = getLevel(world);
////			cir.setReturnValue(level.getBlockState(pos).getLightBlock(level, pos));
//		}
//	}

	@Inject(method = "isPathfindable", at = @At("HEAD"), cancellable = true)
	void overrideIsPathfindable(BlockGetter world,
								BlockPos pos,
								PathComputationType type,
								CallbackInfoReturnable<Boolean> cir) {
		if (isFunny(world)) {
			var level = getLevel(world);
			cir.setReturnValue(level.getBlockState(pos).isPathfindable(level, pos, type));
		}
	}

	@Unique
	private boolean isFunny(BlockGetter blockGetter) {
		return this.is(CakeWorldMod.SUPER_BLOCK) && blockGetter instanceof Level level && isLayer(level.dimension());
	}

	@Unique
	private Level getLevel(BlockGetter blockGetter) {
		if (blockGetter instanceof ServerLevel serverLevel) {
			return serverLevel.getServer().overworld();
		} else {
			return CakeWorldClient.rootWorld;
		}
	}
}
