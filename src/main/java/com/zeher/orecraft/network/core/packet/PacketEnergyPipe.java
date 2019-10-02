package com.zeher.orecraft.network.core.packet;

import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.transfer.IEnergyPipe;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergyPipe implements IMessage {
	
	private static EnumFacing side;
	private static BlockPos pos;
	private static boolean cycle;
	private static int value;
	private static TileEntity tile;
	
	public PacketEnergyPipe(){}
	
	public PacketEnergyPipe(EnumFacing side, BlockPos pos, TileEntity tile){
		this.side = side;
		this.pos = pos;
		this.tile = tile;
		cycle = true;
	}
	
	public PacketEnergyPipe(EnumFacing side, BlockPos pos, TileEntity tile, boolean cycle, int value){
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
	
	public static class Handler implements IMessageHandler<PacketEnergyPipe, IMessage> {
		@Override
		public IMessage onMessage(final PacketEnergyPipe message, final MessageContext ctx) {
			
			TileEntity tile_two = ctx.getServerHandler().player.world.getTileEntity(pos);
			
			
			if (tile_two != null) {
				if (tile_two instanceof IEnergyPipe) {
					if (cycle) {
						((IEnergyPipe) tile_two).cycleside(side);
					}
				}
			}
			StorageUtil.syncBlockAndRerender(ctx.getServerHandler().player.world, pos);
			return null;
		}
	
	}

}
