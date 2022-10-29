package com.modcrafting.lightningmobs;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModTiers implements Tier {
	LIGHTING(3, 200, 8.5f, 3.5f, 15, Registry.LIGHTNING_SHARD.get());

	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final Item repairItem;
	private Ingredient ingredient;

	private ModTiers(int level, int uses, float speed, float damage, int enchantmentValue, Item item) {
		this.level = level;
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairItem = item;
	}

	public int getUses() {
		return this.uses;
	}

	public float getSpeed() {
		return this.speed;
	}

	public float getAttackDamageBonus() {
		return this.damage;
	}

	public int getLevel() {
		return this.level;
	}

	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	public Ingredient getRepairIngredient() {
		if (ingredient == null)
			init();
		return ingredient;
	}

	private void init() {
		ingredient = Ingredient.of(repairItem);
	}

}
