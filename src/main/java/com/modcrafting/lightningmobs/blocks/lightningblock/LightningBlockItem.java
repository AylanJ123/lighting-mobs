/**
 * 
 */
package com.modcrafting.lightningmobs.blocks.lightningblock;

import com.modcrafting.lightningmobs.Registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;

/**
 * @author AylanJ123
 *
 */
public class LightningBlockItem extends BlockItem {
	
	private LightningBlockItem(Properties properties) {
		super(Registry.LIGHTNING_BLOCK.get(), properties);
	}
	
	public static LightningBlockItem init() {
		Properties properties = new Properties();
		properties.rarity(Rarity.RARE).stacksTo(MAX_STACK_SIZE).tab(CreativeModeTab.TAB_DECORATIONS);
		return new LightningBlockItem(properties);
	}
	
}
