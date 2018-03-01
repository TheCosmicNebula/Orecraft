package com.zeher.orecraft.core.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidHandler {

	public static Fluid coolant;
	public static Fluid energized_redstone;
	public static Fluid glowstoneinfused_magma;
	
	public static ResourceLocation coolant_still = new ResourceLocation("orecraft:blocks/fluid/coolant/coolant_still");
	public static ResourceLocation coolant_flow = new ResourceLocation("orecraft:blocks/fluid/coolant/coolant_flow");
	
	public static ResourceLocation energized_redstone_still = new ResourceLocation("orecraft:blocks/fluid/energized_redstone/energized_redstone_still");
	public static ResourceLocation energized_redstone_flow = new ResourceLocation("orecraft:blocks/fluid/energized_redstone/energized_redstone_flow");
	
	public static ResourceLocation glowstoneinfused_magma_still = new ResourceLocation("orecraft:blocks/fluid/glowstoneinfused_magma/glowstoneinfused_magma_still");
	public static ResourceLocation glowstoneinfused_magma_flow = new ResourceLocation("orecraft:blocks/fluid/glowstoneinfused_magma/glowstoneinfused_magma_flow");

	public static void preInit() {
		final Fluid coolant = new Fluid("coolant", coolant_still, coolant_flow);
		coolant.setUnlocalizedName("coolant").setDensity(2000).setViscosity(500).setTemperature(-500);
		
		final Fluid energized_redstone = new Fluid("energized_redstone", energized_redstone_still, energized_redstone_flow);
		energized_redstone.setUnlocalizedName("energized_redstone").setDensity(2500).setViscosity(4000).setTemperature(1500);
		
		final Fluid glowstoneinfused_magma = new Fluid("glowstoneinfused_magma", glowstoneinfused_magma_still, glowstoneinfused_magma_flow);
		glowstoneinfused_magma.setUnlocalizedName("glowstoneinfused_magma").setDensity(4000).setViscosity(6000).setTemperature(2500);
		
		registerFluid(coolant);
		registerFluid(energized_redstone);
		registerFluid(glowstoneinfused_magma);
		
	}

	public static void register() {}

	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		
	}

}
