package com.zeher.orecraft.production.core.block;

import java.util.Random;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.handler.BlockHandler;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredHeatedFluidGenerator;
import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.api.util.ModUtil;
import com.zeher.zeherlib.core.blockstate.StringProperty;
import com.zeher.zeherlib.fluid.ModFluidUtil;
import com.zeher.zeherlib.production.BlockFluidProducer;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
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
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPoweredHeatedFluidGenerator extends BlockFluidProducer implements ITileEntityProvider {

	public static final IUnlistedProperty<Integer> FluidLevel = new Properties.PropertyAdapter<Integer>(PropertyInteger.create("fluidLevel", 0, 32));
	public static final IUnlistedProperty<String> FluidName = new StringProperty("fluidName");
	public static final IUnlistedProperty<Boolean> CullFluidTop = new Properties.PropertyAdapter<Boolean>(PropertyBool.create("cullFluidTop"));

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static boolean keepInventory;

	public BlockPoweredHeatedFluidGenerator(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IExtendedBlockState extendedState = (IExtendedBlockState) state;

		TileEntityPoweredHeatedFluidGenerator tankEntity = (TileEntityPoweredHeatedFluidGenerator) world.getTileEntity(pos);
		BlockPoweredHeatedFluidGenerator block = (BlockPoweredHeatedFluidGenerator) world.getBlockState(pos).getBlock();

		if (tankEntity != null) {
			Fluid tankFluid = tankEntity.getCurrentStoredFluid();
			int fluidLevel = tankEntity.getFillLevel();

			return extendedState.withProperty(FluidName, (tankFluid != null) ? tankFluid.getName() : "")
					.withProperty(FluidLevel, fluidLevel).withProperty(CullFluidTop, false)
					.withProperty(FACING, EnumFacing.SOUTH);
		}

		return extendedState.withProperty(FluidName, "").withProperty(FluidLevel, 0).withProperty(CullFluidTop, false)
				.withProperty(FACING, EnumFacing.SOUTH);
	}
	
	protected BlockStateContainer createBlockState() {
		IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { FluidLevel, FluidName, CullFluidTop };
		IProperty[] listedProperties = new IProperty[] { FACING };
		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(BlockHandler.REGISTRATION.FLUIDPRODUCER.BLOCK_POWERED_HEATEDFLUID_GENERATOR);
	}
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.inventory.getCurrentItem();
		if (!(worldIn.isRemote) && !(playerIn.isSneaking()) && !(ModUtil.isHoldingHammer(playerIn))
				&& stack.isEmpty()) {
			FMLNetworkHandler.openGui(playerIn, Orecraft.INSTANCE, 24, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		if (stack != null) {
			FluidStack liquid = FluidUtil.getFluidContained(stack);
			TileEntityPoweredHeatedFluidGenerator tank = (TileEntityPoweredHeatedFluidGenerator) worldIn
					.getTileEntity(pos);
			if (liquid != null) {
				int amount = tank.fill(liquid, false);
				if (amount == liquid.amount) {
					if (liquid.getFluid().getTemperature() > 1000) {
						tank.fill(liquid, true);
						tank.markDirty();
						if (!playerIn.capabilities.isCreativeMode)
							playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,
									new ItemStack(Items.BUCKET));
						// TRZFluidUtil.useItemSafely(stack));
						if (tank.tank.getFluid().getFluid().getBlock() != null) {
							// worldIn.setLightFor(EnumSkyBlock.SKY, tank.getPos(),
							// tank.tank.getFluid().getFluid().getBlock().getLightValue(state));
						}
						StorageUtil.syncBlockAndRerender(worldIn, pos);
						return true;
					} else {
						return true;
					}
				} else
					StorageUtil.syncBlockAndRerender(worldIn, pos);
				return true;
			} else if (stack.getItem() instanceof ItemBucket) {
				FluidTankInfo[] tanks = tank.getTankInfo(facing);
				if (tanks[0] != null) {
					FluidStack fillFluid = tanks[0].fluid;
					ItemStack fillStack = FluidUtil.tryFillContainer(stack, tank, 1000, playerIn, false).result;
					if (fillStack != null) {
						if (tank.isFluidEmpty()) {
							return true;
						} else {
							tank.drain(FluidUtil.getFluidContained(fillStack).amount, true);
							tank.markDirty();
							if (!playerIn.capabilities.isCreativeMode) {
								if (stack.getCount() == 1) {
									playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,
											fillStack);
								} else {
									playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,
											ModFluidUtil.useItemSafely(stack));
									if (!playerIn.inventory.addItemStackToInventory(fillStack))
										playerIn.dropItem(fillStack, false);
								}
							} else {
								StorageUtil.syncBlockAndRerender(worldIn, pos);
								return true;
							}
							StorageUtil.syncBlockAndRerender(worldIn, pos);
							return true;
						}
					} else
						StorageUtil.syncBlockAndRerender(worldIn, pos);
					return true;
				}
			}
			StorageUtil.syncBlockAndRerender(worldIn, pos);
		}

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

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPoweredHeatedFluidGenerator();
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(BlockHandler.REGISTRATION.FLUIDPRODUCER.BLOCK_POWERED_HEATEDFLUID_GENERATOR);
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}
	
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

}
