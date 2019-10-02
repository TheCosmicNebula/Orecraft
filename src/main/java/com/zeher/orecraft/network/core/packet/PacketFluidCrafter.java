package com.zeher.orecraft.network.core.packet;

import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredFluidCrafter;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredHeatedFluidGenerator;
import com.zeher.orecraft.storage.core.util.StorageUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFluidCrafter implements IMessage {
	
	private static BlockPos pos;
	private static String task;
	private static int mode;
	private static TileEntity tile;
	
	public PacketFluidCrafter(){}
	
	public PacketFluidCrafter(BlockPos pos, String tank, String task, int mode, TileEntity tile){
		this.pos = pos;
		this.task = task;
		this.tile = tile;
		this.mode = mode;
	}
	
	public PacketFluidCrafter(BlockPos pos, String tank, String task, TileEntity tile){
		this.pos = pos;
		this.task = task;
		this.tile = tile;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) { }
	
	public static class Handler implements IMessageHandler<PacketFluidCrafter, IMessage> {
		@Override
		public IMessage onMessage(final PacketFluidCrafter message, final MessageContext ctx) {
			TileEntity tile_two = ctx.getServerHandler().player.world.getTileEntity(pos);
			
			if(tile_two != null){
				if(tile_two instanceof TileEntityPoweredFluidCrafter){
					if(task.equals("empty")){
						((TileEntityPoweredFluidCrafter) tile_two).getTank().drain(((TileEntityPoweredFluidCrafter) tile_two).getTank().getFluid(), true);
					}
					if(task.equals("mode_set")){
						((TileEntityPoweredFluidCrafter) tile_two).setMode(mode);
					}
					if(task.equals("mode_cycle")){
						((TileEntityPoweredFluidCrafter) tile_two).cycleMode();
					}
				}
			}
			if(tile != null){
				if(tile instanceof TileEntityPoweredFluidCrafter){
					if(task.equals("empty")){
						((TileEntityPoweredFluidCrafter) tile).getTank().drain(((TileEntityPoweredFluidCrafter) tile).getTank().getFluid(), true);
					}
					if(task.equals("mode_set")){
						((TileEntityPoweredFluidCrafter) tile).setMode(mode);
					}
					if(task.equals("mode_cycle")){
						((TileEntityPoweredFluidCrafter) tile).cycleMode();
					}
				}
			} 
			StorageUtil.syncBlockAndRerender(ctx.getServerHandler().player.world, pos);
			return null;
		}
	
	}

}
