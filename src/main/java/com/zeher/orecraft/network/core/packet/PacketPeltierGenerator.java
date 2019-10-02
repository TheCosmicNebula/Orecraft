package com.zeher.orecraft.network.core.packet;

import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredPeltierGenerator;
import com.zeher.orecraft.storage.core.util.StorageUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPeltierGenerator implements IMessage {
	
	private static BlockPos pos;
	private static String tank;
	private static String task;
	private static TileEntity tile;
	
	public PacketPeltierGenerator(){}
	
	public PacketPeltierGenerator(BlockPos pos, String tank, String task, TileEntity tile){
		this.pos = pos;
		this.tank = tank;
		this.task = task;
		this.tile = tile;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		tank = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, tank);
	}
	
	public static class Handler implements IMessageHandler<PacketPeltierGenerator, IMessage> {
		@Override
		public IMessage onMessage(final PacketPeltierGenerator message, final MessageContext ctx) {
			
			if(tile != null){
				if(tile instanceof TileEntityPoweredPeltierGenerator){
					if(task.equals("empty")){
						if(tank.equals("one")){
							((TileEntityPoweredPeltierGenerator) tile).tank.drain(((TileEntityPoweredPeltierGenerator) tile).getTank().getFluid(), true);
						}
						else if(tank.equals("two")){
							((TileEntityPoweredPeltierGenerator) tile).tank_two.drain(((TileEntityPoweredPeltierGenerator) tile).getTankTwo().getFluid(), true);
						} else {
							
						}
					}
				}
			} 
			StorageUtil.syncBlockAndRerender(ctx.getServerHandler().player.world, pos);
			return null;
		}
	
	}

}
