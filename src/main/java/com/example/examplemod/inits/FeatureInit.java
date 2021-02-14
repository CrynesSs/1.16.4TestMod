package com.example.examplemod.inits;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class FeatureInit {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> EXAMPLETREE = Feature.TREE.withConfiguration(
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(BlockInit.EXAMPLE_WOOD.get().getDefaultState()),
                            new SimpleBlockStateProvider(BlockInit.EXAMPLE_LEAVES.get().getDefaultState()),
                            new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                            new StraightTrunkPlacer(4, 2, 0),
                            new TwoLayerFeature(1, 0, 1)
                    ).setIgnoreVines().build());


    private static <FC extends IFeatureConfig> void register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    public static void registerTrees() {
        register("example_tree",EXAMPLETREE);
    }
}
