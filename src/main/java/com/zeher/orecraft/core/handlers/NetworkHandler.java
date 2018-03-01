package com.zeher.orecraft.core.handlers;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.network.packet.*;
import com.zeher.orecraft.storage.tileentity.*;
import com.zeher.orecraft.storage.util.StorageUtil;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	public static void preInit() {
		OreCraft.network = NetworkRegistry.INSTANCE.newSimpleChannel("orecraft");
		
		OreCraft.network.registerMessage(PacketCapacitor.Handler.class, PacketCapacitor.class, 1, Side.SERVER);
		OreCraft.network.registerMessage(PacketPeltierGenerator.Handler.class, PacketPeltierGenerator.class, 2, Side.SERVER);
		OreCraft.network.registerMessage(PacketHeatedFluidGenerator.Handler.class, PacketHeatedFluidGenerator.class, 3, Side.SERVER);
		OreCraft.network.registerMessage(PacketOreProcessor.Handler.class, PacketOreProcessor.class, 4, Side.SERVER);
		OreCraft.network.registerMessage(PacketCombiner.Handler.class, PacketCombiner.class, 5, Side.SERVER);
		OreCraft.network.registerMessage(PacketFluidCrafter.Handler.class, PacketFluidCrafter.class, 6, Side.SERVER);
		
		OreCraft.network.registerMessage(PacketPowerTransfer.Handler.class, PacketPowerTransfer.class, 10, Side.SERVER);
		
	}

	public void init() {}

	public void postInit() {}
	
	public static void sendCapacitorPacket(String side, BlockPos pos, TileEntity tile){
		OreCraft.network.sendToServer(new PacketCapacitor(side, pos, tile));
		if(tile instanceof TileEntityPoweredCapacitor){
			((TileEntityPoweredCapacitor) tile).cycleSide(side);
		}
		if(tile instanceof TileEntityEnergizedCapacitor){
			((TileEntityEnergizedCapacitor) tile).cycleSide(side);
		}
	}
	
	public static void sendPeltierGeneratorPacket(BlockPos pos, String tank, String task, TileEntity tile){
		OreCraft.network.sendToServer(new PacketPeltierGenerator(pos, tank, task, tile));
	}
	
	public static void sendHeatedFluidGeneratorPacket(BlockPos pos, String tank, String task, TileEntity tile){
		OreCraft.network.sendToServer(new PacketHeatedFluidGenerator(pos, task, tile));
	}
	
	public static void sendPoweredOreProcessorPacket(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		OreCraft.network.sendToServer(new PacketOreProcessor(pos, tank, task, mode, tile));
	}
	
	public static void sendPoweredOreProcessorPacket(BlockPos pos, String tank, String task, TileEntity tile){
		OreCraft.network.sendToServer(new PacketOreProcessor(pos, tank, task, tile));
	}

	public static void sendEnergizedCombinerPacket(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		OreCraft.network.sendToServer(new PacketCombiner(pos, tank, task, mode, tile));
	}
	
	public static void sendEnergizedCombinerPacket(BlockPos pos, String tank, String task, TileEntity tile){
		OreCraft.network.sendToServer(new PacketCombiner(pos, tank, task, tile));
	}
	
	public static void sendPoweredFluidCrafterPacket(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		OreCraft.network.sendToServer(new PacketFluidCrafter(pos, tank, task, mode, tile));
	}
	
	public static void sendPoweredFluidCrafterPacket(BlockPos pos, String tank, String task, TileEntity tile){
		OreCraft.network.sendToServer(new PacketFluidCrafter(pos, tank, task, tile));
	}
}
