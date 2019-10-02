package com.zeher.orecraft.machine.client.tesr.model.internal;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlockCombinerCenterInternals extends ModelBase {
	
	ModelRenderer top_box;
	ModelRenderer middle_box;
	ModelRenderer side_bar0;
	ModelRenderer side_bar1;
	ModelRenderer side_bar2;
	ModelRenderer side_bar3;
	ModelRenderer top_pixel0;
	ModelRenderer top_pixel1;
	ModelRenderer top_pixel2;
	ModelRenderer top_pixel3;

	public ModelBlockCombinerCenterInternals() {
		textureWidth = 64;
		textureHeight = 64;

		//@Top Box
		top_box = new ModelRenderer(this, 0, 22);
		top_box.addBox(-7F, 15F, -7F, 14, 4, 14);
		top_box.setRotationPoint(0F, 0F, 0F);
		top_box.setTextureSize(256, 128);
		top_box.mirror = true;
		setRotation(top_box, 0F, 0F, 0F);
		
		middle_box = new ModelRenderer(this, 0, 0);
		middle_box.addBox(-8F, 19F, -8F, 16, 4, 16);
		middle_box.setRotationPoint(0F, 0F, 0F);
		middle_box.setTextureSize(256, 128);
		middle_box.mirror = true;
		setRotation(middle_box, 0F, 0F, 0F);
		
		side_bar0 = new ModelRenderer(this, 0, 41);
		side_bar0.addBox(-1F, -8F, -1F, 1, 9, 1);
		side_bar0.setRotationPoint(-8F, 23.2F, -8F);
		side_bar0.setTextureSize(256, 128);
		side_bar0.mirror = true;
		setRotation(side_bar0, -0.2268928F, 0.020944F, 0.2268928F);
		
		side_bar1 = new ModelRenderer(this, 0, 41);
		side_bar1.addBox(0F, -8F, -1F, 1, 9, 1);
		side_bar1.setRotationPoint(8F, 23.2F, -8F);
		side_bar1.setTextureSize(256, 128);
		side_bar1.mirror = true;
		setRotation(side_bar1, -0.2268928F, -0.020944F, -0.2268928F);
		
		side_bar2 = new ModelRenderer(this, 0, 41);
		side_bar2.addBox(-1F, -8F, 0F, 1, 9, 1);
		side_bar2.setRotationPoint(-8F, 23.2F, 8F);
		side_bar2.setTextureSize(256, 128);
		side_bar2.mirror = true;
		setRotation(side_bar2, 0.2268928F, -0.020944F, 0.2268928F);
		
		side_bar3 = new ModelRenderer(this, 0, 41);
		side_bar3.addBox(0F, -8F, 0F, 1, 9, 1);
		side_bar3.setRotationPoint(8F, 23.2F, 8F);
		side_bar3.setTextureSize(256, 128);
		side_bar3.mirror = true;
		setRotation(side_bar3, 0.2268928F, -0.020944F, -0.2268928F);

		
		top_pixel0 = new ModelRenderer(this, 0, 41);
		top_pixel0.addBox(0F, 0F, 0F, 1, 1, 1);
		top_pixel0.setRotationPoint(6F, 14F, -7F);
		top_pixel0.setTextureSize(256, 128);
		top_pixel0.mirror = true;
		setRotation(top_pixel0, 0F, 0F, 0F);
		top_pixel1 = new ModelRenderer(this, 0, 41);
		top_pixel1.addBox(0F, 0F, 0F, 1, 1, 1);
		top_pixel1.setRotationPoint(-7F, 14F, 6F);
		top_pixel1.setTextureSize(256, 128);
		top_pixel1.mirror = true;
		setRotation(top_pixel1, 0F, 0F, 0F);
		top_pixel2 = new ModelRenderer(this, 0, 41);
		top_pixel2.addBox(0F, 0F, 0F, 1, 1, 1);
		top_pixel2.setRotationPoint(6F, 14F, 6F);
		top_pixel2.setTextureSize(256, 128);
		top_pixel2.mirror = true;
		setRotation(top_pixel2, 0F, 0F, 0F);
		top_pixel3 = new ModelRenderer(this, 0, 41);
		top_pixel3.addBox(0F, 0F, 0F, 1, 1, 1);
		top_pixel3.setRotationPoint(-7F, 14F, -7F);
		top_pixel3.setTextureSize(256, 128);
		top_pixel3.mirror = true;
		setRotation(top_pixel3, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		top_box.render(f5);
		middle_box.render(f5);
		
		side_bar0.render(f5);
		side_bar1.render(f5);
		side_bar2.render(f5);
		side_bar3.render(f5);
		
		top_pixel0.render(f5);
		top_pixel1.render(f5);
		top_pixel2.render(f5);
		top_pixel3.render(f5);
	}
	
	public void renderBase(float f5) {
		side_bar0.render(f5);
		side_bar1.render(f5);
		side_bar2.render(f5);
		side_bar3.render(f5);
		
		top_pixel0.render(f5);
		top_pixel1.render(f5);
		top_pixel2.render(f5);
		top_pixel3.render(f5);
	}
	
	public void renderBottomCenter(float f5) {
		middle_box.render(f5);
	}
	
	public void renderTopCenter(float f5) {
		top_box.render(f5);
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
