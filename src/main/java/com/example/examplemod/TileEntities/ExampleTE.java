package com.example.examplemod.TileEntities;

import com.example.examplemod.Util.Types.TileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExampleTE extends TileEntity {
    private final Block attached;
    public ExampleTE() {
        super(TileEntityTypes.EXAMPLE_TE.get());
        attached = null;
    }
    public ExampleTE(Block attached){
        super(TileEntityTypes.EXAMPLE_TE.get());
        this.attached = attached;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), -1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.handleUpdateTag(null,pkt.getNbtCompound());
    }

    @Override
    @Nonnull
    public CompoundNBT getUpdateTag() {
        return this.serializeNBT();
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.deserializeNBT(tag);

    }
    public void updateThis(){
        world.notifyBlockUpdate(this.getPos(),world.getBlockState(this.getPos()),world.getBlockState(this.getPos()),3);
    }

}
