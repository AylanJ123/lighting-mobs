package com.modcrafting.lightningmobs;

import com.modcrafting.lightningmobs.entities.lightningArrow.LightningArrowRenderer;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightningRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ClientRegistry {
	
	@SubscribeEvent
	public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(
			Registry.UNSTABLE_LIGHTNING.get(),
			UnstableLightningRenderer::new
		);
		event.registerEntityRenderer(
			Registry.SHOT_LIGHTNING_ARROW.get(),
			LightningArrowRenderer::new
		);
	}
}
