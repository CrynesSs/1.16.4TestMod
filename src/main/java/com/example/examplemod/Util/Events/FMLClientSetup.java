package com.example.examplemod.Util.Events;

import com.example.examplemod.Blocks.BackedModelBlocks.DoubleSlabModelLoader;
import com.example.examplemod.Blocks.Special.RGBBlock;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.TileEntities.ColorTE;
import com.example.examplemod.inits.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FMLClientSetup {
    @SubscribeEvent
    public static void execute(final FMLClientSetupEvent event){
        registerBlockColors();
        setupRenderLayers();
    }

    private static void registerBlockColors(){
        Minecraft.getInstance().getBlockColors().register((state, blockaccess, pos, tintindex) ->
        {
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
        }, BlockInit.RGBBlock.get());
        Minecraft.getInstance().getBlockColors().register((state, blockaccess, pos, tintindex) ->
        {
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
        },BlockInit.RGBSTAIRS.get());
    }
    private static void setupRenderLayers(){
        RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_LEAVES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.WEB_LIKE_BLOCK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DOUBLE_SLAB.get(), (RenderType) -> true);
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        System.out.println("Registering Loader");
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ExampleMod.MOD_ID, "fancyloader"), new DoubleSlabModelLoader());
    }
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            return;
        }

        event.addSprite(new ResourceLocation(ExampleMod.MOD_ID,"block/double_slab"));
    }
}
