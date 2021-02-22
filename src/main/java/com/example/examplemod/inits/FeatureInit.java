package com.example.examplemod.inits;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.OptionalInt;

public class FeatureInit {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> EXAMPLETREE = Feature.TREE.withConfiguration(
            new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockInit.EXAMPLE_WOOD.get().getDefaultState()),
                    new SimpleBlockStateProvider(BlockInit.EXAMPLE_LEAVES.get().getDefaultState()),
                    new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                    new StraightTrunkPlacer(4, 2, 0),
                    new TwoLayerFeature(1, 0, 1)
            ).setIgnoreVines().build());
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> BIG_EXAMPLE_TREE = Feature.TREE.withConfiguration(
            new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockInit.EXAMPLE_WOOD.get().getDefaultState()),
                    new SimpleBlockStateProvider(BlockInit.EXAMPLE_LEAVES.get().getDefaultState()),
                    new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0)),
                    new DarkOakTrunkPlacer(6, 2, 1),
                    new ThreeLayerFeature(1, 1, 0, 1, 2, OptionalInt.empty())
            ).setIgnoreVines().build());
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DARK_OAK = Feature.TREE.withConfiguration(
            (new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.DARK_OAK_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.DARK_OAK_LEAVES.getDefaultState()),
                    new DarkOakFoliagePlacer(
                            FeatureSpread.func_242252_a(0),
                            FeatureSpread.func_242252_a(0)),
                    new DarkOakTrunkPlacer(6, 2, 1),
                    new ThreeLayerFeature(1, 1, 0, 1, 2, OptionalInt.empty())))
                    .setMaxWaterDepth(Integer.MAX_VALUE)
                    .func_236702_a_(Heightmap.Type.MOTION_BLOCKING)
                    .setIgnoreVines()
                    .build());


    private static <FC extends IFeatureConfig> void register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    public static void registerTrees() {
        register("example_tree", EXAMPLETREE);

    }
}
