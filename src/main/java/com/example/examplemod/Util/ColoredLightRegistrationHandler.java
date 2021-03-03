package com.example.examplemod.Util;

import com.example.examplemod.inits.BlockInit;
import net.hypherionmc.hypcore.api.ColoredLightManager;
import net.minecraftforge.fml.ModList;

public class ColoredLightRegistrationHandler {
    public static void initRegistries(){
        if(ModList.get().isLoaded("hypcore")){
            ColoredLightManager.registerProvider(BlockInit.EXAMPLE_WOOD.get(), BlockInit.EXAMPLE_WOOD.get()::lightBuilder);
        }
    }
}
