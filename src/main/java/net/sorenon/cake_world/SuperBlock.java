package net.sorenon.cake_world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class SuperBlock extends Block {
	public SuperBlock() {
		super(Properties.of(Material.AMETHYST).sound(SoundType.AMETHYST).strength(20));
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

	@Override
	public boolean hasDynamicShape() {
		return true;
	}
}
