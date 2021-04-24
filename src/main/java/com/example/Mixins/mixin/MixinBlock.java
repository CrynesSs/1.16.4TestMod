package com.example.Mixins.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(at = @At("HEAD"), method = "onLanded(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/entity/Entity;)V", cancellable = true)
    public void onLanded(IBlockReader worldIn, Entity entityIn, CallbackInfo ci) {
        if (entityIn.isSuppressingBounce()) {
            entityIn.setMotion(entityIn.getMotion().mul(1.0D, 0.0D, 1.0D));
        } else {
            this.bounceEntity(entityIn);
        }
        ci.cancel();
    }

    private void bounceEntity(Entity entity) {
        Vector3d vector3d = entity.getMotion();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setMotion(vector3d.x, -vector3d.y * d0, vector3d.z);
        }
    }

    @Inject(at = @At("HEAD"), method = "onFallenUpon(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V", cancellable = true)
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance, CallbackInfo ci) {
        if (entityIn.isSuppressingBounce()) {
            entityIn.onLivingFall(fallDistance, 1.0F);
        } else {
            entityIn.onLivingFall(fallDistance, 0.0F);

        }
        ci.cancel();

    }

    @Inject(at = @At("HEAD"), method = "onEntityWalk(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V", cancellable = true)
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn, CallbackInfo ci) {
        double d0 = Math.abs(entityIn.getMotion().y);
        if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            entityIn.setMotion(entityIn.getMotion().mul(d1, 1.0D, d1));
        }
        ci.cancel();
    }
}
