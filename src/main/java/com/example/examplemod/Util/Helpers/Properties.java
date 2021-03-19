package com.example.examplemod.Util.Helpers;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class Properties {
    public static final AbstractBlock.Properties stoneProps = AbstractBlock.Properties.create(Material.ROCK)
            .harvestLevel(0)
            .hardnessAndResistance(1.5f)
            .sound(SoundType.STONE);
}
