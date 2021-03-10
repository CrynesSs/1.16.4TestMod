package com.example.examplemod.Util.Packages;

import com.example.examplemod.TileEntities.ColorTE;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ChangeColorPacket {
    private final int color;
    private final BlockPos pos;

    public ChangeColorPacket(PacketBuffer buf) {
        this.color = buf.readInt();
        this.pos = buf.readBlockPos();

    }

    public ChangeColorPacket(int color, BlockPos pos) {
        this.color = color;
        this.pos = pos;
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ((ColorTE) Objects.requireNonNull(Objects.requireNonNull(ctx.get().getSender()).world.getTileEntity(pos))).setColorFromInt(color));
        return true;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(color);
        buf.writeBlockPos(pos);
    }
}
