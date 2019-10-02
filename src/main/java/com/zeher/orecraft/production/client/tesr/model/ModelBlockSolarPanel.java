package com.zeher.orecraft.production.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

public class ModelBlockSolarPanel extends ModelBase {

	public ModelRenderer baselower;
	public ModelRenderer basemid;
	public ModelRenderer baseupper;
	
	public ModelRenderer ring0;
	public ModelRenderer ring1;
	public ModelRenderer ring2;
	public ModelRenderer ring3;
	
	public ModelRenderer connector0;
	public ModelRenderer connector1;
	public ModelRenderer connector2;
	public ModelRenderer connector3;
	
	public ModelRenderer innercross0;
	public ModelRenderer innercross1;
	public ModelRenderer innercross2;
	public ModelRenderer innercross3;
	
	public ModelRenderer connectorbase0;
	public ModelRenderer connectorbase1;
	public ModelRenderer connectorbase2;
	public ModelRenderer connectorbase3;
	
	public ModelRenderer connectedcornerright0;
	public ModelRenderer connectedcornerleft0;
	public ModelRenderer connectedcross0;
	
	public ModelRenderer connectedcornerleft1;
	public ModelRenderer connectedcross1;
	public ModelRenderer connectedcornerright1;
	
	public ModelRenderer connectedcross2;
	
	public ModelRenderer connectedcross3;
	
