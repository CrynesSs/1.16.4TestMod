package com.example.examplemod.Skills;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;

import java.util.UUID;

public class PlayerSkills {

    private final UUID playerID;
    private final Mining mining = new Mining();
    private final WoodCutting woodCutting = new WoodCutting();


    public PlayerSkills(UUID playerID) {
        this.playerID = playerID;
    }

    public static PlayerSkills handleBlockBreak(Block block, PlayerSkills skills, PlayerEntity playerEntity){
        if(block.isIn(BlockTags.LOGS)){
            skills.woodCutting.checkBlock(block,playerEntity);
        }
        return skills;
    }

    public void deserialize(CompoundNBT nbt) {
       woodCutting.deserialize(nbt);
       mining.deserialize(nbt);
    }

    public static PlayerSkills serialiaze(CompoundNBT nbt, UUID id) {
        PlayerSkills skills = new PlayerSkills(id);
        skills.mining.readAdditional(nbt);
        skills.woodCutting.readAdditional(nbt);
        return skills;
    }

}
