package com.example.Mixins.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TreeFeature.class)
public class MixinTreeFeature {
    @Inject(at = @At("HEAD"),cancellable = true,method = "isDirtOrFarmlandAt(Lnet/minecraft/world/gen/IWorldGenerationBaseReader;Lnet/minecraft/util/math/BlockPos;)Z")
    private static void isDirtOrFarmlandAt(IWorldGenerationBaseReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> cir){

    }
}
