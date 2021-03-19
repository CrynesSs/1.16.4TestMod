package com.example.Mixins.mixin;

import net.minecraft.block.SlabBlock;
import org.spongepowered.asm.mixin.Mixin;

//NEEDS A LOT OF WORK BECAUSE CANT PLACE DOUBLE SLAB FOR SOME REASON
@Mixin(SlabBlock.class)
public class MixinSlabBlock {
    /*@Inject(at = @At("HEAD"), method = "getStateForPlacement(Lnet/minecraft/item/BlockItemUseContext;)Lnet/minecraft/block/BlockState;", cancellable = true)
    public void getStateForPlacement(BlockItemUseContext context, CallbackInfoReturnable<BlockState> callback) {
        BlockPos blockpos = context.getPos();
        BlockState blockstate = context.getWorld().getBlockState(blockpos);
        System.out.println(blockpos);
        if (blockstate.matchesBlock(BlockInit.DOUBLE_SLAB.get())) {
            System.out.println("I am here 1");
            callback.setReturnValue(blockstate.with(SlabBlock.TYPE, SlabType.DOUBLE).with(SlabBlock.WATERLOGGED, Boolean.FALSE));
        } else {
            System.out.println("I am here 2");
            FluidState fluidstate = context.getWorld().getFluidState(blockpos);
            BlockState blockstate1 = ((BlockItem)context.getItem().getItem()).getBlock().getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
            Direction direction = context.getFace();
            callback.setReturnValue(direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitVec().y - (double) blockpos.getY() > 0.5D)) ? blockstate1 : blockstate1.with(SlabBlock.TYPE, SlabType.TOP));
        }
        System.out.println("I am here 3");
    }
     */
}
