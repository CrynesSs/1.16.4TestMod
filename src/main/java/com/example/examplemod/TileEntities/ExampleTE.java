package com.example.examplemod.TileEntities;

import com.example.examplemod.Util.Types.TileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class ExampleTE extends TileEntity {
    private final Block attached;
    public ExampleTE() {
        super(TileEntityTypes.EXAMPLE_TE.get());
        attached = null;
    }
    public ExampleTE(Block attached){
        super(TileEntityTypes.EXAMPLE_TE.get());
        this.attached = attached;
    }
}
