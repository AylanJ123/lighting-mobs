/**
 * 
 */
package com.modcrafting.lightningmobs.blocks.lightningblock;

import java.util.Random;

import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * @author AylanJ123
 */
public class LightningBlock extends HorizontalDirectionalBlock {
	
	private static final BooleanProperty CAN_SUMMON = BooleanProperty.create("can_summon");
	
	public static BooleanProperty getCanSummon() {
		return CAN_SUMMON;
	}

	private LightningBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CAN_SUMMON, true)
		);
	}
	
	public static LightningBlock init() {
		Material lbMaterial = new Material(MaterialColor.NONE, false, true, false, true, false, false, PushReaction.IGNORE);
		LightningBlock block = new LightningBlock(Properties.of(lbMaterial)
				.destroyTime(10)
				.explosionResistance(INDESTRUCTIBLE)
				.requiresCorrectToolForDrops()
				.sound(SoundType.METAL)
				.strength(2, 300)
				.lightLevel((state) -> 6));
		return block;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(CAN_SUMMON);
		super.createBlockStateDefinition(builder);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
			.setValue(
				FACING, context.getHorizontalDirection()
		);
	}
	
	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (level.isClientSide()) return;
		if (!state.getValue(CAN_SUMMON)) return;
		level.setBlock(pos, state.setValue(CAN_SUMMON, false), UPDATE_ALL);
		level.setBlock(
			pos, state.setValue(
				LightningBlock.getCanSummon(), false
			),
			Block.UPDATE_ALL
		);
		UnstableLightning.SpawnLightning(level, pos.above());
		level.scheduleTick(pos, this, 300);
	}
	
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		level.setBlock(
			pos, state.setValue(
				LightningBlock.getCanSummon(), true
			),
			Block.UPDATE_ALL
		);
		level.playSound(null, pos, Registry.ZAP.get(), SoundSource.BLOCKS, 1, 1);
	}
	
}
