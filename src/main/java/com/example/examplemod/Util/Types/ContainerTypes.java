package com.example.examplemod.Util.Types;

import com.example.examplemod.Blocks.UpgradableChest.ChestContainer;
import com.example.examplemod.ExampleMod;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ExampleMod.MOD_ID);
    public static final RegistryObject<ContainerType<ChestContainer>> CHEST_TYPE = CONTAINER_TYPES.register("chest",
            ()-> IForgeContainerType.create(ChestContainer::new));
}
