package com.modcrafting.lightningmobs.blocks.lightningblock;

import com.modcrafting.lightningmobs.Registry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author AylanJ123
 *
 */
public class LightningBlockEntity extends BlockEntity {

	public LightningBlockEntity(BlockPos pos, BlockState state) {
		super(Registry.LIGHTNING_BLOCK_ENTITY.get(), pos, state);
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		
	}
	
}
