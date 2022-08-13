package net.sorenon.oh_nuts;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

public class Cannon extends Block {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public Cannon() {
		super(Properties.of(Material.HEAVY_METAL).strength(4));
		this.registerDefaultState(
				this.stateDefinition.any().setValue(POWERED, Boolean.FALSE)
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

	public void neighborChanged(BlockState state,
								Level world,
								BlockPos pos,
								Block block,
								BlockPos fromPos,
								boolean notify) {
		boolean bl = world.hasNeighborSignal(pos);
		if (bl != state.getValue(POWERED)) {
			if (!world.isClientSide && bl) {
				var random = world.getRandom();

				world.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, SoundSource.BLOCKS, 10, 0.7F + random.nextFloat() * 0.1F);
				world.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 0.2f, 1.2f);
				var entity = OhNutsMod.SLAB_RECIPE_ENTITY_TYPE.create(world);
				entity.setDeltaMovement((random.nextFloat() - 0.5) * 0.5, 2, (random.nextFloat() - 0.5) * 0.5);
				entity.setPos(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5f);
				world.addFreshEntity(entity);
			}

			world.setBlock(pos, state.setValue(POWERED, bl), 3);
		}
	}
}
