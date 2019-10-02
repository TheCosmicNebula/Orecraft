package com.zeher.orecraft.machine.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlockCharger extends ModelBase {
	

	//fields
	public ModelRenderer Base;
	public ModelRenderer BaseLower;
	public ModelRenderer BaseUpper;
	public ModelRenderer Frame0;
	public ModelRenderer Frame1;
	public ModelRenderer Frame2;
	public ModelRenderer Frame3;
	public ModelRenderer FrameInner0;
	public ModelRenderer FrameInner1;
	public ModelRenderer FrameInner2;
	public ModelRenderer FrameInner3;
	
	public ModelBlockCharger() {
		textureHeight = 128;
		textureWidth = 128;
		
		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-7F, 18F, -7F, 14, 4, 14, 0F);
		Base.setRotationPoint(0F, 0F, 0F);
		Base.setTextureSize(128, 128);
		Base.mirror = false;
		setRotation(Base, 0F, 0F, 0F);
		
		BaseLower = new ModelRenderer(this, 0, 20);
		BaseLower.addBox(-8F, 22F, -8F, 16, 2, 16, 0F);
		BaseLower.setRotationPoint(0F, 0F, 0F);
		BaseLower.setTextureSize(128, 128);
		BaseLower.mirror = false;
		setRotation(BaseLower, 0F, 0F, 0F);
		
		BaseUpper = new ModelRenderer(this, 0, 40);
		BaseUpper.addBox(-8F, 17F, -8F, 16, 1, 16, 0F);
		BaseUpper.setRotationPoint(0F, 0F, 0F);
		BaseUpper.setTextureSize(128, 128);
		BaseUpper.mirror = false;
		setRotation(BaseUpper, 0F, 0F, 0F);
		
		Frame0 = new ModelRenderer(this, 36, 59);
		Frame0.addBox(-8F, 16F, -8F, 16, 1, 1, 0F);
		Frame0.setRotationPoint(0F, 0F, 0F);
		Frame0.setTextureSize(128, 128);
		Frame0.mirror = false;
		setRotation(Frame0, 0F, 0F, 0F);
		
		Frame1 = new ModelRenderer(this, 0, 59);
		Frame1.addBox(-8F, 16F, 7F, 16, 1, 1, 0F);
		Frame1.setRotationPoint(0F, 0F, 0F);
		Frame1.setTextureSize(128, 128);
		Frame1.mirror = false;
		setRotation(Frame1, 0F, 0F, 0F);
		
		Frame2 = new ModelRenderer(this, 36, 63);
		Frame2.addBox(-8F, 16F, -7F, 1, 1, 14, 0F);
		Frame2.setRotationPoint(0F, 0F, 0F);
		Frame2.setTextureSize(128, 128);
		Frame2.mirror = false;
		setRotation(Frame2, 0F, 0F, 0F);
		
		Frame3 = new ModelRenderer(this, 68, 63);
		Frame3.addBox(7F, 16F, -7F, 1, 1, 14, 0F);
		Frame3.setRotationPoint(0F, 0F, 0F);
		Frame3.setTextureSize(128, 128);
		Frame3.mirror = false;
		setRotation(Frame3, 0F, 0F, 0F);
		
		FrameInner0 = new ModelRenderer(this, 0, 82);
		FrameInner0.addBox(-7F, 16.5F, 2F, 14, 1, 1, 0F);
		FrameInner0.setRotationPoint(0F, 0F, 0F);
		FrameInner0.setTextureSize(128, 128);
		FrameInner0.mirror = false;
		
		FrameInner1 = new ModelRenderer(this, 0, 82);
		FrameInner1.addBox(-7F, 16.5F, -3F, 14, 1, 1, 0F);
		FrameInner1.setRotationPoint(0F, 0F, 0F);
		FrameInner1.setTextureSize(128, 128);
		FrameInner1.mirror = false;
		setRotation(FrameInner1, 0F, 0F, 0F);
		
		FrameInner2 = new ModelRenderer(this, 0, 86);
		FrameInner2.addBox(2F, 16.5F, -7F, 1, 1, 14, 0F);
		FrameInner2.setRotationPoint(0F, 0F, 0F);
		FrameInner2.setTextureSize(128, 128);
		FrameInner2.mirror = false;
		setRotation(FrameInner2, 0F, 0F, 0F);
		
		FrameInner3 = new ModelRenderer(this, 0, 86);
		FrameInner3.addBox(-3F, 16.5F, -7F, 1, 1, 14, 0F);
		FrameInner3.setRotationPoint(0F, 0F, 0F);
		FrameInner3.setTextureSize(128, 128);
		FrameInner3.mirror = false;
		setRotation(FrameInner3, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Base.render(f5);
		BaseLower.render(f5);
		BaseUpper.render(f5);
		Frame0.render(f5);
		Frame1.render(f5);
		Frame2.render(f5);
		Frame3.render(f5);
		FrameInner0.render(f5);
		FrameInner1.render(f5);
		FrameInner2.render(f5);
		FrameInner3.render(f5);
	}
	
	public void renderBase(float f5) {
		Base.render(f5);
		BaseLower.render(f5);
		BaseUpper.render(f5);
		Frame0.render(f5);
		Frame1.render(f5);
		Frame2.render(f5);
		Frame3.render(f5);
		FrameInner0.render(f5);
		FrameInner1.render(f5);
		FrameInner2.render(f5);
		FrameInner3.render(f5);
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
