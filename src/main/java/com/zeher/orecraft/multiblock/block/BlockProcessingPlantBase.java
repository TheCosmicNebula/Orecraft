package com.zeher.orecraft.multiblock.block;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.orecraft.core.handlers.BlockHandler;
import com.zeher.orecraft.storage.util.StorageUtil;
import com.zeher.trzlib.core.block.TRZBlockContainer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockProcessingPlantBase extends TRZBlockContainer {

	
	public static final PropertyBool is_multi_bool = PropertyBool.create("is_multi");
	private final boolean is_multi;

	public BlockProcessingPlantBase(Material material, String name, String tool, int harvest, int hardness, int resistance, CreativeTabs tab, boolean is_multi) {
		super(name, material, tool, harvest, hardness, resistance, tab);
		this.is_multi = is_multi;
		this.setDefaultState(this.blockState.getBaseState().withProperty(is_multi_bool, is_multi));
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState block_state = world.getBlockState(pos);
		Block block = block_state.getBlock();
		if(block instanceof BlockProcessingPlantBase){
			((BlockProcessingPlantBase) block).setState(is_multi, world, pos);
			
			System.out.println("" + is_multi);
			return true;
		}
		return true;
	}
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.setBlockState(pos, state.withProperty(is_multi_bool, is_multi));
    }

	public static void setState(boolean is_multi_, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		TileEntity tile = world.getTileEntity(pos);
		
		if(is_multi_){
			world.setBlockState(pos, BlockHandler.block_processingplant_base.getDefaultState().withProperty(is_multi_bool, false), 3);
			world.setBlockState(pos, BlockHandler.block_processingplant_base.getDefaultState().withProperty(is_multi_bool, false), 3);
		} else {
			world.setBlockState(pos, BlockHandler.block_processingplant_base_multi.getDefaultState().withProperty(is_multi_bool, true), 3);
			world.setBlockState(pos, BlockHandler.block_processingplant_base_multi.getDefaultState().withProperty(is_multi_bool, true), 3);
		}
		StorageUtil.syncBlockAndRerender(world, pos);
		
		if(tile != null){
			tile.validate();
			world.setTileEntity(pos, tile);
		}
	}
	
	public TileEntity createNewTileEntity(World world, int meta){
		return null;
	}
	
	public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }
    
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(BlockHandler.block_processingplant_base);
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
	public boolean isOpaqueCube(IBlockState state) {
		return !is_multi;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return !is_multi;
	}
	
	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {is_multi_bool});
    }
    
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
	
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {	
		Block block = state.getBlock();
		
		if(block instanceof BlockProcessingPlantBase){
			return state.withProperty(is_multi_bool, ((BlockProcessingPlantBase)block).is_multi);
		}
		
		return state.withProperty(is_multi_bool, false);
    }
	
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB box_entity, List<AxisAlignedBB> box_collide, @Nullable Entity entity, boolean bool){
		if(is_multi){
			addCollisionBoxToList(pos, box_entity, box_collide, new AxisAlignedBB(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f));
		} else {
			addCollisionBoxToList(pos, box_entity, box_collide, new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f));
		}
	}
		
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if(is_multi){
			return new AxisAlignedBB(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f);
		} else {
			return new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
    
    @Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.TRANSLUCENT);
	}
}
