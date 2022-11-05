package com.modcrafting.lightningmobs.items.lightningarrow;

import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.entities.lightningArrow.LightningArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class LightningArrowItem extends ArrowItem {

	public String getDescriptionId() {
		return "item.lightningmobs.lightning_arrow";
	}
	
	private LightningArrowItem(Properties properties) {
		super(properties);
	}
	
	public static LightningArrowItem init() {
		LightningArrowItem item = new LightningArrowItem(new Properties()
			.rarity(Rarity.RARE)
			.stacksTo(MAX_STACK_SIZE)
			.tab(Registry.MOD_TAB)
		);
		return item;
	}
	
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
		return false;
	}
	
	@Override
	public AbstractArrow createArrow(Level level, ItemStack pStack, LivingEntity shooter) {
		return new LightningArrow(level, shooter);
	}

}
