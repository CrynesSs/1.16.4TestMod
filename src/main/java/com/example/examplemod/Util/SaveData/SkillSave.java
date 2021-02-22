package com.example.examplemod.Util.SaveData;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Skills.PlayerSkills;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.*;

public class SkillSave extends WorldSavedData {
    private static final String DATA_NAME = ExampleMod.MOD_ID + "_Skills";
    private final HashMap<UUID, PlayerSkills> playerSkillsMap = new HashMap<>();

    public SkillSave(){
        super(DATA_NAME);
    }


    public SkillSave(String name) {
        super(name);
    }

    //Read the Stuff from Compound. Ez shit
    @Override
    public void read(@Nonnull CompoundNBT nbt) {


    }

    //Write the Stuff to Compound
    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        return compound;
    }

    public static List<SkillSave> getData(World w){
        List<SkillSave> skillSaves = new ArrayList<>();
        for (ServerWorld serverWorld : Objects.requireNonNull(w.getServer()).getWorlds()) {
            skillSaves.add(serverWorld.getSavedData().getOrCreate(SkillSave::new, SkillSave.DATA_NAME));
        }
        return skillSaves;
    }

}
