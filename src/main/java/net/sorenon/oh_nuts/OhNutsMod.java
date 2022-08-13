package net.sorenon.oh_nuts;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OhNutsMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Example Mod");

	public static final DoughItem DOUGH_ITEM = new DoughItem();
	public static final BlunderblussItem BLUNDERBLUSS_ITEM = new BlunderblussItem();

	public static final EntityType<AirborneRecipeEntity> SLAB_RECIPE_ENTITY_TYPE = FabricEntityTypeBuilder.create()
			.entityFactory(AirborneRecipeEntity::new)
			.dimensions(EntityDimensions.fixed(1, 1))
			.build();

	public static final DoughBlock DOUGH_BLOCK = new DoughBlock();

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.ENTITY_TYPE, new ResourceLocation("oh_nuts", "slab_recipe"), SLAB_RECIPE_ENTITY_TYPE);
		Registry.register(Registry.BLOCK, new ResourceLocation("oh_nuts", "dough"), DOUGH_BLOCK);
		Registry.register(Registry.ITEM, new ResourceLocation("oh_nuts", "dough"), DOUGH_ITEM);
		Registry.register(Registry.ITEM, new ResourceLocation("oh_nuts", "blunderbluss"), BLUNDERBLUSS_ITEM);
	}
}
