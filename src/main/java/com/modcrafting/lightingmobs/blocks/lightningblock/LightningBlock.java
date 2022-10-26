/**
 * 
 */
package com.modcrafting.lightingmobs.blocks.lightningblock;

import org.antlr.v4.parse.BlockSetTransformer.setElement_return;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
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
