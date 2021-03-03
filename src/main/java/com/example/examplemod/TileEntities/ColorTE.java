package com.example.examplemod.TileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;
import java.awt.*;

public class ColorTE extends TileEntity {
    public Color color = new Color(0, 0, 0);

    public void setColor(Color color) {
        this.color = color;
    }

    public ColorTE(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        final CompoundNBT target = super.write(compound);
        target.putInt("color", this.getColorAsInt());
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.setColorFromInt(nbt.getInt("color"));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), -1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.handleUpdateTag(this.world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        final CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("color", this.getColorAsInt());
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.setColorFromInt(tag.getInt("color"));
    }

    private int getColorAsInt() {
        final Color color = this.getColor();
        return ((color.getRed() & 0xFF) << 16) | ((color.getGreen() & 0xFF) << 8) | (color.getBlue() & 0xFF);
    }

    private void setColorFromInt(final int color) {
        this.setColor(new Color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF));
    }

    public Color getColor() {
        return color;
    }
}
