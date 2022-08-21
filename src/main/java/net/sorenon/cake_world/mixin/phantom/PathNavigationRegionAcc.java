package net.sorenon.cake_world.mixin.phantom;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PathNavigationRegion.class)
public interface PathNavigationRegionAcc {

	@Accessor
	Level getLevel();
}
