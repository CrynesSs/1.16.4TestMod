package com.example.examplemod.Util.Types;

import com.example.examplemod.Blocks.Quarry.QuarryTE;
import com.example.examplemod.Blocks.UpgradableChest.ChestTE;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.TileEntities.ColorTE;
import com.example.examplemod.TileEntities.ExampleTE;
import com.example.examplemod.inits.BlockInit;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ExampleMod.MOD_ID);
    public static final RegistryObject<TileEntityType<ExampleTE>> EXAMPLE_TE = TILE_ENTITY_TYPES.register("example",()->TileEntityType.Builder.create(ExampleTE::new, BlockInit.EXAMPLE_WOOD.get()).build(null));
    public static final RegistryObject<TileEntityType<QuarryTE>> QUARRY_TE = TILE_ENTITY_TYPES.register("quarry",()->TileEntityType.Builder.create(QuarryTE::new, BlockInit.QUARRY.get()).build(null));
    public static final RegistryObject<TileEntityType<ChestTE>> CHEST_TE = TILE_ENTITY_TYPES.register("chest",()->TileEntityType.Builder.create(ChestTE::new, BlockInit.CHEST.get()).build(null));
    public static final RegistryObject<TileEntityType<ColorTE>> COLOR_TE = TILE_ENTITY_TYPES.register("color",()->TileEntityType.Builder.create(ColorTE::new, BlockInit.RGBBlock.get()).build(null));

}
