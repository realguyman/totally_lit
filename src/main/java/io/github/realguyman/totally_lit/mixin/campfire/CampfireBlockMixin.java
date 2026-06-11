package io.github.realguyman.totally_lit.mixin.campfire;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock {
    protected CampfireBlockMixin(Properties settings) {
        super(settings);
    }

    @ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
    private BlockState setDefaultLitStateForCampfire(BlockState original) {
        return original.setValue(CampfireBlock.LIT, TotallyLit.CONFIG.campfires.defaultLitStateWhenPlaced());
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void igniteUnlitCampfireFromLitItem(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        final boolean canBeIgnited = CampfireBlock.canLight(state);
        final boolean stackHasFireAspect = stack.getEnchantments().keySet().contains(
                world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(
                        Enchantments.FIRE_ASPECT
                )
        );

        if (
                !stack.is(TagRegistry.CAMPFIRE_IGNITER_ITEMS) &&
                        (!TotallyLit.CONFIG.fireAspectIgnitesUnlitVariants() || !stackHasFireAspect) ||
                        !canBeIgnited
        ) {
            return;
        }

        if (!world.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, true))) {
            cir.setReturnValue(InteractionResult.FAIL);
        }

        stack.hurtAndBreak(1, player, EquipmentSlot.values()[hand.ordinal()]);
        world.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
        player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}
