package com.modcrafting.lightningmobs;

import com.modcrafting.lightningmobs.configs.ServerConfigSpec;
import com.mojang.logging.LogUtils;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		ModLoadingContext.get().registerConfig(Type.SERVER, ServerConfigSpec.SPEC, MODID + "-server.toml");
		Registry.init();
    }
	
	private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("STARTING PREINIT");
    }
	
}
