package com.example.examplemod.Blocks.BackedModelBlocks;

import javafx.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DoubleSlabBlock extends SlabBlock {
    public DoubleSlabBlock() {
        super(com.example.examplemod.Util.Helpers.Properties.stoneProps.notSolid());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DoubleSlabTE();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(worldIn.isRemote){
            return ActionResultType.SUCCESS;
        }
        if(state.get(SlabBlock.TYPE) == SlabType.BOTTOM &&
                player.getHeldItemMainhand().getItem() instanceof BlockItem &&
                ((BlockItem) player.getHeldItemMainhand().getItem()).getBlock() instanceof SlabBlock &&
                hit.getFace() == Direction.UP)
        {
            DoubleSlabTE te = ((DoubleSlabTE)worldIn.getTileEntity(pos));
            if(te != null){
                te.updateTextures(new Pair<>(te.getTextures().getKey(),((BlockItem) player.getHeldItemMainhand().getItem()).getBlock().getDefaultState().with(SlabBlock.TYPE,SlabType.TOP)));
                worldIn.setBlockState(pos,state.getBlockState().with(SlabBlock.TYPE,SlabType.DOUBLE));
                player.getHeldItemMainhand().shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        if(state.get(SlabBlock.TYPE) == SlabType.TOP &&
                player.getHeldItemMainhand().getItem() instanceof BlockItem &&
                ((BlockItem) player.getHeldItemMainhand().getItem()).getBlock() instanceof SlabBlock &&
                hit.getFace() == Direction.DOWN)
        {
            DoubleSlabTE te = ((DoubleSlabTE)worldIn.getTileEntity(pos));
            if(te != null){
                te.updateTextures(new Pair<>(((BlockItem) player.getHeldItemMainhand().getItem()).getBlock().getDefaultState().with(SlabBlock.TYPE,SlabType.BOTTOM),te.getTextures().getValue()));
                worldIn.setBlockState(pos,state.getBlockState().with(SlabBlock.TYPE,SlabType.DOUBLE));
                player.getHeldItemMainhand().shrink(1);
                return ActionResultType.SUCCESS;
            }

        }
        return ActionResultType.PASS;
    }
}
