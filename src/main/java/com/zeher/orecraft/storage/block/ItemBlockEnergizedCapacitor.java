package com.zeher.orecraft.storage.block;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.orecraft.storage.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.trzlib.api.TRZTextUtil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockEnergizedCapacitor extends ItemBlock {

	public static int power;
	public int max_power = 24000;
	public String name = "itemblock_capacitor_ender";
	private NonNullList<ItemStack> capacitorItemStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

	public ItemBlockEnergizedCapacitor(Block block) {
		super(block);
		setMaxStackSize(1);
		setMaxDamage(max_power);
	}

	public void setPower(int set_power) {
		this.setDamage(new ItemStack(this), power);
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 11))
			return false;

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == this.block) {
			TileEntityEnergizedCapacitor tile = new TileEntityEnergizedCapacitor();
			if (getDamage(stack) == 20000) {
				tile.stored = (0);
			}
			tile.stored = (max_power - getDamage(stack)) * 1000;
			world.removeTileEntity(pos);
			world.setTileEntity(pos, tile);
			setTileEntityNBT(world, player, pos, stack);
			this.block.onBlockPlacedBy(world, pos, state, player, stack);
		}
		return true;
	}

	public static boolean setTileEntityNBT(World worldIn, @Nullable EntityPlayer player, BlockPos pos,
			ItemStack stackIn) {
		MinecraftServer minecraftserver = worldIn.getMinecraftServer();

		if (minecraftserver == null) {
			return false;
		} else {
			NBTTagCompound nbttagcompound = stackIn.getSubCompound("BlockEntityTag");

			if (nbttagcompound != null) {
				TileEntityEnergizedCapacitor tileentity = (TileEntityEnergizedCapacitor) worldIn.getTileEntity(pos);

				if (tileentity != null) {
					if (!worldIn.isRemote && tileentity.onlyOpsCanSetNbt()
							&& (player == null || !player.canUseCommandBlock())) {
						return false;
					}

					NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());
					NBTTagCompound nbttagcompound2 = nbttagcompound1.copy();
					nbttagcompound1.merge(nbttagcompound);
					nbttagcompound1.setInteger("x", pos.getX());
					nbttagcompound1.setInteger("y", pos.getY());
					nbttagcompound1.setInteger("z", pos.getZ());
					nbttagcompound1.setInteger("power", power);

					if (!nbttagcompound1.equals(nbttagcompound2)) {
						tileentity.readFromNBT(nbttagcompound1);
						tileentity.markDirty();
						return true;
					}
				}
			}

			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		super.addInformation(item, player, list, bool);
		if (!TRZTextUtil.isShiftKeyDown()) {
			list.add(TRZTextUtil.getInfoText("A block that stores energy."));
			if (TRZTextUtil.displayShiftForDetail) {
				list.add(TRZTextUtil.shiftForDetails());
			}
			return;
		}
		if (TRZTextUtil.isShiftKeyDown()) {
			list.add(TRZTextUtil.getFlavorText("Current Power: ") + TRZTextUtil.ORANGE + ((max_power - item.getItemDamage())) * 10000);
			list.add(TRZTextUtil.getFlavorText("Max Power: ") + TRZTextUtil.ORANGE + (this.max_power * 10000));
		}
		return;
	}

}
