package com.example.examplemod.Blocks.BackedModelBlocks;

import com.example.examplemod.ExampleMod;
import com.google.common.collect.ImmutableList;
import javafx.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.*;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DoubleSlabBakedModel implements IDynamicBakedModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "block/double_slab");

    private static Vector3d v(double x, double y, double z) {
        return new Vector3d(x, y, z);
    }


    public DoubleSlabBakedModel() {

    }

    @Override
    public boolean isAmbientOcclusion(BlockState state) {
        return true;
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        TileEntity entity = world.getTileEntity(pos);
        return new ModelDataMap.Builder()
                .withInitial(DoubleSlabTE.TOP_AND_BOTTOM_TEXTURE,((DoubleSlabTE)world.getTileEntity(pos)).textures)
                .build();
    }

    private TextureAtlasSprite getTexture(ResourceLocation location) {
        return Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(location);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        Pair<BlockState, BlockState> topAndBottomState = extraData.getData(DoubleSlabTE.TOP_AND_BOTTOM_TEXTURE);
        if (state == null || topAndBottomState == null) {
            return Collections.emptyList();
        }
        switch (state.get(SlabBlock.TYPE)) {
            case BOTTOM: {
                IBakedModel model1 = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(topAndBottomState.getKey());
                if (model1.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
                    return Collections.emptyList();
                } else {
                    return model1.getQuads(topAndBottomState.getKey(), side, rand, EmptyModelData.INSTANCE);
                }
            }
            case TOP: {
                IBakedModel model2 = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(topAndBottomState.getValue());
                if (model2.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
                    return Collections.emptyList();
                } else {
                    return model2.getQuads(topAndBottomState.getKey(), side, rand, EmptyModelData.INSTANCE);
                }
            }
            case DOUBLE: {
                IBakedModel model1 = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(topAndBottomState.getKey());
                IBakedModel model2 = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(topAndBottomState.getValue());
                if (model1.equals(Minecraft.getInstance().getModelManager().getMissingModel()) || model2.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
                    return Collections.emptyList();
                } else {
                    List<BakedQuad> quads = new ArrayList<>();
                    quads.addAll(model1.getQuads(topAndBottomState.getKey(), side, rand, EmptyModelData.INSTANCE));
                    quads.addAll(model2.getQuads(topAndBottomState.getValue(), side, rand, EmptyModelData.INSTANCE));
                    return quads;
                }
            }
            default: {
                return Collections.emptyList();
            }
        }
        /*
        TextureAtlasSprite texture = getTexture(TEXTURE);
        if(state != null && state.hasProperty(DoubleSlabBlock.TYPE)){
            System.out.println("Received Type : " +state.get(DoubleSlabBlock.TYPE).toString());
        }

        if (state != null && state.hasProperty(DoubleSlabBlock.TYPE)) {
            //System.out.println("I am actually doing sth");
            switch (state.get(DoubleSlabBlock.TYPE)) {
                case BOTTOM: {
                    //System.out.println("Model comes from here lol");
                    return makeBottomHalfSlap(texture);
                }
                case TOP: {
                    return makeTopHalfSlap(texture);
                }
                case DOUBLE: {
                    List<BakedQuad> combined = new ArrayList<>();
                    combined.addAll(makeBottomHalfSlap(texture));
                    combined.addAll(makeTopHalfSlap(texture));
                    return combined;
                }
            }
        }

        double l = .2;
        double r = 1 - .2;
        Vector3d p1 = v(0, 0, 0);
        Vector3d p2 = v(0, 0, 1);
        Vector3d p3 = v(1, 0, 1);
        Vector3d p4 = v(1, 0, 0);
        Vector3d p5 = v(0, .5, 0);
        Vector3d p6 = v(0, .5, 1);
        Vector3d p7 = v(1, .5, 1);
        Vector3d p8 = v(1, .5, 0);
        quads.add(createQuad(p5, p6, p7, p8, texture));
        quads.add(createQuad(p1, p2, p3, p4, texture));
        quads.add(createQuad(p1, p2, p6, p5, texture));
        quads.add(createQuad(p1, p5, p8, p4, texture));
        quads.add(createQuad(p2, p3, p7, p6, texture));
        quads.add(createQuad(p3, p4, p8, p7, texture));
        quads.add(createQuad(v(l, r, l), v(l, r, r), v(r, r, r), v(r, r, l), texture));
        quads.add(createQuad(v(l, l, l), v(r, l, l), v(r, l, r), v(l, l, r), texture));
        quads.add(createQuad(v(r, r, r), v(r, l, r), v(r, l, l), v(r, r, l), texture));
        quads.add(createQuad(v(l, r, l), v(l, l, l), v(l, l, r), v(l, r, r), texture));
        quads.add(createQuad(v(r, r, l), v(r, l, l), v(l, l, l), v(l, r, l), texture));
        quads.add(createQuad(v(l, r, r), v(l, l, r), v(r, l, r), v(r, r, r), texture));

        System.out.println("Returning Fresh");
        return quads;
        */
    }

    public List<BakedQuad> makeBottomHalfSlap(TextureAtlasSprite texture) {
        List<BakedQuad> quads = new ArrayList<>();
        Vector3d p1 = v(0, 0, 0);
        Vector3d p2 = v(0, 0, 1);
        Vector3d p3 = v(1, 0, 1);
        Vector3d p4 = v(1, 0, 0);
        Vector3d p5 = v(0, .5, 0);
        Vector3d p6 = v(0, .5, 1);
        Vector3d p7 = v(1, .5, 1);
        Vector3d p8 = v(1, .5, 0);
        quads.add(createQuad(p5, p6, p7, p8, texture));
        quads.add(createQuad(p1, p2, p3, p4, texture));
        quads.add(createQuad(p1, p2, p6, p5, texture));
        quads.add(createQuad(p1, p5, p8, p4, texture));
        quads.add(createQuad(p2, p3, p7, p6, texture));
        quads.add(createQuad(p3, p4, p8, p7, texture));
        return quads;
    }

    public List<BakedQuad> makeTopHalfSlap(TextureAtlasSprite texture) {
        List<BakedQuad> quads = new ArrayList<>();
        Vector3d p1 = v(0, .5, 0);
        Vector3d p2 = v(0, .5, 1);
        Vector3d p3 = v(1, .5, 1);
        Vector3d p4 = v(1, .5, 0);
        Vector3d p5 = v(0, 1, 0);
        Vector3d p6 = v(0, 1, 1);
        Vector3d p7 = v(1, 1, 1);
        Vector3d p8 = v(1, 1, 0);
        quads.add(createQuad(p5, p6, p7, p8, texture));
        quads.add(createQuad(p1, p2, p3, p4, texture));
        quads.add(createQuad(p1, p2, p6, p5, texture));
        quads.add(createQuad(p1, p5, p8, p4, texture));
        quads.add(createQuad(p2, p3, p7, p6, texture));
        quads.add(createQuad(p3, p4, p8, p7, texture));
        return quads;
    }


    private BakedQuad createQuad(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, TextureAtlasSprite sprite) {
        Vector3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
        int tw = sprite.getWidth();
        int th = sprite.getHeight();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getFacingFromVector(normal.x, normal.y, normal.z));
        putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, 0, th, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, tw, th, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    private void putVertex(BakedQuadBuilder builder, Vector3d normal,
                           double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b) {

        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0; j < elements.size(); j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, r, g, b, 1.0f);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            float iu = sprite.getInterpolatedU(u);
                            float iv = sprite.getInterpolatedV(v);
                            builder.put(j, iu, iv);
                            break;
                        case 2:
                            builder.put(j, (short) 0, (short) 0);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    @Nonnull
    public TextureAtlasSprite getParticleTexture() {
        return getTexture(TEXTURE);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }
}
