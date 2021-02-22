package com.example.examplemod.Skills;

import net.minecraft.nbt.CompoundNBT;

public class PlayerSkills {
    public void setI(int i) {
        this.i = i;
    }
    public PlayerSkills(){

    }

    private int i = 5;
    public void deserialize(CompoundNBT compoundNBT) {
        compoundNBT.putInt("level",i);
    }
    public static PlayerSkills serialize(CompoundNBT nbt){
        PlayerSkills skills = new PlayerSkills();
        skills.setI(nbt.getInt("level"));
        return skills;
    }
}
