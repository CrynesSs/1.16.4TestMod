/*package com.example.examplemod.Items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class MasterSwordItem extends SwordItem {

    //NBT tags
    public String tagCharged = "charged";
    public boolean charged;

    public String tagDragon = "dragon";
    public boolean dragon;

    public String tagWither = "wither";
    public boolean wither;

    public String tagDurability = "durability";
    public int durability;

    public String tagLevel = "level";
    public int level;
    public int[] milestones = {10, 20, 30};

    public String tagCharges = "charges";
    public int charges;

    //Local variables
    private int timer;
    private int cooldown = 200;
    private int counter;
    private boolean removeText1;
    private boolean removeText2;

    public MasterSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (KeyboardHelper.isHoldingCtrl()) { //Adds info of how to level up the sword if CTRL is held down while hovering

            if (nbt.getInt(tagLevel) < 10) { //Less than milestone 1
                tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The sword requires " + (milestones[0] - nbt.getInt(tagLevel)) + " kills to get the next upgrade."));

            } else if ((nbt.getInt(tagLevel) >= 10 && nbt.getInt(tagLevel) < 20) || //Less than milestone 2
                    (nbt.getInt(tagLevel) >= milestones[0] && !nbt.getBoolean(tagWither) && !nbt.getBoolean(tagDragon))) { //Or more than milestone 2 but not killed the boss
                if (nbt.getInt(tagLevel) < milestones[1]) {
                    tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The sword requires " + (milestones[1] - nbt.getInt(tagLevel)) + " kills to get the next upgrade."));
                    removeText1 = false;
                    do {
                        if ((nbt.getInt(tagLevel) >= milestones[1] && !nbt.getBoolean(tagWither)) && !removeText1) {
                            tooltip.remove(1);
                            removeText1 = true;
                        }
                    } while (!removeText1);
                }
                if (removeText1) {
                    tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Enough kills have been achieved."));
                }
                tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The sword also requires the soul of the Wither boss to evolve further."));
                tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Soul collected: " + nbt.getBoolean(tagWither)));

            } else if (((nbt.getInt(tagLevel) >= milestones[1] && nbt.getInt(tagLevel) < milestones[2]) && nbt.getBoolean(tagWither)) || //Less than milestone 3
                    ((nbt.getInt(tagLevel) >= milestones[1] && nbt.getBoolean(tagWither)) && !nbt.getBoolean(tagDragon))) { //More than milestone but not killed the boss
                boolean removed;
                if (nbt.getInt(tagLevel) < milestones[2]) {
                    tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The sword requires " + (milestones[2] - nbt.getInt(tagLevel)) + " kills to get the next upgrade."));
                    removed = false;
                    if ((nbt.getInt(tagLevel) >= milestones[2] && !nbt.getBoolean(tagDragon)) && !removed) {
                        tooltip.remove(1);
                        removed = true;
                    } else if (removed) {
                        tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Enough kills have been achieved."));
                    }
                }
                tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The sword requires " + (milestones[2] - nbt.getInt(tagLevel)) + " kills to get the next upgrade."));
                tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The sword also requires the soul of the Ender Dragon to evolve further."));
                tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Soul collected: " + nbt.getBoolean(tagDragon)));

            } else if (nbt.getInt(tagLevel) >= milestones[2] && nbt.getBoolean(tagDragon) && nbt.getBoolean(tagWither)) { //More than milestone 3
                tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The sword has been fully upgraded and is at it's greatest power!"));

            } else { //Error message
                tooltip.add(new StringTextComponent(TextFormatting.RED + "Something went horribly wrong!"));
            }

        } else if (KeyboardHelper.isHoldingShift()) { //Adds description from the game to the item if SHIFT is held down while hovering
            tooltip.add(new StringTextComponent(TextFormatting.WHITE + "The legendary sword that seals the darkness. Its blade gleams with a sacred luster that can oppose the Calamity. Only a hero chosen by the sword itself may wield it."));

        } else { //Regular info displayed when not holding SHIFT or CTRL
            if (!(nbt.getBoolean(tagCharged))) {
                tooltip.add(new StringTextComponent(TextFormatting.RED + "Sword is broken, time until repaired: " + (cooldown - timer)));
                if (timer >= cooldown && !nbt.getBoolean(tagCharged)) {
                    tooltip.remove(1);
                }
            }
            tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Durability remaining: " + nbt.getInt(tagDurability)));
            tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Is charged: " + nbt.getBoolean(tagCharged)));
            tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Current level: " + nbt.getInt(tagLevel)));
            tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Slash charges remaining: " + nbt.getInt(tagCharges)));
            tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Hold " + TextFormatting.LIGHT_PURPLE + "SHIFT" + TextFormatting.WHITE + " for description."));
            tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Hold " + TextFormatting.LIGHT_PURPLE + "CTRL" + TextFormatting.WHITE + " for more information."));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) { //Just sets the base values of some NBT tags when crafting the item
        CompoundNBT nbt = stack.getOrCreateTag();
        stack.addEnchantment(Enchantments.SHARPNESS, 5);
        this.charged = nbt.getBoolean(tagCharged);
        nbt.putBoolean(tagCharged, true);
        this.durability = nbt.getInt(tagDurability);
        nbt.putInt(tagDurability, 10);
        this.charges = nbt.getInt(tagCharges);
        nbt.putInt(tagCharges, 0);
        this.level = nbt.getInt(tagLevel);
        nbt.putInt(tagLevel, 0);
        this.wither = nbt.getBoolean(tagWither);
        nbt.putBoolean(tagWither, false);
        this.dragon = nbt.getBoolean(tagDragon);
        nbt.putBoolean(tagDragon, false);

        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, @Nonnull World worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (!nbt.getBoolean(tagCharged)) {
            timer++;
        }
        if (!(timer >= cooldown)) {
            super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
            return;
        }
        timer = 0;
        int taglevel = nbt.getInt(tagLevel);
        boolean tagDragon = nbt.getBoolean(this.tagDragon);
        boolean tagWither = nbt.getBoolean(this.tagWither);
        if (taglevel < 10) { //Does V if first upgrade milestone not reached
            setNBT(nbt, stack, 10, 5);

        } else if (taglevel < 20 || !tagWither && !tagDragon) { //Does V if second upgrade milestone not reached
            setNBT(nbt, stack, 10, 10);

        } else if (taglevel < 30 && tagDragon || tagWither && !tagDragon) { //Does V if third upgrade milestone not reached
            setNBT(nbt, stack, 20, 20);

        } else if (tagWither) { //Does V if third upgrade milestone is reached
            setNBT(nbt, stack, 30, 30);
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    private void setNBT(CompoundNBT nbt, ItemStack stack, int durability, int level) {
        nbt.putInt(tagDurability, durability);
        nbt.putBoolean(tagCharged, true);
        stack.addEnchantment(Enchantments.SHARPNESS, level);
        this.durability = durability;
        this.charged = true;
    }

    public enum EMobType {
        WITHER,
        DRAGON,
        REGULAR

    }

    //Method called in LevelupSwordsEvent to level up the sword as well as give it charges per 10 kills
    //Type gives it the type of entity killed, 1 is Wither, 2 is Dragon, 0 is everything else
    public void levelUp(ItemStack stack, EMobType type) {
        CompoundNBT nbt = stack.getOrCreateTag();
        switch (type) {
            case DRAGON: {

            }
            case WITHER: {
                nbt.putInt(tagLevel, nbt.getInt(tagLevel) + 5);
                break;
            }
            case REGULAR: {
                nbt.putInt(tagLevel, nbt.getInt(tagLevel) + 1);
                break;
            }
        }
        counter++;
        if (counter == 10) {
            nbt.putInt(tagCharges, nbt.getInt(tagCharges) + 1);
            counter = 0;
        }
    }

    //Method called in DamageSwordsEvent to remove durability when hitting enemies, if none remain then break the sword
    public void damageItem(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (!nbt.getBoolean(tagCharged)) {
            return;
        }
        if (!nbt.contains(tagDurability)) {
            return;
        }
        if (nbt.getInt(tagDurability) >= 1) {
            nbt.putInt(tagDurability, nbt.getInt(tagDurability) - 1);
        } else {
            this.charged = nbt.getBoolean(tagCharged);
            nbt.putBoolean(tagCharged, false);
            this.durability = nbt.getInt(tagDurability);
            nbt.putInt(tagDurability, 0);
            stack.getEnchantmentTagList().clear();
        }
    }

    //Making the sword not enchantable and unbreakable, also giving it enchantment effect when it is not broken
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(tagCharged);
    }

}
 */
