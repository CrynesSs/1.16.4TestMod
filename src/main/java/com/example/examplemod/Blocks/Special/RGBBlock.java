package com.example.examplemod.Blocks.Special;

import com.example.examplemod.Util.SaveData.ColorSave;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import java.awt.*;

public class RGBBlock extends Block {

    public RGBBlock() {
        super(AbstractBlock.Properties.create(Material.WOOD));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        ColorSave.colorHashMap.put(pos,new Color(0,0,0));
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote){return ActionResultType.SUCCESS;}
        int randomColor = (int) (Math.random() * Integer.MAX_VALUE);
        ColorSave.colorHashMap.put(pos,new Color(randomColor));
        ColorSave.saves.forEach(WorldSavedData::markDirty);
        TileEntity entity = worldIn.getTileEntity(pos);
        worldIn.notifyBlockUpdate(pos,state,state,3);
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    public static int getColorAsInt(Color color) {
        if(color == null){
            return 0;
        }
        return ((color.getRed() & 0xFF) << 16) | ((color.getGreen() & 0xFF) << 8) | (color.getBlue() & 0xFF);
    }
}
