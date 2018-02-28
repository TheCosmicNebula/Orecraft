package com.zeher.orecraft.network.packet;

import com.zeher.orecraft.machine.tileentity.TileEntityPoweredOreProcessor;
import com.zeher.orecraft.storage.util.StorageUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOreProcessor implements IMessage {
	
	private static BlockPos pos;
	private static String task;
	private static int mode;
	private static TileEntity tile;
	
	public PacketOreProcessor(){}
	
	public PacketOreProcessor(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		this.pos = pos;
		this.task = task;
		this.tile = tile;
		this.mode = mode;
	}
	
	public PacketOreProcessor(BlockPos pos, String tank, String task, TileEntity tile){
		this.pos = pos;
		this.task = task;
		this.tile = tile;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) { }
	
	public static class Handler implements IMessageHandler<PacketOreProcessor, IMessage> {
		@Override
		public IMessage onMessage(final PacketOreProcessor message, final MessageContext ctx) {
			TileEntity tile_two = ctx.getServerHandler().playerEntity.world.getTileEntity(pos);
			
			if(tile_two != null){
				if(tile_two instanceof TileEntityPoweredOreProcessor){
					if(task.equals("empty")){
						((TileEntityPoweredOreProcessor) tile_two).tank.drain(((TileEntityPoweredOreProcessor) tile_two).getTank().getFluid(), true);
					}
					if(task.equals("mode_set")){
						((TileEntityPoweredOreProcessor) tile_two).setMode(mode);
					}
					if(task.equals("mode_cycle")){
						((TileEntityPoweredOreProcessor) tile_two).cycleMode();
					}
				}
			}
			if(tile != null){
				if(tile instanceof TileEntityPoweredOreProcessor){
					if(task.equals("empty")){
						((TileEntityPoweredOreProcessor) tile).tank.drain(((TileEntityPoweredOreProcessor) tile).getTank().getFluid(), true);
					}
					if(task.equals("mode_set")){
						((TileEntityPoweredOreProcessor) tile).setMode(mode);
					}
					if(task.equals("mode_cycle")){
						((TileEntityPoweredOreProcessor) tile).cycleMode();
					}
				}
			} 
			StorageUtil.syncBlockAndRerender(ctx.getServerHandler().playerEntity.world, pos);
			return null;
		}
	
	}

}
