package com.modcrafting.lightingmobs;

import com.modcrafting.lightingmobs.blocks.lightningblock.LightningBlock;
import com.modcrafting.lightingmobs.blocks.lightningblock.LightningBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {
	
	@SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
    {
        LMobs.LOGGER.info("Registering Blocks");
        blockRegistryEvent.getRegistry().register(LightningBlock.init());
    }
	
	@SubscribeEvent
    public static void onBlockItemRegistry(final RegistryEvent.Register<Item> blockRegistryEvent)
    {
        LMobs.LOGGER.info("Registering Block Items");
        blockRegistryEvent.getRegistry().register(LightningBlockItem.init());
    }
	
}
