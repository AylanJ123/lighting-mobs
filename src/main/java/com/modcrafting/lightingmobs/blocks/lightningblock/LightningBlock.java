/**
 * 
 */
package com.modcrafting.lightingmobs.blocks.lightningblock;

import com.modcrafting.lightingmobs.LMobs;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * @author AylanJ123
 *
 */
public class LightningBlock extends HorizontalDirectionalBlock {
	
	private LightningBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any().setValue(FACING, Direction.NORTH)
		);
	}
	
	public static LightningBlock init() {
		Material lbMaterial = new Material(MaterialColor.NONE, false, true, false, true, false, false, PushReaction.NORMAL);
		return new LightningBlock(Properties.of(lbMaterial)
				.destroyTime(20)
				.explosionResistance(INDESTRUCTIBLE)
				.requiresCorrectToolForDrops()
				.sound(SoundType.STONE)
				.strength(2, 300)
				.lightLevel((state) -> 6));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
			.setValue(
				FACING, context.getHorizontalDirection()
		);
	}
}
