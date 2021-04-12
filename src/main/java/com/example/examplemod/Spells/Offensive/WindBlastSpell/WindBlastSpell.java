package com.example.examplemod.Spells.Offensive.WindBlastSpell;

import com.example.examplemod.Spells.Enums.ESpellType;
import com.example.examplemod.Spells.SpellSuper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WindBlastSpell extends SpellSuper {

    public WindBlastSpell() {
        super(false, 10, 15, 20, ESpellType.DESTRUCTION, new WindBlastSpellEffect());
    }

    @Override
    public void cast(World w, LivingEntity entity) {
        Vector3d forwardVec = entity.getForward();
        PlayerEntity playerEntity = (PlayerEntity) entity;
        //Normal Vector of the Plane that is the WindBlast Spell
        Vector3d facingVec = playerEntity.getLookVec();
        WindBlastEntity windBlastEntity = new WindBlastEntity(w,facingVec);
        windBlastEntity.setPosition(entity.getPosX() + facingVec.x * 3, 0, entity.getPosZ() + facingVec.z * 3);
        w.addEntity(windBlastEntity);
        System.out.println("Casting");

    }
}
