package net.sorenon.oh_nuts.mixin.phantom;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
