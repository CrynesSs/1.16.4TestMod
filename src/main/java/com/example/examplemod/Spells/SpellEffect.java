package com.example.examplemod.Spells;

import com.example.examplemod.Spells.Enums.EEfectType;

public abstract class SpellEffect {
    private int power;
    private byte level;
    private EEfectType effecttype;

    public SpellEffect(int power, byte level, EEfectType effecttype){
        this.power = power;
        this.level = level;
        this.effecttype = effecttype;
    }




}

