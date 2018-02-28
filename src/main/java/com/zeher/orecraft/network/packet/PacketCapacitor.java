package com.zeher.orecraft.network.packet;

import com.zeher.orecraft.storage.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.orecraft.storage.tileentity.TileEntityPoweredCapacitor;
import com.zeher.orecraft.storage.util.StorageUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCapacitor implements IMessage {
	
	private static String side;
	private static BlockPos pos;
	private static boolean cycle;
	private static int value;
	private static TileEntity tile;
	
	public PacketCapacitor(){}
	
	public PacketCapacitor(String side, BlockPos pos, TileEntity tile){
		this.side = side;
		this.pos = pos;
		this.tile = tile;
		cycle = true;
	}
	
	public PacketCapacitor(String side, BlockPos pos, TileEntity tile, boolean cycle, int value){
		this.side = side;
		this.pos = pos;
		this.cycle = cycle;
		this.value = value;
		this.tile = tile;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		side = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, side);
	}
	
	public static class Handler implements IMessageHandler<PacketCapacitor, IMessage> {
		@Override
		public IMessage onMessage(final PacketCapacitor message, final MessageContext ctx) {
			
			TileEntity tile_two = ctx.getServerHandler().playerEntity.world.getTileEntity(pos);
			
			if(!tile_two.equals(null)){
				if(tile_two instanceof TileEntityPoweredCapacitor){
					if(cycle){
						((TileEntityPoweredCapacitor) tile_two).cycleSide(side);
					} else {
						((TileEntityPoweredCapacitor) tile_two).setSideNBT(side, value);
					}
				}
				if(tile_two instanceof TileEntityEnergizedCapacitor){
					if(cycle){
						((TileEntityEnergizedCapacitor) tile_two).cycleSide(side);
					} else {
						((TileEntityEnergizedCapacitor) tile_two).setSideNBT(side, value);
					}
				}
				
			}
			StorageUtil.syncBlockAndRerender(ctx.getServerHandler().playerEntity.world, pos);
			return null;
		}
	
	}

}
