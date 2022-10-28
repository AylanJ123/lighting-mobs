/**
 * 
 */
package com.modcrafting.lightningmobs.blocks.lightningblock;

import com.modcrafting.lightningmobs.LMobs;
import com.modcrafting.lightningmobs.Registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * @author AylanJ123
 */
public class LightningBlock extends HorizontalDirectionalBlock implements EntityBlock {
	
	private static final BooleanProperty CAN_SUMMON = BooleanProperty.create("can_summon");
	
	private LightningBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CAN_SUMMON, true)
		);
	}
	
	public static LightningBlock init() {
		Material lbMaterial = new Material(MaterialColor.NONE, false, true, false, true, false, false, PushReaction.NORMAL);
		LightningBlock block = new LightningBlock(Properties.of(lbMaterial)
				.destroyTime(10)
				.explosionResistance(INDESTRUCTIBLE)
				.requiresCorrectToolForDrops()
				.sound(SoundType.DEEPSLATE)
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
		LMobs.getLogger().debug("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEY");
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LightningBlockEntity(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return type == Registry.LIGHTNING_BLOCK_ENTITY.get() ? LightningBlockEntity::tick : null;
	}
	
}
