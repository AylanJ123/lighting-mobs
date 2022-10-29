package com.modcrafting.lightningmobs.items.lightningsword;

import com.modcrafting.lightningmobs.ModTiers;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;


public class LightningSword extends SwordItem {

	public LightningSword(Properties properties) {
		super(ModTiers.LIGHTNING, 2, 9, properties);
	}

	public static LightningSword init() {
		LightningSword item = new LightningSword(
				new Properties().rarity(Rarity.RARE).tab(CreativeModeTab.TAB_COMBAT));
		return item;
	}
}
