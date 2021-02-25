package com.example.examplemod.Skills;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;

public class Mining extends AbstractSkill {

    public Mining() {

    }

    @Override
    public void onLevelUp(PlayerEntity playerEntity) {
        playerEntity.sendMessage(new StringTextComponent("Leveled up WoodCutting to " + this.level),playerEntity.getUniqueID());
    }

    @Override
    public void readAdditional(CompoundNBT save) {
        CompoundNBT nbt = (CompoundNBT) save.get("mining");
        System.out.println("Reading additional mining");
        if (nbt == null) {
            return;
        }
        this.level = nbt.getInt("level");
        this.maxExpLevel = nbt.getInt("maxexp");
        this.exp = nbt.getInt("exp");
        System.out.println(this.toString());
    }


    @Override
    public void checkBlock(Block block,PlayerEntity playerEntity) {

    }

    @Override
    public void deserialize(CompoundNBT nbt) {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("level", this.level);
        compound.putInt("maxexp", this.maxExpLevel);
        compound.putInt("exp", this.exp);
        nbt.put("mining", compound);
    }

}
