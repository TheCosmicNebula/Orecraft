package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.core.reference.OrecraftReference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidHandler {
	
	public static final ResourceLocation FLUID_COOLANT_STILL_TEXTURE = new ResourceLocation(OrecraftReference.RESOURCE.BLOCKS + "fluid/coolant/coolant_still");
	public static final ResourceLocation FLUID_COOLANT_FLOWING_TEXTURE = new ResourceLocation(OrecraftReference.RESOURCE.BLOCKS + "fluid/coolant/coolant_flow");
	
	public static final ResourceLocation FLUID_ENERGIZED_REDSTONE_STILL_TEXTURE = new ResourceLocation(OrecraftReference.RESOURCE.BLOCKS + "fluid/energized_redstone/energized_redstone_still");
	public static final ResourceLocation FLUID_ENERGIZED_REDSTONE_FLOWING_TEXTURE = new ResourceLocation(OrecraftReference.RESOURCE.BLOCKS + "fluid/energized_redstone/energized_redstone_flow");
	
	public static final ResourceLocation FLUID_GLOWSTONEINFUSED_MAGMA_STILL_TEXTURE = new ResourceLocation(OrecraftReference.RESOURCE.BLOCKS + "fluid/glowstoneinfused_magma/glowstoneinfused_magma_still");
	public static final ResourceLocation FLUID_GLOWSTONEINFUSED_MAGMA_FLOWING_TEXTURE = new ResourceLocation(OrecraftReference.RESOURCE.BLOCKS + "fluid/glowstoneinfused_magma/glowstoneinfused_magma_flow");

	public static final Fluid FLUID_COOLANT = new Fluid("coolant", FLUID_COOLANT_STILL_TEXTURE, FLUID_COOLANT_FLOWING_TEXTURE).setDensity(2000).setViscosity(500).setTemperature(-500);
	public static final Fluid FLUID_ENERGIZED_REDSTONE = new Fluid("energized_redstone", FLUID_ENERGIZED_REDSTONE_STILL_TEXTURE, FLUID_ENERGIZED_REDSTONE_FLOWING_TEXTURE).setDensity(2500).setViscosity(4000).setTemperature(1500);
	public static final Fluid FLUIS_GLOWSTONEINFUSED_MAGMA = new Fluid("glowstoneinfused_magma", FLUID_GLOWSTONEINFUSED_MAGMA_STILL_TEXTURE, FLUID_GLOWSTONEINFUSED_MAGMA_FLOWING_TEXTURE).setDensity(4000).setViscosity(6000).setTemperature(2500);
	
	public static void preInit() {
		registerFluid(FLUID_COOLANT);
		registerFluid(FLUID_ENERGIZED_REDSTONE);
		registerFluid(FLUIS_GLOWSTONEINFUSED_MAGMA);
	}
	
	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}
	
}
