package com.modcrafting.lightningmobs.blocks.weather_channeler;

import com.modcrafting.lightningmobs.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;

/**
 * @author AylanJ123
 *
 */
public class WeatherChannelerItem extends BlockItem {
	
	@Override
	public String getDescriptionId() {
		return "block.lightningmobs.weather_channeler";
	}
	
	private WeatherChannelerItem(Properties properties) {
		super(Registry.WEATHER_CHANNELER.get(), properties);
	}
	
	public static WeatherChannelerItem init() {
		Properties properties = new Properties();
		properties.rarity(Rarity.RARE).stacksTo(MAX_STACK_SIZE).tab(Registry.MOD_TAB);
		return new WeatherChannelerItem(properties);
	}
	
}