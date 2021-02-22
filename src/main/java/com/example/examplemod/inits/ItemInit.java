package com.example.examplemod.inits;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Items.MultiPotion;
import com.example.examplemod.Items.TestItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);
    public static final RegistryObject<Item> EXAMPLE_SAPLING_ITEM = ITEMS.register("example_sapling",()->new BlockItem(BlockInit.EXAMPLE_SAPLING.get(), (new Item.Properties()).group(ExampleMod.TAB)));
    public static final RegistryObject<Item> EXAMPLE_WOOD_ITEM = ITEMS.register("example_wood",()->new BlockItem(BlockInit.EXAMPLE_WOOD.get(),new Item.Properties().group(ExampleMod.TAB)));
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", TestItem::new);
    public static final RegistryObject<Item> WEB_LIKE_BLOCK_ITEM = ITEMS.register("web_like_block",()->new BlockItem(BlockInit.WEB_LIKE_BLOCK.get(),new Item.Properties().group(ExampleMod.TAB)));
    public static final RegistryObject<Item> MULTI_POTION = ITEMS.register("multi_potion", MultiPotion::new);
}
