package com.modcrafting.lightningmobs.items.lightningshard;

import java.util.List;

import com.modcrafting.lightningmobs.LMobs;
import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.configs.ServerConfigSpec;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

public class LightningShardLootModifier extends LootModifier {
	
	public LootItemCondition[] getConditions() {
		return conditions;
	}
	
	protected LightningShardLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}
	
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		if (canDrop(context.getRandom().nextFloat(), context.getLootingModifier())) {
			generatedLoot.add(
				new ItemStack(Registry.LIGHTNING_SHARD.get(),
					getAmount(
						context.getRandom().nextFloat(),
						context.getRandom().nextFloat(),
						context.getLootingModifier()
					)
				)
			);
		}
		return generatedLoot;
	}
	
	private boolean canDrop(float random, int looting) {
		float configAmount = ServerConfigSpec.SHARDS_CHANCE.get().floatValue();
		float chances = 0;
		for(int i = 1; i <= looting; i++) {
			chances += i * 0.25;
		}
		return random <= configAmount + configAmount * chances;
	}
	
	private int getAmount(float random, float randomAmount, int looting) {
		float configAmount = ServerConfigSpec.SHARDS_CHANCE_AMOUNT.get().floatValue();
		float chances = configAmount * looting;
		int halfLoot = (int) Math.ceil(looting / 2d);
		if (random <= chances) {
			return (int) (1 + (random <= chances / 2 ? halfLoot : 1) + Math.floor(randomAmount * halfLoot));
		} else return 1;
	}
	
}
