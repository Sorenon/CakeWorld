package net.sorenon.cake_world;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.sorenon.cake_world.fake_player.FakeConnection;
import net.sorenon.cake_world.fake_player.FakePlayer;
import net.sorenon.cake_world.mixin.MinecraftServerAcc;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.OptionalLong;

public class CakeWorldMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("CakeWorld");

	public static final ResourceKey<Level> LAYER = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("cake_world", "layer"));

	public static final SuperBlock SUPER_BLOCK = new SuperBlock();
	public static final EntityType<FakePlayer> FAKE_PLAYER_ENTITY_TYPE = FabricEntityTypeBuilder.<FakePlayer>create().trackRangeChunks(0).disableSummon().disableSaving().build();

	private final ResourceKey<DimensionType> LAYER_DIMENSION_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("cake_world", "layer"));
	private final Holder<DimensionType> LAYER_DIM_HOLDER = BuiltinRegistries.DIMENSION_TYPE.getOrCreateHolderOrThrow(LAYER_DIMENSION_TYPE);

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.BLOCK, new ResourceLocation("cake_world", "super"), SUPER_BLOCK);
		Registry.register(Registry.ENTITY_TYPE, new ResourceLocation("cake_world", "fake_player"), FAKE_PLAYER_ENTITY_TYPE);
		Registry.register(Registry.ITEM, new ResourceLocation("cake_world", "space_knife"), new SpaceKnifeItem());

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

//		CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> {
//			dispatcher.register(Commands.literal("summon_fake_player").executes(context -> {
//				var player = context.getSource().getPlayer();
//				if (player != null) {
//					var fakeplayer = new FakePlayer(player.server, player.server.overworld(), player);
//					new ServerGamePacketListenerImpl(player.server, new FakeConnection(player.connection.connection), fakeplayer);
//					player.server.overworld().addFreshEntity(fakeplayer);
//
//					return 1;
//				}
//
//				return 0;
//			}));
//		});
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
}
