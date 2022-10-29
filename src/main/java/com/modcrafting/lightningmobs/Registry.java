package com.modcrafting.lightningmobs;

import java.util.function.Supplier;

import com.modcrafting.lightningmobs.blocks.lightningblock.LightningBlock;
import com.modcrafting.lightningmobs.blocks.lightningblock.LightningBlockEntity;
import com.modcrafting.lightningmobs.blocks.lightningblock.LightningBlockItem;
import com.modcrafting.lightningmobs.entities.UnstableLightning;
import com.modcrafting.lightningmobs.items.lightningcharge.LightningCharge;
import com.modcrafting.lightningmobs.items.lightningshard.LightningShard;
import com.modcrafting.lightningmobs.items.lightningshard.LightningShardLootModifierSerializer;
import com.modcrafting.lightningmobs.items.lightningsword.LightningSword;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registry {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LMobs.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LMobs.MODID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LMobs.MODID);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LMobs.MODID);
	private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, LMobs.MODID);
	
	public static final RegistryObject<Block> LIGHTNING_BLOCK = BLOCKS.register("lightning_block", () -> LightningBlock.init());
	public static final RegistryObject<BlockItem> LIGHTNING_BLOCK_ITEM = registerBlockItem("lightning_block", () -> LightningBlockItem.init());
	public static final RegistryObject<BlockEntityType<LightningBlockEntity>> LIGHTNING_BLOCK_ENTITY = BLOCK_ENTITIES.register(
		"lightning_block_entity", (Supplier<? extends BlockEntityType<LightningBlockEntity>>)
		() -> BlockEntityType.Builder.of(LightningBlockEntity::new, LIGHTNING_BLOCK.get()).build(null)
	);
	
	public static final RegistryObject<Item> LIGHTNING_SHARD = ITEMS.register("lightning_shard", () -> LightningShard.init());
	public static final RegistryObject<LightningShardLootModifierSerializer> LIGHTNING_SHARD_LM = LOOT.register(
		"lightning_shard_lm", () -> new LightningShardLootModifierSerializer()
	);
	
	public static final RegistryObject<Item> LIGHTNING_CARGE = ITEMS.register("lightning_charge", () -> LightningCharge.init());
	
	public static final RegistryObject<Item> LIGHTNING_SWORD = ITEMS.register("lightning_sword", () -> LightningSword.init());
	
	public static final RegistryObject<EntityType<UnstableLightning>> UNSTABLE_LIGHTNING = ENTITIES.register(
		"unstable_lightning", (Supplier<? extends EntityType<UnstableLightning>>)
		() -> {
			EntityType.Builder<UnstableLightning> builder = EntityType.Builder.of(UnstableLightning::new, MobCategory.AMBIENT);
			EntityType<UnstableLightning> type = builder.build(new ResourceLocation(LMobs.MODID + ":unstable_lightning").toString());
			return type;
		}
	);
	
	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
		ENTITIES.register(bus);
		BLOCK_ENTITIES.register(bus);
		LOOT.register(bus);
	}
	
	private static RegistryObject<BlockItem> registerBlockItem(String registryName, Supplier<? extends BlockItem> sup) {
		return ITEMS.register(registryName, sup);
	}
	
}
