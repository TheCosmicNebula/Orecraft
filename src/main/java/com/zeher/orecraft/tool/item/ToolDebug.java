package com.zeher.orecraft.tool.item;

import com.zeher.orecraft.storage.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.trzlib.core.item.TRZItemBase;
import com.zeher.trzlib.storage.TRZTileEntityCapacitor;
import com.zeher.trzlib.transfer.TRZTileEntityEnergyPipe;

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

public class ToolDebug extends TRZItemBase {

	public ToolDebug(String name, CreativeTabs tab, int maxStackSize) {
		super(name, tab, maxStackSize);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		player.swingArm(hand);
		if(player.isSneaking()){
			TileEntity tile = worldIn.getTileEntity(pos);
			Block block = worldIn.getBlockState(pos).getBlock();
			if(!tile.equals(null)){
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
				if(tile instanceof TRZTileEntityCapacitor){
					int power = ((TRZTileEntityCapacitor) tile).getStored();
					//String name = tile.getDisplayName().toString();
					String name = block.getLocalizedName();
					
					int up = ((TRZTileEntityCapacitor)tile).getSide("up");
					int down = ((TRZTileEntityCapacitor)tile).getSide("down");
					int north = ((TRZTileEntityCapacitor)tile).getSide("north");
					int south = ((TRZTileEntityCapacitor)tile).getSide("south");
					int east = ((TRZTileEntityCapacitor)tile).getSide("east");
					int west = ((TRZTileEntityCapacitor)tile).getSide("west");
					if(!worldIn.isRemote){
					player.sendMessage(new TextComponentString(" - Debug Begin - "
						+ "\n" + "Tile name:" + " " + name 
						+ "\n" + "Power Level:" + " " + power
						+ "\n" + "UP:" + " " + up 
						+ "\n" + "DOWN:" + " " + down
						+ "\n" + "NORTH:" + "" + north
						+ "\n" + "SOUTH:" + "" + south
						+ "\n" + "EAST:" + "" + east
						+ "\n" + "WEST:" + "" + west));
					}
				}
				if(tile instanceof TRZTileEntityEnergyPipe){
					int up = ((TRZTileEntityEnergyPipe)tile).getSide("up");
					int down = ((TRZTileEntityEnergyPipe)tile).getSide("down");
					int north = ((TRZTileEntityEnergyPipe)tile).getSide("north");
					int south = ((TRZTileEntityEnergyPipe)tile).getSide("south");
					int east = ((TRZTileEntityEnergyPipe)tile).getSide("east");
					int west = ((TRZTileEntityEnergyPipe)tile).getSide("west");
					
					int stored = ((TRZTileEntityEnergyPipe) tile).getStored();
					EnumFacing face = ((TRZTileEntityEnergyPipe) tile).getLastFrom();
					
					if(!worldIn.isRemote){
						player.sendMessage(new TextComponentString(" - Debug Begin - " + stored + " " + face
								+ "\n" + "UP:" + " " + up 
								+ "\n" + "DOWN:" + " " + down
								+ "\n" + "NORTH:" + "" + north
								+ "\n" + "SOUTH:" + "" + south
								+ "\n" + "EAST:" + "" + east
								+ "\n" + "WEST:" + "" + west));
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
