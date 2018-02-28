package com.zeher.orecraft.network.packet;

import com.zeher.orecraft.machine.tileentity.TileEntityEnergizedCombiner;
import com.zeher.orecraft.storage.util.StorageUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCombiner implements IMessage {
	
	private static BlockPos pos;
	private static String task;
	private static int mode;
	private static TileEntity tile;
	
	public PacketCombiner(){}
	
	public PacketCombiner(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		this.pos = pos;
		this.task = task;
		this.tile = tile;
		this.mode = mode;
	}
	
	public PacketCombiner(BlockPos pos, String tank, String task, TileEntity tile){
		this.pos = pos;
		this.task = task;
		this.tile = tile;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) { }
	
	public static class Handler implements IMessageHandler<PacketCombiner, IMessage> {
		@Override
		public IMessage onMessage(final PacketCombiner message, final MessageContext ctx) {
			TileEntity tile_two = ctx.getServerHandler().playerEntity.world.getTileEntity(pos);
			
			if(tile_two != null){
				if(tile_two instanceof TileEntityEnergizedCombiner){
					if(task.equals("empty")){
						((TileEntityEnergizedCombiner) tile_two).tank.drain(((TileEntityEnergizedCombiner) tile_two).getTank().getFluid(), true);
					}
					if(task.equals("mode_set")){
						((TileEntityEnergizedCombiner) tile_two).setMode(mode);
					}
					if(task.equals("mode_cycle")){
						((TileEntityEnergizedCombiner) tile_two).cycleMode();
					}
				}
			}
			if(tile != null){
				if(tile instanceof TileEntityEnergizedCombiner){
					if(task.equals("empty")){
						((TileEntityEnergizedCombiner) tile).tank.drain(((TileEntityEnergizedCombiner) tile).getTank().getFluid(), true);
					}
					if(task.equals("mode_set")){
						((TileEntityEnergizedCombiner) tile).setMode(mode);
					}
					if(task.equals("mode_cycle")){
						((TileEntityEnergizedCombiner) tile).cycleMode();
					}
				}
			} 
			StorageUtil.syncBlockAndRerender(ctx.getServerHandler().playerEntity.world, pos);
			return null;
		}
	
	}

}
