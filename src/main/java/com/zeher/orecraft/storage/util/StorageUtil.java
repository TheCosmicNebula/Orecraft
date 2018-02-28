package com.zeher.orecraft.storage.util;

import com.zeher.orecraft.client.model.baked.BakedFluidTankModel;
import com.zeher.orecraft.storage.tileentity.TileEntityFluidTank;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;

public class StorageUtil {

	public static int getFluidLevel(int fillPercentage) {
		int level = (int) Math.round((fillPercentage / 100.0d) * BakedFluidTankModel.FLUID_LEVELS);

		// Make sure that even for small amounts the fluid is rendered at the
		// first level.
		return (fillPercentage > 0) ? Math.max(1, level) : 0;
	}

	public static final void syncBlockAndRerender(World world, BlockPos pos) {
		if (world == null || pos == null)
			return;

		IBlockState state = world.getBlockState(pos);

		world.markAndNotifyBlock(pos, null, state, state, 2);
	}
	
	public static final <T extends TileEntity> T getTileEntityAt(IBlockAccess access, Class<T> tileType, BlockPos pos)
	{
		if (access != null && tileType != null && pos != null)
		{
			TileEntity tile = (access instanceof ChunkCache)
				? ((ChunkCache)access).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK)
				: access.getTileEntity(pos);

			if (tile != null && tile.getClass() == tileType)
			{
				return (T) tile;
			}
		}

		return null;
	}
	
	public static ItemStack getTankStackFromTile(TileEntityFluidTank tank, boolean keepFluid, Block block) {
		ItemStack stack = new ItemStack(block);
		stack.writeToNBT(new NBTTagCompound());
		FluidStack fluid = tank.tank.getFluid();
		
		if ((fluid != null) && (keepFluid)) {
			NBTTagCompound tagFluid = new NBTTagCompound();
			fluid.writeToNBT(tagFluid);
			stack.serializeNBT().setTag("Fluid", tagFluid);
		}
		return stack;
	}
}
