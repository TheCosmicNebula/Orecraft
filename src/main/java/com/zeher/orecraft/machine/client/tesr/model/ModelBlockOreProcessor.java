package com.zeher.orecraft.machine.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlockOreProcessor extends ModelBase {
	
		public ModelRenderer pillar2;
		public ModelRenderer pillar3;
		public ModelRenderer itemstand;
		public ModelRenderer base;
		public ModelRenderer panelrear;
		public ModelRenderer panelleft;
		public ModelRenderer panelright;
		public ModelRenderer panelfront;
		public ModelRenderer fluidtank;
		public ModelRenderer topglass;
		public ModelRenderer rightshaft;
		public ModelRenderer leftshaft;
		public ModelRenderer rightheadupper1;
		public ModelRenderer rightheadupper2;
		public ModelRenderer rightheadlower1;
		public ModelRenderer rightheadlower2;
		public ModelRenderer leftheadupper1;
		public ModelRenderer leftheadupper2;
		public ModelRenderer leftheadlower2;
		public ModelRenderer leftheadlower1;
		public ModelRenderer itemstandhot;
	
	public ModelBlockOreProcessor() {

		this.textureWidth = 128;
		this.textureHeight = 64;
		
		pillar2 = new ModelRenderer(this, 36, 0);
		pillar2.addBox(6F, 0F, 6F, 1, 10, 1, 0F);
		pillar2.setRotationPoint(0F, 9F, 0F);
		pillar2.setTextureSize(128, 64);
		pillar2.mirror = false;
		setRotation(pillar2, 0F, 0F,0F);
		
		pillar3 = new ModelRenderer(this, 36, 0);
		pillar3.addBox(-7F, 0F, 6F, 1, 10, 1, 0F);
		pillar3.setRotationPoint(0F, 9F, 0F);
		pillar3.setTextureSize(128, 64);
		pillar3.mirror = false;
		setRotation(pillar3, 0F, 0F, 0F);
		
		itemstand = new ModelRenderer(this, 63, 16);
		itemstand.addBox(-3F, 0F, 1F, 6, 1, 6, 0F);
		itemstand.setRotationPoint(0F, 17F, 0F);
		itemstand.setTextureSize(128, 64);
		itemstand.mirror = false;
		setRotation(itemstand, 0F, 0F, 0F);
		
		base = new ModelRenderer(this, 0, 23);
		base.addBox(-7F, 0F, -7F, 14, 6, 15, 0F);
		base.setRotationPoint(0F, 18F, 0F);
		base.setTextureSize(128, 64);
		base.mirror = false;
		setRotation(base, 0F, 0F, 0F);
		
		panelrear = new ModelRenderer(this, 0, 0);
		panelrear.addBox(-8F, 0F, -8F, 16, 16, 1, 0F);
		panelrear.setRotationPoint(0F, 8F, 0F);
		panelrear.setTextureSize(128, 64);
		panelrear.mirror = false;
		setRotation(panelrear, 0F, 0F, 0F);
		
		panelleft = new ModelRenderer(this, 60, 33);
		panelleft.addBox(7F, 8F, -7F, 1, 16, 15, 0F);
		panelleft.setRotationPoint(0F, 0F, 0F);
		panelleft.setTextureSize(128, 64);
		panelleft.mirror = false;
		setRotation(panelleft, 0F, 0F, 0F);
		
		panelright = new ModelRenderer(this, 94, 33);
		panelright.addBox(-8F, 8F, -7F, 1, 16, 15, 0F);
		panelright.setRotationPoint(0F, 0F, 0F);
		panelright.setTextureSize(128, 64);
		panelright.mirror = false;
		setRotation(panelright, 0F, 0F, 0F);
		
		panelfront = new ModelRenderer(this, 66, 0);
		panelfront.addBox(-7F, 8F, 7F, 14, 2, 1, 0F);
		panelfront.setRotationPoint(0F, 0F, 0F);
		panelfront.setTextureSize(128, 64);
		panelfront.mirror = false;
		setRotation(panelfront, 0F, 0F, 0F);
		
		fluidtank = new ModelRenderer(this, 88, 17);
		fluidtank.addBox(-7F, 9F, -7F, 14, 9, 6, 0F);
		fluidtank.setRotationPoint(0F, 0F, 0F);
		fluidtank.setTextureSize(128, 64);
		fluidtank.mirror = false;
		setRotation(fluidtank, 0F, 0F, 0F);
		
		topglass = new ModelRenderer(this, 0, 46);
		topglass.addBox(-7F, 0F, -7F, 14, 1, 14, 0F);
		topglass.setRotationPoint(0F, 8F, 0F);
		topglass.setTextureSize(128, 64);
		topglass.mirror = false;
		setRotation(topglass, 0F, 0F, 0F);
		
		rightshaft = new ModelRenderer(this, 42, 0);
		rightshaft.addBox(-5F, 13F, 3F, 1, 5, 1, 0F);
		rightshaft.setRotationPoint(0F, 0F, 0F);
		rightshaft.setTextureSize(128, 64);
		rightshaft.mirror = false;
		setRotation(rightshaft, 0F, 0F, 0F);
		
		leftshaft = new ModelRenderer(this, 42, 0);
		leftshaft.addBox(4F, 13F, 3F, 1, 5, 1, 0F);
		leftshaft.setRotationPoint(0F, 0F, 0F);
		leftshaft.setTextureSize(128, 64);
		leftshaft.mirror = false;
		setRotation(leftshaft, 0F, 0F, 0F);
		
		rightheadupper1 = new ModelRenderer(this, 48, 0);
		rightheadupper1.addBox(-2F, 0F, -1F, 4, 1, 2, 0F);
		rightheadupper1.setRotationPoint(0F, 0F, 0F);
		rightheadupper1.setTextureSize(128, 64);
		rightheadupper1.mirror = false;
		setRotation(rightheadupper1, 0F, 0F, 0F);
		
		rightheadupper2 = new ModelRenderer(this, 48, 5);
		rightheadupper2.addBox(-1F, 0F, -2F, 2, 1, 4, 0F);
		rightheadupper2.setRotationPoint(0F, 0F, 0F);
		rightheadupper2.setTextureSize(128, 64);
		rightheadupper2.mirror = false;
		setRotation(rightheadupper2, 0F, 0F, 0F);
		
		rightheadlower1 = new ModelRenderer(this, 48, 0);
		rightheadlower1.addBox(-2F, -2F, -1F, 4, 1, 2, 0F);
		rightheadlower1.setRotationPoint(0, 0F, 0F);
		rightheadlower1.setTextureSize(128, 64);
		rightheadlower1.mirror = false;
		setRotation(rightheadlower1, 0F, 0F, 0F);
		
		rightheadlower2 = new ModelRenderer(this, 48, 5);
		rightheadlower2.addBox(-1F, -2F, -2F, 2, 1, 4, 0F);
		rightheadlower2.setRotationPoint(0F, 0F, 0F);
		rightheadlower2.setTextureSize(128, 64);
		rightheadlower2.mirror = false;
		setRotation(rightheadlower2, 0F, 0F, 0F);
		
		leftheadupper1 = new ModelRenderer(this, 48, 0);
		leftheadupper1.addBox(-2F, 0F, -1F, 4, 1, 2, 0F);
		leftheadupper1.setRotationPoint(0F, 0F, 0F);
		leftheadupper1.setTextureSize(128, 64);
		leftheadupper1.mirror = false;
		setRotation(leftheadupper1, 0F, 0F, 0F);
		
		leftheadupper2 = new ModelRenderer(this, 48, 5);
		leftheadupper2.addBox(-1F, 0F, -2F, 2, 1, 4, 0F);
		leftheadupper2.setRotationPoint(0F, 0F, 0F);
		leftheadupper2.setTextureSize(128, 64);
		leftheadupper2.mirror = false;
		setRotation(leftheadupper2, 0F, 0F, 0F);
		
		leftheadlower2 = new ModelRenderer(this, 48, 5);
		leftheadlower2.addBox(-1F, -2F, -2F, 2, 1, 4, 0F);
		leftheadlower2.setRotationPoint(0F, 0F, 0F);
		leftheadlower2.setTextureSize(128, 64);
		leftheadlower2.mirror = false;
		setRotation(leftheadlower2, 0F, 0F, 0F);
		
		leftheadlower1 = new ModelRenderer(this, 48, 0);
		leftheadlower1.addBox(-2F, -2F, -1F, 4, 1, 2, 0F);
		leftheadlower1.setRotationPoint(0F, 0F, 0F);
		leftheadlower1.setTextureSize(128, 64);
		leftheadlower1.mirror = false;
		setRotation(leftheadlower1, 0F, 0F, 0F);
		
		itemstandhot = new ModelRenderer(this, 63, 25);
		itemstandhot.addBox(-3F, 17F, 1F, 6, 1, 6, 0F);
		itemstandhot.setRotationPoint(0F, 0F, 0F);
		itemstandhot.setTextureSize(128, 64);
		itemstandhot.mirror = false;
		setRotation(itemstandhot, 0F, 0F, 0F);
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
		topglass.render(f5);
		rightshaft.render(f5);
		leftshaft.render(f5);
		rightheadupper1.render(f5);
		rightheadupper2.render(f5);
		rightheadlower1.render(f5);
		rightheadlower2.render(f5);
		leftheadupper1.render(f5);
		leftheadupper2.render(f5);
		leftheadlower2.render(f5);
		leftheadlower1.render(f5);
		itemstandhot.render(f5);
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
		rightshaft.render(f5);
		leftshaft.render(f5);
	}
	
	public void renderItemStand(float f5, boolean hot) {
		if(hot) {
			itemstandhot.render(f5);
		} else {
			itemstand.renderWithRotation(f5);
		}
	}
	
	public void renderLeftBrush(float f5) {
		leftheadupper1.render(f5);
		leftheadupper2.render(f5);
		leftheadlower2.render(f5);
		leftheadlower1.render(f5);
	}
	
	public void renderRightBrush(float f5) {
		rightheadupper1.render(f5);
		rightheadupper2.render(f5);
		rightheadlower1.render(f5);
		rightheadlower2.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
	
}
