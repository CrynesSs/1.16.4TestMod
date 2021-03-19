package com.example.examplemod.Items;

import com.example.examplemod.Spells.SpellSuper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellItem extends Item {
    private final SpellSuper spellAttached;

    public SpellItem(SpellSuper spellAttached,Item.Properties properties) {
        super(properties);
        this.spellAttached = spellAttached;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        if (stack.getItem() instanceof SpellItem) {
            return ((SpellItem) stack.getItem()).spellAttached.getCastTime();
        }
        return -1;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        BlockPos pos = new BlockPos(entityLiving.getPositionVec());
        spellAttached.cast(worldIn,entityLiving);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
