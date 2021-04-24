package com.example.examplemod.Features;

import com.example.examplemod.Blocks.Wood.ExampleWood;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.inits.BlockInit;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class ExampleSapling extends SaplingBlock {
    public static final DeferredRegister<Biome> register = DeferredRegister.create(ForgeRegistries.BIOMES, ExampleMod.MOD_ID);
    public ExampleSapling() {
        super(new ExampleTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT));
    }

    private static final List<Block> validBlocks = ImmutableList.of(Blocks.ACACIA_WOOD);
    @Override
    protected boolean isValidGround(@Nonnull BlockState state,@Nonnull IBlockReader worldIn,@Nonnull BlockPos pos) {
        return validBlocks.stream().anyMatch(state.getBlock()::equals) || super.isValidGround(state, worldIn, pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn instanceof ItemEntity){
            ((ItemEntity) entityIn).getItem().getItem().equals("kekw");
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }
}
