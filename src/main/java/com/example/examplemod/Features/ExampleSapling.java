package com.example.examplemod.Features;

import com.example.examplemod.Blocks.Wood.ExampleWood;
import com.example.examplemod.inits.BlockInit;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class ExampleSapling extends SaplingBlock {
    private static final List<Block> validBlocks = ImmutableList.of(Blocks.ACACIA_WOOD,new ExampleWood());
    @Override
    protected boolean isValidGround(@Nonnull BlockState state,@Nonnull IBlockReader worldIn,@Nonnull BlockPos pos) {
        return validBlocks.stream().anyMatch(state::isIn) || super.isValidGround(state, worldIn, pos);
    }

    public ExampleSapling() {
        super(new ExampleTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT));
    }
}
