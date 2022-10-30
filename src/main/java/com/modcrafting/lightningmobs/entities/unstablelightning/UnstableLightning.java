package com.modcrafting.lightningmobs.entities.unstablelightning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.collect.Sets;
import com.modcrafting.lightningmobs.LMobs;
import com.modcrafting.lightningmobs.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class UnstableLightning extends Entity {

	public static ArrayList<Pair<String, String>> upgradeables = new ArrayList<>();
	private int life;
	public long seed;
	private int flashes;
	@Nullable private ServerPlayer cause;
	private final Set<Entity> hitEntities = Sets.newHashSet();
	
	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		super.tick();
		if (this.life == 2) {
			if (this.level.isClientSide()) {
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER,
						SoundSource.WEATHER, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT,
						SoundSource.WEATHER, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
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
				this.level.setSkyFlashTime(2);
			} else {
				List<Entity> list1 = this.level
					.getEntities(
						this, new AABB(
							this.getX() - 3.0D, this.getY() - 3.0D, this.getZ() - 3.0D,
							this.getX() + 3.0D, this.getY() + 6.0D + 3.0D, this.getZ() + 3.0D),
						Entity::isAlive
					);
				this.hitEntities.addAll(list1);
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
		this.life = 2;
		this.seed = this.random.nextLong();
		this.flashes = this.random.nextInt(3) + 1;
	}

	public static void SpawnLightning(Level level, BlockPos pos) {
		UnstableLightning lightning = Registry.UNSTABLE_LIGHTNING.get().create(level);
		lightning.moveTo(Vec3.atBottomCenterOf(pos));
		level.addFreshEntity(lightning);
	}

	public Stream<Entity> getHitEntities() {
		return hitEntities.stream().filter(Entity::isAlive);
	}

	private static void lookForUpgradeable(Entity entity, BlockPos pos) {
		LMobs.getLogger().debug("Hey! This hit something");
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
						((LivingEntity) spawnedEntity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 255));
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
			if (pair.getRight().equals(str)) return pair.getRight();
		}
		return "<missing entry>";
	}

}
