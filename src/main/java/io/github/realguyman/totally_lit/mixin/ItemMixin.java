package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.Initializer;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        PlayerEntity player = null;

        if (entity.isPlayer()) {
            player = (PlayerEntity) entity;
        }

        if (player != null && !world.isClient() && !player.isCreative() && !player.isSpectator() && player.age % 20 == 0) {
            Float chance = null;
            Item item = null;

            if(stack.isOf(Items.JACK_O_LANTERN)) {
                chance = Initializer.configuration.jackOLanternConfiguration.extinguishInRainChance;
                item = Items.CARVED_PUMPKIN;
            } else if (stack.isOf(Items.LANTERN)) {
                chance = Initializer.configuration.lanternConfiguration.extinguishInRainChance;
                item = ItemRegistry.UNLIT_LANTERN;
            } else if (stack.isOf(Items.TORCH)) {
                chance = Initializer.configuration.torchConfiguration.extinguishInRainChance;
                item = ItemRegistry.UNLIT_TORCH;
            }

            if (chance != null && ((player.isSubmergedInWater() || player.isSwimming()) || (player.isTouchingWater() && world.getRandom().nextInt(100) == 0) || (player.age % 940 == 0 && world.hasRain(player.getCameraBlockPos()) && world.getRandom().nextFloat() < chance))) {
                player.getInventory().setStack(slot, new ItemStack(item, stack.getCount()));
            }
        }
    }
}
