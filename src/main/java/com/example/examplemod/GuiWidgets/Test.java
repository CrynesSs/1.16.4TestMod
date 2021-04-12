package com.example.examplemod.GuiWidgets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;

import java.util.stream.Collectors;

public class Test {
    private byte[] packed_flags;

    public void packFlags(Boolean... b) {
        int packed_length = (b.length / 8) + 1;
        int index = 0;
        byte[] packing = setPacked_flags(new byte[packed_length]);
        for (boolean b1 : b) {
            byte toPack = packing[index / 8];
            toPack += b1 ? 1 : 0;
            toPack = (byte) (toPack << 1);
            packing[index / 8] = toPack;
        }
        setPacked_flags(packing);
    }

    public byte[] setPacked_flags(byte[] packed_flags) {
        this.packed_flags = packed_flags;
        return packed_flags;
    }

    private NonNullList<ItemStack> sink = NonNullList.withSize(9, ItemStack.EMPTY);

    public void fillUp(PlayerEntity playerIn, Hand handIn) {
        sink = (NonNullList<ItemStack>) sink.parallelStream().map(k -> {
            if (k.isEmpty() && !playerIn.getHeldItemMainhand().isEmpty()) {
                k = new ItemStack(playerIn.getHeldItemMainhand().getItem(), 1);
                playerIn.getHeldItemMainhand().shrink(1);
            }
            return k;
        }).collect(Collectors.toList());
        for (int i = 0; i < 9; i++) {
            if (sink.get(i).equals(new ItemStack(Items.AIR))) {
                continue;
            }
            sink.set(i, playerIn.getHeldItemMainhand());
            playerIn.getHeldItemMainhand().shrink(1);
            break;

        }

    }
}
