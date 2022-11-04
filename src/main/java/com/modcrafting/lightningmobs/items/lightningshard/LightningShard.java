package com.modcrafting.lightningmobs.items.lightningshard;

import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import com.modcrafting.lightningmobs.helpers.ItemHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * @author PumpkinNightmare
 *
 */
public class LightningShard extends Item {

	private static final float SUMMON_CHANCE = 0.1f;
	
	@Override
	public String getDescriptionId() {
		return "item.lightningmobs.lightning_shard";
	}
	
	public LightningShard(Properties properties) {
		super(properties);
	}
	
	public static LightningShard init() {
		LightningShard item = new LightningShard(new Properties()
			.rarity(Rarity.RARE)
			.stacksTo(MAX_STACK_SIZE)
			.tab(Registry.MOD_TAB)
		);
		return item;
	}
	
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
		return ItemHelpers.consumeAndCallback(player.level, player, stack, () -> {
			if (player.level.random.nextFloat() <= chancesOnWeather(player.level, target.blockPosition())) {
				if (player.level.isRainingAt(target.blockPosition()))
					UnstableLightning.SpawnLightning(player.level, target.blockPosition());
				else
					UnstableLightning.SpawnVanillaLightning(player.level, target.blockPosition());
			}
		});
	}
	
	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		return ItemHelpers.consumeAndCallback(context.getLevel(), context.getPlayer(), stack, () -> {
			Level level = context.getLevel();
			if (level.random.nextFloat() <= chancesOnWeather(context.getLevel(), context.getClickedPos())) {
				if (context.getLevel().isRainingAt(context.getClickedPos().above()))
					UnstableLightning.SpawnLightning(level, context.getClickedPos().above());
				else
					UnstableLightning.SpawnVanillaLightning(level, context.getClickedPos().above());
			}
		});
	}
	
	private static float chancesOnWeather(Level level, BlockPos pos) {
		if (!level.isRainingAt(pos)) return SUMMON_CHANCE;
		if (level.isThundering()) return SUMMON_CHANCE * 2f;
		if (level.isRaining()) return SUMMON_CHANCE * 1.5f;
		return SUMMON_CHANCE;
	}
	
}
