package com.modcrafting.lightningmobs;

import java.util.function.Supplier;

import com.modcrafting.lightningmobs.blocks.lightningblock.ElectricLapisBlock;
import com.modcrafting.lightningmobs.blocks.lightningblock.ElectricLapisBlockItem;
import com.modcrafting.lightningmobs.blocks.lightningblock.LightningBlock;
import com.modcrafting.lightningmobs.blocks.lightningblock.LightningBlockEntity;
import com.modcrafting.lightningmobs.blocks.lightningblock.LightningBlockItem;
import com.modcrafting.lightningmobs.entities.unstablelightning.UnstableLightning;
import com.modcrafting.lightningmobs.items.lightningcharge.LightningCharge;
import com.modcrafting.lightningmobs.items.lightningshard.LightningShard;
import com.modcrafting.lightningmobs.items.lightningshard.LightningShardLootModifierSerializer;
import com.modcrafting.lightningmobs.items.lightningsword.LightningSword;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LMobs.MODID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LMobs.MODID);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LMobs.MODID);
	private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, LMobs.MODID);
	
	public static final RegistryObject<Block> LIGHTNING_BLOCK = BLOCKS.register("lightning_block", () -> LightningBlock.init());
	public static final RegistryObject<BlockItem> LIGHTNING_BLOCK_ITEM = registerBlockItem("lightning_block", () -> LightningBlockItem.init());
	public static final RegistryObject<BlockEntityType<LightningBlockEntity>> LIGHTNING_BLOCK_ENTITY = BLOCK_ENTITIES.register(
		"lightning_block_entity", (Supplier<? extends BlockEntityType<LightningBlockEntity>>)
		() -> BlockEntityType.Builder.of(LightningBlockEntity::new, LIGHTNING_BLOCK.get()).build(null)
	);
	
	public static final RegistryObject<Block> ELECTRIC_LAPIS_BLOCK = BLOCKS.register("electric_lapis_block", () -> ElectricLapisBlock.init());
	public static final RegistryObject<BlockItem> ELECTRIC_LAPIS_BLOCK_ITEM = registerBlockItem("electric_lapis_block", () -> ElectricLapisBlockItem.init());
	
	public static final RegistryObject<Item> LIGHTNING_SHARD = ITEMS.register("lightning_shard", () -> LightningShard.init());
	public static final RegistryObject<LightningShardLootModifierSerializer> LIGHTNING_SHARD_LM = LOOT.register(
		"lightning_shard_lm", () -> new LightningShardLootModifierSerializer()
	);
	
	public static final RegistryObject<Item> LIGHTNING_CARGE = ITEMS.register("lightning_charge", () -> LightningCharge.init());
	
	public static final RegistryObject<Item> LIGHTNING_SWORD = ITEMS.register("lightning_sword", () -> LightningSword.init());
	
	public static final RegistryObject<EntityType<UnstableLightning>> UNSTABLE_LIGHTNING = ENTITIES.register(
		"unstable_lightning", (Supplier<? extends EntityType<UnstableLightning>>)
		() -> configureLightning(EntityType.Builder.of(UnstableLightning::new, MobCategory.MISC)).build("")
	);
	
	public static final RegistryObject<SoundEvent> LEFTOVER_CHARGE = registerSound("leftover_charge");
	public static final RegistryObject<SoundEvent> THUNDER = registerSound("thunder");
	
	public static final CreativeModeTab MOD_TAB = new CreativeModeTab("lightningMobsTab") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(LIGHTNING_SHARD.get());
		}
	};
	
	
	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
		ENTITIES.register(bus);
		BLOCK_ENTITIES.register(bus);
		LOOT.register(bus);
		SOUNDS.register(bus);
	}
	
	private static RegistryObject<BlockItem> registerBlockItem(String registryName, Supplier<? extends BlockItem> sup) {
		return ITEMS.register(registryName, sup);
	}
	
	private static RegistryObject<SoundEvent> registerSound(String registryName) {
		return SOUNDS.register(registryName, () -> new SoundEvent(new ResourceLocation(LMobs.MODID, registryName)));
	}
	
	private static EntityType.Builder<UnstableLightning> configureLightning(EntityType.Builder<UnstableLightning> builder) {
		return builder.sized(1, 1)
			.canSpawnFarFromPlayer()
			.noSummon().noSave()
			.fireImmune()
			.clientTrackingRange(16)
			.setShouldReceiveVelocityUpdates(false);
	}
	
}
