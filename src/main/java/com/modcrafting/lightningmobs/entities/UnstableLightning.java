package com.modcrafting.lightningmobs.entities;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

import com.modcrafting.lightningmobs.LMobs;
import com.modcrafting.lightningmobs.Registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class UnstableLightning extends LightningBolt {

	private static ArrayList<Pair<String, String>> upgradeables = new ArrayList<>();
	
	public UnstableLightning(EntityType<? extends LightningBolt> type, Level level) {
		super(type, level);
		setDamage(0);
	}
	
	public static void SpawnLightning(Level level) {
		level.addFreshEntity(EntityType.LIGHTNING_BOLT.create(level));
		//level.addFreshEntity(Registry.UNSTABLE_LIGHTNING.get().create(level));
	}
	
	@Override
	public Stream<Entity> getHitEntities() {
		LMobs.getLogger().debug("Hey! This hit something");
		Stream<Entity> entities = super.getHitEntities();
		entities.forEach(UnstableLightning::lookForUpgradeable);
		return entities;
	}
	
	private static void lookForUpgradeable(Entity entity) {
		boolean didSpawn = false;
		boolean shouldSpawn = false;
		for (Pair<String, String> pair : upgradeables) {
			if (entity.level.isClientSide()) return;
			if (pair.getLeft().equals(entity.getType().getRegistryName().toString())) {
				shouldSpawn = true;
				for (Map.Entry<ResourceKey<EntityType<?>>,EntityType<?>> map : ForgeRegistries.ENTITIES.getEntries()) {
					if (pair.getRight().equals(map.getKey().getRegistryName().toString())) {
						didSpawn = true;
						entity.level.addFreshEntity(map.getValue().create(entity.level));
						break;
					}
				}
			}
			if (didSpawn) break;
		}
		if (shouldSpawn && !didSpawn) {
			LMobs.getLogger().error(
				"We are supposed to spawn "
					+ upgradeables.get(
						upgradeables.indexOf(
							(Object)entity
							.getType()
							.getRegistryName()
							.toString()
						)
					).getValue()
				+ " from a " + entity.getType().getRegistryName()
				+ " but we looked inside the registries and is missing");
		}
	}

}
