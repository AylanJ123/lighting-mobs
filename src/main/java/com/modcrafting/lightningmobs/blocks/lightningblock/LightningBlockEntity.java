package com.modcrafting.lightningmobs.blocks.lightningblock;

import com.modcrafting.lightningmobs.Registry;
import com.modcrafting.lightningmobs.entities.UnstableLightning;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author AylanJ123
 *
 */
public class LightningBlockEntity extends BlockEntity {
	
	private static final int COOLDOWN = 300;
	
	private int counter = 0;
	private boolean charged = true;
	
	public LightningBlockEntity(BlockPos pos, BlockState state) {
		super(Registry.LIGHTNING_BLOCK_ENTITY.get(), pos, state);
		/*level.setBlock(pos, state.setValue(
			LightningBlock.getCanSummon(), true
		), Block.UPDATE_ALL);*/
	}
	
	public void summon(Level level, BlockPos pos, BlockState state) {
		counter = 0;
		charged = false;
		level.setBlock(
			pos, state.setValue(
				LightningBlock.getCanSummon(), false
			),
			Block.UPDATE_ALL
		);
		UnstableLightning.SpawnLightning(level);
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		LightningBlockEntity entity = (LightningBlockEntity) blockEntity;
		if (entity.charged) return;
		entity.counter++;
		if (entity.counter >= COOLDOWN) {
			entity.charged = true;
			level.setBlock(
				pos, state.setValue(
					LightningBlock.getCanSummon(), true
				),
				Block.UPDATE_ALL
			);
		}
	}
	
}
