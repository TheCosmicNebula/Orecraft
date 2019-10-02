package com.zeher.orecraft.transfer.client.tesr.model;

import com.zeher.zeherlib.api.connection.ConnectionType;
import com.zeher.zeherlib.transfer.IEnergyPipe;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

public class ModelBlockEnergyPipe extends ModelBase {
	private ModelRenderer Base;
	private ModelRenderer Base_Horizontal;
	private ModelRenderer Base_Horizontal_NS;
	private ModelRenderer Base_Vertical;
	private ModelRenderer Connection_Cable;
	private ModelRenderer Connection_Long;
	private ModelRenderer Connection_Support;
	
	private ModelRenderer Interface_Input;
	private ModelRenderer Interface_Output;

	private ModelRenderer Interface_Input_Long;
	private ModelRenderer Interface_Output_Long;

	public ModelBlockEnergyPipe() {
		this.textureWidth = 96;
		this.textureHeight = 36;

		this.Base = new ModelRenderer(this, 0, 0);
		this.Base.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
		this.Base.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Base.setTextureSize(96, 36);
		this.Base.mirror = true;
		setRotation(this.Base, 0.0F, 0.0F, 0.0F);
		this.Base_Horizontal = new ModelRenderer(this, 0, 24);
		this.Base_Horizontal.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
		this.Base_Horizontal.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Base_Horizontal.setTextureSize(96, 36);
		this.Base_Horizontal.mirror = true;
		setRotation(this.Base_Horizontal, 0.0F, 0.0F, 0.0F);
		this.Base_Horizontal_NS = new ModelRenderer(this, 30, 12);
		this.Base_Horizontal_NS.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
		this.Base_Horizontal_NS.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Base_Horizontal_NS.setTextureSize(96, 36);
		this.Base_Horizontal_NS.mirror = true;
		setRotation(this.Base_Horizontal_NS, 0.0F, 0.0F, 0.0F);
		this.Base_Vertical = new ModelRenderer(this, 24, 0);
		this.Base_Vertical.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
		this.Base_Vertical.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Base_Vertical.setTextureSize(96, 36);
		this.Base_Vertical.mirror = true;
		setRotation(this.Base_Vertical, 0.0F, 0.0F, 0.0F);
		this.Connection_Cable = new ModelRenderer(this, 0, 12);
		this.Connection_Cable.addBox(3.0F, -3.0F, -3.0F, 5, 6, 6);
		this.Connection_Cable.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Connection_Cable.setTextureSize(96, 36);
		this.Connection_Cable.mirror = true;
		setRotation(this.Connection_Cable, 0.0F, 0.0F, 0.0F);
		this.Connection_Long = new ModelRenderer(this, 30, 24);
		this.Connection_Long.addBox(3.0F, -3.0F, -3.0F, 8, 6, 6);
		this.Connection_Long.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Connection_Long.setTextureSize(96, 36);
		this.Connection_Long.mirror = true;
		setRotation(this.Connection_Long, 0.0F, 0.0F, 0.0F);
		this.Connection_Support = new ModelRenderer(this, 30, 24);
		this.Connection_Support.addBox(3.0F, -1.0F, -1.0F, 5, 2, 2);
		this.Connection_Support.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Connection_Support.setTextureSize(96, 36);
		this.Connection_Support.mirror = true;
		setRotation(this.Connection_Support, 0.0F, 0.0F, 0.0F);
		
		this.Interface_Input = new ModelRenderer(this, 58, 0);
		this.Interface_Input.addBox(5.0F, -3.5F, -3.5F, 3, 7, 7);
		this.Interface_Input.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Interface_Input.setTextureOffset(96, 36);
		this.Interface_Input.mirror = true;
		setRotation(this.Interface_Input, 0.0F, 0.0F, 0.0F);
		
		this.Interface_Output = new ModelRenderer(this, 58, 15);
		this.Interface_Output.addBox(5.0F, -3.5F, -3.5F, 3, 7, 7);
		this.Interface_Output.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Interface_Output.setTextureOffset(96, 36);
		this.Interface_Output.mirror = true;
		setRotation(this.Interface_Output, 0.0F, 0.0F, 0.0F);
		
		this.Interface_Input_Long = new ModelRenderer(this, 58, 0);
		this.Interface_Input_Long.addBox(8.0F, -3.5F, -3.5F, 3, 7, 7);
		this.Interface_Input_Long.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Interface_Input_Long.setTextureOffset(96, 36);
		this.Interface_Input_Long.mirror = true;
		setRotation(this.Interface_Input_Long, 0.0F, 0.0F, 0.0F);
		
		this.Interface_Output_Long = new ModelRenderer(this, 58, 15);
		this.Interface_Output_Long.addBox(8.0F, -3.5F, -3.5F, 3, 7, 7);
		this.Interface_Output_Long.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Interface_Output_Long.setTextureOffset(96, 36);
		this.Interface_Output_Long.mirror = true;
		setRotation(this.Interface_Output_Long, 0.0F, 0.0F, 0.0F);
		
	}

