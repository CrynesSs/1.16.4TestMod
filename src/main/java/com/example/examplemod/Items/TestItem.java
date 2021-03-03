package com.example.examplemod.Items;

import com.example.examplemod.ExampleMod;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.Objects;

public class TestItem extends Item {
    public TestItem() {
        super(new Item.Properties().group(ExampleMod.TAB));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        if(context.getWorld().isRemote){
            return ActionResultType.SUCCESS;
        }
        Objects.requireNonNull(context.getPlayer()).disableShield(true);
        ForgeRegistries.BLOCKS.getValues().parallelStream().filter(block -> block instanceof LeavesBlock).forEach(block -> {
            Field f;
            try {
                f = AbstractBlock.class.getDeclaredField("canCollide");
                f.setAccessible(true);
                f.set(block,false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });


        BlockPos start = context.getPos().add(-1,0,-1);
        for(int x = 0;x<3;x++){
            for(int z =0;z<3;z++){
                Block blockBelow = context.getWorld().getBlockState(context.getWorld().getHeight(Heightmap.Type.WORLD_SURFACE,start.add(x,0,z)).down()).getBlock();
                BlockPos pos = context.getWorld().getHeight(Heightmap.Type.WORLD_SURFACE,start.add(x,0,z));
                if(blockBelow instanceof BushBlock){
                    context.getWorld().setBlockState(pos.down(blockBelow instanceof DoublePlantBlock ? 2 : 1),Blocks.ACACIA_LOG.getDefaultState());
                }else{
                    context.getWorld().setBlockState(pos, Blocks.ACACIA_LOG.getDefaultState());
                }
                }
        }
        return ActionResultType.SUCCESS;
    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        System.out.println("UsingItem");
        return super.onItemUse(context);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        System.out.println("LOL");
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        System.out.println("ONITEMRIGHTCLICK");
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
