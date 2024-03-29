package com.zeher.orecraft.machine.client.baked.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.zeher.orecraft.production.core.block.BlockPoweredPeltierGenerator;

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

public class BakedPeltierGeneratorModel implements IBakedModel {
	public static final int FLUID_LEVELS = 5;

	// Fluid model cache: The HashMap key corresponds to the fluid name, the model
	// array index to the fluid level.
	public static final HashMap<String, IBakedModel[]> FLUID_MODELS = new HashMap<>();

	private IBakedModel baseModel;

	public BakedPeltierGeneratorModel(IBakedModel baseModel) {
		this.baseModel = baseModel;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		List<BakedQuad> quads = new LinkedList<BakedQuad>();

		if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT_MIPPED) {
			// Frame
			quads.addAll(baseModel.getQuads(state, side, rand));
		} else if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
			// Fluid
			IExtendedBlockState exState = (IExtendedBlockState) state;
			boolean cullFluidTop = exState.getValue(BlockPoweredPeltierGenerator.CullFluidTop);
			int fluidLevel = exState.getValue(BlockPoweredPeltierGenerator.FluidLevel);
			String fluidName = exState.getValue(BlockPoweredPeltierGenerator.FluidName);

			int fluidLevel_two = exState.getValue(BlockPoweredPeltierGenerator.FluidLevel_two);
			String fluidName_two = exState.getValue(BlockPoweredPeltierGenerator.FluidName_two);

			// The top quad of the fluid model needs a separate culling logic from the
			// rest of the tank, because the top needs to be visible if the tank isn't
			// full, even if there's a tank above.
			// (Note that 'side' is null for quads that don't have a cullface annotation in
			// the .json.
			// The tank model has cullface annotations for every side.)
			if ((side != null && side != EnumFacing.UP) || (side == null && !cullFluidTop)) {
				if (fluidLevel > 0 && fluidLevel <= FLUID_LEVELS && FLUID_MODELS.containsKey(fluidName)) {
					IBakedModel fluidModel = FLUID_MODELS.get(fluidName)[fluidLevel - 1];
					quads.addAll(fluidModel.getQuads(null, side, rand));
				}
				if (fluidLevel_two > 0 && fluidLevel_two <= FLUID_LEVELS && FLUID_MODELS.containsKey(fluidName_two)) {
					IBakedModel fluidModel = FLUID_MODELS.get(fluidName_two)[fluidLevel_two - 1];
					quads.addAll(fluidModel.getQuads(null, side, rand));
				}
			}
		}

		return quads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return baseModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return baseModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return baseModel.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return baseModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}
}
