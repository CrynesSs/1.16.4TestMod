package com.example.examplemod.Blocks.Wood;

import com.example.examplemod.Blocks.Sounds.SoundInit;
import com.example.examplemod.TileEntities.ExampleTE;
import com.google.common.hash.HashFunction;
import net.hypherionmc.hypcore.api.ColoredLightManager;
import net.hypherionmc.hypcore.api.Light;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ExampleWood extends Block {
    private EFade fade = EFade.RedBlue;
    private int timer = 0;
    private int delay = 2;

    public Color getColor() {
        return color;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        worldIn.playSound(player,pos, SoundInit.EXAMPLE_SOUND.get(), SoundCategory.BLOCKS,1,1);
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    public enum EFade {
        RedBlue,
        RedGreen,
        BlueRed,
        BlueGreen,
        GreenRed,
        GreenBlue
    }

    public void applyLinearFade() {
        switch (fade) {
            case BlueRed: {
                this.color = new Color(Math.min(this.color.getRed() + 1, 255), this.color.getGreen(), Math.max(this.color.getBlue() - 1, 0));
                if (Math.min(this.color.getRed() + 1, 255) == 255 && Math.max(this.color.getBlue() - 1, 0) == 0) {
                    switchFade();
                }
                break;
            }
            case RedBlue: {
                this.color = new Color(Math.max(this.color.getRed() - 1, 0), this.color.getGreen(), Math.min(this.color.getBlue() + 1, 255));
                if (Math.max(this.color.getRed() - 1, 0) == 0 && Math.min(this.color.getBlue() + 1, 255) == 255) {
                    switchFade();
                }
                break;
            }
            case BlueGreen: {
                this.color = new Color(this.color.getRed(), Math.min(this.color.getGreen() + 1, 255), Math.max(this.color.getBlue() - 1, 0));
                if (Math.min(this.color.getGreen() + 1, 255) == 255 && Math.max(this.color.getBlue() - 1, 0) == 0) {
                    switchFade();
                }
                break;
            }
            case RedGreen: {
                this.color = new Color(Math.max(this.color.getRed() - 1, 0), Math.min(this.color.getGreen() + 1, 255), this.color.getBlue());
                if (Math.max(this.color.getRed() - 1, 0) == 0 && Math.min(this.color.getGreen() + 1, 255) == 255) {
                    switchFade();
                }
                break;
            }
            case GreenRed: {
                this.color = new Color(Math.min(this.color.getRed() + 1, 255), Math.max(this.color.getGreen() - 1, 0), this.color.getBlue());
                if (Math.min(this.color.getRed() + 1, 255) == 255 && Math.max(this.color.getGreen() - 1, 0) == 0) {
                    switchFade();
                }
                break;
            }
            case GreenBlue: {
                this.color = new Color(this.color.getRed(), Math.max(this.color.getGreen() - 1, 0), Math.min(this.color.getBlue() + 1, 255));
                if (Math.min(this.color.getBlue() + 1, 255) == 255 && Math.max(this.color.getGreen() - 1, 0) == 0) {
                    switchFade();
                }
                break;
            }
        }
    }
    public void logarithmicInterpolation(){
        switch (this.fade){
            case GreenBlue:{
                
                break;
            }
        }
    }

    public Light lightBuilder(BlockPos pos, BlockState state) {
        this.timer++;
        //int salt = (int) ((Math.exp((float)pos.getX() / (float)pos.getZ()) * Math.exp((float)pos.getZ() / pos.getX())) * 400 * Math.random() % 100);
        if (this.timer == this.delay) {
            this.timer = 0;
            applyLinearFade();
        }
        return Light.builder().pos(pos).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f).radius(14).build();
    }

    public void switchFade() {
        List<EFade> fades = Arrays.stream(EFade.values()).filter(k -> k != this.fade).collect(Collectors.toList());
        this.fade = fades.get((int) (Math.random() * fades.size()));
    }


    public void setColor(Color color) {
        this.color = color;
    }

    private Color color = new Color(255, 255, 255);

    public ExampleWood() {
        super(AbstractBlock.Properties.create(Material.WOOD));
        if (ModList.get().isLoaded("hypcore")) {
            ColoredLightManager.registerProvider(this, this::lightBuilder);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ExampleTE(this);
    }


    private int getColorAsInt() {
        final Color color = this.getColor();
        return ((color.getRed() & 0xFF) << 16) | ((color.getGreen() & 0xFF) << 8) | (color.getBlue() & 0xFF);
    }

    private void setColorFromInt(final int color) {
        this.setColor(new Color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF));
    }
}
