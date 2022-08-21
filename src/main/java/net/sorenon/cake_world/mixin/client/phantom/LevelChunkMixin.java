package net.sorenon.cake_world.mixin.client.phantom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.sorenon.cake_world.CakeWorldMod;
import net.sorenon.cake_world.client.CakeWorldClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {

    @Shadow
    @Final
    Level level;

    @Inject(at = @At("RETURN"), method = "getBlockState", cancellable = true)
    void getBlockState(BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (!level.isClientSide || !CakeWorldClient.isRendering || !RenderSystem.isOnRenderThread() || CakeWorldClient.rootWorld == null) {
            return;
        }
		if (this.level == CakeWorldClient.rootWorld) {
			return;
		}

        if (cir.getReturnValue().is(CakeWorldMod.SUPER_BLOCK)) {
            cir.setReturnValue(CakeWorldClient.rootWorld.getBlockState(pos));
        }
    }
}
