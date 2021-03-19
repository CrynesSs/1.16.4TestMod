package com.example.examplemod.Spells.Offensive.WindBlastSpell;

import com.example.examplemod.Spells.Enums.ESpellType;
import com.example.examplemod.Spells.SpellSuper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WindBlastSpell extends SpellSuper {

    public WindBlastSpell() {
        super(false, 10, 15, 20, ESpellType.DESTRUCTION, new WindBlastSpellEffect());
    }

    @Override
    public void cast(World w, LivingEntity entity) {
        Vector3d forwardVec = entity.getForward();
        Vector3d toSideCW = new Vector3d(forwardVec.z, 0, -forwardVec.x).normalize();
        Vector3d toSideCCW = new Vector3d(-forwardVec.z, 0, forwardVec.x).normalize();

        System.out.println("Casting");
    }
}
