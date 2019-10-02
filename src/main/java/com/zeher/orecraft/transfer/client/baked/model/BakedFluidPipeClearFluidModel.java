package com.zeher.orecraft.transfer.client.baked.model;

import java.util.EnumMap;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.zeher.orecraft.client.baked.BakedUtil;
import com.zeher.orecraft.core.handler.FluidHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BakedFluidPipeClearFluidModel implements IBakedModel
{
	private static final float x[] = { 0.32f, 0.32f, 0.68f, 0.68f };
    private static final float z[] = { 0.32f, 0.68f, 0.68f, 0.32f };
    
    private static final float xMain[] = { 0, 0.32f, 0.68f, 1 };
    private static final float zMain[] = { 0, 0.32f, 0.68f, 1 };
    private static final float yMain[] = { 0, 0.32f, 0.68f, 1 };
    
	
    private Fluid fluid;
	private double side_connect;
	private VertexFormat format;
	private EnumMap<EnumFacing, List<BakedQuad>> faceQuads;
	
	public BakedFluidPipeClearFluidModel(Fluid fluid, double side_connect)
	{
		this.fluid = fluid;
		this.side_connect = side_connect;
		
		format = DefaultVertexFormats.ITEM;
		faceQuads = Maps.newEnumMap(EnumFacing.class);
		
        for(EnumFacing side : EnumFacing.values())
        {
            faceQuads.put(side, ImmutableList.<BakedQuad>of());
        }
        
        TextureAtlasSprite texture = getParticleTexture();
        UnpackedBakedQuad.Builder quad_builder = null;
        UnpackedBakedQuad.Builder quad_builder_two = null;
        
        if(side_connect == 0) { BakedUtil.renderBase(fluid, quad_builder, texture, format, faceQuads); }
        if(side_connect == 1) { BakedUtil.renderDown(fluid, quad_builder, texture, format, faceQuads); }
        if(side_connect == 2) { BakedUtil.renderUp(fluid, quad_builder, texture, format, faceQuads); }
        if(side_connect == 3) { BakedUtil.renderUpDown(fluid, quad_builder, texture, format, faceQuads); }
        if(side_connect == 4) { BakedUtil.renderNorth(fluid, quad_builder, texture, format, faceQuads); }
        if(side_connect == 5) { BakedUtil.renderNorthDown(fluid, quad_builder, quad_builder_two, texture, format, faceQuads); }
        
        //else {BakedUtils.renderBase(fluid, quad_builder, texture, format, faceQuads);}
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		if (side != null) return faceQuads.get(side);
		
		return faceQuads.get(EnumFacing.UP);
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return false;
	}

	@Override
	public boolean isGui3d()
	{
		return false;
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		// Use the fluid still texture by default. If the fluid has no still texture, use the
		// flowing texture instead. If there's no flowing texture either, use the water still texture.
		
		
		String fluidTextureLoc = (fluid.getStill() != null)
				? fluid.getStill().toString()
				: (fluid.getFlowing() != null)
				? fluid.getFlowing().toString()
				: FluidRegistry.WATER.getStill().toString();
				
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluidTextureLoc);
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms()
	{
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}
	
}
