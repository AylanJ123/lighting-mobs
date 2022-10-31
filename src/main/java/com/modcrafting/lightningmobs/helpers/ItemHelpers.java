package com.modcrafting.lightningmobs.helpers;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class ItemHelpers {
	
	private ItemHelpers() {} //So it can't be instantiated
	
	public static InteractionResult consumeAndCallback(Level level, Player player, ItemStack stack, Runnable callback) {
		if (level.isClientSide()) return InteractionResult.SUCCESS;
		callback.run();
		if (!player.isCreative()) stack.setCount(stack.getCount() - 1);
		return InteractionResult.SUCCESS;
	}
	
}
