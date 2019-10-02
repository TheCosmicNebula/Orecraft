package com.zeher.orecraft.machine.core.block;

import java.util.Random;

import com.zeher.orecraft.core.handler.BlockHandler;
import com.zeher.orecraft.machine.core.tileentity.TileEntityCombinerPedestal;
import com.zeher.zeherlib.core.block.ModBlockContainer;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCombinerPedestal extends ModBlockContainer {
	public final boolean is_on;

	public BlockCombinerPedestal(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab, boolean is_on) {
		super(name, material, tool, harvest, hardness, resistance, tab);
		this.is_on = is_on;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		TileEntityCombinerPedestal tile = (TileEntityCombinerPedestal) worldIn.getTileEntity(pos);
		if (!tile.getStackInSlot(0).isEmpty()) {
			if (!tile.getStackInSlot(0).isEmpty()) {
				playerIn.inventory.addItemStackToInventory(tile.getStackInSlot(0));
			}
		}
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityCombinerPedestal tile = (TileEntityCombinerPedestal) worldIn.getTileEntity(pos);
		if ((!playerIn.getHeldItem(hand).isEmpty()) && (tile.getStackInSlot(0).isEmpty())) {
			ItemStack stack = playerIn.getHeldItem(hand);

			tile.setInventorySlotContents(0, stack.copy());
			stack.shrink(1);
		}
		return true;
	}

	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCombinerPedestal();
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.is_on) {
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.2D, pos.getY() + 0.5D, pos.getZ() + 0.2D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.2D, pos.getY() + 0.5D, pos.getZ() + 0.8D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.8D, pos.getY() + 0.5D, pos.getZ() + 0.8D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.8D, pos.getY() + 0.5D, pos.getZ() + 0.2D, 0.0D, 0.0D, 0.0D, new int[0]);

			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.3D, pos.getY() + 0.5D, pos.getZ() + 0.3D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.3D, pos.getY() + 0.5D, pos.getZ() + 0.7D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.7D, pos.getY() + 0.5D, pos.getZ() + 0.7D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.7D, pos.getY() + 0.5D, pos.getZ() + 0.3D, 0.0D, 0.0D, 0.0D, new int[0]);

			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.1D, pos.getY() + 0.5D, pos.getZ() + 0.1D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.1D, pos.getY() + 0.5D, pos.getZ() + 0.9D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.9D, pos.getY() + 0.5D, pos.getZ() + 0.9D, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.9D, pos.getY() + 0.5D, pos.getZ() + 0.1D, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		ItemStack stack = ((TileEntityCombinerPedestal) tileentity).getStackInSlot(0);
		
		if (!active) {
			worldIn.setBlockState(pos, BlockHandler.REGISTRATION.MACHINE.BLOCK_COMBINER_PEDESTAL.getDefaultState(), 3);
			worldIn.setBlockState(pos, BlockHandler.REGISTRATION.MACHINE.BLOCK_COMBINER_PEDESTAL.getDefaultState(), 3);
		}
		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
			((TileEntityCombinerPedestal) worldIn.getTileEntity(pos)).setInventorySlotContents(0, stack);
		}
	}
}
