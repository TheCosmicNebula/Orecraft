package com.zeher.orecraft.machine.client.tesr;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.tesr.model.internal.ModelBlockCombinerCenterInternals;
import com.zeher.orecraft.machine.core.recipe.CombinerCraftingHandler;
import com.zeher.orecraft.machine.core.tileentity.TileEntityCombinerCenter;
import com.zeher.orecraft.machine.core.tileentity.TileEntityCombinerPedestal;
import com.zeher.zeherlib.api.tesr.EnumTESRColour;
import com.zeher.zeherlib.api.tesr.TESRUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererCombinerCenter extends TileEntitySpecialRenderer<TileEntityCombinerCenter> {
	
	@SideOnly(Side.CLIENT)
	private ModelBlockCombinerCenterInternals model;

	@SideOnly(Side.CLIENT)
	public RendererCombinerCenter() {
		this.model = new ModelBlockCombinerCenterInternals();
	}
	
	private static final ResourceLocation texture_location = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/combiner/internals.png");
	
	@Override
	public void render(TileEntityCombinerCenter tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		
		if(tileentity == null) {
			return;
		}
		
		if (tileentity.canCombine())  {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
			GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
			this.bindTexture(texture_location);
			
			GlStateManager.rotate(Minecraft.getSystemTime() / 720.0F * 40, 0.0F, 1.0F, 0.0F);
			
			GL11.glDisable(3008);
			this.model.renderTopCenter(0.0625F);
			GL11.glEnable(3008);
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
			GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
			this.bindTexture(texture_location);
			
			GlStateManager.rotate(-(Minecraft.getSystemTime() / 720.0F * 40), 0.0F, 1.0F, 0.0F);
			
			GL11.glDisable(3008);
			this.model.renderBottomCenter(0.0625F);
			GL11.glEnable(3008);
			GlStateManager.popMatrix();
		} else {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
			GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
			this.bindTexture(texture_location);
			
			GL11.glDisable(3008);
			this.model.renderTopCenter(0.0625F);
			this.model.renderBottomCenter(0.0625F);
			GL11.glEnable(3008);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
		this.bindTexture(texture_location);
		GL11.glDisable(3008);
		this.model.renderBase(0.0625f);
		GL11.glEnable(3008);
		GlStateManager.popMatrix();
		
		World world = Minecraft.getMinecraft().world;
		BlockPos pos = tileentity.getPos();
		
		TileEntity tile_n = world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 3));
		TileEntity tile_s = world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 3));

		TileEntity tile_e = world.getTileEntity(new BlockPos(pos.getX() + 3, pos.getY(), pos.getZ()));
		TileEntity tile_w = world.getTileEntity(new BlockPos(pos.getX() - 3, pos.getY(), pos.getZ()));

		TileEntity tile_ne = world.getTileEntity(new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() - 2));
		TileEntity tile_nw = world.getTileEntity(new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() - 2));

		TileEntity tile_se = world.getTileEntity(new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() + 2));
		TileEntity tile_sw = world.getTileEntity(new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() + 2));
		
		int slot = 0;

		if (!tileentity.getStackInSlot(slot).isEmpty()) {			
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5F, y + 1.0F, z + 0.5F);
			GlStateManager.rotate(Minecraft.getSystemTime() / 720.0F * 31.830988F, 0.0F, 1.0F, 0.0F);
			
			if ((tileentity.getStackInSlot(slot).getItem() instanceof ItemBlock)) {
				GlStateManager.scale(0.8F, 0.8F, 0.8F);
			} else {
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
			}
			
			GlStateManager.pushAttrib();
	
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(slot), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (tileentity.canCombine()) {
			
			if (!tileentity.getStackInSlot(0).isEmpty() && tileentity.getStackInSlot(0).getItem() instanceof ItemBlock) {
				
				TESRUtil.renderLaser(pos.getX() + 0.095F, pos.getY() + 0.58F, pos.getZ() + 0.095F, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
				
				TESRUtil.renderLaser(pos.getX() + 0.905F, pos.getY() + 0.58F, pos.getZ() + 0.095F, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
				
				TESRUtil.renderLaser(pos.getX() + 0.095F, pos.getY() + 0.58F, pos.getZ() + 0.905F, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
				
				TESRUtil.renderLaser(pos.getX() + 0.905F, pos.getY() + 0.58F, pos.getZ() + 0.905F, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
			
			} else if (!tileentity.getStackInSlot(0).isEmpty()) {
				
				TESRUtil.renderLaser(pos.getX() + 0.095F, pos.getY() + 0.58F, pos.getZ() + 0.095F, pos.getX() + 0.905F, pos.getY() + 0.58F, pos.getZ() + 0.095F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
				
				TESRUtil.renderLaser(pos.getX() + 0.095F, pos.getY() + 0.58F, pos.getZ() + 0.095F, pos.getX() + 0.095F, pos.getY() + 0.58F, pos.getZ() + 0.905F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
				
				TESRUtil.renderLaser(pos.getX() + 0.905F, pos.getY() + 0.58F, pos.getZ() + 0.905F, pos.getX() + 0.095F, pos.getY() + 0.58F, pos.getZ() + 0.905F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
				
				TESRUtil.renderLaser(pos.getX() + 0.905F, pos.getY() + 0.58F, pos.getZ() + 0.905F, pos.getX() + 0.905F, pos.getY() + 0.58F, pos.getZ() + 0.095F, 80, 1F, 0.035F, this.getColour(tileentity, world, pos));
			
			}
			
			if (tile_n != null && tile_n instanceof TileEntityCombinerPedestal) {
				if (!((TileEntityCombinerPedestal) tile_n).getStackInSlot(0).isEmpty()) { 
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_n.getPos().getX() + 0.5, tile_n.getPos().getY() + 0.15, tile_n.getPos().getZ() + 0.5, 80, 1F, 0.1F, this.getColour(tileentity, world, pos));
				}
			}
			
			if (tile_s != null && tile_s instanceof TileEntityCombinerPedestal) {
				if(!((TileEntityCombinerPedestal) tile_s).getStackInSlot(0).isEmpty()) {
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_s.getPos().getX() + 0.5, tile_s.getPos().getY() + 0.15, tile_s.getPos().getZ() + 0.5, 80, 1F, 0.1F, this.getColour(tileentity, world, pos));
				}
			}
			
			if (tile_e != null && tile_e instanceof TileEntityCombinerPedestal) {
				if (!((TileEntityCombinerPedestal) tile_e).getStackInSlot(0).isEmpty()) { 
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_e.getPos().getX() + 0.5, tile_e.getPos().getY() + 0.15, tile_e.getPos().getZ() + 0.5, 80, 1F, 0.1F, this.getColour(tileentity, world, pos));
				}
			}
			
			if (tile_w != null && tile_w instanceof TileEntityCombinerPedestal) {
				if (!((TileEntityCombinerPedestal) tile_w).getStackInSlot(0).isEmpty()) { 
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_w.getPos().getX() + 0.5, tile_w.getPos().getY() + 0.15, tile_w.getPos().getZ() + 0.5, 80, 1F, 0.1F, this.getColour(tileentity, world, pos));
				}
			}
			
			if (tile_ne != null && tile_ne instanceof TileEntityCombinerPedestal) {
				if (!((TileEntityCombinerPedestal) tile_ne).getStackInSlot(0).isEmpty()) { 
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_ne.getPos().getX() + 0.5, tile_ne.getPos().getY() + 0.15, tile_ne.getPos().getZ() + 0.5, 80, 1F, 0.05F, this.getColour(tileentity, world, pos));
				}
			}
			
			if (tile_nw != null && tile_nw instanceof TileEntityCombinerPedestal) {
				if (!((TileEntityCombinerPedestal) tile_nw).getStackInSlot(0).isEmpty()) { 
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_nw.getPos().getX() + 0.5, tile_nw.getPos().getY() + 0.15, tile_nw.getPos().getZ() + 0.5, 80, 1F, 0.05F, this.getColour(tileentity, world, pos));
				}
			}
			
			if (tile_se != null && tile_se instanceof TileEntityCombinerPedestal) {
				if (!((TileEntityCombinerPedestal) tile_se).getStackInSlot(0).isEmpty()) { 
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_se.getPos().getX() + 0.5, tile_se.getPos().getY() + 0.15, tile_se.getPos().getZ() + 0.5, 80, 1F, 0.05F, this.getColour(tileentity, world, pos));
				}
			}
			
			if (tile_sw != null && tile_sw instanceof TileEntityCombinerPedestal) {
				if (!((TileEntityCombinerPedestal) tile_sw).getStackInSlot(0).isEmpty()) { 
					TESRUtil.renderLaser(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5, tile_sw.getPos().getX() + 0.5, tile_sw.getPos().getY() + 0.15, tile_sw.getPos().getZ() + 0.5, 80, 1F, 0.05F, this.getColour(tileentity, world, pos));
				}
			}
		}
		
	}
	
	public float[] getColour(TileEntityCombinerCenter tileentity, World world, BlockPos pos) {
		
		TileEntity tile_n = world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 3));
		TileEntity tile_s = world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 3));

		TileEntity tile_e = world.getTileEntity(new BlockPos(pos.getX() + 3, pos.getY(), pos.getZ()));
		TileEntity tile_w = world.getTileEntity(new BlockPos(pos.getX() - 3, pos.getY(), pos.getZ()));

		TileEntity tile_ne = world.getTileEntity(new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() - 2));
		TileEntity tile_nw = world.getTileEntity(new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() - 2));

		TileEntity tile_se = world.getTileEntity(new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() + 2));
		TileEntity tile_sw = world.getTileEntity(new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() + 2));
		
		if (((tile_n instanceof TileEntityCombinerPedestal)) && ((tile_s instanceof TileEntityCombinerPedestal)) && ((tile_e instanceof TileEntityCombinerPedestal))
				&& ((tile_w instanceof TileEntityCombinerPedestal)) && ((tile_ne instanceof TileEntityCombinerPedestal)) && ((tile_nw instanceof TileEntityCombinerPedestal))
				&& ((tile_se instanceof TileEntityCombinerPedestal)) && ((tile_sw instanceof TileEntityCombinerPedestal))) {
			
			ItemStack stack_n = ((TileEntityCombinerPedestal) tile_n).getStackInSlot(0);
			ItemStack stack_s = ((TileEntityCombinerPedestal) tile_s).getStackInSlot(0);

			ItemStack stack_e = ((TileEntityCombinerPedestal) tile_e).getStackInSlot(0);
			ItemStack stack_w = ((TileEntityCombinerPedestal) tile_w).getStackInSlot(0);

			ItemStack stack_ne = ((TileEntityCombinerPedestal) tile_ne).getStackInSlot(0);
			ItemStack stack_nw = ((TileEntityCombinerPedestal) tile_nw).getStackInSlot(0);
			ItemStack stack_se = ((TileEntityCombinerPedestal) tile_se).getStackInSlot(0);
			ItemStack stack_sw = ((TileEntityCombinerPedestal) tile_sw).getStackInSlot(0);
			
			if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (stack_e.isEmpty()) && (stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty())
					&& (stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_n);
				list.add(stack_s);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, world);
				if (result_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				ItemStack output_stack = (ItemStack) tileentity.getStackInSlot(0);
				if (output_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, world).getItem())) {
					return CombinerCraftingHandler.getInstance().findColour(list, world);
				}
				
				return EnumTESRColour.WHITE.colour;
			}
			
			if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty())
					&& (stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_e);
				list.add(stack_w);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, world);
				if (result_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				ItemStack output_stack = (ItemStack) tileentity.getStackInSlot(0);
				if (output_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, world).getItem())) {
					return CombinerCraftingHandler.getInstance().findColour(list, world);
				}
				
				return EnumTESRColour.WHITE.colour;
			}
			
			if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty())
					&& (stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_n);
				list.add(stack_s);
				list.add(stack_e);
				list.add(stack_w);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, world);
				if (result_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				ItemStack output_stack = (ItemStack) tileentity.getStackInSlot(0);
				if (output_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, world).getItem())) {
					return CombinerCraftingHandler.getInstance().findColour(list, world);
				}
				
				return EnumTESRColour.WHITE.colour;
			}
			
			if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (stack_e.isEmpty()) && (stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty()) && (!stack_se.isEmpty())
					&& (!stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_ne);
				list.add(stack_nw);
				list.add(stack_se);
				list.add(stack_sw);
				
				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, world);
				if (result_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				ItemStack output_stack = (ItemStack) tileentity.getStackInSlot(0);
				if (output_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, world).getItem())) {
					return CombinerCraftingHandler.getInstance().findColour(list, world);
				}
				
				return EnumTESRColour.WHITE.colour;
			}
			
			if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty()) && (!stack_se.isEmpty()) 
					&& (!stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_n);
				list.add(stack_s);
				list.add(stack_e);
				list.add(stack_w);
				list.add(stack_ne);
				list.add(stack_nw);
				list.add(stack_se);
				list.add(stack_sw);
				
				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, world);
				if (result_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				ItemStack output_stack = (ItemStack) tileentity.getStackInSlot(0);
				if (output_stack.isEmpty()) {
					return EnumTESRColour.WHITE.colour;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, world).getItem())) {
					return CombinerCraftingHandler.getInstance().findColour(list, world);
				}
				
				return EnumTESRColour.WHITE.colour;
			}
		}
		return EnumTESRColour.WHITE.colour;
	}
	
}
