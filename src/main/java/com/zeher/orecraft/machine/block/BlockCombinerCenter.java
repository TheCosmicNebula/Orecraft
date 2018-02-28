package com.zeher.orecraft.machine.block;

import com.zeher.orecraft.machine.tileentity.TileEntityCombinerCenter;
import com.zeher.trzlib.core.block.TRZBlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCombinerCenter extends TRZBlockContainer {
	public BlockCombinerCenter(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab, boolean is_on) {
		super(name, material, tool, harvest, hardness, resistance, tab);
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		TileEntityCombinerCenter tile = (TileEntityCombinerCenter) worldIn.getTileEntity(pos);
		if (!tile.getStackInSlot(0).isEmpty()) {
			if (!playerIn.getHeldItemMainhand().isEmpty()) {
				if (playerIn.getHeldItemMainhand().getItem().equals(tile.getStackInSlot(0).getItem())) {
					playerIn.getHeldItemMainhand().grow(1);
					ItemStack stack = tile.getStackInSlot(0);
					stack.shrink(1);
				}
			} else {
				ItemStack stack = tile.getStackInSlot(0);
				playerIn.setHeldItem(EnumHand.MAIN_HAND, stack.copy());
				stack.shrink(1);
			}
		}
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityCombinerCenter tile = (TileEntityCombinerCenter) worldIn.getTileEntity(pos);
		if ((!playerIn.getHeldItem(hand).isEmpty()) && (tile.getStackInSlot(0).isEmpty())) {
			ItemStack stack = playerIn.getHeldItem(hand);

			tile.setInventorySlotContents(0, stack.copy());
			stack.grow(1);
		}
		return true;
	}

	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCombinerCenter();
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.675D, 1.0D);
	}
}
