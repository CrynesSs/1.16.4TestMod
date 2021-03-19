package com.example.examplemod.Spells;

import com.example.examplemod.Spells.Enums.ESpellType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public abstract class SpellSuper {
    private boolean instantCast;



    private int manaCost;
    private int castTime;
    private int skillRequired;
    private ESpellType type;
    private SpellEffect effect;

    public SpellSuper(boolean instantCast, int manaCost, int castTime, int skillRequired, ESpellType type, SpellEffect effect) {
        this.instantCast = instantCast;
        this.manaCost = manaCost;
        this.castTime = castTime;
        this.skillRequired = skillRequired;
        this.type = type;
        this.effect = effect;
    }
    public abstract void cast(World w, LivingEntity entity);

    public void reduceMana(int amount, LivingEntity entity) {

    }

    public int getCastTime() {
        return castTime;
    }
    public boolean isInstantCast() {
        return instantCast;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getSkillRequired() {
        return skillRequired;
    }

    public ESpellType getType() {
        return type;
    }

    public SpellEffect getEffect() {
        return effect;
    }
}

