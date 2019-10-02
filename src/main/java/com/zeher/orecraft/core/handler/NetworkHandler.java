package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.network.core.packet.PacketCapacitor;
import com.zeher.orecraft.network.core.packet.PacketEnergyPipe;
import com.zeher.orecraft.network.core.packet.PacketFluidCrafter;
import com.zeher.orecraft.network.core.packet.PacketHeatedFluidGenerator;
import com.zeher.orecraft.network.core.packet.PacketOreProcessor;
import com.zeher.orecraft.network.core.packet.PacketPeltierGenerator;
import com.zeher.orecraft.network.core.packet.PacketPowerTransfer;
import com.zeher.orecraft.storage.core.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.orecraft.storage.core.tileentity.TileEntityPoweredCapacitor;
import com.zeher.zeherlib.transfer.IEnergyPipe;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	public static void preInit() {
		Orecraft.NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Orecraft.MOD_ID);
		
		registerMessages();
	}
	
	public static void registerMessages() {
		Orecraft.NETWORK.registerMessage(PacketCapacitor.Handler.class, PacketCapacitor.class, 1, Side.SERVER);
		Orecraft.NETWORK.registerMessage(PacketPeltierGenerator.Handler.class, PacketPeltierGenerator.class, 2, Side.SERVER);
		Orecraft.NETWORK.registerMessage(PacketHeatedFluidGenerator.Handler.class, PacketHeatedFluidGenerator.class, 3, Side.SERVER);
		Orecraft.NETWORK.registerMessage(PacketOreProcessor.Handler.class, PacketOreProcessor.class, 4, Side.SERVER);
		Orecraft.NETWORK.registerMessage(PacketFluidCrafter.Handler.class, PacketFluidCrafter.class, 5, Side.SERVER);
		
		Orecraft.NETWORK.registerMessage(PacketPowerTransfer.Handler.class, PacketPowerTransfer.class, 10, Side.SERVER);
		
		Orecraft.NETWORK.registerMessage(PacketEnergyPipe.Handler.class, PacketEnergyPipe.class, 6, Side.SERVER);
	}
	
	public static void sendCapacitorPacket(EnumFacing side, BlockPos pos, TileEntity tile){
		Orecraft.NETWORK.sendToServer(new PacketCapacitor(side, pos, tile));
		if(tile instanceof TileEntityPoweredCapacitor){
			((TileEntityPoweredCapacitor) tile).cycleside(side);
		}
		if(tile instanceof TileEntityEnergizedCapacitor){
			((TileEntityEnergizedCapacitor) tile).cycleside(side);
		}
	}
	
	public static void sendEnergyPipePacket(EnumFacing side, BlockPos pos, TileEntity tile) {
		Orecraft.NETWORK.sendToServer(new PacketEnergyPipe(side, pos, tile));
		
		if (tile instanceof IEnergyPipe) {
			((IEnergyPipe) tile).cycleside(side);
		}
	}
	
	public static void sendPeltierGeneratorPacket(BlockPos pos, String tank, String task, TileEntity tile){
		Orecraft.NETWORK.sendToServer(new PacketPeltierGenerator(pos, tank, task, tile));
	}
	
	public static void sendHeatedFluidGeneratorPacket(BlockPos pos, String tank, String task, TileEntity tile){
		Orecraft.NETWORK.sendToServer(new PacketHeatedFluidGenerator(pos, task, tile));
	}
	
	public static void sendPoweredOreProcessorPacket(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		Orecraft.NETWORK.sendToServer(new PacketOreProcessor(pos, tank, task, mode, tile));
	}
	
	public static void sendPoweredOreProcessorPacket(BlockPos pos, String tank, String task, TileEntity tile){
		Orecraft.NETWORK.sendToServer(new PacketOreProcessor(pos, tank, task, tile));
	}
	
	public static void sendPoweredFluidCrafterPacket(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		Orecraft.NETWORK.sendToServer(new PacketFluidCrafter(pos, tank, task, mode, tile));
	}
	
	public static void sendPoweredFluidCrafterPacket(BlockPos pos, String tank, String task, TileEntity tile){
		Orecraft.NETWORK.sendToServer(new PacketFluidCrafter(pos, tank, task, tile));
	}
}
