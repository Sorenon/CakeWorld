package net.sorenon.cake_world;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.sorenon.cake_world.fake_player.FakePlayer;
import net.sorenon.cake_world.mixin.MinecraftServerAcc;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.OptionalLong;

import static net.minecraft.resources.ResourceLocation.validPathChar;

public class CakeWorldMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("CakeWorld");

	public static String MODID = "cake_world";

	public static final ResourceKey<Level> LAYER = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(MODID, "layer"));

	public static ResourceLocation S2C_WRAPPED_PACKET = new ResourceLocation("layer", "wrapped");
	public static ResourceLocation S2C_CLEANUP = new ResourceLocation("layer", "cleanup");

	public static final SuperBlock SUPER_BLOCK = new SuperBlock();
	public static final EntityType<FakePlayer> FAKE_PLAYER_ENTITY_TYPE = FabricEntityTypeBuilder.<FakePlayer>create().trackRangeChunks(0).disableSummon().disableSaving().build();

	private final ResourceKey<DimensionType> LAYER_DIMENSION_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(MODID, "layer"));
	private final Holder<DimensionType> LAYER_DIM_HOLDER = BuiltinRegistries.DIMENSION_TYPE.getOrCreateHolderOrThrow(LAYER_DIMENSION_TYPE);

	public static SpaceKnifeItem knife;
	public static final QuiltItemGroup group = QuiltItemGroup.builder(new ResourceLocation(MODID, "cake_world")).icon(() -> new ItemStack(knife)).build();

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.BLOCK, new ResourceLocation(MODID, "super"), SUPER_BLOCK);
		Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(MODID, "fake_player"), FAKE_PLAYER_ENTITY_TYPE);
		knife = Registry.register(Registry.ITEM, new ResourceLocation(MODID, "dimensional_blade"), new SpaceKnifeItem());

		BuiltinRegistries.register(
				BuiltinRegistries.DIMENSION_TYPE,
				LAYER_DIMENSION_TYPE,
				new DimensionType(
						OptionalLong.empty(),
						true,
						false,
						false,
						true,
						1.0,
						true,
						false,
						-64,
						384,
						384,
						BlockTags.INFINIBURN_OVERWORLD,
						BuiltinDimensionTypes.OVERWORLD_EFFECTS,
						0.0F,
						new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)
				)
		);

		ServerLifecycleEvents.READY.register(server -> {
			registerDim(server, LAYER);
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> {
			dispatcher.register(Commands.literal("create_layer_world").requires(source -> source.hasPermission(2))
					.then(Commands.argument("name", StringArgumentType.word()).executes(context -> {
						var str = StringArgumentType.getString(context, "name");
						if (isValidPath(str)) {
							var id = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(MODID, str));
							if (context.getSource().getServer().getLevel(id) != null) return 0;
							registerDim(context.getSource().getServer(), id);
							return 1;
						}

						return 0;
					})));
		});
	}

	private static boolean isValidPath(String path) {
		for (int i = 0; i < path.length(); ++i) {
			if (!validPathChar(path.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	public void registerDim(MinecraftServer server, ResourceKey<Level> key) {
		var acc = (MinecraftServerAcc) server;

		var overworld = server.getLevel(Level.OVERWORLD);
		assert overworld != null;
		var overworldGen = overworld.getChunkSource().getGenerator();

		DerivedLevelData derivedLevelData = new DerivedLevelData(server.getWorldData(), server.getWorldData().overworldData());
		ServerLevel newLevel = new ServerLevel(
				server,
				acc.getExecutor(),
				acc.getStorageSource(),
				derivedLevelData,
				key,
				new LevelStem(LAYER_DIM_HOLDER, new VoidGenerator(overworldGen.getBiomeSource(), overworldGen.getMinY(), overworldGen.getGenDepth(), overworldGen.getSeaLevel())),
				new LoggerChunkProgressListener(11),
				false,
				BiomeManager.obfuscateSeed(server.getWorldData().worldGenSettings().seed()),
				ImmutableList.of(),
				false
		);
		overworld.getWorldBorder().addListener(new BorderChangeListener.DelegateBorderChangeListener(newLevel.getWorldBorder()));

		acc.getLevels().put(key, newLevel);
	}

	public static boolean isLayer(ResourceKey<Level> key) {
		return key.location().getNamespace().equals(MODID);
	}
}
