package com.zeher.orecraft.client.model.baked;

import java.util.EnumMap;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.zeher.orecraft.core.handlers.FluidHandler;

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

public class BakedPeltierGeneratorFluidModel implements IBakedModel
{
	private static final float offset = 0.00005f;
    private static final float x[] = { 0, 0, 1f, 1f };
    private static final float z[] = { 0, 1f, 1f, 0 };
    
    private static final float yTop[] = {0.5f, 0.6f, 0.7f, 0.8f, 1f - offset};
    private static final float yBottom[] = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f};
	
    private Fluid fluid;
	private int level;
	private VertexFormat format;
	private EnumMap<EnumFacing, List<BakedQuad>> faceQuads;
	
	public BakedPeltierGeneratorFluidModel(Fluid fluid, int level)
	{
		this.fluid = fluid;
		this.level = level;
		
		format = DefaultVertexFormats.ITEM;
		faceQuads = Maps.newEnumMap(EnumFacing.class);
		
        for(EnumFacing side : EnumFacing.values())
        {
            faceQuads.put(side, ImmutableList.<BakedQuad>of());
        }
        
        TextureAtlasSprite texture = getParticleTexture();
        
        if(fluid.getTemperature() >= 1000){
        	float[] y = new float[4];
        	
        	for(int i = 0; i < 4; i++)
            {
                y[i] = ((float)level / 10);
            }
        	
        	UnpackedBakedQuad.Builder quadBuilder;
            EnumFacing side = EnumFacing.UP;
            
        	quadBuilder = new UnpackedBakedQuad.Builder(format);
        	quadBuilder.setQuadOrientation(side);
        	quadBuilder.setTexture(texture);
        	float X, Z;
            
            for (int i = 0; i < 4; i++)
            {
            	X = Math.min(Math.max(x[i], offset), 1f - offset);
            	Z = Math.min(Math.max(z[i], offset), 1f - offset);
            	
            	putVertex(
                    	quadBuilder, side,
                        X, y[i] - offset, Z,
                        texture.getInterpolatedU(x[i] * 16),
                        texture.getInterpolatedV(z[i] * 16));
            }
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.DOWN;
            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            for(int i = 0; i < 4; i++)
            {
            	X = Math.min(Math.max(x[i], offset), 1f - offset);
            	Z = Math.min(Math.max(z[i], offset), 1f - offset);
                
            	putVertex(
            			quadBuilder, side,
            			Z, 0 + offset, X,
            			texture.getInterpolatedU(z[i] * 16),
            			texture.getInterpolatedV(x[i] * 16));
            }
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.EAST;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[2] - offset, y[2], z[2],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[2] - offset, 0 + offset, z[2],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[3] - offset, 0 + offset, z[3],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[3] - offset, y[3], z[3],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.WEST;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[0] + offset, y[0], z[0],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[0] + offset, 0 + offset, z[0],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[1] + offset, 0 + offset, z[1],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[1] + offset, y[1], z[1],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.SOUTH;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[1], y[1], z[1] - offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[1], 0 + offset, z[1] - offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[2], 0 + offset, z[2] - offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[2], y[2], z[2] - offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.NORTH;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[3], y[3], z[3] + offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[3], 0 + offset, z[3] + offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[0], 0 + offset, z[0] + offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[0], y[0], z[0] + offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
        }
        
        if(fluid.getTemperature() < 1000){
        	
        	float[] y = new float[4];
        	
            for(int i = 0; i < 4; i++)
            {
                y[i] = 0.5f + ((float)level / 10);
            }
        	
        	UnpackedBakedQuad.Builder quadBuilder;
            EnumFacing side = EnumFacing.UP;
            
        	quadBuilder = new UnpackedBakedQuad.Builder(format);
        	quadBuilder.setQuadOrientation(side);
        	quadBuilder.setTexture(texture);
        	float X, Z;
            
            for (int i = 0; i < 4; i++)
            {
            	X = Math.min(Math.max(x[i], offset), 1f - offset);
            	Z = Math.min(Math.max(z[i], offset), 1f - offset);
            	
            	putVertex(
                    	quadBuilder, side,
                        X, y[i] - offset, Z,
                        texture.getInterpolatedU(x[i] * 16),
                        texture.getInterpolatedV(z[i] * 16));
            }
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.DOWN;
            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            for(int i = 0; i < 4; i++)
            {
            	X = Math.min(Math.max(x[i], offset), 1f - offset);
            	Z = Math.min(Math.max(z[i], offset), 1f - offset);
                
            	putVertex(
            			quadBuilder, side,
            			Z, 0.5f + offset, X,
            			texture.getInterpolatedU(z[i] * 16),
            			texture.getInterpolatedV(x[i] * 16));
            }
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.EAST;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[2] - offset, y[2], z[2],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[2] - offset, 0.5f + offset, z[2],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[3] - offset, 0.5f + offset, z[3],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[3] - offset, y[3], z[3],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.WEST;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[0] + offset, y[0], z[0],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[0] + offset, 0.5f + offset, z[0],
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[1] + offset, 0.5f + offset, z[1],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[1] + offset, y[1], z[1],
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.SOUTH;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[1], y[1], z[1] - offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[1], 0.5f + offset, z[1] - offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[2], 0.5f + offset, z[2] - offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[2], y[2], z[2] - offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
            
            side = EnumFacing.NORTH;

            quadBuilder = new UnpackedBakedQuad.Builder(format);
            quadBuilder.setQuadOrientation(side);
            quadBuilder.setTexture(texture);
            
            putVertex(
                	quadBuilder, side,
                    x[3], y[3], z[3] + offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16 - level));
            putVertex(
                	quadBuilder, side,
                    x[3], 0.5f + offset, z[3] + offset,
                    texture.getInterpolatedU(0),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[0], 0.5f + offset, z[0] + offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16));
            putVertex(
                	quadBuilder, side,
                    x[0], y[0], z[0] + offset,
                    texture.getInterpolatedU(16),
                    texture.getInterpolatedV(16 - level));
            
            faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
        }
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
    
	private void putVertex(UnpackedBakedQuad.Builder builder, EnumFacing side, float x, float y, float z, float u, float v)
    {
        for(int e = 0; e < format.getElementCount(); e++)
        {
            switch(format.getElement(e).getUsage())
            {
            	case POSITION:
            		float[] data = new float[] { x, y, z };
            		builder.put(e, data);
            		break;
            	
            	case COLOR:
            		builder.put(e,
            				((fluid.getColor() >> 16) & 0xFF) / 255f,
            				((fluid.getColor() >> 8) & 0xFF) / 255f,
            				(fluid.getColor() & 0xFF) / 255f,
            				((fluid.getColor() >> 24) & 0xFF) / 255f);
            		break;
            	
            	case UV:
            		if(format.getElement(e).getIndex() == 0)
            		{
            			builder.put(e, u, v);
            			break;
            		}
            	
            	case NORMAL:
            		builder.put(e, (float)side.getFrontOffsetX(), (float)side.getFrontOffsetY(), (float)side.getFrontOffsetZ(), 0f);
            		break;
            	
            	default:
            		builder.put(e);
            		break;
            }
        }
    }
}
