package com.example.examplemod.Skills;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;

public abstract class AbstractSkill {
    protected int level;
    protected HashMap<Block,Boolean> unlockedMap = new HashMap<>();
    public abstract void onLevelUp();
    public abstract void readAdditional(CompoundNBT save);
    protected void levelUp(){
        this.level++;
        onLevelUp();
    }
    public abstract void checkBlock(Block block);
    protected int exp;
    protected int maxExpLevel;

    public static void addExp(int amount, AbstractSkill skill){
        if(skill.exp + amount >= skill.maxExpLevel){
            skill.levelUp();
        }
    }
    public abstract void deserialize(CompoundNBT nbt);

    @Override
    public String toString() {
        return "AbstractSkill{" +
                "level=" + level +
                ", unlockedMap=" + unlockedMap +
                ", exp=" + exp +
                ", maxExpLevel=" + maxExpLevel +
                '}';
    }
}
