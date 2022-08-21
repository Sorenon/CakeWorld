package net.sorenon.oh_nuts.mixin;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.sorenon.oh_nuts.OhNutsMod;
import net.sorenon.oh_nuts.client.OhNutsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockGetter.class)
public interface BlockGetterMixin {

	@Inject(method = "getLightEmission", at = @At("HEAD"), cancellable = true)
	default void getEmission(BlockPos pos, CallbackInfoReturnable<Integer> cir){
		if (this instanceof LevelChunk levelChunk) {
			if (levelChunk.getLevel().dimension() != OhNutsMod.LAYER) return;
		} else if (this instanceof Level level) {
			if (level.dimension() != OhNutsMod.LAYER) return;
		} else {
			return;
		}
		cir.setReturnValue(OhNutsClient.rootWorld.getLightEmission(pos));
	}


}
