package com.example.examplemod.Items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class FoodTest {
    public static final Effect[] effects = new Effect[]{Effects.ABSORPTION,Effects.FIRE_RESISTANCE,Effects.BLINDNESS};
    public static final float[] probs = new float[]{0.2f,0.3f,0.1f};

    public static final Food EXAMPLE = (new Food.Builder())
            .hunger(300)
            .saturation(350F)
            .effect(new EffectInstance(effects[0],500,5),probs[0])
            .meat()
            .build();
}
