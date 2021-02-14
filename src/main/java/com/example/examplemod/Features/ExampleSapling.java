package com.example.examplemod.Features;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class ExampleSapling extends SaplingBlock {
    private static final List<Block> validBlocks = ImmutableList.of(Blocks.ACACIA_WOOD);
    @Override
    protected boolean isValidGround(@Nonnull BlockState state,@Nonnull IBlockReader worldIn,@Nonnull BlockPos pos) {
        return validBlocks.stream().anyMatch(state::isIn) || super.isValidGround(state, worldIn, pos);
    }

    public ExampleSapling() {
        super(new ExampleTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT));
    }





    @Override
    public void onPlantGrow(BlockState state, IWorld world, BlockPos pos, BlockPos source) {
        System.out.println("Tree Grown, YEY");
    }

    @Override
    public void grow(@Nonnull ServerWorld worldIn,@Nonnull Random rand,@Nonnull BlockPos pos,@Nonnull BlockState state) {
        System.out.println("In the Grow Function");
        super.grow(worldIn, rand, pos, state);
    }
    @Override
    public void placeTree(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.func_235896_a_(STAGE), 4);
        } else {
            System.out.println("Here 1");
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos)) return;
            try {
                System.out.println("Here 2");
                Field f = SaplingBlock.class.getDeclaredField("tree");
                f.setAccessible(true);
                ExampleTree tree = (ExampleTree) f.get(this);
                boolean bb = tree.attemptGrowTree(world, world.getChunkProvider().getChunkGenerator(), pos, state, rand);
                boolean b = this.attemptGrowTree(world, world.getChunkProvider().getChunkGenerator(), pos, state, rand,tree.getTreeFeature(rand,false));
                System.out.println(b + "   " + bb);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean attemptGrowTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random rand,ConfiguredFeature<BaseTreeFeatureConfig, ?> configuredFeature) {
        if (configuredFeature == null) {
            System.out.println("Tree is null");
            return false;
        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            configuredFeature.config.forcePlacement();
            if (configuredFeature.generate(world, chunkGenerator, rand, pos)) {
                System.out.println("Tree is generated");
                return true;
            } else {
                world.setBlockState(pos, state, 4);
                System.out.println("Tree was not generated");
                return false;
            }
        }
    }
}
