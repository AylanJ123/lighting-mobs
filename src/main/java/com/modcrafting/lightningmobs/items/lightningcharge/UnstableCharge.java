package com.modcrafting.lightningmobs.items.lightningcharge;

import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import com.modcrafting.lightningmobs.helpers.ItemHelpers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;

/**
 * @author AylanJ123
 */
public class UnstableCharge extends Item {
	
	@Override
	public String getDescriptionId() {
		return "item.lightningmobs.unstable_charge";
	}
	
	public UnstableCharge(Properties properties) {
		super(properties);
	}
	
	public static UnstableCharge init() {
		UnstableCharge item = new UnstableCharge(new Properties()
			.rarity(Rarity.EPIC)
			.stacksTo(MAX_STACK_SIZE)
			.tab(Registry.MOD_TAB)
		);
		return item;
	}
	
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
		return ItemHelpers.consumeAndCallback(player.level, player, stack, () -> {
			UnstableLightning.SpawnLightning(player.level, target.blockPosition().above());
		});
	}
	
	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		return ItemHelpers.consumeAndCallback(context.getLevel(), context.getPlayer(), stack, () -> {
			UnstableLightning.SpawnLightning(context.getLevel(), context.getClickedPos().above());
		});
	}
	
}
