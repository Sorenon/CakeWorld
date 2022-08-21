package net.sorenon.cake_world.mixin.phantom;


import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockGetter.class)
public interface BlockGetterMixin {

//	@Inject(method = "getLightEmission", at = @At("HEAD"), cancellable = true)
//	default void getEmission(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
//		Level level;
//		if (this instanceof LevelChunk levelChunk) {
//			level = levelChunk.getLevel();
//		} else if (this instanceof Level) {
//			level = (Level) this;
//		} else {
//			return;
//		}
//
//		if (level.dimension() != OhNutsMod.LAYER) return;
//
//		if (level instanceof ServerLevel serverLevel) {
//			cir.setReturnValue(serverLevel.getServer().overworld().getLightEmission(pos));
//		} else {
//			cir.setReturnValue(OhNutsClient.rootWorld.getLightEmission(pos));
//		}
//	}
}
