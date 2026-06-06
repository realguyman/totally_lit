package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Item.class)
public abstract class ItemMixin {

    // TODO: Fix items not extinguishing in inventory
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, EquipmentSlot slot, CallbackInfo ci) {
        if (!TotallyLit.CONFIG.itemsCanExtinguishInPlayerInventory() || !entity.isPlayer()) {
            return;
        }

        PlayerEntity player = (PlayerEntity) entity;

        if (
                !world.isClient()
                && !player.isCreative()  // Do not extinguish items if in creative mode
                && !player.isSpectator() // Do not extinguish items if in spectator mode
                && player.age % 20 == 0  // Only check once a second
                && player.isTouchingWaterOrRain()
        ) {
            TotallyLit.JACK_O_LANTERN_MAP.forEach((lit, unlit) -> {
                        extinguish(
                                TotallyLit.CONFIG.jackOLanterns.extinguishInRainChance(),
                                lit, unlit, stack, player, world
                        );
                    }
            );

            TotallyLit.LANTERN_MAP.forEach((lit, unlit) -> {
                        extinguish(
                                TotallyLit.CONFIG.lanterns.extinguishInRainChance(),
                                lit, unlit, stack, player, world
                        );
                    }
            );

            TotallyLit.TORCH_MAP.forEach((lit, unlit) -> {
                extinguish(
                        TotallyLit.CONFIG.torches.extinguishInRainChance(),
                        lit, unlit, stack, player, world
                    );
                }
            );
        }
    }

    @Unique
    private boolean shouldExtinguish(float chance, Block lit, PlayerEntity player, World world) {
        if (player.isSubmergedInWater() || player.isSwimming()) {
            return true;
        }

        if (player.isTouchingWater() && new Random().nextInt(100) == 0) {
            return true;
        }

        return player.age % 940 == 0
                && world.hasRain(player.getBlockPos())
                && world.getRandom().nextFloat() < chance
                && TotallyLit.TORCH_MAP.containsKey(lit);
    }

    @Unique
    private void extinguish(Float chance, Block lit, Block unlit, ItemStack stack, PlayerEntity player, World world) {
        if (!shouldExtinguish(chance, lit, player, world) || !stack.isOf(lit.asItem())) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        ItemStack offHandStack = inventory.getStack(PlayerInventory.OFF_HAND_SLOT);

        if (ItemStack.areEqual(offHandStack, stack)) {
            inventory.setStack(PlayerInventory.OFF_HAND_SLOT, stack.withItem(unlit.asItem()));
            return;
        }

        DefaultedList<ItemStack> inventoryStacks = inventory.getMainStacks();

        for (int slot = 0; slot < PlayerInventory.MAIN_SIZE; slot++) {
            var inventoryStack = inventoryStacks.get(slot);

            if (!ItemStack.areEqual(inventoryStack, stack)) {
                continue;
            }

            inventoryStacks.set(slot, stack.withItem(unlit.asItem()));
            break;
        }
    }
}
