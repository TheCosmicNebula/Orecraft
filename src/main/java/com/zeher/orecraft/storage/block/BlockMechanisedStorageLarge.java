package com.zeher.orecraft.storage.block;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.storage.tileentity.TileEntityMechanisedStorageLarge;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.storage.TRZBlockItemStorage;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockMechanisedStorageLarge extends TRZBlockItemStorage {
	
	protected static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.05D, 0, 0.05D, 0.95D, 1D, 0.95D);

	public BlockMechanisedStorageLarge(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(name, material, tool, harvest, hardness, resistance, tab);
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!(worldIn.isRemote) && !(playerIn.isSneaking()) && !(TRZUtil.isHoldingHammer(playerIn))) {
			FMLNetworkHandler.openGui(playerIn, OreCraft.instance, 58, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMechanisedStorageLarge();
    }
	
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this.bounding_box;
    }
	
}
