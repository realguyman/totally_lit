package io.github.realguyman.totally_lit.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlintAndSteelItem.class)
public abstract class DamageFlintAndSteelItemInRecipeMixin implements FabricItem {
    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        var damagedStack = stack.copy();

        if (stack.getDamage() < stack.getMaxDamage() - 1) {
            damagedStack.setDamage(stack.getDamage() + 1);
            return damagedStack;
        }

        return ItemStack.EMPTY;
    }
}
