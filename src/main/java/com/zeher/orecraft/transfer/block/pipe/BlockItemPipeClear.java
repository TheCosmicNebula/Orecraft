package com.zeher.orecraft.transfer.block.pipe;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.transfer.tileentity.pipe.TileEntityItemPipeClear;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.api.connect.TRZPipeConnectionsList;
import com.zeher.trzlib.transfer.TRZBlockItemPipe;
import com.zeher.trzlib.transfer.TRZTileEntityItemPipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockItemPipeClear extends TRZBlockItemPipe {
	
	protected static final AxisAlignedBB[] BOUNDING_BOXES = TRZUtil.BOUNDING_BOXES_FAT; 
			
	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 0.7D, 0.7D);

	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 0.7D, 1.0D);
	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.0D, 0.7D, 0.7D, 0.7D);

	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 1.0D, 0.7D, 0.7D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.3D, 0.3D, 0.7D, 0.7D, 0.7D);

	protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 1.0D, 0.7D);
	protected static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.7D, 0.7D);

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
	public BlockItemPipeClear(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)));
		this.setHarvestLevel("pickaxe", 2);
		this.setCreativeTab(OreCraft.tab_orecraft_energy);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityItemPipeClear();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityItemPipeClear tile = (TileEntityItemPipeClear) world.getTileEntity(pos);
		if ((tile instanceof TileEntityItemPipeClear) && tile != null) {
			if ((TRZUtil.isHoldingHammer(player)) && (player.isSneaking())) {
				player.swingArm(EnumHand.MAIN_HAND);
				if (!world.isRemote) {
					EntityItem itemDropped = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(),
							new ItemStack(Item.getItemFromBlock(this)));

					world.spawnEntity(itemDropped);
					world.setBlockToAir(pos);
				}
			}
			if ((TRZUtil.isHoldingHammer(player)) && (!player.isSneaking())) {
				player.swingArm(EnumHand.MAIN_HAND);
				if (side.equals(EnumFacing.UP)) {
					int i = tile.getSide("up");
					i = i+1;
					if(i > 2){
						i = 0;
					}
					tile.setSide("up", i);
				}
				if (side.equals(EnumFacing.DOWN)) {
					int i = tile.getSide("down");
					i = i+1;
					if(i > 2){
						i = 0;
					}
					tile.setSide("down", i);
				}
				if (side.equals(EnumFacing.NORTH)) {
					int i = tile.getSide("north");
					i = i+1;
					if(i > 2){
						i = 0;
					}
					tile.setSide("north", i);
				}
				if (side.equals(EnumFacing.SOUTH)) {
					int i = tile.getSide("south");
					i = i+1;
					if(i > 2){
						i = 0;
					}
					tile.setSide("south", i);
				}
				if (side.equals(EnumFacing.EAST)) {
					int i = tile.getSide("east");
					i = i+1;
					if(i > 2){
						i = 0;
					}
					tile.setSide("east", i);
				}
				if (side.equals(EnumFacing.WEST)) {
					int i = tile.getSide("west");
					i = i+1;
					if(i > 2){
						i = 0;
					}
					tile.setSide("west", i);
				}
			}
		}
		return false;
	}
	
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        if (!p_185477_7_)
        {
            state = state.getActualState(worldIn, pos);
        }

        addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);

        if (((Boolean)state.getValue(NORTH)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
        }

        if (((Boolean)state.getValue(EAST)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
        }

        if (((Boolean)state.getValue(WEST)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        }
        if (((Boolean)state.getValue(UP)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
        }
        if (((Boolean)state.getValue(DOWN)).booleanValue())
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_AABB);
        }
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, UP, DOWN});
    }
	
	public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = this.getActualState(state, source, pos);
        return BOUNDING_BOXES[getBoundingBoxIdx(state)];
    }

	private static int getBoundingBoxIdx(IBlockState state)
    {
        int i = 0;

        if (((Boolean)state.getValue(NORTH)).booleanValue())
        {
            i |= 1 << 2;
        }

        if (((Boolean)state.getValue(EAST)).booleanValue())
        {
            i |= 1 << 5;
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue())
        {
            i |= 1 << 3;
        }

        if (((Boolean)state.getValue(WEST)).booleanValue())
        {
            i |= 1 << 4;
        }
        if (((Boolean)state.getValue(UP)).booleanValue())
        {
            i |= 1 << 1;
        }
        if (((Boolean)state.getValue(DOWN)).booleanValue())
        {
            i |= 1 << 0;
        }
        //System.out.println(i);
        return i;
    }
	
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		TileEntity tile = worldIn.getTileEntity(pos);
		int northInt = 0;
		int eastInt = 0;
		int southInt = 0;
		int westInt = 0;
		int downInt = 0;
		int upInt = 0;
		if(tile != null && tile instanceof TileEntityItemPipeClear){
			northInt = ((TileEntityItemPipeClear) tile).getSide("north");
			eastInt = ((TileEntityItemPipeClear) tile).getSide("east");
			southInt = ((TileEntityItemPipeClear) tile).getSide("south");
			westInt = ((TileEntityItemPipeClear) tile).getSide("west");
			downInt = ((TileEntityItemPipeClear) tile).getSide("down");
			upInt = ((TileEntityItemPipeClear) tile).getSide("up");
		} else {
			
		}
		
		boolean upBool = false;
		boolean downBool = false;
		boolean northBool = false;
		boolean southBool = false;
		boolean eastBool = false;
		boolean westBool = false;
		if(upInt == 1){
			upBool = false;
		} else if(upInt == 2){
			upBool = true;
		} else {
			upBool = canFenceConnectTo(worldIn, pos, EnumFacing.UP);
		}
		
		if(downInt == 1){
			downBool = false;
		} else if(downInt == 2){
			downBool = true;
		} else {
			downBool = canFenceConnectTo(worldIn, pos, EnumFacing.DOWN);
		}
		
		if(northInt == 1){
			northBool = false;
		} else if(northInt == 2){
			northBool = true;
		} else {
			northBool = canFenceConnectTo(worldIn, pos, EnumFacing.NORTH);
		}
		
		if(southInt == 1){
			southBool = false;
		} else if(southInt == 2){
			southBool = true;
		} else {
			southBool = canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH);
		}
		
		if(eastInt == 1){
			eastBool = false;
		} else if(eastInt == 2){
			eastBool = true;
		} else {
			eastBool = canFenceConnectTo(worldIn, pos, EnumFacing.EAST);
		}
		
		if(westInt == 1){
			westBool = false;
		} else if(westInt == 2){
			westBool = true;
		} else {
			westBool = canFenceConnectTo(worldIn, pos, EnumFacing.WEST);
		}
		
        return state.withProperty(NORTH, northBool)
                .withProperty(EAST, eastBool)
                .withProperty(SOUTH, southBool)
                .withProperty(WEST, westBool)
                .withProperty(UP, upBool)
                .withProperty(DOWN, downBool);
    }
	
	private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        TileEntity tile = world.getTileEntity(pos.offset(facing));
        if(tile != null){
        	if(tile instanceof TRZTileEntityItemPipe){
        		if(facing.equals(EnumFacing.UP)){
        			if(((TRZTileEntityItemPipe) tile).getSide("down") == 1){
        				return false;
        			} else {
        				return true;
        			}
        		}
        		if(facing.equals(EnumFacing.DOWN)){
        			if(((TRZTileEntityItemPipe) tile).getSide("up") == 1){
        				return false;
        			} else {
        				return true;
        			}
        		}
        		if(facing.equals(EnumFacing.NORTH)){
        			if(((TRZTileEntityItemPipe) tile).getSide("south") == 1){
        				return false;
        			} else {
        				return true;
        			}
        		}
        		if(facing.equals(EnumFacing.SOUTH)){
        			if(((TRZTileEntityItemPipe) tile).getSide("north") == 1){
        				return false;
        			} else {
        				return true;
        			}
        		}
        		if(facing.equals(EnumFacing.EAST)){
        			if(((TRZTileEntityItemPipe) tile).getSide("west") == 1){
        				return false;
        			} else {
        				return true;
        			}
        		}
        		if(facing.equals(EnumFacing.WEST)){
        			if(((TRZTileEntityItemPipe) tile).getSide("east") == 1){
        				return false;
        			} else {
        				return true;
        			}
        		}
        	}
        }
        return block.canBeConnectedTo(world, pos.offset(facing), facing.getOpposite()) || canConnectTo(world, pos.offset(facing), facing);
    }
	
	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return TRZPipeConnectionsList.getItemBlockConnections(worldIn, pos, side);
    }
	
}
