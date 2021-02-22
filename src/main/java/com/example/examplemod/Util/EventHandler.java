package com.example.examplemod.Util;


import com.example.examplemod.ExampleMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event){
        if(event.getState().getBlock().isIn(BlockTags.LOGS)){
            //level up woodcutting
        }
        addPotionEffects(event.getPlayer(), ImmutableList.of(
                new Triple<>(Effects.REGENERATION, 9000, 1),
                new Triple<>(Effects.ABSORPTION, 10000, 1),
                new Triple<>(Effects.FIRE_RESISTANCE, 8000, 0),
                new Triple<>(Effects.SPEED, 9000, 2),
                new Triple<>(Effects.STRENGTH, 10000, 2),
                new Triple<>(Effects.JUMP_BOOST, 8000, 1),
                new Triple<>(Effects.INVISIBILITY, 8000, 0)
        ));
        HashMap<Integer,Integer> exampleMap = new HashMap<>();
    }
    @SubscribeEvent
    public static void onEntityItemUpdate(PlayerEvent.ItemPickupEvent event){
        System.out.println(event.getEntity().getEntityWorld().getDimensionType().toString());
        System.out.println(DimensionType.THE_NETHER.toString());
        System.out.println(DimensionType.OVERWORLD.toString());
    }
    public static void addPotionEffects(PlayerEntity playerEntity, List<Triple<Effect,Integer,Integer>> effectInstances){
        effectInstances.parallelStream().forEach(effect->playerEntity.addPotionEffect(new EffectInstance(effect.a,effect.b,effect.c)));
    }
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event){
        if(true){
            //Check here if the Player can craft the Recipe via your Skill System
        }
        //If he can`t this will inform him that this recipe is not craftable yet
        //*Gets all the recipes that match the Item Output of the Crafting (In vanilla this should be unique iirc
        List<IRecipe<?>> recipes = event.getPlayer().getEntityWorld().getRecipeManager().getRecipes().parallelStream().filter(iRecipe -> iRecipe.getRecipeOutput().getItem() == event.getCrafting().getItem()).collect(Collectors.toList());
        //*If we don`t find any recipe matching we return
        if(recipes.isEmpty()){
            System.out.println("Recipes are empty");
            return;
        }
        //*Very Hacky Solution and i have absolutely no Idea if this works for every Case.
        //*Tbh i have no idea wth i did, but it does work
        List<ItemStack[]> itemStacks = recipes.get(0).getIngredients().stream().map(Ingredient::getMatchingStacks).collect(Collectors.toList());
        for(int i = event.getInventory().getSizeInventory() - 1;i>=0;i--){
            int finalI = i;
            if(Arrays.stream(itemStacks.get(0)).anyMatch(stack -> stack.getItem().equals(event.getInventory().getStackInSlot(finalI).getItem()))){
                List<ItemStack> matches = Arrays.stream(itemStacks.get(0)).filter(stack -> stack.getItem().equals(event.getInventory().getStackInSlot(finalI).getItem())).collect(Collectors.toList());
                event.getInventory().getStackInSlot(i).setCount(event.getInventory().getStackInSlot(i).getCount() + matches.get(0).getCount());
            }
        }
        if(!event.getPlayer().getEntityWorld().isRemote()){
            event.getPlayer().sendMessage(new StringTextComponent("You can`t craft this right now"),event.getPlayer().getUniqueID());
        }
        event.getCrafting().setCount(0);
    }


}
