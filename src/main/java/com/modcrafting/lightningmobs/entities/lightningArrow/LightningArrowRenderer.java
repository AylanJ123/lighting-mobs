package com.modcrafting.lightningmobs.entities.lightningArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightningArrowRenderer extends ArrowRenderer<LightningArrow> {
	
	public static final ResourceLocation ARROW_LOCATION = new ResourceLocation("lightningmobs:textures/entity/projectiles/lightning_arrow.png");
	
	public LightningArrowRenderer(Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(LightningArrow pEntity) {
		return ARROW_LOCATION;
	}

}
