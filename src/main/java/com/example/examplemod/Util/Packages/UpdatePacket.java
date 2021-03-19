package com.example.examplemod.Util.Packages;

import com.example.examplemod.TileEntities.ColorTE;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class UpdatePacket {
    private final BlockPos pos;

    public UpdatePacket(PacketBuffer buf) {
        this.pos = buf.readBlockPos();

    }

    public UpdatePacket(BlockPos pos) {
        this.pos = pos;
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        System.out.println("Received Package");
        World server = Objects.requireNonNull(ctx.get().getSender()).world;
        server.notifyBlockUpdate(pos, server.getBlockState(pos), server.getBlockState(pos), 3);
        return true;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
    }
}
