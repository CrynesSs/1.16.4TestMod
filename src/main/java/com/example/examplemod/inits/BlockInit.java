package com.example.examplemod.inits;

import com.example.examplemod.Blocks.Wood.ExampleWood;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.Features.ExampleSapling;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,ExampleMod.MOD_ID);
    public static final RegistryObject<Block> EXAMPLE_SAPLING = BLOCKS.register("example_sapling", ExampleSapling::new);
    public static final RegistryObject<Block> EXAMPLE_LEAVES = BLOCKS.register("example_leaves",BlockInit::createLeavesBlock);
    public static final RegistryObject<Block> EXAMPLE_WOOD = BLOCKS.register("example_wood", ExampleWood::new);








    private static LeavesBlock createLeavesBlock() {
        return new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(BlockInit::allowsSpawnOnLeaves).setSuffocates(BlockInit::isntSolid).setBlocksVision(BlockInit::isntSolid));
    }
    private static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }
    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }


}
