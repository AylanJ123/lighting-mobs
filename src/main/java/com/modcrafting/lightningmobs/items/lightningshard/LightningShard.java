package com.modcrafting.lightningmobs.items.lightningshard;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

/**
 * @author PumpkinNightmare
 *
 */
public class LightningShard extends Item {

	public LightningShard(Properties properties) {
		super(properties);
	}
	
	public static LightningShard init() {
		LightningShard item = new LightningShard(new Properties()
			.rarity(Rarity.RARE)
			.stacksTo(MAX_STACK_SIZE)
			.tab(CreativeModeTab.TAB_MISC)
		);
		return item;
	}
	
}
