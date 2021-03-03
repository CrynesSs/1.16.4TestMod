package com.example.examplemod.Util.SaveData;

import com.example.examplemod.Blocks.Special.RGBBlock;
import com.example.examplemod.ExampleMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ColorSave extends WorldSavedData {
    public static final String name = ExampleMod.MOD_ID + "_color";
    public static HashMap<BlockPos, Color> colorHashMap = new HashMap<>();
    public static List<ColorSave> saves = new ArrayList<>();
    public ColorSave(String name) {
        super(name);
    }
    public ColorSave(){
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {
        AtomicInteger integer = new AtomicInteger(0);
        while (nbt.contains("color"+integer.get())){
            CompoundNBT compoundNBT = nbt.getCompound("color"+integer.get());
            BlockPos pos = new BlockPos(compoundNBT.getInt("x"),compoundNBT.getInt("y"),compoundNBT.getInt("z"));
            System.out.println("Reading Color Values for pos " +pos + " with Color " +compoundNBT.getInt("color"));
            colorHashMap.put(pos,new Color(compoundNBT.getInt("color")));
            integer.getAndIncrement();
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        System.out.println("writing Colors");
        AtomicInteger integer = new AtomicInteger(0);
        colorHashMap.forEach((k,v)->{
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("x",k.getX());
            nbt.putInt("y",k.getY());
            nbt.putInt("z",k.getZ());
            System.out.println("putting pos " + k + "  with color " + RGBBlock.getColorAsInt(v));
            nbt.putInt("color", RGBBlock.getColorAsInt(v));
            compound.put("color"+integer.get(),nbt);
            System.out.println(nbt.getInt("color"));
            integer.getAndIncrement();
        });
        return compound;
    }
}
