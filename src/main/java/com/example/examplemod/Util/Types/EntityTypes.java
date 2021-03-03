package com.example.examplemod.Util.Types;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Items.Potatoprojectile;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ExampleMod.MOD_ID);
    public static final RegistryObject<EntityType<Potatoprojectile>> POTATO_PROJECTILE = ENTITY_TYPES.register("potato_projectile",
            ()->EntityType.Builder.create(Potatoprojectile::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F)
                    .trackingRange(4)
                    .func_233608_b_(20)
                    .immuneToFire()
                    .build("lolKekW"));
}
