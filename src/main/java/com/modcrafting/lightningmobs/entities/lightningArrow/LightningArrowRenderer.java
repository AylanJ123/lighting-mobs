package com.modcrafting.lightningmobs.entities.lightningArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;

public class LightningArrowRenderer extends ArrowRenderer<LightningArrow> {
	
	public LightningArrowRenderer(Context p_173917_) {
		super(p_173917_);
	}

	@Override
	public ResourceLocation getTextureLocation(LightningArrow pEntity) {
		return TippableArrowRenderer.NORMAL_ARROW_LOCATION;
	}

}
