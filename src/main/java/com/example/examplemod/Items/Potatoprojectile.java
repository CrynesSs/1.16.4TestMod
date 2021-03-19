package com.example.examplemod.Items;

import com.example.examplemod.Util.Types.EntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Potatoprojectile extends AbstractArrowEntity {

    protected Potatoprojectile(double x, double y, double z, World worldIn) {
        super(EntityTypes.POTATO_PROJECTILE.get(), x, y, z, worldIn);
    }

    protected Potatoprojectile(LivingEntity shooter, World worldIn) {
        super(EntityTypes.POTATO_PROJECTILE.get(), shooter, worldIn);
    }

    public Potatoprojectile(EntityType<? extends AbstractArrowEntity> tEntityType, World world) {
        super(tEntityType,world);
    }


    @Override
    @Nonnull
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.POTATO);
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        living.setFire(3000);
        super.arrowHit(living);
    }
}
