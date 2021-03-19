package com.example.examplemod.Blocks.Special;

import com.example.examplemod.TileEntities.ColorTE;
import com.example.examplemod.Util.Packages.ChangeColorPacket;
import com.example.examplemod.Util.Packages.Networking;
import com.example.examplemod.inits.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;

public class RGBStairs extends StairsBlock {
    public RGBStairs() {
        super(BlockInit.RGBBlock.get().getDefaultState(), com.example.examplemod.Util.Helpers.Properties.stoneProps);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ColorTE();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
        System.out.println("Setting new Color ");
        int randomColor = (int) (Math.random() * Integer.MAX_VALUE);
        ((ColorTE) Objects.requireNonNull(worldIn.getTileEntity(pos))).setColorFromInt(randomColor);
        worldIn.notifyBlockUpdate(pos,state,state,3);
        Networking.sendToServer(new ChangeColorPacket(randomColor,pos));
        return ActionResultType.SUCCESS;
    }
}
