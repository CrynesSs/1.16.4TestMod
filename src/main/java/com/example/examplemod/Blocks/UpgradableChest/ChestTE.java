package com.example.examplemod.Blocks.UpgradableChest;

import com.example.examplemod.Util.Types.TileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChestTE extends InventoryTile implements INamedContainerProvider {
    private int size = 0;
    private int level = 0;
    public ChestTE() {
        super(TileEntityTypes.CHEST_TE.get(),255);
    }



    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public ISidedInventory createInventory(BlockState state, IWorld world, BlockPos pos) {
        return this;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.alloy_furnace");
    }

    @Nullable
    @Override
    public Container createMenu(int index, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return new ChestContainer(index,new InvWrapper(playerInventory),this);
    }
}
