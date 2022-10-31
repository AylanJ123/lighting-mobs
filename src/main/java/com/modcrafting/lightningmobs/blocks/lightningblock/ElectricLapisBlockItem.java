package com.modcrafting.lightningmobs.blocks.lightningblock;

import com.modcrafting.lightningmobs.Registry;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;

public class ElectricLapisBlockItem extends BlockItem{

	@Override
	public String getDescriptionId() {
		return "block.lightningmobs.lightning_block";
	}
	
	private ElectricLapisBlockItem(Properties properties) {
		super(Registry.ELECTRIC_LAPIS_BLOCK.get(), properties);
	}
	
	public static ElectricLapisBlockItem init() {
		Properties properties = new Properties();
		properties.rarity(Rarity.RARE).stacksTo(MAX_STACK_SIZE).tab(Registry.MOD_TAB);
		return new ElectricLapisBlockItem(properties);
	}
	
}
