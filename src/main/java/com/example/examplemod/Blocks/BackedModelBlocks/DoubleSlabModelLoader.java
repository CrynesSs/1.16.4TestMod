package com.example.examplemod.Blocks.BackedModelBlocks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class DoubleSlabModelLoader implements IModelLoader<DoubleSlabGeometry> {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public DoubleSlabGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new DoubleSlabGeometry();
    }
}
