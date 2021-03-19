package com.example.examplemod.Spells.Offensive.WindBlastSpell;

import com.example.examplemod.Spells.Enums.EEfectType;
import com.example.examplemod.Spells.SpellEffect;

public class WindBlastSpellEffect extends SpellEffect {

    public WindBlastSpellEffect() {
        super(5, (byte) 5, EEfectType.OFFENSIVE);
    }
}
