package com.example.examplemod.Skills;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;

public abstract class AbstractSkill {
    protected int level;
    protected HashMap<Block,Boolean> unlockedMap = new HashMap<>();
    public abstract void onLevelUp(PlayerEntity playerEntity);
    public abstract void readAdditional(CompoundNBT save);
    protected void levelUp(PlayerEntity playerEntity){
        this.level++;
        this.onLevelUp(playerEntity);
    }
    public abstract void checkBlock(Block block,PlayerEntity entity);
    protected int exp;
    protected int maxExpLevel = 10;

    public static void addExp(int amount, AbstractSkill skill, PlayerEntity playerEntity){
        if(skill.exp + amount >= skill.maxExpLevel){
            skill.levelUp(playerEntity);
            skill.exp = skill.exp + amount - skill.maxExpLevel;
            skill.maxExpLevel += 10;
            skill.maxExpLevel *= 10;
            System.out.println(skill.toString());
        }else{
            skill.exp += amount;
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