	public void Render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Base.render(f5);
		this.Connection_Cable.render(f5);
		this.Connection_Long.render(f5);
	}
	
	public void render(IEnergyPipe entity, float f5) {
		if ((entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.ALL
				&& entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.ALL)
				&& ((entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.ALL
				|| entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.ALL)
				|| (entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.ALL
				|| entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.ALL))) {
			this.Base.render(f5);
		}

		else if (entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.ALL
				&& entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.ALL
				&& !(entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.LONG) 
				&& !(entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.LONG) 
				&& !(entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.LONG)
				&& !(entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.LONG)
				&& !(entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.SHORT) 
				&& !(entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.SHORT) 
				&& !(entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.SHORT)
				&& !(entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.SHORT)) {
			this.Base_Vertical.render(f5);
		}

		else if ((entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.ALL
				&& entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.ALL)
				&& ((entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.ALL
				|| entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.ALL)
				|| (entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.ALL
				|| entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.ALL))) {
			this.Base.render(f5);
		}

		else if (((entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.ALL
				&& entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.ALL))
				&& (entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.ALL
			    || entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.ALL)
				|| (entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.ALL
				|| entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.ALL)) {
			this.Base.render(f5);
		}

		else if ((entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.ALL
				&& entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.ALL) 
				&& !(entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.LONG) 
				&& !(entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.LONG) 
				&& !(entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.LONG)
				&& !(entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.LONG)
				&& !(entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.SHORT) 
				&& !(entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.SHORT) 
				&& !(entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.SHORT)
				&& !(entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.SHORT)) {
			this.Base_Horizontal.render(f5);
		}

		else if (entity.getPipeConnectionState(EnumFacing.NORTH) == ConnectionType.PIPE.ENERGY.ALL
				&& entity.getPipeConnectionState(EnumFacing.SOUTH) == ConnectionType.PIPE.ENERGY.ALL
				&& !(entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.LONG) 
				&& !(entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.LONG) 
				&& !(entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.LONG)
				&& !(entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.LONG)
				&& !(entity.getPipeConnectionState(EnumFacing.EAST) == ConnectionType.PIPE.ENERGY.SHORT) 
				&& !(entity.getPipeConnectionState(EnumFacing.WEST) == ConnectionType.PIPE.ENERGY.SHORT) 
				&& !(entity.getPipeConnectionState(EnumFacing.UP) == ConnectionType.PIPE.ENERGY.SHORT)
				&& !(entity.getPipeConnectionState(EnumFacing.DOWN) == ConnectionType.PIPE.ENERGY.SHORT)) {
			this.Base_Horizontal_NS.render(f5);
		} else {
			this.Base.render(f5);
		}
		
		this.renderSide(entity.getPipeConnectionState(EnumFacing.EAST), 0.0F, 0.0F, f5);
		this.renderSide(entity.getPipeConnectionState(EnumFacing.SOUTH), -1.5707964F, 0.0F, f5);
		this.renderSide(entity.getPipeConnectionState(EnumFacing.WEST), 3.1415927F, 0.0F, f5);
		this.renderSide(entity.getPipeConnectionState(EnumFacing.NORTH), 1.5707964F, 0.0F, f5);
		this.renderSide(entity.getPipeConnectionState(EnumFacing.UP), 0.0F, 1.5707964F, f5);
		this.renderSide(entity.getPipeConnectionState(EnumFacing.DOWN), 0.0F, -1.5707964F, f5);
	}

	private void renderSide(ConnectionType.PIPE.ENERGY state, float yRot, float zRot, float scale) {
		this.renderModelState(state, yRot, zRot, scale);
	}

	private void renderModelState(ConnectionType.PIPE.ENERGY state, float yRot, float zRot, float scale) {
		if (state.equals(ConnectionType.PIPE.ENERGY.ALL)) {
			this.Connection_Cable.rotateAngleY = yRot;
			this.Connection_Cable.rotateAngleZ = zRot;
			this.Connection_Cable.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ENERGY.LONG)) {
			this.Connection_Long.rotateAngleY = yRot;
			this.Connection_Long.rotateAngleZ = zRot;
			this.Connection_Long.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ENERGY.SUPPORT)) {
			this.Connection_Support.rotateAngleY = yRot;
			this.Connection_Support.rotateAngleZ = zRot;
			this.Connection_Support.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ENERGY.SHORT)) {
			this.Connection_Cable.rotateAngleY = yRot;
			this.Connection_Cable.rotateAngleZ = zRot;
			this.Connection_Cable.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ENERGY.INPUT)) {
			this.Connection_Cable.rotateAngleY = yRot;
			this.Connection_Cable.rotateAngleZ = zRot;
			this.Connection_Cable.render(scale);
			
			this.Interface_Input.rotateAngleY = yRot;
			this.Interface_Input.rotateAngleZ = zRot;
			this.Interface_Input.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ENERGY.OUTPUT)) {
			this.Connection_Cable.rotateAngleY = yRot;
			this.Connection_Cable.rotateAngleZ = zRot;
			this.Connection_Cable.render(scale);
			
			this.Interface_Output.rotateAngleY = yRot;
			this.Interface_Output.rotateAngleZ = zRot;
			this.Interface_Output.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ENERGY.LONG_INPUT)) {
			this.Connection_Long.rotateAngleY = yRot;
			this.Connection_Long.rotateAngleZ = zRot;
			this.Connection_Long.render(scale);
			
			this.Interface_Input_Long.rotateAngleY = yRot;
			this.Interface_Input_Long.rotateAngleZ = zRot;
			this.Interface_Input_Long.render(scale);
		}
		if (state.equals(ConnectionType.PIPE.ENERGY.LONG_OUTPUT)) {
			this.Connection_Long.rotateAngleY = yRot;
			this.Connection_Long.rotateAngleZ = zRot;
			this.Connection_Long.render(scale);
			
			this.Interface_Output_Long.rotateAngleY = yRot;
			this.Interface_Output_Long.rotateAngleZ = zRot;
			this.Interface_Output_Long.render(scale);
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
