package com.zeher.orecraft.core.decor.block;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.zeher.zeherlib.api.interfaces.IMetaName;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ModBlockLog extends BlockLog implements IMetaName {

	public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.<BlockPlanks.EnumType>create("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>(){
		public boolean apply(@Nullable BlockPlanks.EnumType apply) {
			return apply.getMeta() < 2;
		}
	});
	
	public ModBlockLog(String name, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setHarvestLevel(tool, harvest);
		this.setCreativeTab(tab);
		this.setHardness(hardness);
		this.setResistance(resistance);
		
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.BEECH).withProperty(LOG_AXIS, EnumAxis.Y));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockPlanks.EnumType enum$planks : BlockPlanks.EnumType.values()) {
			items.add(new ItemStack(this, 1, enum$planks.getMeta()));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetaData((meta & 1) % 2));

		switch (meta & 6) {
		case 0:
			state = state.withProperty(LOG_AXIS, EnumAxis.Y);
			break;
		case 2:
			state = state.withProperty(LOG_AXIS, EnumAxis.X);
			break;
		case 4:
			state = state.withProperty(LOG_AXIS, EnumAxis.Z);
			break;
		default:
			state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
		}
		
		return state;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMeta();
		
		switch((BlockLog.EnumAxis)state.getValue(LOG_AXIS)) {
		case X:
			i |= 2;
			break;
		case Y:
			i |= 4;
			break;
		case Z:
			i |= 6;
			break;
		}
		return i;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT, LOG_AXIS});
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMeta());
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public String getSpecialName(ItemStack stack) {
		return BlockPlanks.EnumType.values()[stack.getItemDamage()].getName();
	}

}
