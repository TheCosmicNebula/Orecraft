package com.zeher.orecraft.core.decor.block;

import com.zeher.zeherlib.api.interfaces.IMetaName;
import com.zeher.zeherlib.core.block.ModBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockPlanks extends ModBlock implements IMetaName {

	public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.<BlockPlanks.EnumType>create("variant", BlockPlanks.EnumType.class);
	
	public BlockPlanks( String name, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(name, Material.WOOD, tool, harvest, hardness, resistance, tab);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.BEECH));
		
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockPlanks.EnumType enum$planks : BlockPlanks.EnumType.values()) {
			items.add(new ItemStack(this, 1, enum$planks.getMeta()));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetaData(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((BlockPlanks.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult result, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, (int)(this.getMetaFromState(world.getBlockState(pos))));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	public static enum EnumType implements IStringSerializable {
		
		BEECH(0, "beech"),
		RUBBER(1, "rubber");
		
		private static final BlockPlanks.EnumType[] META_LOOKUP = new BlockPlanks.EnumType[values().length];
		private final int meta;
		private final String name, unloc_name;
		
		private EnumType(int meta, String name) {
			this(meta, name, name);
		}
		
		private EnumType(int meta, String name, String unloc_name) {
			this.meta = meta;
			this.name = name;
			this.unloc_name = unloc_name;
		}
		

		@Override
		public String getName() {
			return name;
		}
		
		public int getMeta() {
			return this.meta;
		}
		
		public String getUnlocalizedName() {
			return this.unloc_name;
		}
		
		@Override
		public String toString() {
			return this.name();
		}
		
		public static BlockPlanks.EnumType byMetaData(int meta) {
			return META_LOOKUP[meta];
		}
		
		static {
			for (BlockPlanks.EnumType planks$enum : values()) {
				META_LOOKUP[planks$enum.getMeta()] = planks$enum;
			}
		}
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return BlockPlanks.EnumType.values()[stack.getItemDamage()].getName();
	}
}
