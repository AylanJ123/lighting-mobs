package com.modcrafting.lightningmobs.blocks.weather_channeler;

import java.util.Random;
import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.configs.ServerConfigSpec;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import com.modcrafting.lightningmobs.helpers.ItemHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

public class WeatherChanneler extends Block {

	public WeatherChanneler(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any().setValue(FILLED, false).setValue(UNSTABLE, false)
		);
	}
	
	public static final BooleanProperty FILLED = BooleanProperty.create("filled");
	public static final BooleanProperty UNSTABLE = BooleanProperty.create("unstable");
	
	public static WeatherChanneler init() {
		Material lbMaterial = new Material(MaterialColor.NONE, false, true, false, true, false, false, PushReaction.IGNORE);
		WeatherChanneler block = new WeatherChanneler(Properties.of(lbMaterial)
			.destroyTime(10)
			.explosionResistance(INDESTRUCTIBLE)
			.requiresCorrectToolForDrops()
			.sound(SoundType.METAL)
			.strength(2, 300)
			.randomTicks().lightLevel((state) ->
				state.getValue(FILLED) &&
				state.getValue(UNSTABLE) ?
					16 : state.getValue(FILLED) ?
						4 : 0
			)
		);
		return block;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FILLED);
		builder.add(UNSTABLE);
		super.createBlockStateDefinition(builder);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		if (state.getValue(FILLED)) {
			ItemStack stack = new ItemStack(
				state.getValue(UNSTABLE) ?
					Registry.UNSTABLE_CHARGE.get() :
					Registry.LIGHTNING_CHARGE.get()
			);
			if (!player.getInventory().add(stack)) {
				ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
				item.setNoPickUpDelay();
				level.addFreshEntity(item);
			}
			level.setBlock(
				pos, state.setValue(FILLED, false).setValue(UNSTABLE, false), UPDATE_ALL
			);
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		if (level.isClientSide() || hand != InteractionHand.MAIN_HAND) return InteractionResult.SUCCESS;
		ItemStack stack = player.getMainHandItem();
		if (stack.getItem() != Registry.LIGHTNING_CHARGE.get()) return InteractionResult.SUCCESS;
		return ItemHelpers.consumeAndCallback(level, player, stack, () -> {
			level.setBlock(
				pos, state.setValue(FILLED, true).setValue(UNSTABLE, false), UPDATE_ALL
			);
		});
	}
	
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos,
			boolean isMoving) {
		if (level.isClientSide()) return;
		if (level.getBlockState(fromPos).getBlock() instanceof LightningRodBlock && level.getBlockState(fromPos).getValue(LightningRodBlock.POWERED))
			lightningStruck(state, (ServerLevel) level, pos, RANDOM.nextFloat());
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		if (level.isClientSide()) return;
		if (
				random.nextFloat() <= ServerConfigSpec.WEATHER_CHANGE_CHANCE.get().floatValue() &&
				state.getValue(FILLED) &&
				!state.getValue(UNSTABLE)
			) {
			if (
					(
						level.isThundering() ||
						(level.isRaining() && random.nextFloat() <= .5)
					) &&
					level.getBlockState(pos.above())
						.getBlock() instanceof LightningRodBlock
					) {
				UnstableLightning.SpawnVanillaLightning(level, pos.above());
				lightningStruck(state, level, pos, random.nextFloat());
			} else if (level.isRaining()) {
				level.setWeatherParameters(60, 1200, true, true);
				level.playSound(null, pos, Registry.ZAP.get(), SoundSource.BLOCKS, 1.15f, 1.25f);
			} else {
				level.setWeatherParameters(60, 1200, true, false);
				level.playSound(null, pos, Registry.ZAP.get(), SoundSource.BLOCKS, 1, 1);
			}
		}
	}

	private void lightningStruck(BlockState state, ServerLevel level, BlockPos pos, float random) {
		level.setBlock(
			pos, state
			.setValue(FILLED, true)
			.setValue(UNSTABLE, true),
			UPDATE_ALL
		);
		level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), Registry.LEFTOVER_CHARGE.get(),
			SoundSource.WEATHER, 3.75F, 0.3F + random * 0.1925F);
	}
	

}
