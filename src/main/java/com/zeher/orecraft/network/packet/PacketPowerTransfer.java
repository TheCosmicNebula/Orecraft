package com.zeher.orecraft.network.packet;

import com.zeher.orecraft.storage.tileentity.TileEntityPoweredCapacitor;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPowerTransfer implements IMessage {

	private static BlockPos fromPos;
	private static BlockPos toPos;

	public PacketPowerTransfer() {
	}

	public PacketPowerTransfer(BlockPos fromPos, BlockPos toPos) {
		this.fromPos = fromPos;
		this.toPos = toPos;
	}

	public static class Handler implements IMessageHandler<PacketPowerTransfer, IMessage> {
		@Override
		public IMessage onMessage(final PacketPowerTransfer message, final MessageContext ctx) {
			TileEntityPoweredCapacitor tileTo = (TileEntityPoweredCapacitor) ctx.getServerHandler().playerEntity.world
					.getTileEntity(toPos);
			TileEntityPoweredCapacitor tileFrom = (TileEntityPoweredCapacitor) ctx.getServerHandler().playerEntity.world
					.getTileEntity(fromPos);
			if (tileTo != null && tileFrom != null) {
				if (toPos == fromPos) {
					System.out.println("OO");
				}
				if (tileTo.stored < tileTo.capacity && tileFrom.stored > 0) {
					tileTo.stored = +10000;
					tileFrom.stored -= 10000;
					System.out.println("Sending power....");
				}
			}
			if (tileTo == null || tileFrom == null) {
				// System.out.println("TileEntity is NULL!. Report to mod
				// author!");
			}
			return null;
		}

	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

}
