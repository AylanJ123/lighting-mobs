package com.modcrafting.lightningmobs.items.lightningcharge;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

/**
 * @author PumpkinNightmare
 */
public class LightningCharge extends Item {

	public LightningCharge(Properties properties) {
		super(properties);
	}
	
	public static LightningCharge init() {
		LightningCharge item = new LightningCharge(new Properties()
			.rarity(Rarity.RARE)
			.stacksTo(MAX_STACK_SIZE)
			.tab(CreativeModeTab.TAB_MISC)
		);
		return item;
	}
	
}
