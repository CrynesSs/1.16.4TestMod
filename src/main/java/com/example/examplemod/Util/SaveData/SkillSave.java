package com.example.examplemod.Util.SaveData;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Skills.PlayerSkills;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.*;

public class SkillSave extends WorldSavedData {
    public static final String DATA_NAME = ExampleMod.MOD_ID + "_Skills";
    public static List<SkillSave> saves = new ArrayList<>();
    public HashMap<UUID, PlayerSkills> getPlayerSkillsMap() {
        return playerSkillsMap;
    }

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
        System.out.println("Reading Player Skills");
        nbt.keySet().parallelStream().forEach(k->{
            UUID id = UUID.fromString(k);
            System.out.println(id.toString());
            CompoundNBT nbt1 = (CompoundNBT) nbt.get(k);
            playerSkillsMap.put(id,PlayerSkills.serialiaze(nbt1,id));
        });

    }

    //Write the Stuff to Compound
    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        System.out.println("Saving Player Skills");
        playerSkillsMap.forEach((k,v)->{
            CompoundNBT nbt = new CompoundNBT();
            v.deserialize(nbt);
            compound.put(k.toString(),nbt);
        });
        return compound;
    }

    public static List<SkillSave> getData(World w){
        List<SkillSave> skillSaves = new ArrayList<>();
        for (ServerWorld serverWorld : Objects.requireNonNull(w.getServer()).getWorlds()) {
            skillSaves.add(serverWorld.getSavedData().getOrCreate(SkillSave::new, SkillSave.DATA_NAME));
        }
        return skillSaves;
    }
    public static PlayerSkills getPlayerData(UUID id,SkillSave save){
       return save.playerSkillsMap.get(id);
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

}
