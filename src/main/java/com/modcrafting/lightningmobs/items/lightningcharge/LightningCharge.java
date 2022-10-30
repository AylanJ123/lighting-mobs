package com.modcrafting.lightningmobs.items.lightningcharge;

import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

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
		if (player.level.isClientSide()) return InteractionResult.SUCCESS;
		UnstableLightning.SpawnLightning(player.level, target.blockPosition());
		if (!player.isCreative()) stack.setCount(stack.getCount() - 1);
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		Level level = context.getLevel();
		if (level.isClientSide()) return InteractionResult.SUCCESS;
		UnstableLightning.SpawnLightning(level, context.getClickedPos());
		if (!context.getPlayer().isCreative()) stack.setCount(stack.getCount() - 1);
		return InteractionResult.SUCCESS;
	}
	
}
