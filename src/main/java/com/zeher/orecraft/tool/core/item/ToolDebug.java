package com.zeher.orecraft.tool.core.item;

import com.zeher.orecraft.storage.core.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.core.item.ItemBase;
import com.zeher.zeherlib.storage.IStorage;
import com.zeher.zeherlib.transfer.IEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ToolDebug extends ItemBase {

	public ToolDebug(String name, CreativeTabs tab, int maxStackSize) {
		super(name, tab, maxStackSize);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		player.swingArm(hand);
		if(player.isSneaking()){
			TileEntity tile = worldIn.getTileEntity(pos);
			Block block = worldIn.getBlockState(pos).getBlock();
			if(tile != null){
				if(tile instanceof TileEntityEnergizedCapacitor){
					int power = ((TileEntityEnergizedCapacitor) tile).getStored();
					String name = ((TileEntityEnergizedCapacitor) tile).getName();
					
						player.sendMessage(new TextComponentString(" - Debug Begin - "
								+ "\n" + "Tile name:" + " " + name
								+ "\n" + "Power Level:" + " " + power));
					
				}
				if(tile instanceof TileEntityEnergizedCapacitor){
					int power = ((TileEntityEnergizedCapacitor) tile).getStored();
					String name = ((TileEntityEnergizedCapacitor) tile).getName();
						player.sendMessage(new TextComponentString(" - Debug Begin - "
								+ "\n" + "Tile name:" + " " + name
								+ "\n" + "Power Level:" + " " + power));
					
				}
				if(tile instanceof IStorage){
					int power = ((IStorage) tile).getStored();
					String name = block.getLocalizedName();

					EnumSide[] side_array = ((IStorage) tile).side_array;
					
					if(!worldIn.isRemote){
					player.sendMessage(new TextComponentString(" - Debug Begin - "
						+ "\n" + "Tile name:" + " " + name 
						+ "\n" + "Power Level:" + " " + power
						+ "\n" + "UP:" + " " + side_array[1] 
							+ "\n" + "DOWN:" + " " + side_array[0]
							+ "\n" + "NORTH:" + " " + side_array[2]
							+ "\n" + "SOUTH:" + " " + side_array[3]
							+ "\n" + "EAST:" + " " + side_array[5]
							+ "\n" + "WEST:" + " " + side_array[4]));
					}
				}
				if(tile instanceof IEnergyPipe){
					
					EnumSide[] side_array = ((IEnergyPipe) tile).getSideArray();
					
					int stored = ((IEnergyPipe) tile).getStored();
					EnumFacing face = ((IEnergyPipe) tile).getLastFrom();
					
					if(!worldIn.isRemote){
						player.sendMessage(new TextComponentString(" - Debug Begin - " + stored + " " + face
								+ "\n" + "UP:" + " " + side_array[1] 
								+ "\n" + "DOWN:" + " " + side_array[0]
								+ "\n" + "NORTH:" + " " + side_array[2]
								+ "\n" + "SOUTH:" + " " + side_array[3]
								+ "\n" + "EAST:" + " " + side_array[5]
								+ "\n" + "WEST:" + " " + side_array[4]));
					}
				}
			}
			return EnumActionResult.FAIL;
		}
        return EnumActionResult.PASS;
    }
	
	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }
}
