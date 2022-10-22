/**
 * 
 */
package com.modcrafting.lightingmobs.blocks.lightningblock;
import net.minecraft.world.level.block.Block;
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
public class LightningBlock extends Block {
	
	private static LightningBlock block;
	
	public static LightningBlock getBlock() {
		return block;
	}
	
	private LightningBlock(Properties properties) {
		super(properties);
	}
	
	public static LightningBlock init() {
		Material lbMaterial = new Material(MaterialColor.NONE, false, true, false, true, false, false, PushReaction.NORMAL);
		block = new LightningBlock(Properties.of(lbMaterial).destroyTime(20).explosionResistance(INDESTRUCTIBLE).requiresCorrectToolForDrops().sound(SoundType.STONE).strength(2, 300).lightLevel((state) -> 6));
		block.setRegistryName("lightning_block");
		return block;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
	}
	
}
