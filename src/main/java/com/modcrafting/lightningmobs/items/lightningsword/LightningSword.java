package com.modcrafting.lightningmobs.items.lightningsword;

import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.helpers.ModTiers;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;

public class LightningSword extends SwordItem {
	
	@Override
	public String getDescriptionId() {
		return "item.lightningmobs.lightning_sword";
	}
	
	public LightningSword(Properties properties) {
		super(ModTiers.LIGHTNING, 3, -2f, properties);
	}

	public static LightningSword init() {
		LightningSword item = new LightningSword(
				new Properties().rarity(Rarity.RARE).tab(Registry.MOD_TAB));
		return item;
	}
}
