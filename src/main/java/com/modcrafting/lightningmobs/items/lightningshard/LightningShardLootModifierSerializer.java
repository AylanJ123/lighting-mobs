package com.modcrafting.lightningmobs.items.lightningshard;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;

public class LightningShardLootModifierSerializer extends GlobalLootModifierSerializer<LightningShardLootModifier> {
	
	@Override
	public LightningShardLootModifier read(ResourceLocation location, JsonObject object,
			LootItemCondition[] ailootcondition) {
		return new LightningShardLootModifier(ailootcondition);
	}

	@Override
	public JsonObject write(LightningShardLootModifier instance) {
		JsonObject json = makeConditions(instance.getConditions());
		return json;
	}
	
}
