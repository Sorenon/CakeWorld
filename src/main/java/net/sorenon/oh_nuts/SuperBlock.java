package net.sorenon.oh_nuts;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class SuperBlock extends Block {
	public SuperBlock() {
		super(Properties.of(Material.BARRIER));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state,
										  BlockGetter world, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return 0;
	}
}