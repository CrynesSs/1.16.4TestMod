package com.example.examplemod.Blocks.Special;

import com.example.examplemod.TileEntities.ColorTE;
import com.example.examplemod.Util.Packages.ChangeColorPacket;
import com.example.examplemod.Util.Packages.Networking;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;

public class RGBBlock extends Block {

    public RGBBlock() {
        super(AbstractBlock.Properties.create(Material.WOOD));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        System.out.println("Creating new TE");
        return new ColorTE();
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn,@Nonnull BlockPos pos,@Nonnull PlayerEntity player,@Nonnull Hand handIn,@Nonnull BlockRayTraceResult hit) {
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

    public static int getColorAsInt(Color color) {
        if (color == null) {
            return 0;
        }
        return ((color.getRed() & 0xFF) << 16) | ((color.getGreen() & 0xFF) << 8) | (color.getBlue() & 0xFF);
    }
}
