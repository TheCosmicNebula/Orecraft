package com.zeher.orecraft.storage.core.block;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.orecraft.storage.core.tileentity.TileEntityPoweredCapacitor;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.api.util.TextUtil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockPoweredCapacitor extends ItemBlock {

	public static int power;
	public int max_power = Reference.VALUE.STORAGE.POWERED_CAPACITY / 1000;
	private NonNullList<ItemStack> capacitorItemStacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);

	public ItemBlockPoweredCapacitor(Block block) {
		super(block);
		setMaxStackSize(1);
		setMaxDamage(max_power);
	}
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 11)) {
			return false;
		}

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == this.block) {
			TileEntityPoweredCapacitor tile = new TileEntityPoweredCapacitor();
			
			if (getDamage(stack) == 1000) {
				tile.stored = (0);
			}
			tile.stored = (max_power - getDamage(stack)) * 1000;
			
			world.removeTileEntity(pos);
			world.setTileEntity(pos, tile);
			setTileEntityNBT(world, player, pos, stack);

			this.block.onBlockPlacedBy(world, pos, state, player, stack);

		}
		
		if (stack.hasTagCompound()) {
			TileEntityPoweredCapacitor tile = (TileEntityPoweredCapacitor) world.getTileEntity(pos);
			
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("sides");
			
			EnumSide[] side_array = EnumSide.getDefaultArray();
			
			for (EnumFacing c : EnumFacing.VALUES) {
				int side_index = tag.getInteger("index_" + c.getIndex());
				
				side_array[c.getIndex()] = EnumSide.getSideFromIndex(side_index);
			}
			
			tile.setSideArray(side_array);
		}
		return true;
	}

	public static boolean setTileEntityNBT(World worldIn, @Nullable EntityPlayer player, BlockPos pos, ItemStack stackIn) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (!TextUtil.isShiftKeyDown()) {
			tooltip.add(TextUtil.getInfoText("A block that stores energy."));
			if (TextUtil.displayShiftForDetail) {
				tooltip.add(TextUtil.shiftForDetails());
			}
		} else if (TextUtil.isShiftKeyDown()) {
			tooltip.add(TextUtil.getFlavorText("Current Power: ") + TextUtil.ORANGE + ((max_power - stack.getItemDamage())) * 1000);
			tooltip.add(TextUtil.getFlavorText("Max Power: ") + TextUtil.ORANGE + (this.max_power * 1000));
			
			if (stack.hasTagCompound()) {
				NBTTagCompound tag = stack.getTagCompound();
				
				if (!TextUtil.isShiftKeyDown()) {
					
				} else if (TextUtil.isShiftKeyDown()) {
					if (tag.hasKey("sides")) {
						tooltip.add(TextUtil.GREEN + TextUtil.UNDERLINE + "Side configuration:");
						tooltip.add(TextUtil.LIGHT_BLUE + "D: " + TextUtil.ORANGE + TextUtil.BOLD + "[" + EnumSide.getSideFromIndex(tag.getCompoundTag("sides").getInteger("index_0")).getGuiName() + "]" + TextUtil.LIGHT_BLUE + " U: " + TextUtil.ORANGE + TextUtil.BOLD + "[" + EnumSide.getSideFromIndex(tag.getCompoundTag("sides").getInteger("index_1")).getGuiName() + "]");
						tooltip.add(TextUtil.LIGHT_BLUE + "N: " + TextUtil.ORANGE + TextUtil.BOLD + "[" + EnumSide.getSideFromIndex(tag.getCompoundTag("sides").getInteger("index_2")).getGuiName() + "]" + TextUtil.LIGHT_BLUE + " S: " + TextUtil.ORANGE + TextUtil.BOLD + "[" + EnumSide.getSideFromIndex(tag.getCompoundTag("sides").getInteger("index_3")).getGuiName() + "]");
						tooltip.add(TextUtil.LIGHT_BLUE + "W: " + TextUtil.ORANGE + TextUtil.BOLD + "[" + EnumSide.getSideFromIndex(tag.getCompoundTag("sides").getInteger("index_4")).getGuiName() + "]" + TextUtil.LIGHT_BLUE + " E: " + TextUtil.ORANGE + TextUtil.BOLD + "[" + EnumSide.getSideFromIndex(tag.getCompoundTag("sides").getInteger("index_5")).getGuiName() + "]");
					}
				}
			}
		}
	}

}
