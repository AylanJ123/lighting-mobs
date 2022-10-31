package com.modcrafting.lightningmobs.items.lightningcharge;

import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import com.modcrafting.lightningmobs.helpers.ItemHelpers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;

/**
 * @author PumpkinNightmare
 */
public class LightningCharge extends Item {

	public LightningCharge(Properties properties) {
		super(properties);
	}
	
	public static LightningCharge init() {
		LightningCharge item = new LightningCharge(new Properties()
			.rarity(Rarity.RARE)
			.stacksTo(MAX_STACK_SIZE)
			.tab(CreativeModeTab.TAB_MISC)
		);
		return item;
	}
	
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
		return ItemHelpers.consumeAndCallback(player.level, player, stack, () -> {
			if (player.level.isRainingAt(target.blockPosition()))
				UnstableLightning.SpawnLightning(player.level, target.blockPosition());
			else
				UnstableLightning.SpawnVanillaLightning(player.level, target.blockPosition());
		});
	}
	
	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		return ItemHelpers.consumeAndCallback(context.getLevel(), context.getPlayer(), stack, () -> {
			if (context.getLevel().isRainingAt(context.getClickedPos().above()))
				UnstableLightning.SpawnLightning(context.getLevel(), context.getClickedPos());
			else
				UnstableLightning.SpawnVanillaLightning(context.getLevel(), context.getClickedPos());
		});
	}
	
}
