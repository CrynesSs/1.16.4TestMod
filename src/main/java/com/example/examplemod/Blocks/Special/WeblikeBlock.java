package com.example.examplemod.Blocks.Special;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.WebBlock;
import net.minecraft.block.material.Material;

public class WeblikeBlock extends WebBlock {
    public WeblikeBlock() {
        super(AbstractBlock.Properties.create(Material.WEB).doesNotBlockMovement().setRequiresTool().hardnessAndResistance(4.0F));
    }
}
