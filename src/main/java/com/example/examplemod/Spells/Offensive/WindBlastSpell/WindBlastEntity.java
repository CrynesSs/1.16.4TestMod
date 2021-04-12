package com.example.examplemod.Spells.Offensive.WindBlastSpell;

import com.example.examplemod.Util.Types.EntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class WindBlastEntity extends Entity {
    private Vector3d forwardVec;
    private int tick = 0;

    public WindBlastEntity(EntityType<? extends Entity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    public WindBlastEntity(World worldIn, Vector3d forwardVec) {
        super(EntityTypes.WINDBLAST_SPELL_ENTITY.get(), worldIn);
        this.forwardVec = forwardVec;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return super.getBoundingBox();
    }

    private void pushEntity(Entity entity) {
        Vector3d v = this.forwardVec;
        entity.addVelocity(-v.x * 5, -v.y * 5, -v.z * 5);
    }

    @Override
    public void tick() {
        tick++;
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()) {
            list.parallelStream().forEach(this::pushEntity);
        }
        if (tick == 100) {
            this.remove();
        }
        super.tick();
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, this.getEntityId());
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        Vector3d v = this.forwardVec;
        pushEntity(entityIn);
        super.onCollideWithPlayer(entityIn);
    }

    protected void collideWithNearbyEntities() {

    }

    @Override
    public void onAddedToWorld() {
        Vector3d v = this.forwardVec;
        this.addVelocity(v.x, v.y, v.z);
        super.onAddedToWorld();
    }
}
