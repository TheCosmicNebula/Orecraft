package com.zeher.orecraft.machine.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlockFluidCrafter extends ModelBase {
	
	public ModelRenderer pillar2;
	public ModelRenderer pillar3;
	public ModelRenderer itemstand;
	public ModelRenderer base;
	public ModelRenderer panelrear;
	public ModelRenderer panelleft;
	public ModelRenderer panelright;
	public ModelRenderer panelfront;
	public ModelRenderer fluidtank;
	public ModelRenderer itemstandhot;
	public ModelRenderer topglass;
	public ModelRenderer fluidbar;
	public ModelRenderer fluidsupplyright1;
	public ModelRenderer fluidsupplyright2;
	public ModelRenderer fluidsupplleft1;
	public ModelRenderer fluidsupplyleft2;
	
	public ModelBlockFluidCrafter() {
		
		this.textureHeight = 64;
		this.textureWidth = 128;
	
		pillar2 = new ModelRenderer(this, 36, 0);
		pillar2.addBox(6F, 0F, 6F, 1, 10, 1, 0F);
		pillar2.setRotationPoint(0F, 9F, 0F);
		pillar2.rotateAngleX = 0F;
		pillar2.rotateAngleY = 0F;
		pillar2.rotateAngleZ = 0F;
		pillar2.mirror = false;
		pillar3 = new ModelRenderer(this, 36, 0);
		pillar3.addBox(-7F, 0F, 6F, 1, 10, 1, 0F);
		pillar3.setRotationPoint(0F, 9F, 0F);
		pillar3.rotateAngleX = 0F;
		pillar3.rotateAngleY = 0F;
		pillar3.rotateAngleZ = 0F;
		pillar3.mirror = false;
		itemstand = new ModelRenderer(this, 63, 16);
		itemstand.addBox(-3F, 0F, 1F, 6, 1, 6, 0F);
		itemstand.setRotationPoint(0F, 17F, 0F);
		itemstand.rotateAngleX = 0F;
		itemstand.rotateAngleY = 0F;
		itemstand.rotateAngleZ = 0F;
		itemstand.mirror = false;
		base = new ModelRenderer(this, 0, 23);
		base.addBox(-7F, 0F, -7F, 14, 6, 15, 0F);
		base.setRotationPoint(0F, 18F, 0F);
		base.rotateAngleX = 0F;
		base.rotateAngleY = 0F;
		base.rotateAngleZ = 0F;
		base.mirror = false;
		panelrear = new ModelRenderer(this, 0, 0);
		panelrear.addBox(-8F, 0F, -8F, 16, 16, 1, 0F);
		panelrear.setRotationPoint(0F, 8F, 0F);
		panelrear.rotateAngleX = 0F;
		panelrear.rotateAngleY = 0F;
		panelrear.rotateAngleZ = 0F;
		panelrear.mirror = false;
		panelleft = new ModelRenderer(this, 60, 33);
		panelleft.addBox(7F, 8F, -7F, 1, 16, 15, 0F);
		panelleft.setRotationPoint(0F, 0F, 0F);
		panelleft.rotateAngleX = 0F;
		panelleft.rotateAngleY = 0F;
		panelleft.rotateAngleZ = 0F;
		panelleft.mirror = false;
		panelright = new ModelRenderer(this, 94, 33);
		panelright.addBox(-8F, 8F, -7F, 1, 16, 15, 0F);
		panelright.setRotationPoint(0F, 0F, 0F);
		panelright.rotateAngleX = 0F;
		panelright.rotateAngleY = 0F;
		panelright.rotateAngleZ = 0F;
		panelright.mirror = false;
		panelfront = new ModelRenderer(this, 66, 0);
		panelfront.addBox(-7F, 8F, 7F, 14, 2, 1, 0F);
		panelfront.setRotationPoint(0F, 0F, 0F);
		panelfront.rotateAngleX = 0F;
		panelfront.rotateAngleY = 0F;
		panelfront.rotateAngleZ = 0F;
		panelfront.mirror = false;
		fluidtank = new ModelRenderer(this, 88, 17);
		fluidtank.addBox(-7F, 9F, -7F, 14, 9, 6, 0F);
		fluidtank.setRotationPoint(0F, 0F, 0F);
		fluidtank.rotateAngleX = 0F;
		fluidtank.rotateAngleY = 0F;
		fluidtank.rotateAngleZ = 0F;
		fluidtank.mirror = false;
		itemstandhot = new ModelRenderer(this, 63, 25);
		itemstandhot.addBox(-3F, 0F, 1F, 6, 1, 6, 0F);
		itemstandhot.setRotationPoint(0F, 17F, 0F);
		itemstandhot.rotateAngleX = 0F;
		itemstandhot.rotateAngleY = 0F;
		itemstandhot.rotateAngleZ = 0F;
		itemstandhot.mirror = false;
		topglass = new ModelRenderer(this, 0, 46);
		topglass.addBox(-7F, 8F, -7F, 14, 1, 14, 0F);
		topglass.setRotationPoint(0F, 0F, 0F);
		topglass.rotateAngleX = 0F;
		topglass.rotateAngleY = 0F;
		topglass.rotateAngleZ = 0F;
		topglass.mirror = false;
		fluidbar = new ModelRenderer(this, 42, 4);
		fluidbar.addBox(-7F, 11F, 3F, 14, 1, 1, 0F);
		fluidbar.setRotationPoint(0F, 0F, 0F);
		fluidbar.rotateAngleX = 0F;
		fluidbar.rotateAngleY = 0F;
		fluidbar.rotateAngleZ = 0F;
		fluidbar.mirror = false;
		fluidsupplyright1 = new ModelRenderer(this, 43, 8);
		fluidsupplyright1.addBox(-7F, 17F, -1F, 1, 1, 5, 0F);
		fluidsupplyright1.setRotationPoint(0F, 0F, 0F);
		fluidsupplyright1.rotateAngleX = 0F;
		fluidsupplyright1.rotateAngleY = 0F;
		fluidsupplyright1.rotateAngleZ = 0F;
		fluidsupplyright1.mirror = false;
		fluidsupplyright2 = new ModelRenderer(this, 57, 8);
		fluidsupplyright2.addBox(-7F, 12F, 3F, 1, 5, 1, 0F);
		fluidsupplyright2.setRotationPoint(0F, 0F, 0F);
		fluidsupplyright2.rotateAngleX = 0F;
		fluidsupplyright2.rotateAngleY = 0F;
		fluidsupplyright2.rotateAngleZ = 0F;
		fluidsupplyright2.mirror = false;
		fluidsupplleft1 = new ModelRenderer(this, 43, 8);
		fluidsupplleft1.addBox(6F, 17F, -1F, 1, 1, 5, 0F);
		fluidsupplleft1.setRotationPoint(0F, 0F, 0F);
		fluidsupplleft1.rotateAngleX = 0F;
		fluidsupplleft1.rotateAngleY = 0F;
		fluidsupplleft1.rotateAngleZ = 0F;
		fluidsupplleft1.mirror = false;
		fluidsupplyleft2 = new ModelRenderer(this, 57, 8);
		fluidsupplyleft2.addBox(6F, 12F, 3F, 1, 5, 1, 0F);
		fluidsupplyleft2.setRotationPoint(0F, 0F, 0F);
		fluidsupplyleft2.rotateAngleX = 0F;
		fluidsupplyleft2.rotateAngleY = 0F;
		fluidsupplyleft2.rotateAngleZ = 0F;
		fluidsupplyleft2.mirror = false;
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		pillar2.render(f5);
		pillar3.render(f5);
		itemstand.render(f5);
		base.render(f5);
		panelrear.render(f5);
		panelleft.render(f5);
		panelright.render(f5);
		panelfront.render(f5);
		fluidtank.render(f5);
		itemstandhot.render(f5);
		topglass.render(f5);
		fluidbar.render(f5);
		fluidsupplyright1.render(f5);
		fluidsupplyright2.render(f5);
		fluidsupplleft1.render(f5);
		fluidsupplyleft2.render(f5);
	}
	
	public void renderBase(float f5) {
		pillar2.render(f5);
		pillar3.render(f5);
		base.render(f5);
		panelrear.render(f5);
		panelleft.render(f5);
		panelright.render(f5);
		panelfront.render(f5);
		fluidtank.render(f5);
		topglass.render(f5);
		fluidbar.render(f5);
		fluidsupplyright1.render(f5);
		fluidsupplyright2.render(f5);
		fluidsupplleft1.render(f5);
		fluidsupplyleft2.render(f5);
	}
	
	public void renderItemStand(float f5, boolean hot) {
		if(hot) {
			itemstandhot.render(f5);
		} else {
			itemstand.renderWithRotation(f5);
		}
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
