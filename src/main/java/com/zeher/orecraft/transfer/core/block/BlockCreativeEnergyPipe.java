package com.zeher.orecraft.transfer.core.block;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityCreativeEnergyPipe;
import com.zeher.zeherlib.api.connection.Connection;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.api.util.ModUtil;
import com.zeher.zeherlib.transfer.BlockEnergyPipe;

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
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockCreativeEnergyPipe extends BlockEnergyPipe {
	
	protected static final AxisAlignedBB[] BOUNDING_BOXES = ModUtil.BOUNDING_BOXES_FAT;
	
	/**protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 0.7D, 0.7D);

	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 0.7D, 1.0D);
	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.0D, 0.7D, 0.7D, 0.7D);

	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 1.0D, 0.7D, 0.7D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.3D, 0.3D, 0.7D, 0.7D, 0.7D);

	protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 1.0D, 0.7D);
	protected static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.7D, 0.7D);
	*/
	
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
	public BlockCreativeEnergyPipe(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)));
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(8);
		this.setResistance(6);
		this.setCreativeTab(Orecraft.TAB_ENERGY);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityCreativeEnergyPipe();
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
	
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!(worldIn.isRemote) && !(playerIn.isSneaking()) && (ModUtil.isHoldingHammer(playerIn))) {
			FMLNetworkHandler.openGui(playerIn, Orecraft.INSTANCE, 60, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		// this.sendEnergy(world, x, y, z);
		TileEntityCreativeEnergyPipe tile = (TileEntityCreativeEnergyPipe) world.getTileEntity(pos);
		if ((tile instanceof TileEntityCreativeEnergyPipe) && tile != null) {
			if ((ModUtil.isHoldingHammer(player)) && (player.isSneaking())) {
				player.swingArm(EnumHand.MAIN_HAND);
				if (!world.isRemote) {
					EntityItem itemDropped = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getItemFromBlock(this)));

					world.spawnEntity(itemDropped);
					world.setBlockToAir(pos);
				}
			}
			
			if ((ModUtil.isHoldingHammer(player)) && (!player.isSneaking())) {
				player.swingArm(EnumHand.MAIN_HAND);
				
				tile.cycleside(side);
			}
		}
		return false;
	}
	
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        if (!p_185477_7_) {
            state = state.getActualState(worldIn, pos);
        }

        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[0]);

        if (((Boolean)state.getValue(NORTH)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[4]);
        }

        if (((Boolean)state.getValue(EAST)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[32]);
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[8]);
        }

        if (((Boolean)state.getValue(WEST)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[16]);
        }
        
        if (((Boolean)state.getValue(UP)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[2]);
        }
        
        if (((Boolean)state.getValue(DOWN)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[1]);
        }
    }
	
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, UP, DOWN});
    }
	
	public int getMetaFromState(IBlockState state) {
        return 0;
    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = this.getActualState(state, source, pos);
        return BOUNDING_BOXES[getBoundingBoxIdx(state)];
    }

	private static int getBoundingBoxIdx(IBlockState state) {
        int i = 0;

        if (((Boolean)state.getValue(NORTH)).booleanValue()) {
            i |= 1 << 2;
        }

        if (((Boolean)state.getValue(EAST)).booleanValue()) {
            i |= 1 << 5;
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue()) {
            i |= 1 << 3;
        }

        if (((Boolean)state.getValue(WEST)).booleanValue()) {
            i |= 1 << 4;
        }
        
        if (((Boolean)state.getValue(UP)).booleanValue()) {
            i |= 1 << 1;
        }
        if (((Boolean)state.getValue(DOWN)).booleanValue()) {
            i |= 1 << 0;
        }
        //System.out.println(i);
        return i;
    }
	
	public boolean getSide(EnumFacing side, World world, BlockPos pos){
		TileEntity tile = world.getTileEntity(pos);

		EnumSide[] side_array = new EnumSide[] { EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO };

		if(tile != null && tile instanceof TileEntityCreativeEnergyPipe){
			side_array = ((TileEntityCreativeEnergyPipe)tile).getSideArray();
		}
		
		boolean upBool = false;
		boolean downBool = false;
		boolean northBool = false;
		boolean southBool = false;
		boolean eastBool = false;
		boolean westBool = false;
		
		if(side.equals(EnumFacing.DOWN)){
			if(side_array[0].equals(EnumSide.NONE)){
				downBool = false;
			} else if(side_array[0].equals(EnumSide.INPUT_FROM)){
				downBool = true;
			} else {
				downBool = canConnectToBlock(world, pos, EnumFacing.DOWN);
			}
			return downBool;
		}
		
		if(side.equals(EnumFacing.UP)){
			if(side_array[1].equals(EnumSide.NONE)){
				upBool = false;
			} else if(side_array[1].equals(EnumSide.INPUT_FROM)){
				upBool = true;
			} else {
				upBool = canConnectToBlock(world, pos, EnumFacing.UP);
			}
			return upBool;
		}
		
		if(side.equals(EnumFacing.NORTH)){
			if(side_array[2].equals(EnumSide.NONE)){
				northBool = false;
			} else if(side_array[2].equals(EnumSide.INPUT_FROM)){
				northBool = true;
			} else {
				northBool = canConnectToBlock(world, pos, EnumFacing.NORTH);
			}
			return northBool;
		}
		
		if(side.equals(EnumFacing.SOUTH)){
			if(side_array[3].equals(EnumSide.NONE)){
				southBool = false;
			} else if(side_array[3].equals(EnumSide.INPUT_FROM)){
				southBool = true;
			} else {
				southBool = canConnectToBlock(world, pos, EnumFacing.SOUTH);
			}
			return southBool;
		}
		
		if(side.equals(EnumFacing.WEST)){
			if(side_array[4].equals(EnumSide.NONE)){
				westBool = false;
			} else if(side_array[4].equals(EnumSide.INPUT_FROM)){
				westBool = true;
			} else {
				westBool = canConnectToBlock(world, pos, EnumFacing.WEST);
			}
			return westBool;
		}
		
		if(side.equals(EnumFacing.EAST)){
			if(side_array[5].equals(EnumSide.NONE)){
				eastBool = false;
			} else if(side_array[5].equals(EnumSide.INPUT_FROM)){
				eastBool = true;
			} else {
				eastBool = canConnectToBlock(world, pos, EnumFacing.EAST);
			}
			return eastBool;
		}
		return false;
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity tile = worldIn.getTileEntity(pos);

		EnumSide[] side_array = new EnumSide[] { EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO, EnumSide.OUTPUT_TO };

		if(tile != null && tile instanceof TileEntityCreativeEnergyPipe){
			side_array = ((TileEntityCreativeEnergyPipe)tile).getSideArray();
		}
		
		boolean upBool = false;
		boolean downBool = false;
		boolean northBool = false;
		boolean southBool = false;
		boolean eastBool = false;
		boolean westBool = false;
		
		if(side_array[0].equals(EnumSide.NONE)){
			downBool = false;
		} else if(side_array[0].equals(EnumSide.INPUT_FROM)){
			downBool = true;
		} else {
			downBool = canConnectToBlock(worldIn, pos, EnumFacing.DOWN);
		}
		
		if(side_array[1].equals(EnumSide.NONE)){
			upBool = false;
		} else if(side_array[1].equals(EnumSide.INPUT_FROM)){
			upBool = true;
		} else {
			upBool = canConnectToBlock(worldIn, pos, EnumFacing.UP);
		}
		
		if(side_array[2].equals(EnumSide.NONE)){
			northBool = false;
		} else if(side_array[2].equals(EnumSide.INPUT_FROM)){
			northBool = true;
		} else {
			northBool = canConnectToBlock(worldIn, pos, EnumFacing.NORTH);
		}
		
		if(side_array[3].equals(EnumSide.NONE)){
			southBool = false;
		} else if(side_array[3].equals(EnumSide.INPUT_FROM)){
			southBool = true;
		} else {
			southBool = canConnectToBlock(worldIn, pos, EnumFacing.SOUTH);
		}
		
		if(side_array[4].equals(EnumSide.NONE)){
			westBool = false;
		} else if(side_array[4].equals(EnumSide.INPUT_FROM)){
			westBool = true;
		} else {
			westBool = canConnectToBlock(worldIn, pos, EnumFacing.WEST);
		}
		
		if(side_array[5].equals(EnumSide.NONE)){
			eastBool = false;
		} else if(side_array[5].equals(EnumSide.INPUT_FROM)){
			eastBool = true;
		} else {
			eastBool = canConnectToBlock(worldIn, pos, EnumFacing.EAST);
		}
		
		
        return state.withProperty(NORTH, northBool)
                .withProperty(EAST, eastBool)
                .withProperty(SOUTH, southBool)
                .withProperty(WEST, westBool)
                .withProperty(UP, upBool)
                .withProperty(DOWN, downBool);
    }
	
	private boolean canConnectToBlock(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		Block block = world.getBlockState(pos.offset(facing)).getBlock();
        IBlockState state = world.getBlockState(pos.offset(facing));
        TileEntity tile_offset = world.getTileEntity(pos.offset(facing));
        
        if (tile_offset != null) {
        	return Connection.PIPE.BLOCK.canEnergyConnectTo(tile_offset, facing, state);
        }
        
        return block.canBeConnectedTo(world, pos.offset(facing), facing.getOpposite()) || canConnectTo(world, pos.offset(facing), facing);
    }
	
	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return Connection.PIPE.BLOCK.getEnergyConnectionsAccess(worldIn, pos, side);
    }
	
}
