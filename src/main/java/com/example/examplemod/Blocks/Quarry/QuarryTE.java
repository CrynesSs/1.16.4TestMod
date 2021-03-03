package com.example.examplemod.Blocks.Quarry;

import com.example.examplemod.Util.Types.TileEntityTypes;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuarryTE extends TileEntity implements ITickableTileEntity {
    public boolean toggled = false;
    public QuarryTE() {
        super(TileEntityTypes.QUARRY_TE.get());
    }

    public void toggle(){
      this.toggled = !this.toggled;
    }

    private int timer = 0;
    private int area = 9;
    private int stepper = 0;
    private int yOffset = 0;
    public void tick(){
        if(this.world.isRemote){
            return;
        }
        timer++;
        if(timer == 20){
            timer = 0;
            BlockPos start = this.pos.add(-1,-yOffset,-1);
            BlockPos pos = start.add(stepper/Math.sqrt(area),0,stepper%Math.sqrt(area));
            this.world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BASALT_BREAK, SoundCategory.BLOCKS,1.0f,1.0f,false);
            if(world.getBlockState(pos).getBlock() instanceof QuarryBlock){
                stepper++;
                return;
            }
            if(!world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)){
                world.setBlockState(pos, Blocks.AIR.getDefaultState(),3);
            }
            if(pos.getY() == 0){
                toggle();
                return;
            }
            stepper++;
            if(stepper == area){
                stepper = 0;
                yOffset++;
            }

        }
    }

}
