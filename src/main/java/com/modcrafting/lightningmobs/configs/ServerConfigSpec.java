package com.modcrafting.lightningmobs.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfigSpec {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.DoubleValue SHARDS_CHANCE;
	public static final ForgeConfigSpec.DoubleValue SHARDS_CHANCE_AMOUNT;
	
	static {
		BUILDER.push("Looting");
		SHARDS_CHANCE = BUILDER.comment("Chances for shards to drop. Is a percentage factor [0,1]. Looting adds an accumulative 25% of this value (25%, 75%, 150%)")
				.defineInRange("shards_chance", Double.valueOf(0.02), 0, 1);
		SHARDS_CHANCE_AMOUNT = BUILDER.comment("Chances for shards when drop to give extra loot. Is a percentage factor [0,1]. If chances are given, 50% or less of the looting level will be dropped extra, half this value for upper 50%. Looting also multiplies the chance.")
				.defineInRange("shards_chance_amount", Double.valueOf(0.1), 0, 1);
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
	
}
