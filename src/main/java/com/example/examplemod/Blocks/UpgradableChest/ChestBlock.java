package com.example.examplemod.Blocks.UpgradableChest;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.DirectionProperty;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ChestBlock extends Block {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public ChestBlock() {
        super(AbstractBlock.Properties.create(Material.WOOD).notSolid().harvestTool(ToolType.AXE).harvestLevel(1));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ChestTE tile = (ChestTE) worldIn.getTileEntity(pos);
        if (tile != null && tile.getClass().equals(ChestTE.class)) {
            if (player instanceof ServerPlayerEntity) {
                try {
                    tile.checkRecipe();
                    NetworkHooks.openGui((ServerPlayerEntity) player, tile, tile.getPos());
                    player.addStat(Stats.INTERACT_WITH_FURNACE);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChestTE();
    }
}
