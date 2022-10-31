package com.modcrafting.lightningmobs.blocks.electric_lapis_block;

import com.modcrafting.lightningmobs.Registry;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.BlockItem;

public class ElectricLapisBlockItem extends BlockItem{
	
	@Override
	public String getDescriptionId() {
		return "block.lightningmobs.electric_lapis_block";
	}
	
	private ElectricLapisBlockItem(Properties properties) {
		super(Registry.ELECTRIC_LAPIS_BLOCK.get(), properties);
	}
	
	public static ElectricLapisBlockItem init() {
		Properties properties = new Properties();
		properties.rarity(Rarity.COMMON).stacksTo(MAX_STACK_SIZE).tab(Registry.MOD_TAB);
		return new ElectricLapisBlockItem(properties);
	}
	
}
