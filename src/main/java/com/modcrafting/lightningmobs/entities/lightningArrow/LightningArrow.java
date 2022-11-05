package com.modcrafting.lightningmobs.entities.lightningArrow;

import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class LightningArrow extends Arrow {
	
	private boolean alreadyHit = false;
	
	public LightningArrow(EntityType<? extends Arrow> type, Level level) {
		super(type, level);
		setEffect();
	}

	public LightningArrow(Level level, double x, double y, double z) {
		super(level, x, y, z);
		setEffect();
	}

	public LightningArrow(Level level, LivingEntity owner) {
		super(level, owner);
		setEffect();
	}
	
	private void setEffect() {
		addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 20));
		pickup = AbstractArrow.Pickup.DISALLOWED;
		setSoundEvent(Registry.ZAP.get());
		playSound(Registry.LONGER_ZAP.get(), 0.85f, 0.95F + random.nextFloat() * 0.1925F);
	}
	
	@Override
	public EntityType<?> getType() {
		return Registry.SHOT_LIGHTNING_ARROW.get();
	}
	
	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (alreadyHit) return;
		BlockPos pos = new BlockPos(result.getLocation());
		alreadyHit = true;
		if (level.isRainingAt(pos))
			UnstableLightning.SpawnLightning(level, pos);
		else
			UnstableLightning.SpawnVanillaLightning(level, pos);
	}
	
}
