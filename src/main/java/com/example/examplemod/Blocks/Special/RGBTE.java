package com.example.examplemod.Blocks.Special;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RGBTE extends TileEntity {
    public int color = 0;

    public RGBTE() {
        super(TileEntityType.BANNER);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        this.color = nbt.getInt("color");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("color",color);
        return super.write(compound);
    }
}
