package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
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
                                lit, unlit, stack, player, slot, world
                        );
                    }
            );

            TotallyLit.LANTERN_MAP.forEach((lit, unlit) -> {
                        extinguish(
                                TotallyLit.CONFIG.lanterns.extinguishInRainChance(),
                                lit, unlit, stack, player, slot, world
                        );
                    }
            );

            TotallyLit.TORCH_MAP.forEach((lit, unlit) -> {
                extinguish(
                        TotallyLit.CONFIG.torches.extinguishInRainChance(),
                        lit, unlit, stack, player, slot, world
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
    private void extinguish(Float chance, Block lit, Block unlit, ItemStack stack, PlayerEntity player, int slot, World world) {
        if (!shouldExtinguish(chance, lit, player, world) || !stack.isOf(lit.asItem())) {
            return;
        }

        if (player.getOffHandStack().isOf(lit.asItem())) {
            player.getInventory().offHand.set(0, new ItemStack(unlit.asItem(), player.getOffHandStack().getCount()));
        } else {
            player.getInventory().setStack(slot, new ItemStack(unlit.asItem(), stack.getCount()));
        }
    }
}
