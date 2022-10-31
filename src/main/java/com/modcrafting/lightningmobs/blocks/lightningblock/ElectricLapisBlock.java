package com.modcrafting.lightningmobs.blocks.lightningblock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

public class ElectricLapisBlock extends Block {
	
	@Override
	public String getDescriptionId() {
		return "block.lightningmobs.electric_lightning_block";
	}
	
	private ElectricLapisBlock(Properties properties) {
		super(properties);
	}
	
	public static ElectricLapisBlock init() {
		Material lbMaterial = new Material(MaterialColor.NONE, false, true, false, true, false, false, PushReaction.NORMAL);
		ElectricLapisBlock block = new ElectricLapisBlock(Properties.of(lbMaterial)
				.destroyTime(8)
				.explosionResistance(6)
				.requiresCorrectToolForDrops()
				.sound(SoundType.COPPER)
				.strength(2, 300));
		return block;
	}
	
}
