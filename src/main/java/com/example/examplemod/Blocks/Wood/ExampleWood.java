package com.example.examplemod.Blocks.Wood;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class ExampleWood extends Block {
    public ExampleWood() {
        super(AbstractBlock.Properties.create(Material.WOOD));
    }

    @Override
    @Nonnull
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        DimensionType WORLD_DIMENSION = null;
        try{
            Field f = World.class.getDeclaredField("dimensionType");
            f.setAccessible(true);
            WORLD_DIMENSION = (DimensionType) f.get(worldIn);
            Field f1 = DimensionType.class.getDeclaredField("OVERWORLD_TYPE");
            f1.setAccessible(true);
            f.set(worldIn,f1.get(worldIn.getDimensionType()));
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(WORLD_DIMENSION);



        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
