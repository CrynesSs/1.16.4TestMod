package com.example.examplemod.inits;

import com.example.examplemod.ExampleMod;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);
    public static final RegistryObject<Item> EXAMPLE_SAPLING_ITEM = ITEMS.register("example_sapling",()->new BlockItem(BlockInit.EXAMPLE_SAPLING.get(), (new Item.Properties()).group(ExampleMod.TAB)));
    public static final RegistryObject<Item> EXAMPLE_WOOD_ITEM = ITEMS.register("example_wood",()->new BlockItem(BlockInit.EXAMPLE_WOOD.get(),new Item.Properties().group(ExampleMod.TAB)));

}