	public ModelBlockSolarPanel() {
		
		this.textureWidth = 128;
		this.textureHeight = 64;
		
		baselower = new ModelRenderer(this, 0, 0);
		baselower.addBox(-8F, 22F, -8F, 16, 2, 16, 0F);
		baselower.setRotationPoint(0F, 0F, 0F);
		baselower.rotateAngleX = 0F;
		baselower.rotateAngleY = 0F;
		baselower.rotateAngleZ = 0F;
		baselower.mirror = false;
		basemid = new ModelRenderer(this, 0, 20);
		basemid.addBox(-7F, 21F, -7F, 14, 1, 14, 0F);
		basemid.setRotationPoint(0F, 0F, 0F);
		basemid.rotateAngleX = 0F;
		basemid.rotateAngleY = 0F;
		basemid.rotateAngleZ = 0F;
		basemid.mirror = false;
		baseupper = new ModelRenderer(this, 0, 37);
		baseupper.addBox(-8F, 20F, -8F, 16, 1, 16, 0F);
		baseupper.setRotationPoint(0F, 0F, 0F);
		baseupper.rotateAngleX = 0F;
		baseupper.rotateAngleY = 0F;
		baseupper.rotateAngleZ = 0F;
		baseupper.mirror = false;
		ring0 = new ModelRenderer(this, 0, 56);
		ring0.addBox(-8F, 19F, -8F, 16, 1, 1, 0F);
		ring0.setRotationPoint(0F, 0F, 0F);
		ring0.rotateAngleX = 0F;
		ring0.rotateAngleY = 0F;
		ring0.rotateAngleZ = 0F;
		ring0.mirror = false;
		ring1 = new ModelRenderer(this, 31, 60);
		ring1.addBox(-8F, 19F, 7F, 16, 1, 1, 0F);
		ring1.setRotationPoint(0F, 0F, 0F);
		ring1.rotateAngleX = 0F;
		ring1.rotateAngleY = 0F;
		ring1.rotateAngleZ = 0F;
		ring1.mirror = false;
		ring2 = new ModelRenderer(this, 97, 0);
		ring2.addBox(-8F, 19F, -7F, 1, 1, 14, 0F);
		ring2.setRotationPoint(0F, 0F, 0F);
		ring2.rotateAngleX = 0F;
		ring2.rotateAngleY = 0F;
		ring2.rotateAngleZ = 0F;
		ring2.mirror = false;
		ring3 = new ModelRenderer(this, 66, 0);
		ring3.addBox(7F, 19F, -7F, 1, 1, 14, 0F);
		ring3.setRotationPoint(0F, 0F, 0F);
		ring3.rotateAngleX = 0F;
		ring3.rotateAngleY = 0F;
		ring3.rotateAngleZ = 0F;
		ring3.mirror = false;
		connector0 = new ModelRenderer(this, 66, 18);
		connector0.addBox(-2F, 14F, -8F, 4, 4, 1, 0F);
		connector0.setRotationPoint(0F, 0F, 0F);
		connector0.rotateAngleX = 0F;
		connector0.rotateAngleY = 0F;
		connector0.rotateAngleZ = 0F;
		connector0.mirror = false;
		connector1 = new ModelRenderer(this, 66, 18);
		connector1.addBox(-2F, 14F, 7F, 4, 4, 1, 0F);
		connector1.setRotationPoint(0F, 0F, 0F);
		connector1.rotateAngleX = 0F;
		connector1.rotateAngleY = 0F;
		connector1.rotateAngleZ = 0F;
		connector1.mirror = false;
		connector2 = new ModelRenderer(this, 66, 25);
		connector2.addBox(-8F, 14F, -2F, 1, 4, 4, 0F);
		connector2.setRotationPoint(0F, 0F, 0F);
		connector2.rotateAngleX = 0F;
		connector2.rotateAngleY = 0F;
		connector2.rotateAngleZ = 0F;
		connector2.mirror = false;
		connector3 = new ModelRenderer(this, 66, 25);
		connector3.addBox(7F, 14F, -2F, 1, 4, 4, 0F);
		connector3.setRotationPoint(0F, 0F, 0F);
		connector3.rotateAngleX = 0F;
		connector3.rotateAngleY = 0F;
		connector3.rotateAngleZ = 0F;
		connector3.mirror = false;
		innercross0 = new ModelRenderer(this, 0, 60);
		innercross0.addBox(-7F, 19.5F, 2F, 14, 1, 1, 0F);
		innercross0.setRotationPoint(0F, 0F, 0F);
		innercross0.rotateAngleX = 0F;
		innercross0.rotateAngleY = 0F;
		innercross0.rotateAngleZ = 0F;
		innercross0.mirror = false;
		innercross1 = new ModelRenderer(this, 0, 60);
		innercross1.addBox(-7F, 19.5F, -3F, 14, 1, 1, 0F);
		innercross1.setRotationPoint(0F, 0F, 0F);
		innercross1.rotateAngleX = 0F;
		innercross1.rotateAngleY = 0F;
		innercross1.rotateAngleZ = 0F;
		innercross1.mirror = false;
		innercross2 = new ModelRenderer(this, 66, 47);
		innercross2.addBox(-3F, 19.5F, -7F, 1, 1, 14, 0F);
		innercross2.setRotationPoint(0F, 0F, 0F);
		innercross2.rotateAngleX = 0F;
		innercross2.rotateAngleY = 0F;
		innercross2.rotateAngleZ = 0F;
		innercross2.mirror = false;
		innercross3 = new ModelRenderer(this, 66, 47);
		innercross3.addBox(2F, 19.5F, -7F, 1, 1, 14, 0F);
		innercross3.setRotationPoint(0F, 0F, 0F);
		innercross3.rotateAngleX = 0F;
		innercross3.rotateAngleY = 0F;
		innercross3.rotateAngleZ = 0F;
		innercross3.mirror = false;
		connectorbase0 = new ModelRenderer(this, 78, 18);
		connectorbase0.addBox(-3F, 18F, -8F, 6, 1, 1, 0F);
		connectorbase0.setRotationPoint(0F, 0F, 0F);
		connectorbase0.rotateAngleX = 0F;
		connectorbase0.rotateAngleY = 0F;
		connectorbase0.rotateAngleZ = 0F;
		connectorbase0.mirror = false;
		connectorbase1 = new ModelRenderer(this, 78, 22);
		connectorbase1.addBox(-3F, 18F, 7F, 6, 1, 1, 0F);
		connectorbase1.setRotationPoint(0F, 0F, 0F);
		connectorbase1.rotateAngleX = 0F;
		connectorbase1.rotateAngleY = 0F;
		connectorbase1.rotateAngleZ = 0F;
		connectorbase1.mirror = false;
		connectorbase2 = new ModelRenderer(this, 78, 26);
		connectorbase2.addBox(-8F, 18F, -3F, 1, 1, 6, 0F);
		connectorbase2.setRotationPoint(0F, 0F, 0F);
		connectorbase2.rotateAngleX = 0F;
		connectorbase2.rotateAngleY = 0F;
		connectorbase2.rotateAngleZ = 0F;
		connectorbase2.mirror = false;
		connectorbase3 = new ModelRenderer(this, 78, 35);
		connectorbase3.addBox(7F, 18F, -3F, 1, 1, 6, 0F);
		connectorbase3.setRotationPoint(0F, 0F, 0F);
		connectorbase3.rotateAngleX = 0F;
		connectorbase3.rotateAngleY = 0F;
		connectorbase3.rotateAngleZ = 0F;
		connectorbase3.mirror = false;
		
		connectedcornerright0 = new ModelRenderer(this, 71, 38);
		connectedcornerright0.addBox(-8F, 19F, -8F, 1, 1, 1, 0F);
		connectedcornerright0.setRotationPoint(0F, 0F, 0F);
		connectedcornerright0.rotateAngleX = 0F;
		connectedcornerright0.rotateAngleY = 0F;
		connectedcornerright0.rotateAngleZ = 0F;
		connectedcornerright0.mirror = false;
		connectedcornerleft0 = new ModelRenderer(this, 66, 38);
		connectedcornerleft0.addBox(7F, 19F, -8F, 1, 1, 1, 0F);
		connectedcornerleft0.setRotationPoint(0F, 0F, 0F);
		connectedcornerleft0.rotateAngleX = 0F;
		connectedcornerleft0.rotateAngleY = 0F;
		connectedcornerleft0.rotateAngleZ = 0F;
		connectedcornerleft0.mirror = false;
		connectedcross0 = new ModelRenderer(this, 0, 60);
		connectedcross0.addBox(-7F, 19.5F, -8F, 14, 1, 1, 0F);
		connectedcross0.setRotationPoint(0F, 0F, 0F);
		connectedcross0.rotateAngleX = 0F;
		connectedcross0.rotateAngleY = 0F;
		connectedcross0.rotateAngleZ = 0F;
		connectedcross0.mirror = false;
		connectedcornerleft1 = new ModelRenderer(this, 66, 35);
		connectedcornerleft1.addBox(7F, 19F, 7F, 1, 1, 1, 0F);
		connectedcornerleft1.setRotationPoint(0F, 0F, 0F);
		connectedcornerleft1.rotateAngleX = 0F;
		connectedcornerleft1.rotateAngleY = 0F;
		connectedcornerleft1.rotateAngleZ = 0F;
		connectedcornerleft1.mirror = false;
		connectedcornerright1 = new ModelRenderer(this, 71, 35);
		connectedcornerright1.addBox(-8F, 19F, 7F, 1, 1, 1, 0F);
		connectedcornerright1.setRotationPoint(0F, 0F, 0F);
		connectedcornerright1.rotateAngleX = 0F;
		connectedcornerright1.rotateAngleY = 0F;
		connectedcornerright1.rotateAngleZ = 0F;
		connectedcornerright1.mirror = false;
		connectedcross1 = new ModelRenderer(this, 0, 60);
		connectedcross1.addBox(-7F, 19.5F, 7F, 14, 1, 1, 0F);
		connectedcross1.setRotationPoint(0F, 0F, 0F);
		connectedcross1.rotateAngleX = 0F;
		connectedcross1.rotateAngleY = 0F;
		connectedcross1.rotateAngleZ = 0F;
		connectedcross1.mirror = false;
		connectedcross2 = new ModelRenderer(this, 66, 47);
		connectedcross2.addBox(-8F, 19.5F, -7F, 1, 1, 14, 0F);
		connectedcross2.setRotationPoint(0F, 0F, 0F);
		connectedcross2.rotateAngleX = 0F;
		connectedcross2.rotateAngleY = 0F;
		connectedcross2.rotateAngleZ = 0F;
		connectedcross2.mirror = false;
		connectedcross3 = new ModelRenderer(this, 66, 47);
		connectedcross3.addBox(7F, 19.5F, -7F, 1, 1, 14, 0F);
		connectedcross3.setRotationPoint(0F, 0F, 0F);
		connectedcross3.rotateAngleX = 0F;
		connectedcross3.rotateAngleY = 0F;
		connectedcross3.rotateAngleZ = 0F;
		connectedcross3.mirror = false;
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		baselower.render(f5);
		basemid.render(f5);
		baseupper.render(f5);
		ring0.render(f5);
		ring1.render(f5);
		ring2.render(f5);
		ring3.render(f5);
		connector0.render(f5);
		connector1.render(f5);
		connector2.render(f5);
		connector3.render(f5);
		innercross0.render(f5);
		innercross1.render(f5);
		innercross2.render(f5);
		innercross3.render(f5);
	}
	
