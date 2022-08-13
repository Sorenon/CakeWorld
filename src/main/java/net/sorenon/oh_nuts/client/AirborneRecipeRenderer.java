package net.sorenon.oh_nuts.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.sorenon.oh_nuts.AirborneRecipeEntity;
import net.sorenon.oh_nuts.OhNutsMod;

public class AirborneRecipeRenderer extends EntityRenderer<AirborneRecipeEntity> {
	private final BlockRenderDispatcher dispatcher;

	public AirborneRecipeRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.dispatcher = context.getBlockRenderDispatcher();
	}

	public void render(AirborneRecipeEntity fallingBlockEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		var blockState = OhNutsMod.DOUGH_BLOCK.defaultBlockState();


		if (blockState.getRenderShape() == RenderShape.MODEL) {
			Level level = fallingBlockEntity.getLevel();
			if (blockState != level.getBlockState(fallingBlockEntity.blockPosition()) && blockState.getRenderShape() != RenderShape.INVISIBLE) {
				poseStack.pushPose();
				BlockPos blockPos = new BlockPos(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
				poseStack.translate(-0.5, 0.25, -0.5);


				poseStack.translate(0.5, 0.25, 0.5);

//				poseStack.mulPose(Vector3f.XP.rotation(fallingBlockEntity.tickCount / 2f));
//				poseStack.mulPose(Vector3f.YP.rotation(fallingBlockEntity.tickCount / 2f));

				poseStack.translate(-0.5, -0.25, -0.5);
				this.dispatcher
						.getModelRenderer()
						.tesselateBlock(
								level,
								this.dispatcher.getBlockModel(blockState),
								blockState,
								blockPos,
								poseStack,
								multiBufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(blockState)),
								false,
								RandomSource.create(),
								blockState.getSeed(BlockPos.ZERO),
								OverlayTexture.NO_OVERLAY
						);
				poseStack.popPose();
				super.render(fallingBlockEntity, f, g, poseStack, multiBufferSource, i);
			}
		}
	}

	public ResourceLocation getTextureLocation(AirborneRecipeEntity fallingBlockEntity) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
