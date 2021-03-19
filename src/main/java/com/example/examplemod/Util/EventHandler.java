package com.example.examplemod.Util;


import com.example.examplemod.Blocks.BackedModelBlocks.DoubleSlabTE;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.Skills.PlayerSkills;
import com.example.examplemod.Util.SaveData.ColorSave;
import com.example.examplemod.Util.SaveData.SkillSave;
import com.example.examplemod.inits.BlockInit;
import com.google.common.collect.ImmutableList;
import javafx.util.Pair;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.misc.Triple;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {


    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event) {
        event.getPlayer().attackEntityFrom(DamageSource.MAGIC, 1);
        PlayerSkills skills = PlayerSkills.handleBlockBreak(event.getState().getBlock(), SkillSave.saves.get(0).getPlayerSkillsMap().get(event.getPlayer().getUniqueID()), event.getPlayer());
        SkillSave.saves.forEach(k -> k.getPlayerSkillsMap().put(event.getPlayer().getUniqueID(), skills));
        SkillSave.saves.forEach(SkillSave::markDirty);
        List<Map.Entry<BlockPos, Color>> setList = ColorSave.colorHashMap.entrySet().parallelStream().filter(blockPosColorEntry -> blockPosColorEntry.getKey().getX() == event.getPos().getX() &&
                blockPosColorEntry.getKey().getY() == event.getPos().getY() &&
                blockPosColorEntry.getKey().getZ() == event.getPos().getZ()).collect(Collectors.toList());
        setList.forEach(blockPosColorEntry -> ColorSave.colorHashMap.remove(blockPosColorEntry.getKey()));

        addPotionEffects(event.getPlayer(), ImmutableList.of(
                new Triple<>(Effects.REGENERATION, 9000, 1),
                new Triple<>(Effects.ABSORPTION, 10000, 1),
                new Triple<>(Effects.FIRE_RESISTANCE, 8000, 0),
                new Triple<>(Effects.SPEED, 9000, 2),
                new Triple<>(Effects.STRENGTH, 10000, 2),
                new Triple<>(Effects.JUMP_BOOST, 8000, 1),
                new Triple<>(Effects.INVISIBILITY, 8000, 0)
        ));
        HashMap<Integer, Integer> exampleMap = new HashMap<>();
    }

    //TODO NEEDS A LOT OF DEBUGGING
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof PlayerEntity && event.getState().getBlock() instanceof SlabBlock) {
            System.out.println("Received a Tile with " + event.getPlacedBlock().get(SlabBlock.TYPE) + " at pos " + event.getPos());
            SlabType type = event.getPlacedBlock().get(SlabBlock.TYPE);
            switch (type) {
                case BOTTOM: {
                    event.getWorld().setBlockState(event.getPos(), BlockInit.DOUBLE_SLAB.get().getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, event.getPlacedBlock().get(SlabBlock.WATERLOGGED)), 3);
                    TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
                    if (tileEntity instanceof DoubleSlabTE) {
                        System.out.println("Received the new TE");
                        ((DoubleSlabTE) tileEntity).textures = new Pair<>(event.getPlacedBlock(), null);
                    }
                    break;
                }
                case TOP: {
                    event.getWorld().setBlockState(event.getPos(), BlockInit.DOUBLE_SLAB.get().getDefaultState().with(SlabBlock.TYPE, SlabType.TOP).with(SlabBlock.WATERLOGGED, event.getPlacedBlock().get(SlabBlock.WATERLOGGED)), 3);
                    TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
                    if (tileEntity instanceof DoubleSlabTE) {
                        System.out.println("Received the new TE");
                        ((DoubleSlabTE) tileEntity).updateTextures(new Pair<>(null, event.getPlacedBlock()));
                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onUpdate(UpdateEvent event) {
        event.w.notifyBlockUpdate(event.pos, event.w.getBlockState(event.pos), event.w.getBlockState(event.pos), 3);
    }

    public static class UpdateEvent extends Event {
        private final World w;
        private final BlockPos pos;

        public UpdateEvent(World w, BlockPos pos) {
            this.w = w;
            this.pos = pos;
        }
    }

    @SubscribeEvent
    public static void onEntityItemUpdate(PlayerEvent.ItemPickupEvent event) {
        System.out.println(event.getEntity().getEntityWorld().getDimensionType().toString());
        System.out.println(DimensionType.THE_NETHER.toString());
        System.out.println(DimensionType.OVERWORLD.toString());
    }

    public static void addPotionEffects(PlayerEntity playerEntity, List<Triple<Effect, Integer, Integer>> effectInstances) {
        effectInstances.parallelStream().forEach(effect -> playerEntity.addPotionEffect(new EffectInstance(effect.a, effect.b, effect.c)));
    }

    @SubscribeEvent
    public static void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        Entity eventEntity = event.getEntity();
        if (!(eventEntity instanceof PlayerEntity)) {
            return;
        }
        if (eventEntity.prevDistanceWalkedModified == eventEntity.distanceWalkedModified) {
            return;
        }
        PlayerEntity player = (PlayerEntity) eventEntity;
        if (!player.isSprinting()) {
            return;
        }
        if (player.getHeldItemMainhand().getItem() instanceof ShearsItem && (int) (Math.random() * 10) == 0) {
            player.attackEntityFrom(DamageSource.MAGIC, 2);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println("Player joined the World");
        SkillSave.saves.forEach(save -> {
            if (!save.getPlayerSkillsMap().containsKey(event.getPlayer().getUniqueID())) {
                save.getPlayerSkillsMap().put(event.getPlayer().getUniqueID(), new PlayerSkills(event.getPlayer().getUniqueID()));
                save.markDirty();
            }
            ;
        });
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (true) {
            //Check here if the Player can craft the Recipe via your Skill System
        }
        //If he can`t this will inform him that this recipe is not craftable yet
        //*Gets all the recipes that match the Item Output of the Crafting (In vanilla this should be unique iirc
        List<IRecipe<?>> recipes = event.getPlayer().getEntityWorld().getRecipeManager().getRecipes().parallelStream().filter(iRecipe -> iRecipe.getRecipeOutput().getItem() == event.getCrafting().getItem()).collect(Collectors.toList());
        //*If we don`t find any recipe matching we return
        if (recipes.isEmpty()) {
            System.out.println("Recipes are empty");
            return;
        }
        //*Very Hacky Solution and i have absolutely no Idea if this works for every Case.
        //*Tbh i have no idea wth i did, but it does work
        List<ItemStack[]> itemStacks = recipes.get(0).getIngredients().stream().map(Ingredient::getMatchingStacks).collect(Collectors.toList());
        for (int i = event.getInventory().getSizeInventory() - 1; i >= 0; i--) {
            int finalI = i;
            if (Arrays.stream(itemStacks.get(0)).anyMatch(stack -> stack.getItem().equals(event.getInventory().getStackInSlot(finalI).getItem()))) {
                List<ItemStack> matches = Arrays.stream(itemStacks.get(0)).filter(stack -> stack.getItem().equals(event.getInventory().getStackInSlot(finalI).getItem())).collect(Collectors.toList());
                event.getInventory().getStackInSlot(i).setCount(event.getInventory().getStackInSlot(i).getCount() + matches.get(0).getCount());
            }
        }
        if (!event.getPlayer().getEntityWorld().isRemote()) {
            event.getPlayer().sendMessage(new StringTextComponent("You can`t craft this right now"), event.getPlayer().getUniqueID());
        }
        event.getCrafting().setCount(0);
    }


}
