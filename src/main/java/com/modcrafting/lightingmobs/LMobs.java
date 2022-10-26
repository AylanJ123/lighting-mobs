package com.modcrafting.lightingmobs;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
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
		Registry.init();
    }
	
}

/*
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    MinecraftForge.EVENT_BUS.register(this);
    
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("STARTING PREINIT");
    }
*/
