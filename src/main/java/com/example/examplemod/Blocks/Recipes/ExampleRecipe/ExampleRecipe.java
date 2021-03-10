package com.example.examplemod.Blocks.Recipes.ExampleRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public class ExampleRecipe implements IExampleRecipe{
    private final ResourceLocation id;
    private final Ingredient input1;
    private final Ingredient input2;
    private final ItemStack output;
    private final Integer smelttime;
    private final ItemStack input1stack;
    private final ItemStack input2stack;

    public ExampleRecipe(ResourceLocation id, Ingredient input1, Ingredient input2, ItemStack output, Integer smelttime, ItemStack input1stack, ItemStack input2stack) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.smelttime = smelttime;
        this.input1stack = input1stack;
        this.input2stack = input2stack;
    }
    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull World worldIn) {
        return this.input1.test(inv.getStackInSlot(0)) && this.input2.test(inv.getStackInSlot(1)) || this.input1.test(inv.getStackInSlot(1)) && this.input2.test(inv.getStackInSlot(0));
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull RecipeWrapper inv) {
        return this.output;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.EXAMPLE_SERIALIZER.get();
    }

    @Override
    public Ingredient getInput1() {
        return this.input1;
    }

    @Override
    public Ingredient getInput2() {
        return this.input2;
    }

    @Override
    public ItemStack getInput1Stack() {
        return this.input1stack;
    }

    @Override
    public ItemStack getInput2Stack() {
        return this.input2stack;
    }


    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(null, this.input1, this.input2);
    }

    @Override
    public Integer getSmeltTime() {
        return this.smelttime;
    }
}
