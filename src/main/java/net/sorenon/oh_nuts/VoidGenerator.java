package net.sorenon.oh_nuts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class VoidGenerator extends ChunkGenerator {
	public static final Codec<VoidGenerator> CODEC = RecordCodecBuilder.create(
			instance ->
					instance.group(BiomeSource.CODEC.fieldOf("biomeSource").forGetter(VoidGenerator::getBiomeSource))
							.and(Codec.INT.fieldOf("minY").forGetter(VoidGenerator::getMinY))
							.and(Codec.INT.fieldOf("height").forGetter(VoidGenerator::getGenDepth))
							.and(Codec.INT.fieldOf("seaLevel").forGetter(VoidGenerator::getSeaLevel))
							.apply(instance, instance.stable(VoidGenerator::new))
	);

	private final int minY;
	private final int height;
	private final int seaLevel;

	public VoidGenerator(BiomeSource biomeSource, int minY, int height, int seaLevel) {
		super(BuiltinRegistries.STRUCTURE_SETS, Optional.of(HolderSet.direct()), biomeSource);
		this.minY = minY;
		this.height = height;
		this.seaLevel = seaLevel;
	}

	@Override
	protected Codec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	public void applyCarvers(WorldGenRegion chunkRegion,
							 long seed,
							 RandomState randomState,
							 BiomeManager biomeAccess,
							 StructureManager structureManager,
							 ChunkAccess chunk,
							 GenerationStep.Carving generationStep) {

	}

	@Override
	public void buildSurface(WorldGenRegion region,
							 StructureManager structureManager,
							 RandomState randomState, ChunkAccess chunk) {

	}

	@Override
	public void spawnOriginalMobs(WorldGenRegion region) {

	}

	@Override
	public int getGenDepth() {
		return height;
	}

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor,
														Blender blender,
														RandomState randomState,
														StructureManager structureManager,
														ChunkAccess chunk) {
		return CompletableFuture.supplyAsync(() -> {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					for (int y = this.minY; y < this.minY + this.height; y++) {
						chunk.setBlockState(new BlockPos(x, y, z), OhNutsMod.SUPER_BLOCK.defaultBlockState(), false);
					}
				}
			}

			return chunk;
		});
	}

	@Override
	public int getSeaLevel() {
		return seaLevel;
	}

	@Override
	public int getMinY() {
		return minY;
	}

	@Override
	public int getBaseHeight(int x,
							 int z,
							 Heightmap.Types heightmap,
							 LevelHeightAccessor world,
							 RandomState randomState) {
		return height;
	}

	@Override
	public NoiseColumn getBaseColumn(int x,
									 int z,
									 LevelHeightAccessor world,
									 RandomState randomState) {
		return new NoiseColumn(minY, new BlockState[0]);
	}

	@Override
	public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos pos) {

	}
}
