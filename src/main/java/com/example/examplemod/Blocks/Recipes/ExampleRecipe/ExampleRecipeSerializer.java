package com.example.examplemod.Blocks.Recipes.ExampleRecipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExampleRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExampleRecipe> {
    @Override
    @Nonnull
    public ExampleRecipe read(@Nonnull ResourceLocation recipeId,@Nonnull JsonObject json) {
        System.out.println("Reading Alloysmelting");
        JsonObject inputs = JSONUtils.getJsonObject(json, "input");
        ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
        Ingredient input1 = Ingredient.deserialize(inputs.getAsJsonObject("0"));
        Ingredient input2 = Ingredient.deserialize(inputs.getAsJsonObject("1"));
        ItemStack input1stack = CraftingHelper.getItemStack(inputs.getAsJsonObject("0"), true);
        ItemStack input2stack = CraftingHelper.getItemStack(inputs.getAsJsonObject("1"), true);
        Integer smelttime = JSONUtils.getInt(json, "smelttime");
        return new ExampleRecipe(recipeId, input1, input2, output, smelttime, input1stack, input2stack);
    }

    @Nullable
    @Override
    public ExampleRecipe read(@Nonnull ResourceLocation recipeId,@Nonnull PacketBuffer buffer) {
        System.out.println("Reading Alloysmelting");
        ItemStack output = buffer.readItemStack();
        Ingredient input1 = Ingredient.read(buffer);
        Ingredient input2 = Ingredient.read(buffer);
        ItemStack input1stack = buffer.readItemStack();
        ItemStack input2stack = buffer.readItemStack();
        Integer smelttime = buffer.readInt();
        return new ExampleRecipe(recipeId, input1, input2, output, smelttime, input1stack, input2stack);
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer,@Nonnull ExampleRecipe recipe) {
        Ingredient input1 = recipe.getIngredients().get(0);
        Ingredient input2 = recipe.getIngredients().get(1);
        ItemStack input1s = recipe.getInput1Stack();
        ItemStack input2s = recipe.getInput2Stack();

        buffer.writeItemStack(recipe.getRecipeOutput(), false);
        input1.write(buffer);
        input2.write(buffer);
        buffer.writeItemStack(input1s);
        buffer.writeItemStack(input2s);
        buffer.writeInt(recipe.getSmeltTime());
    }
}
