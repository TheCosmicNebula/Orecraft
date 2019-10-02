package com.zeher.orecraft.network.core.packet;

import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.storage.IStorage;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCapacitor implements IMessage {
	
	private static EnumFacing side;
	private static BlockPos pos;
	private static boolean cycle;
	private static int value;
	private static TileEntity tile;
	
	public PacketCapacitor(){}
	
	public PacketCapacitor(EnumFacing side, BlockPos pos, TileEntity tile){
		this.side = side;
		this.pos = pos;
		this.tile = tile;
		cycle = true;
	}
	
	public PacketCapacitor(EnumFacing side, BlockPos pos, TileEntity tile, boolean cycle, int value){
		this.side = side;
		this.pos = pos;
		this.cycle = cycle;
		this.value = value;
		this.tile = tile;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }
	
	public static class Handler implements IMessageHandler<PacketCapacitor, IMessage> {
		@Override
		public IMessage onMessage(final PacketCapacitor message, final MessageContext ctx) {
			
			TileEntity tile_offset = ctx.getServerHandler().player.world.getTileEntity(pos);
			
			if(!tile_offset.equals(null)){
				if (tile_offset instanceof IStorage) {
					if (cycle) {
						((IStorage) tile_offset).cycleside(side);
					}
				}
			}
			StorageUtil.syncBlockAndRerender(ctx.getServerHandler().player.world, pos);
			return null;
		}
	
	}

}