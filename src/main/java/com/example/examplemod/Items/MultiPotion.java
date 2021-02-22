package com.example.examplemod.Items;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiPotion extends Item {
    private int maxEffectAmount = 0;

    private final List<EffectInstance> effects = new ArrayList<>();
    public static final List<Effect> minecraftEffects = Arrays.stream(Effects.class.getDeclaredFields()).filter(field -> Modifier.isStatic(field.getModifiers())).map(field -> {
        try {
            System.out.println("Putting Potion Effect");
            return (Effect)field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }).collect(Collectors.toList());

    public MultiPotion() {
        super(new Item.Properties().group(ExampleMod.TAB));
    }

    public MultiPotion(int maxEffectAmount, EffectInstance... instance){
        super(new Item.Properties());
        effects.addAll(Arrays.asList(instance));
        this.maxEffectAmount = maxEffectAmount;
    }

    public static void addEffectToPotion(MultiPotion potion,EffectInstance ...instance){
        Arrays.stream(instance).forEach(instance1 -> {
            if(potion.maxEffectAmount < potion.effects.size()){
                potion.effects.add(instance1);
            }
        });
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn,@Nonnull PlayerEntity playerIn,@Nonnull Hand handIn) {
        if(worldIn.isRemote){
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        Effect randomEffect = minecraftEffects.get((int) Math.floor(Math.random() * minecraftEffects.size()));
        System.out.println("adding Potion effect");
        this.effects.add(new EffectInstance(randomEffect,24,24));
        System.out.println("Effects in this = " +this.effects.size());
        CompoundNBT nbt = new CompoundNBT();
        if (playerIn.getHeldItem(handIn).hasTag()) {
            nbt =playerIn.getHeldItem(handIn).getTag();
        }
        if(nbt !=null) {
            nbt.putString("effect"+nbt.size(),randomEffect.getName());
            playerIn.getHeldItem(handIn).setTag(nbt);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn,@Nonnull List<ITextComponent> tooltip,@Nonnull ITooltipFlag flagIn) {
        if(stack.hasTag() && stack.getTag() !=null){
            int i =0;
            while (stack.getTag().contains("effect"+i)){tooltip.add(new TranslationTextComponent(stack.getTag().getString("effect"+i)));i++;}
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
