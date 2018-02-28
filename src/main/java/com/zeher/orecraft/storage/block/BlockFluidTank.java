package com.zeher.orecraft.storage.block;

import java.util.Random;

import com.zeher.orecraft.core.blockstate.StringProperty;
import com.zeher.orecraft.storage.tileentity.TileEntityFluidTank;
import com.zeher.orecraft.storage.util.StorageUtil;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.storage.TRZBlockFluidTank;
import com.zeher.trzlib.storage.TRZFluidUtil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidTank extends TRZBlockFluidTank implements ITileEntityProvider {

	public static final IUnlistedProperty<Integer> FluidLevel = new Properties.PropertyAdapter<Integer>(
			PropertyInteger.create("fluidLevel", 0, 16));
	public static final IUnlistedProperty<String> FluidName = new StringProperty("fluidName");
	public static final IUnlistedProperty<Boolean> CullFluidTop = new Properties.PropertyAdapter<Boolean>(
			PropertyBool.create("cullFluidTop"));

	public static final PropertyInteger NORTH = PropertyInteger.create("north", 0, 2);
	public static final PropertyInteger EAST = PropertyInteger.create("east", 0, 2);
	public static final PropertyInteger SOUTH = PropertyInteger.create("south", 0, 2);
	public static final PropertyInteger WEST = PropertyInteger.create("west", 0, 2);
	public static final PropertyInteger UP = PropertyInteger.create("up", 0, 2);
	public static final PropertyInteger DOWN = PropertyInteger.create("down", 0, 2);

	public BlockFluidTank(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(name, material, tool, harvest, hardness, resistance, tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, 0).withProperty(EAST, 0).withProperty(SOUTH, 0).withProperty(WEST, 0).withProperty(UP, 0).withProperty(DOWN, 0));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return StorageUtil.getTankStackFromTile((TileEntityFluidTank) world.getTileEntity(pos), true, this);
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFluidTank();
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

	protected BlockStateContainer createBlockState() {
		IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { FluidLevel, FluidName, CullFluidTop };
		IProperty[] listedProperties = new IProperty[] { NORTH, EAST, WEST, SOUTH, UP, DOWN };
		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IExtendedBlockState extendedState = (IExtendedBlockState) state;

		TileEntityFluidTank tankEntity = (TileEntityFluidTank) world.getTileEntity(pos);

		if (tankEntity != null) {
			Fluid tankFluid = tankEntity.getCurrentStoredFluid();
			int fluidLevel = tankEntity.getFillLevel();

			return extendedState.withProperty(FluidName, (tankFluid != null) ? tankFluid.getName() : "")
					.withProperty(FluidLevel, fluidLevel).withProperty(CullFluidTop, false);
		}

		return extendedState.withProperty(FluidName, "").withProperty(FluidLevel, 0).withProperty(CullFluidTop, false);
	}

	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.inventory.getCurrentItem();
		player.swingArm(hand);
		if ((TRZUtil.isHoldingHammer(player)) && (player.isSneaking()) && !(world.isRemote)) {
			System.out.println();
			TileEntityFluidTank tank = (TileEntityFluidTank) world.getTileEntity(pos);
			player.swingArm(hand);
			TRZFluidUtil.dropStackInWorld(world, pos, StorageUtil.getTankStackFromTile(tank, true, this));
			world.destroyBlock(pos, false);
			
			return true;
		}

		if (stack.isEmpty()) {
			if (world.isRemote) {
				TileEntityFluidTank tank = (TileEntityFluidTank) world.getTileEntity(pos);
				if (tank.getCurrentStoredAmount() > 0) {
					Fluid fluid = tank.getCurrentStoredFluid();
					FluidStack fluidStack = new FluidStack(fluid, 0);
					String fluidName = tank.getCurrentStoredFluid().getLocalizedName(fluidStack);
					player.sendMessage(
							new TextComponentString("Fluid tank: [" + fluidName + "] (" + tank.getCurrentStoredAmount() + " / " + tank.getCapacity() + " mB)"));
				} else {
					player.sendMessage(
							new TextComponentString("Fluid tank: [Empty] (" + "0" +  " / " + tank.getCapacity() + " mB)"));
				}
			}
		}

		if (stack != null) {
			FluidStack liquid = FluidUtil.getFluidContained(stack);
			TileEntityFluidTank tank = (TileEntityFluidTank) world.getTileEntity(pos);
			if (liquid != null) {
				int amount = tank.fill(liquid, false);
				if (amount == liquid.amount) {
					tank.fill(liquid, true);
					if (!player.capabilities.isCreativeMode)
						player.inventory.setInventorySlotContents(player.inventory.currentItem,
								TRZFluidUtil.useItemSafely(stack));
					if (tank.tank.getFluid().getFluid().getBlock() != null) {
						//world.setLightFor(EnumSkyBlock.SKY, tank.getPos(),
								//tank.tank.getFluid().getFluid().getBlock().getLightValue(state));
					}
					StorageUtil.syncBlockAndRerender(world, pos);
					return true;
				} else
					StorageUtil.syncBlockAndRerender(world, pos);
				return true;
			} else if (stack.getItem() instanceof ItemBucket) {
				FluidTankInfo[] tanks = tank.getTankInfo(side);
				if (tanks[0] != null) {
					FluidStack fillFluid = tanks[0].fluid;
					ItemStack fillStack = FluidUtil.tryFillContainer(stack, tank, 1000, player, false).result;
					if (fillStack != null) {
						if (tank.isEmpty()) {
							return true;
						} else {
							tank.drain(FluidUtil.getFluidContained(fillStack).amount, true);
							if (!player.capabilities.isCreativeMode) {
								if (stack.getCount() == 1) {
									player.inventory.setInventorySlotContents(player.inventory.currentItem, fillStack);
								} else {
									player.inventory.setInventorySlotContents(player.inventory.currentItem,
											TRZFluidUtil.useItemSafely(stack));
									if (!player.inventory.addItemStackToInventory(fillStack))
										player.dropItem(fillStack, false);
								}
							} else {
								StorageUtil.syncBlockAndRerender(world, pos);
								return true;
							}
							StorageUtil.syncBlockAndRerender(world, pos);
							return true;
						}
					} else
						StorageUtil.syncBlockAndRerender(world, pos);
					return true;
				}
			}
			StorageUtil.syncBlockAndRerender(world, pos);
		}

		TileEntityFluidTank tile = (TileEntityFluidTank) world.getTileEntity(pos);
		if ((TRZUtil.isHoldingHammer(player)) && (!player.isSneaking())) {
			player.swingArm(EnumHand.MAIN_HAND);
			if (side.equals(EnumFacing.UP)) {
				if (world.isRemote) {
					tile.cycleSide("up");
				} else {
					tile.cycleSide("up");
				}
			}
			if (side.equals(EnumFacing.DOWN)) {
				if (world.isRemote) {
					tile.cycleSide("down");
				} else {
					tile.cycleSide("down");
				}
			}
			if (side.equals(EnumFacing.NORTH)) {
				if (world.isRemote) {
					tile.cycleSide("north");
				} else {
					tile.cycleSide("north");
				}
			}
			if (side.equals(EnumFacing.SOUTH)) {
				if (world.isRemote) {
					tile.cycleSide("south");
				} else {
					tile.cycleSide("south");
				}
			}
			if (side.equals(EnumFacing.EAST)) {
				if (world.isRemote) {
					tile.cycleSide("east");
				} else {
					tile.cycleSide("east");
				}
			}
			if (side.equals(EnumFacing.WEST)) {
				if (world.isRemote) {
					tile.cycleSide("west");
				} else {
					tile.cycleSide("west");
				}
			}
			return false;
		}
		StorageUtil.syncBlockAndRerender(world, pos);
		return true;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (!player.capabilities.isCreativeMode) {
			TileEntityFluidTank tank = (TileEntityFluidTank) world.getTileEntity(pos);
			// TRZFluidUtil.dropStackInWorld(world, pos,
			// TRZFluidUtil.getTankStackFromTile(tank, true));
			EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getX(), new ItemStack(this));
			world.spawnEntity(entityItem);
		}
		return world.setBlockToAir(pos);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (stack.hasTagCompound()) {
			TileEntityFluidTank tank = (TileEntityFluidTank) world.getTileEntity(pos);
			if (tank != null) {
				NBTTagCompound tagFluid = stack.getTagCompound().getCompoundTag("Fluid");
				if (tagFluid != null) {
					FluidStack liquid = FluidStack.loadFluidStackFromNBT(tagFluid);
					tank.tank.setFluid(liquid);
				}
			}
		}
	}

	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		TRZFluidUtil.dropStackInWorld(world, pos,
				StorageUtil.getTankStackFromTile((TileEntityFluidTank) world.getTileEntity(pos), true, this));
		world.setBlockToAir(pos);
		super.onBlockDestroyedByExplosion(world, pos, explosion);
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
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

	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

	@Override
	public boolean requiresUpdates() {
		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityFluidTank tile = (TileEntityFluidTank) worldIn.getTileEntity(pos);

		return state.withProperty(NORTH, tile.getSide("north")).withProperty(EAST, tile.getSide("east"))
				.withProperty(SOUTH, tile.getSide("south")).withProperty(WEST, tile.getSide("west"))
				.withProperty(UP, tile.getSide("up")).withProperty(DOWN, tile.getSide("down"));
	}

	public boolean getSide(EnumFacing side, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		int northInt = 0;
		int eastInt = 0;
		int southInt = 0;
		int westInt = 0;
		int downInt = 0;
		int upInt = 0;
		if (tile != null && tile instanceof TileEntityFluidTank) {
			northInt = ((TileEntityFluidTank) tile).getSide("north");
			eastInt = ((TileEntityFluidTank) tile).getSide("east");
			southInt = ((TileEntityFluidTank) tile).getSide("south");
			westInt = ((TileEntityFluidTank) tile).getSide("west");
			downInt = ((TileEntityFluidTank) tile).getSide("down");
			upInt = ((TileEntityFluidTank) tile).getSide("up");
		} else {

		}

		boolean upBool = false;
		boolean downBool = false;
		boolean northBool = false;
		boolean southBool = false;
		boolean eastBool = false;
		boolean westBool = false;
		if (side.equals(EnumFacing.UP)) {
			if (upInt == 1) {
				upBool = false;
			} else if (upInt == 2 || upInt == 0) {
				upBool = true;
			}
			return upBool;
		}

		if (side.equals(EnumFacing.DOWN)) {
			if (downInt == 1) {
				downBool = false;
			} else if (downInt == 2 || downInt == 0) {
				downBool = true;
			}
			return downBool;
		}
		if (side.equals(EnumFacing.NORTH)) {
			if (northInt == 1) {
				northBool = false;
			} else if (northInt == 2 || northInt == 0) {
				northBool = true;
			}
			return northBool;
		}
		if (side.equals(EnumFacing.SOUTH)) {
			if (southInt == 1) {
				southBool = false;
			} else if (southInt == 2 || southInt == 0) {
				southBool = true;
			}
			return southBool;
		}
		if (side.equals(EnumFacing.EAST)) {
			if (eastInt == 1) {
				eastBool = false;
			} else if (eastInt == 2 || eastInt == 0) {
				eastBool = true;
			}
			return eastBool;
		}
		if (side.equals(EnumFacing.WEST)) {
			if (westInt == 1) {
				westBool = false;
			} else if (westInt == 2 || westInt == 0) {
				westBool = true;
			}
			return westBool;
		}
		StorageUtil.syncBlockAndRerender(world, pos);
		return false;
	}

}