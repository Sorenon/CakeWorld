package net.sorenon.oh_nuts.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.sorenon.oh_nuts.OhNutsMod;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class OhNutsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		EntityRendererRegistry.register(OhNutsMod.SLAB_RECIPE_ENTITY_TYPE, AirborneRecipeRenderer::new);
	}
}
