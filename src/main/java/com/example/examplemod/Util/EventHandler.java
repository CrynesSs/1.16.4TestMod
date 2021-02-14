package com.example.examplemod.Util;


import com.example.examplemod.ExampleMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event){
        DimensionType NETHER_TYPE = null;
        try{
            Field f = World.class.getDeclaredField("dimensionType");
            f.setAccessible(true);
            NETHER_TYPE = (DimensionType) f.get(event.getWorld());
        }catch (Exception e){
            e.printStackTrace();
        }

        if(event.getWorld().getDimensionType() == NETHER_TYPE){

        }
        System.out.println(DimensionType.THE_NETHER.toString());
        System.out.println(DimensionType.OVERWORLD.toString());
        System.out.println(event.getWorld().getDimensionType().toString());
    }
    @SubscribeEvent
    public static void onEntityItemUpdate(PlayerEvent.ItemPickupEvent event){
        System.out.println(event.getEntity().getEntityWorld().getDimensionType().toString());
        System.out.println(DimensionType.THE_NETHER.toString());
        System.out.println(DimensionType.OVERWORLD.toString());

    }
}
