package net.sorenon.oh_nuts.mixin.client.phantom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.sorenon.oh_nuts.OhNutsMod;
import net.sorenon.oh_nuts.client.OhNutsClient;
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
        if (!level.isClientSide || !OhNutsClient.isRendering || !RenderSystem.isOnRenderThread() || OhNutsClient.rootWorld == null) {
            return;
        }
		if (this.level == OhNutsClient.rootWorld) {
			return;
		}

        if (cir.getReturnValue().is(OhNutsMod.SUPER_BLOCK)) {
            cir.setReturnValue(OhNutsClient.rootWorld.getBlockState(pos));
        }
    }
}
