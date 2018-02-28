package com.zeher.orecraft.network.packet;

import com.zeher.orecraft.production.tileentity.TileEntityHeatedFluidGeneratorBasic;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHeatedFluidGenerator implements IMessage {
	
	private static BlockPos pos;
	private static String task;
	private static TileEntity tile;
	
	public PacketHeatedFluidGenerator(){}
	
	public PacketHeatedFluidGenerator(BlockPos pos, String task, TileEntity tile){
		this.pos = pos;
		this.task = task;
		this.tile = tile;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class Handler implements IMessageHandler<PacketHeatedFluidGenerator, IMessage> {
		@Override
		public IMessage onMessage(final PacketHeatedFluidGenerator message, final MessageContext ctx) {
			
			if(tile != null){
				if(tile instanceof TileEntityHeatedFluidGeneratorBasic){
					if(task.equals("empty")){
						((TileEntityHeatedFluidGeneratorBasic) tile).tank.drain(((TileEntityHeatedFluidGeneratorBasic) tile).getTank().getFluid(), true);
					}
				}
			}
			ctx.getServerHandler().playerEntity.world.markBlockRangeForRenderUpdate(pos, pos);
			return null;
		}
	
	}

}
