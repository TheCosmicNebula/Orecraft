package com.zeher.orecraft.storage.block;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.orecraft.storage.tileentity.TileEntityPoweredCapacitor;
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

public class ItemBlockPoweredCapacitor extends ItemBlock {

	public static int power;
	public int max_power = 8000;
	private NonNullList<ItemStack> capacitorItemStacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);

	/*
	 * public static int up; public int down; public int north; public int
	 * south; public int east; public int west;
	 */

	public ItemBlockPoweredCapacitor(Block block) {
		super(block);
		setMaxStackSize(1);
		setMaxDamage(max_power);
	}

	/*
	 * public void setPower(int set_power){ this.setDamage(new ItemStack(this),
	 * power); }
	 */

	/*
	 * public void setSide(String str, int value) { if (str == "up") { this.up =
	 * value; } if (str == "down") { this.down = value; } if (str == "north") {
	 * this.north = value; } if (str == "south") { this.south = value; } if (str
	 * == "east") { this.east = value; } if (str == "west") { this.west = value;
	 * } }
	 */

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 11))
			return false;

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == this.block) {
			TileEntityPoweredCapacitor tile = new TileEntityPoweredCapacitor();
			if (getDamage(stack) == 1000) {
				tile.stored = (0);
			}
			tile.stored = (max_power - getDamage(stack)) * 1000;
			world.removeTileEntity(pos);
			/*
			 * tile.setSideNBT("up", up); tile.setSideNBT("down", down);
			 * tile.setSideNBT("north", north); tile.setSideNBT("south", south);
			 * tile.setSideNBT("east", east); tile.setSideNBT("west", west);
			 */
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
				TileEntityPoweredCapacitor tileentity = (TileEntityPoweredCapacitor) worldIn.getTileEntity(pos);

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
