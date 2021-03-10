package com.example.examplemod;

import com.example.examplemod.Blocks.Recipes.ExampleRecipe.RecipeSerializerInit;
import com.example.examplemod.Blocks.Sounds.SoundInit;
import com.example.examplemod.Blocks.Special.RGBBlock;
import com.example.examplemod.Blocks.UpgradableChest.ChestScreen;
import com.example.examplemod.TileEntities.ColorTE;
import com.example.examplemod.Util.ColoredLightRegistrationHandler;
import com.example.examplemod.Util.Packages.Networking;
import com.example.examplemod.Util.SaveData.ColorSave;
import com.example.examplemod.Util.SaveData.SkillSave;
import com.example.examplemod.Util.Types.ContainerTypes;
import com.example.examplemod.Util.Types.EntityTypes;
import com.example.examplemod.Util.Types.TileEntityTypes;
import com.example.examplemod.inits.BlockInit;
import com.example.examplemod.inits.FeatureInit;
import com.example.examplemod.inits.ItemInit;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("example_mod")
public class ExampleMod {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "example_mod";
    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public ExampleMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        SoundInit.SOUNDS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        RecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
        TileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ContainerTypes.CONTAINER_TYPES.register(modEventBus);
        EntityTypes.ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        FeatureInit.registerTrees();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_LEAVES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.WEB_LIKE_BLOCK.get(), RenderType.getCutout());
        ScreenManager.registerFactory(ContainerTypes.CHEST_TYPE.get(), ChestScreen::new);
        ColoredLightRegistrationHandler.initRegistries();
        System.out.println("DOing Client Setup");

        Minecraft.getInstance().getBlockColors().register((state, blockaccess, pos, tintindex) ->
        {
            System.out.println("Setting up a Color");
            if(Minecraft.getInstance().world == null || pos == null){
                return 0;
            }
            TileEntity tile = Minecraft.getInstance().world.getTileEntity(pos);
            if(tile instanceof ColorTE){
                return RGBBlock.getColorAsInt(((ColorTE) tile).color);
            }else{
                System.out.println("I am useless");
                return 0;
            }
        },BlockInit.RGBBlock.get());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
        event.getServer().getWorlds().forEach(serverWorld -> {
            SkillSave save = serverWorld.getSavedData().getOrCreate(SkillSave::new, SkillSave.DATA_NAME);
            SkillSave.saves.add(save);
            save.markDirty();
            if (save.getPlayerSkillsMap().isEmpty()) {
                System.out.println("Empty Skill List");
            }
        });
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    public static final ItemGroup TAB = new ItemGroup("RedstoneEnhancements") {
        @Override
        public ItemStack createIcon() {
            //*This Sets the Item that will be in the Thumbnail of the Item Tab
            return new ItemStack(ItemInit.EXAMPLE_SAPLING_ITEM.get());
        }
    };
}
