package com.example.examplemod.Skills;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;

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
    private int timer = 0;
    private int area = 9;
    private int stepper = 0;
    public void tick(){
        timer++;
        if(timer == 20){
            BlockPos start = new BlockPos(0,0,0);
            BlockPos pos = start.add(stepper/Math.sqrt(area),0,stepper%Math.sqrt(area));
            //now remove the Block at Pos. This goes 0-0 to 0-1 to 0-2 to 0-3 to 1-0 to 1-1 ... 3-3
            stepper++;
            if(stepper > area){
                stepper = 0;
            }
        }
    }

}
