package com.modcrafting.lightningmobs;

import com.modcrafting.lightningmobs.configs.ServerConfigSpec;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import com.mojang.logging.LogUtils;

import net.minecraftforge.fml.InterModComms.IMCMessage;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

@Mod(LMobs.MODID)
public class LMobs
{
	
    protected static final Logger LOGGER = LogUtils.getLogger();
    protected static final String MODID = "lightningmobs";
    
	public static Logger getLogger() {
		return LOGGER;
	}
	
	public static String getModid() {
		return MODID;
	}
	
	public LMobs()
    {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loaded);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		ModLoadingContext.get().registerConfig(Type.SERVER, ServerConfigSpec.SPEC, MODID + "-server.toml");
		Registry.init();
    }
	
	private void processIMC(final InterModProcessEvent event) {
		Stream<IMCMessage> msgs = event.getIMCStream();
		msgs.forEach(LMobs::processMsg);
		UnstableLightning.upgradeables.add(Pair.of("minecraft:chicken", "minecraft:creeper"));
	}
	
	private void loaded(final FMLLoadCompleteEvent event) {
		LOGGER.info("Mod finished loading");
	}
	
	private static void processMsg(IMCMessage msg) {
		try {
			String str = (String) msg.messageSupplier().get();
			String[] separated = str.split("#");
			UnstableLightning.upgradeables.add(Pair.of(separated[0], separated[1]));
		} catch (ClassCastException e) {
			LOGGER.error(String.format("The sender %s attempted to send something that is not an string: ", msg.senderModId(), msg.messageSupplier().get().getClass().toString()));
		} catch (Exception e) {
			LOGGER.error("Something else happened: " + e.getMessage());
		}
	}
	
}
