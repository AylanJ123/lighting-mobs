package com.modcrafting.lightningmobs.entities.unstablelightning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import com.modcrafting.lightningmobs.LMobs;
import com.modcrafting.lightningmobs.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class UnstableLightning extends Entity {

	public static ArrayList<Pair<String, String>> upgradeables = new ArrayList<>();
	private int life;
	public long seed;
	private int flashes;
	boolean alreadyHit = false;
	
	public void tick() {
		super.tick();
		if (this.life > 1 && this.level.isClientSide()) {
			if (life == 5) {
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), Registry.THUNDER.get(),
						SoundSource.WEATHER, 10250.0F, 1.1F + this.random.nextFloat() * 0.2125F, true);
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT,
						SoundSource.WEATHER, 2.25F, 0.8F + this.random.nextFloat() * 0.2125F, true);
			} else if (life == 2) {
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), Registry.LEFTOVER_CHARGE.get(),
						SoundSource.WEATHER, 3.75F, 0.3F + this.random.nextFloat() * 0.1925F, true);
			}
		}
		--this.life;
		if (this.life < 0) {
			if (this.flashes == 0) {
				this.discard();
			} else if (this.life < -this.random.nextInt(10)) {
				--this.flashes;
				this.life = 1;
				this.seed = this.random.nextLong();
			}
		}
		if (this.life >= 0) {
			if (this.level.isClientSide()) {
				this.level.setSkyFlashTime(5);
			} else if (!alreadyHit){
				List<Entity> list1 = this.level
					.getEntities(
						this, new AABB(
							this.getX() - 1.0D, this.getY() - 1.0D, this.getZ() - 1.0D,
							this.getX() + 1.0D, this.getY() + 2.0D + 1.0D, this.getZ() + 2.0D),
						Entity::isAlive
					);
				alreadyHit = true;
				AreaEffectCloud cloud = EntityType.AREA_EFFECT_CLOUD.create(level);
				cloud.setPotion(Potions.STRONG_SLOWNESS);
				cloud.moveTo(position());
				cloud.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 18));
				cloud.setRadius(3);
				cloud.setDuration(80);
				cloud.setWaitTime(0);
				level.addFreshEntity(cloud);
				for(Entity e : list1) lookForUpgradeable(e, this.blockPosition());
			}
		}
	}
	
	public boolean shouldRenderAtSqrDistance(double pDistance) {
		double d0 = 64.0D * getViewScale();
		return pDistance < d0 * d0;
	}
	
	protected void defineSynchedData() {
	}
	
	protected void readAdditionalSaveData(CompoundTag pCompound) {
	}

	protected void addAdditionalSaveData(CompoundTag pCompound) {
	}

	public Packet<?> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}
	
	public UnstableLightning(EntityType<? extends UnstableLightning> type, Level level) {
		super(type, level);
		this.noCulling = true;
		this.life = 5;
		this.seed = this.random.nextLong();
		this.flashes = this.random.nextInt(5) + 5;
	}

	public static void SpawnLightning(Level level, BlockPos pos) {
		UnstableLightning lightning = Registry.UNSTABLE_LIGHTNING.get().create(level);
		lightning.moveTo(Vec3.atBottomCenterOf(pos.above()));
		level.addFreshEntity(lightning);
	}
	
	public static void SpawnVanillaLightning(Level level, BlockPos pos) {
		LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
		lightning.moveTo(Vec3.atBottomCenterOf(pos.above()));
		level.addFreshEntity(lightning);
	}

	private static void lookForUpgradeable(Entity entity, BlockPos pos) {
		boolean didSpawn = false;
		boolean shouldSpawn = false;
		for (Pair<String, String> pair : upgradeables) {
			if (entity.level.isClientSide())
				return;
			if (pair.getLeft().equals(entity.getType().getRegistryName().toString())) {
				shouldSpawn = true;
				for (Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>> map : ForgeRegistries.ENTITIES.getEntries()) {
					if (pair.getRight().equals(map.getKey().location().toString())) {
						didSpawn = true;
						entity.discard();
						Entity spawnedEntity = map.getValue().create(entity.level);
						spawnedEntity.moveTo(Vec3.upFromBottomCenterOf(pos, 1));
						((LivingEntity) spawnedEntity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 255));
						entity.level.addFreshEntity(spawnedEntity);
						break;
					}
				}
			} else {
				entity.hurt(DamageSource.LIGHTNING_BOLT, 20);
			}
			if (didSpawn)
				break;
		}
		if (shouldSpawn && !didSpawn) {
			LMobs.getLogger().error(String.format(
				"We are supposed to spawn %s from a %s but we looked inside the registries and is missing",
				lookForEntityInList(entity.getType().getRegistryName().toString()),
				entity.getType().getRegistryName())
			);
		}
	}
	
	private static String lookForEntityInList(String str) {
		for (Pair<String, String> pair : upgradeables) {
			if (pair.getLeft().equals(str)) return pair.getRight();
		}
		return "<missing entry>";
	}

}
