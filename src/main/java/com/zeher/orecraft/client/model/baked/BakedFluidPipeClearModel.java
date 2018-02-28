package com.zeher.orecraft.client.model.baked;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.zeher.orecraft.transfer.block.pipe.BlockFluidPipeClear;
import com.zeher.trzlib.api.TRZUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BakedFluidPipeClearModel implements IBakedModel
{
	public static final int FLUID_LEVELS = 64;
	
	// Fluid model cache: The HashMap key corresponds to the fluid name, the model array index to the fluid level.
	public static final HashMap<String, IBakedModel[]> FLUID_MODELS = new HashMap<>();
	
	private IBakedModel baseModel;
	
	public BakedFluidPipeClearModel(IBakedModel baseModel)
	{
		this.baseModel = baseModel;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		List<BakedQuad> quads = new LinkedList<BakedQuad>();
		
		if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT_MIPPED)
		{
			// Frame
			quads.addAll(baseModel.getQuads(state, side, rand));
		}
		else if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT)
		{
			// Fluid
			IExtendedBlockState exState = (IExtendedBlockState)state;
			boolean cullFluidTop = exState.getValue(BlockFluidPipeClear.CullFluidTop);
			String fluidName = exState.getValue(BlockFluidPipeClear.FluidName);
			
			boolean down = state.getValue(BlockFluidPipeClear.DOWN);
			boolean up = state.getValue(BlockFluidPipeClear.UP);
			boolean north = state.getValue(BlockFluidPipeClear.NORTH);
			boolean south = state.getValue(BlockFluidPipeClear.SOUTH);
			boolean west = state.getValue(BlockFluidPipeClear.WEST);
			boolean east = state.getValue(BlockFluidPipeClear.EAST);
			
			int side_connect = TRZUtil.getSides(down, up, north, south, west, east);
			
			// The top quad of the fluid model needs a separate culling logic from the 
			// rest of the Pipe, because the top needs to be visible if the Pipe isn't
			// full, even if there's a Pipe above.
			// (Note that 'side' is null for quads that don't have a cullface annotation in the .json.
			// The Pipe model has cullface annotations for every side.)
			if ((side != null && side != EnumFacing.UP) || (side == null && !cullFluidTop))
			{
				if(FLUID_MODELS.containsKey(fluidName)){
					IBakedModel fluidModel = FLUID_MODELS.get(fluidName)[side_connect];
					quads.addAll(fluidModel.getQuads(null, side, rand));
				}
			}
		}
		
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return false;
	}

	@Override
	public boolean isGui3d()
	{
		return baseModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return baseModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return baseModel.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms()
	{
		return baseModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return null;
	}
}
