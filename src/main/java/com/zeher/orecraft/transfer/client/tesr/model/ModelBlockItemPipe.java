package com.zeher.orecraft.transfer.client.tesr.model;

import org.lwjgl.util.vector.Vector3f;

import com.zeher.zeherlib.api.connection.ConnectionType;
import com.zeher.zeherlib.transfer.IItemPipe;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class ModelBlockItemPipe extends ModelBase {
	private ModelRenderer _base;
	private ModelRenderer _basehoro;
	private ModelRenderer _basehoroNS;
	private ModelRenderer _basevert;
	private ModelRenderer _cableConn;
	private ModelRenderer _interfaceConn;
	private ModelRenderer _SUPPORTConn;
	// private ModelRenderer _bandWhite;
	private Vector3f[] _bandColors = new Vector3f[16];

	public ModelBlockItemPipe() {
		this.textureWidth = 64;
		this.textureHeight = 36;

		this._base = new ModelRenderer(this, 0, 0);
		this._base.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
		this._base.setRotationPoint(0.0F, 0.0F, 0.0F);
		this._base.setTextureSize(64, 36);
		this._base.mirror = true;
		setRotation(this._base, 0.0F, 0.0F, 0.0F);
		this._basehoro = new ModelRenderer(this, 0, 24);
		this._basehoro.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
		this._basehoro.setRotationPoint(0.0F, 0.0F, 0.0F);
		this._basehoro.setTextureSize(64, 36);
		this._basehoro.mirror = true;
		setRotation(this._basehoro, 0.0F, 0.0F, 0.0F);
		this._basehoroNS = new ModelRenderer(this, 30, 12);
		this._basehoroNS.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
		this._basehoroNS.setRotationPoint(0.0F, 0.0F, 0.0F);
		this._basehoroNS.setTextureSize(64, 36);
		this._basehoroNS.mirror = true;
		setRotation(this._basehoroNS, 0.0F, 0.0F, 0.0F);
		this._basevert = new ModelRenderer(this, 24, 0);
		this._basevert.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
		this._basevert.setRotationPoint(0.0F, 0.0F, 0.0F);
		this._basevert.setTextureSize(64, 36);
		this._basevert.mirror = true;
		setRotation(this._basevert, 0.0F, 0.0F, 0.0F);
		this._cableConn = new ModelRenderer(this, 0, 12);
		this._cableConn.addBox(3.0F, -3.0F, -3.0F, 5, 6, 6);
		this._cableConn.setRotationPoint(0.0F, 0.0F, 0.0F);
		this._cableConn.setTextureSize(64, 36);
		this._cableConn.mirror = true;
		setRotation(this._cableConn, 0.0F, 0.0F, 0.0F);
		this._interfaceConn = new ModelRenderer(this, 30, 24);
		this._interfaceConn.addBox(3.0F, -3.0F, -3.0F, 5, 6, 6);
		this._interfaceConn.setRotationPoint(0.0F, 0.0F, 0.0F);
		this._interfaceConn.setTextureSize(64, 36);
		this._interfaceConn.mirror = true;
		setRotation(this._interfaceConn, 0.0F, 0.0F, 0.0F);
		this._SUPPORTConn = new ModelRenderer(this, 30, 24);
		this._SUPPORTConn.addBox(3.0F, -1.0F, -1.0F, 5, 2, 2);
		this._SUPPORTConn.setRotationPoint(0.0F, 0.0F, 0.0F);
		this._SUPPORTConn.setTextureSize(64, 36);
		this._SUPPORTConn.mirror = true;
		setRotation(this._SUPPORTConn, 0.0F, 0.0F, 0.0F);
		/**
		 * this._bandWhite = new ModelRenderer(this, 24, 0);
		 * this._bandWhite.addBox(7.0F, -2.5F, -2.5F, 1, 5, 5);
		 * this._bandWhite.setRotationPoint(0.0F, 0.0F, 0.0F);
		 * this._bandWhite.setTextureSize(64, 36); this._bandWhite.mirror = true;
		 * setRotation(this._bandWhite, 0.0F, 0.0F, 0.0F);
		 */
	}

	public void Render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this._base.render(f5);
		this._cableConn.render(f5);
		this._interfaceConn.render(f5);
		// this._bandWhite.render(f5);
	}

	public void render(float f5) {
		this._base.render(f5);
		renderSide(ConnectionType.PIPE.ITEM.ALL, 0.0F, 0.0F, f5);
		renderSide(ConnectionType.PIPE.ITEM.ALL, 3.1415927F, 0.0F, f5);
	}

	public void render(TileEntity entity, float f5) {
		if (entity instanceof IItemPipe) {

			if ((((IItemPipe) entity).getItemConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ITEM.ALL
					&& ((IItemPipe) entity).getItemConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ITEM.ALL)
					&& ((((IItemPipe) entity).getItemConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ITEM.ALL
							|| ((IItemPipe) entity).getItemConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ITEM.ALL)
							|| (((IItemPipe) entity).getItemConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ITEM.ALL
									|| ((IItemPipe) entity).getItemConnectionState(
											EnumFacing.SOUTH) == ConnectionType.PIPE.ITEM.ALL))) {
				this._base.render(f5);
			}

			else if (((IItemPipe) entity).getItemConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ITEM.ALL
					&& ((IItemPipe) entity).getItemConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ITEM.ALL
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ITEM.LONG)) {
				this._basevert.render(f5);
			}

			else if ((((IItemPipe) entity).getItemConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ITEM.ALL
					&& ((IItemPipe) entity).getItemConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ITEM.ALL)
					&& ((((IItemPipe) entity).getItemConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ITEM.ALL
							|| ((IItemPipe) entity).getItemConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ITEM.ALL)
							|| (((IItemPipe) entity).getItemConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ITEM.ALL
									|| ((IItemPipe) entity).getItemConnectionState(
											EnumFacing.DOWN) == ConnectionType.PIPE.ITEM.ALL))) {
				this._base.render(f5);
			}

			else if (((((IItemPipe) entity).getItemConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ITEM.ALL
					&& ((IItemPipe) entity).getItemConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ITEM.ALL))
					&& (((IItemPipe) entity).getItemConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ITEM.ALL
							|| ((IItemPipe) entity).getItemConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ITEM.ALL)
					|| (((IItemPipe) entity).getItemConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ITEM.ALL
							|| ((IItemPipe) entity).getItemConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ITEM.ALL)) {
				this._base.render(f5);
			}

			else if ((((IItemPipe) entity).getItemConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ITEM.ALL
					&& ((IItemPipe) entity).getItemConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ITEM.ALL)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ITEM.LONG)) {
				this._basehoro.render(f5);
			}

			else if (((IItemPipe) entity).getItemConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ITEM.ALL
					&& ((IItemPipe) entity).getItemConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ITEM.ALL
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ITEM.LONG)
					&& !(((IItemPipe) entity).getItemConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ITEM.LONG)) {
				this._basehoroNS.render(f5);
			} else {
				this._base.render(f5);
			}
			renderSide(((IItemPipe) entity).getItemConnectionState(EnumFacing.EAST), 0.0F, 0.0F, f5);
			renderSide(((IItemPipe) entity).getItemConnectionState(EnumFacing.SOUTH), -1.5707964F, 0.0F, f5);
			renderSide(((IItemPipe) entity).getItemConnectionState(EnumFacing.WEST), 3.1415927F, 0.0F, f5);
			renderSide(((IItemPipe) entity).getItemConnectionState(EnumFacing.NORTH), 1.5707964F, 0.0F, f5);
			renderSide(((IItemPipe) entity).getItemConnectionState(EnumFacing.UP), 0.0F, 1.5707964F, f5);
			renderSide(((IItemPipe) entity).getItemConnectionState(EnumFacing.DOWN), 0.0F, -1.5707964F, f5);
		}
	}

	private void renderSide(ConnectionType.PIPE.ITEM state, float yRot, float zRot, float scale) {
		renderModelState(state, yRot, zRot, scale);
	}

	private void renderModelState(ConnectionType.PIPE.ITEM state, float yRot, float zRot, float scale) {
		if (state.equals(ConnectionType.PIPE.ITEM.ALL)) {
			this._cableConn.rotateAngleY = yRot;
			this._cableConn.rotateAngleZ = zRot;
			this._cableConn.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ITEM.LONG)) {
			this._interfaceConn.rotateAngleY = yRot;
			this._interfaceConn.rotateAngleZ = zRot;
			this._interfaceConn.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ITEM.SUPPORT)) {
			this._SUPPORTConn.rotateAngleY = yRot;
			this._SUPPORTConn.rotateAngleZ = zRot;
			this._SUPPORTConn.render(scale);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotataionAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
