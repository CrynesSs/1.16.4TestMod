package com.example.examplemod.Blocks.UpgradableChest;

import com.example.examplemod.Util.Types.ContainerTypes;
import com.example.examplemod.inits.BlockInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Objects;

public class ChestContainer extends Container {
    private final IWorldPosCallable canInteractWithCallable;


    public ChestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, new InvWrapper(playerInventory), getTileEntity(data, playerInventory.player.world));
    }
    protected ChestContainer(int id,IItemHandlerModifiable playerInventory,ChestTE tile) {
        super(ContainerTypes.CHEST_TYPE.get(), id);
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tile.getWorld()), tile.getPos());
        IItemHandlerModifiable tileInv = tile.getInventory();
        int x = 4;
        x = x << 4;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new SlotItemHandler(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new SlotItemHandler(playerInventory, k, 8 + k * 18, 142));
        }

    }
    private static ChestTE getTileEntity(final PacketBuffer data, final World world) {
        if (world != null) {
            final TileEntity tileAtPos = world.getTileEntity(data.readBlockPos());
            if (tileAtPos instanceof ChestTE) {
                return (ChestTE) tileAtPos;
            }
        }
        return null;
    }
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.CHEST.get());
    }
}
