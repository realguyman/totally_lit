package io.github.realguyman.totally_lit.mixin.campfire;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public abstract class DefaultStateAndIgnitionMixin extends BlockWithEntity {
    protected DefaultStateAndIgnitionMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void litStateWhenInitialized(boolean emitsParticles, int fireDamage, Settings settings, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(CampfireBlock.LIT, TotallyLit.CONFIG.campfires.defaultLitStateWhenPlaced()));
    }

    @ModifyReturnValue(method = "getPlacementState", at = @At("RETURN"))
    private BlockState litStateWhenPlaced(BlockState original) {
        return original.with(CampfireBlock.LIT, TotallyLit.CONFIG.campfires.defaultLitStateWhenPlaced());
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void ignite(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        final ItemStack itemInHand = player.getStackInHand(hand);
        final boolean canBeIgnited = CampfireBlock.canBeLit(state);

        if (itemInHand.isIn(TagRegistry.CAMPFIRE_IGNITER_ITEMS) && canBeIgnited && world.setBlockState(pos, state.with(CampfireBlock.LIT, true))) {
            itemInHand.damage(1, player, playerInScope -> playerInScope.sendToolBreakStatus(hand));

            world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
