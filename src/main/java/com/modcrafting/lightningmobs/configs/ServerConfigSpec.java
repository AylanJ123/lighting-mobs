package com.modcrafting.lightningmobs.configs;

import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ServerConfigSpec {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.DoubleValue SHARDS_CHANCE;
	public static final ForgeConfigSpec.DoubleValue SHARDS_CHANCE_AMOUNT;
	
	public static final ConfigValue<List<? extends String>> UPGRADEABLE_MOBS;
	
	static {
		BUILDER.push("Looting");
		SHARDS_CHANCE = BUILDER.comment("Chances for shards to drop. Is a percentage factor [0,1]. Looting adds an accumulative 25% of this value (25%, 75%, 150%)")
				.defineInRange("shards_chance", Double.valueOf(0.02), 0, 1);
		SHARDS_CHANCE_AMOUNT = BUILDER.comment("Chances for shards when drop to give extra loot. Is a percentage factor [0,1]. If chances are given, 50% or less of the looting level will be dropped extra, half this value for upper 50%. Looting also multiplies the chance.")
				.defineInRange("shards_chance_amount", Double.valueOf(0.1), 0, 1);
		BUILDER.pop();
		BUILDER.push("Upgrading");
		UPGRADEABLE_MOBS = BUILDER.comment("Mobs that will be upgraded upon being struck by a lightning. This is a list of Strings and follows the format \"namespace:upgradeable#namespace:upgraded\"")
				.defineList("upgradeable_mobs", List.of(
						"minecraft:cow#minecraft:mooshroom",
						"minecraft:squid#minecraft:glow_squid",
						"minecraft:mooshroom#lightningmobs:minotaur",
						"minecraft:chicken#lightningmobs:dinosaur",
						"minecraft:wolf#lightningmobs:cerberus",
						"minecraft:bee#lightningmobs:wasp",
						"minecraft:glow_squid#lightningmobs:cthulhu",
						"minecraft:dolphin#lightningmobs:killer_whale",
						"minecraft:turtle#lightningmobs:busco_beast"
					), x -> Boolean.TRUE
				);
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
	
}
