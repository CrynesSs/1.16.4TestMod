package com.example.examplemod.Blocks.Recipes.ExampleRecipe;

import com.example.examplemod.ExampleMod;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class RecipeSerializerInit {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExampleMod.MOD_ID);

    public static final IRecipeSerializer<ExampleRecipe> EXAMPLE_RECIPE_SERIALIZER = new ExampleRecipeSerializer();
    public static final IRecipeType<IExampleRecipe> EXAMPLE_TYPE = Registry.register(Registry.RECIPE_TYPE,IExampleRecipe.RECIPE_TYPE_ID,new RecipeType<>());

    public static final RegistryObject<IRecipeSerializer<?>> ALLOY_SMELTING_SERIALIZER = RECIPE_SERIALIZERS.register("example",
            () -> EXAMPLE_RECIPE_SERIALIZER);



    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Objects.requireNonNull(Registry.RECIPE_TYPE.getKey(this)).toString();
        }
    }


}
