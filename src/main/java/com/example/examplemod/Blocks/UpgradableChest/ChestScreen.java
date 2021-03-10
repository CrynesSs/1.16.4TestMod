package com.example.examplemod.Blocks.UpgradableChest;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ChestScreen extends ContainerScreen<ChestContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/gui/chest.png");

    public ChestScreen(ChestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 175;
        this.ySize = 183;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.minecraft == null) {
            return;
        }
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int texx = (this.width - this.xSize) / 2;
        //*Halves the Screen Height(So it is centered Horizontally)
        int texy = (this.height - this.ySize) / 2;
        int j = this.guiTop;
        int l = this.guiLeft;
        this.blit(matrixStack,l, j, 0, 0, this.xSize, this.ySize);

    }
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack stack,int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(stack,mouseX, mouseY);
        this.font.drawString(stack, this.title.getString(), 8.0f, 6.0f, 4210752);

    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
