package com.example.examplemod.Features;

import com.example.examplemod.inits.FeatureInit;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ExampleTree extends Tree {
    ExampleTree(){

    }
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(@Nonnull Random randomIn, boolean largeHive) {
        return FeatureInit.EXAMPLETREE;
    }
}
