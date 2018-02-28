package com.zeher.orecraft.storage.block;

import java.util.ArrayList;
import java.util.Random;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.storage.tileentity.TileEntityPoweredCapacitor;
import com.zeher.trzlib.api.TRZBlockPos;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.storage.TRZBlockCapacitor;
import com.zeher.trzlib.transfer.TRZBlockEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockPoweredCapacitor extends TRZBlockCapacitor{
	
	private final Random capacitor_random = new Random();
	private boolean keepCapacitorInventory;
	protected boolean needsRandomTick = true;
	
	public static final PropertyInteger NORTH = PropertyInteger.create("north", 0, 2);
    public static final PropertyInteger EAST = PropertyInteger.create("east", 0, 2);
    public static final PropertyInteger SOUTH = PropertyInteger.create("south", 0, 2);
    public static final PropertyInteger WEST = PropertyInteger.create("west", 0, 2);
    public static final PropertyInteger UP = PropertyInteger.create("up", 0, 2);
    public static final PropertyInteger DOWN = PropertyInteger.create("down", 0, 2);
    
    public int _power;
    
	public BlockPoweredCapacitor(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(NORTH, 0)
				.withProperty(EAST, 0)
				.withProperty(SOUTH, 0)
				.withProperty(WEST, 0)
				.withProperty(UP, 0)
				.withProperty(DOWN, 0));
		setHardness(8);
		setResistance(10);
		this.setCreativeTab(OreCraft.tab_orecraft_energy);
	}
	
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		Item item = Item.getItemFromBlock(this);
		ItemStack stack = new ItemStack(this, 1, 1000);
		return stack.getItem();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	public TileEntity createNewTileEntity(World world, int i){
		return new TileEntityPoweredCapacitor();
	}
	
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
		if(!(worldIn.isRemote) && !(playerIn.isSneaking()) && (TRZUtil.isHoldingHammer(playerIn))){
			FMLNetworkHandler.openGui(playerIn, OreCraft.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		if(!worldIn.isRemote){
			ArrayList<String> side_list = this.checkSides(pos, worldIn);
			String up = "null";
			String down = "null";
			String north = "null";
			String south = "null";
			String east = "null";
			String west = "null";
			
			for(int i = 0; i < side_list.size(); i++){
				String check = side_list.get(i);
				if(check.contains("up_")){
					up = check.substring(3);
				}
				if(check.contains("down_")){
					down = check.substring(5);
				}
				if(check.contains("north_")){
					north = check.substring(6);
				}
				if(check.contains("south_")){
					south = check.substring(6);
				}
				if(check.contains("east_")){
					east = check.substring(5);
				}
				if(check.contains("west_")){
					west = check.substring(5);
				}
			}
			System.out.println("Up: " + up);
			System.out.println("Down: " + down);
			System.out.println("North: " + north);
			System.out.println("South: " + south);
			System.out.println("East: " + east);
			System.out.println("West: " + west);
		}
	}
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		worldIn.setBlockState(pos, state);
		worldIn.notifyBlockUpdate(pos, state, state, 3);
		
		this.getActualState(state, worldIn, pos);
		if(!(worldIn.isRemote) && !(playerIn.isSneaking()) && !(TRZUtil.isHoldingHammer(playerIn))){
			FMLNetworkHandler.openGui(playerIn, OreCraft.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		if((TRZUtil.isHoldingHammer(playerIn)) && (playerIn.isSneaking()) && !(worldIn.isRemote)){
			playerIn.swingArm(EnumHand.MAIN_HAND);
			
			TileEntityPoweredCapacitor tile = (TileEntityPoweredCapacitor)worldIn.getTileEntity(pos);
			int set_power = tile.getStored();
			
			ItemBlockPoweredCapacitor item1 = (ItemBlockPoweredCapacitor) ItemBlock.getItemFromBlock(this);
			ItemStack stack = new ItemStack(item1);
			item1.setDamage(stack, item1.getMaxDamage() - (set_power / 1000));
			
			spawnAsEntity(worldIn, pos, stack);
			
			worldIn.destroyBlock(pos, false);
			return false;
		}
		TileEntityPoweredCapacitor tile = (TileEntityPoweredCapacitor) worldIn.getTileEntity(pos);
		if ((TRZUtil.isHoldingHammer(playerIn)) && (!playerIn.isSneaking())) {
			playerIn.swingArm(EnumHand.MAIN_HAND);
			if (side.equals(EnumFacing.UP)) {
				if(worldIn.isRemote){
					tile.cycleSide("up");
				} else {
					tile.cycleSide("up");
				}
			}
			if (side.equals(EnumFacing.DOWN)) {
				if(worldIn.isRemote){
					tile.cycleSide("down");
				} else {
					tile.cycleSide("down");
				}
			}
			if (side.equals(EnumFacing.NORTH)) {
				if(worldIn.isRemote){
					tile.cycleSide("north");
				} else {
					tile.cycleSide("north");
				}
			}
			if (side.equals(EnumFacing.SOUTH)) {
				if(worldIn.isRemote){
					tile.cycleSide("south");
				} else {
					tile.cycleSide("south");
				}
			}
			if (side.equals(EnumFacing.EAST)) {
				if(worldIn.isRemote){
					tile.cycleSide("east");
				} else {
					tile.cycleSide("east");
				}
			}
			if (side.equals(EnumFacing.WEST)) {
				if(worldIn.isRemote){
					tile.cycleSide("west");
				} else {
					tile.cycleSide("west");
				}
			}
			return false;
		}
        return true;
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, UP, DOWN});
    }
	
	public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
	
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {	
		TileEntityPoweredCapacitor tile = (TileEntityPoweredCapacitor)worldIn.getTileEntity(pos);
		
		return state.withProperty(NORTH, tile.getSide("north"))
                .withProperty(EAST, tile.getSide("east"))
                .withProperty(SOUTH, tile.getSide("south"))
                .withProperty(WEST, tile.getSide("west"))
                .withProperty(UP, tile.getSide("up"))
                .withProperty(DOWN, tile.getSide("down"));
	}
	
	public boolean getSide(EnumFacing side, World world, BlockPos pos){
		TileEntity tile = world.getTileEntity(pos);
		int northInt = 0;
		int eastInt = 0;
		int southInt = 0;
		int westInt = 0;
		int downInt = 0;
		int upInt = 0;
		if(tile != null && tile instanceof TileEntityPoweredCapacitor){
			northInt = ((TileEntityPoweredCapacitor) tile).getSide("north");
			eastInt = ((TileEntityPoweredCapacitor) tile).getSide("east");
			southInt = ((TileEntityPoweredCapacitor) tile).getSide("south");
			westInt = ((TileEntityPoweredCapacitor) tile).getSide("west");
			downInt = ((TileEntityPoweredCapacitor) tile).getSide("down");
			upInt = ((TileEntityPoweredCapacitor) tile).getSide("up");
		} else {
			
		}
		
		boolean upBool = false;
		boolean downBool = false;
		boolean northBool = false;
		boolean southBool = false;
		boolean eastBool = false;
		boolean westBool = false;
		if(side.equals(EnumFacing.UP)){
			if(upInt == 1){
				upBool = false;
			} else if(upInt == 2 || upInt == 0){
				upBool = true;
			}
			return upBool;
		}
		
		if(side.equals(EnumFacing.DOWN)){
			if(downInt == 1){
				downBool = false;
			} else if(downInt == 2 || downInt == 0){
				downBool = true;
			}
			return downBool;
		}
		if(side.equals(EnumFacing.NORTH)){
			if(northInt == 1){
				northBool = false;
			} else if(northInt == 2 || northInt == 0){
				northBool = true;
			}
			return northBool;
		}
		if(side.equals(EnumFacing.SOUTH)){
			if(southInt == 1){
				southBool = false;
			} else if(southInt == 2 || southInt == 0){
				southBool = true;
			}
			return southBool;
		}
		if(side.equals(EnumFacing.EAST)){
			if(eastInt == 1){
				eastBool = false;
			} else if(eastInt == 2 || eastInt == 0){
				eastBool = true;
			}
			return eastBool;
		}
		if(side.equals(EnumFacing.WEST)){
			if(westInt == 1){
				westBool = false;
			} else if(westInt == 2 || westInt == 0){
				westBool = true;
			}
			return westBool;
		}
		return false;
	}
	
	public ArrayList<String> checkSides(BlockPos pos, World worldIn){
		ArrayList<String> side_list = new ArrayList<String>();
		TRZBlockPos posUp = new TRZBlockPos(pos);
		posUp.orientation = EnumFacing.UP;
		posUp.moveForwards(1);
		Block blockUp = worldIn.getBlockState(posUp.pos).getBlock();
		if(blockUp instanceof TRZBlockEnergyPipe || blockUp instanceof TRZBlockCapacitor){
			side_list.add("up_true");
		} else {
			side_list.add("up_false");
		}
		
		TRZBlockPos posDown = new TRZBlockPos(pos);
		posDown.orientation = EnumFacing.DOWN;
		posDown.moveForwards(1);
		Block blockDown = worldIn.getBlockState(posDown.pos).getBlock();
		if(blockDown instanceof TRZBlockEnergyPipe || blockDown instanceof TRZBlockCapacitor){
			side_list.add("down_true");
		} else {
			side_list.add("down_false");
		}
		
		TRZBlockPos posNorth = new TRZBlockPos(pos);
		posNorth.orientation = EnumFacing.NORTH;
		posNorth.moveForwards(1);
		Block blockNorth = worldIn.getBlockState(posNorth.pos).getBlock();
		if(blockNorth instanceof TRZBlockEnergyPipe || blockNorth instanceof TRZBlockCapacitor){
			side_list.add("north_true");
		} else {
			side_list.add("north_false");
		}
		
		TRZBlockPos posSouth = new TRZBlockPos(pos);
		posSouth.orientation = EnumFacing.SOUTH;
		posSouth.moveForwards(1);
		Block blockSouth = worldIn.getBlockState(posSouth.pos).getBlock();
		if(blockSouth instanceof TRZBlockEnergyPipe || blockSouth instanceof TRZBlockCapacitor){
			side_list.add("south_true");
		} else {
			side_list.add("south_false");
		}
		
		TRZBlockPos posEast = new TRZBlockPos(pos);
		posEast.orientation = EnumFacing.EAST;
		posEast.moveForwards(1);
		Block blockEast = worldIn.getBlockState(posEast.pos).getBlock();
		if(blockEast instanceof TRZBlockEnergyPipe || blockEast instanceof TRZBlockCapacitor){
			side_list.add("east_true");
		} else {
			side_list.add("east_false");
		}
		
		TRZBlockPos posWest = new TRZBlockPos(pos);
		posWest.orientation = EnumFacing.WEST;
		posWest.moveForwards(1);
		Block blockWest = worldIn.getBlockState(posWest.pos).getBlock();
		if(blockWest instanceof TRZBlockEnergyPipe || blockWest instanceof TRZBlockCapacitor){
			side_list.add("west_true");
		} else {
			side_list.add("west_false");
		}
		return side_list;
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
	    if (!keepCapacitorInventory)
	    {
	      TileEntityPoweredCapacitor tileentityenergystorage_1 = (TileEntityPoweredCapacitor)worldIn.getTileEntity(pos);
	      if (tileentityenergystorage_1 != null)
	      {
	        for (int j1 = 0; j1 < tileentityenergystorage_1.getSizeInventory(); j1++)
	        {
	          ItemStack itemstack = tileentityenergystorage_1.getStackInSlot(j1);
	          if (itemstack != null)
	          {
	            float f = this.capacitor_random.nextFloat() * 0.8F + 0.1F;
	            float f1 = this.capacitor_random.nextFloat() * 0.8F + 0.1F;
	            float f2 = this.capacitor_random.nextFloat() * 0.8F + 0.1F;
	            while (itemstack.getCount() > 0)
	            {
	              int k1 = this.capacitor_random.nextInt(21) + 10;
	              if (k1 > itemstack.getCount()) {
	                k1 = itemstack.getCount();
	              }
	              itemstack.setCount(itemstack.getCount() - k1);
	              EntityItem entityitem = new EntityItem(worldIn, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
	              if (itemstack.hasTagCompound()) {
	                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
	              }
	              float f3 = 0.05F;
	              entityitem.posX = ((float)this.capacitor_random.nextGaussian() * f3);
	              entityitem.posY = ((float)this.capacitor_random.nextGaussian() * f3 + 0.2F);
	              entityitem.posZ = ((float)this.capacitor_random.nextGaussian() * f3);
	              worldIn.spawnEntity(entityitem);
	            }
	          }
	        }
	      }
	    }
	    super.breakBlock(worldIn, pos, state);
	  }
}