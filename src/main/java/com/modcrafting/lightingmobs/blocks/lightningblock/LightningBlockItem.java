/**
 * 
 */
package com.modcrafting.lightingmobs.blocks.lightningblock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;

/**
 * @author AylanJ123
 *
 */
public class LightningBlockItem extends BlockItem {
	
	private static LightningBlockItem item;
	
	public static LightningBlockItem getItem() {
		return item;
	}
	
	private LightningBlockItem(Properties properties) {
		super(LightningBlock.init(), properties);
	}
	
	public static LightningBlockItem init() {
		Properties properties = new Properties();
		properties.rarity(Rarity.RARE).stacksTo(MAX_STACK_SIZE).tab(CreativeModeTab.TAB_DECORATIONS);
		item = new LightningBlockItem(properties);
		item.setRegistryName("lightning_block_item");
		return item;
	}
	
}