	public void renderBase(float f5) {
		baselower.render(f5);
		basemid.render(f5);
		baseupper.render(f5);
		innercross0.render(f5);
		innercross1.render(f5);
		innercross2.render(f5);
		innercross3.render(f5);
	}
	
	public void renderConnector(float f5, EnumFacing facing) {
		if (facing.equals(EnumFacing.NORTH)) {
			connector0.render(f5);
			connectorbase0.render(f5);
		} else if (facing.equals(EnumFacing.SOUTH)) {
			connector1.render(f5);
			connectorbase1.render(f5);
		} else if (facing.equals(EnumFacing.EAST)) {
			connector2.render(f5);
			connectorbase2.render(f5);
		} else if (facing.equals(EnumFacing.WEST)) {
			connector3.render(f5);
			connectorbase3.render(f5);
		}
	}
	
	public void renderAllConnected(float f5) {
		
	}
	
	public void renderConnected(float f5, boolean connected, EnumFacing facing) {
		if (facing.equals(EnumFacing.NORTH)) {
			if (connected) {
				connectedcornerleft0.render(f5);
				connectedcornerright0.render(f5);
				connectedcross0.render(f5);	
			} else {
				ring0.render(f5);
			}
		}
		
		if (facing.equals(EnumFacing.SOUTH)) {
			if (connected) {
				connectedcornerleft1.render(f5);
				connectedcornerright1.render(f5);
				connectedcross1.render(f5);
			} else {
				ring1.render(f5);
			}
		}
		
		if (facing.equals(EnumFacing.EAST)) {
			if (connected) {
				connectedcross2.render(f5);
			} else {
				ring2.render(f5);
			}
		}
		
		if (facing.equals(EnumFacing.WEST)) {
			if (connected) {
				connectedcross3.render(f5);
			} else {
				ring3.render(f5);
			}
		}
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
	
}
