package net.sorenon.oh_nuts;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class DoughBlock extends Block {
	public DoughBlock() {
		super(Properties.of(Material.CLAY).destroyTime(1).sound(SoundType.HONEY_BLOCK));
	}
}
