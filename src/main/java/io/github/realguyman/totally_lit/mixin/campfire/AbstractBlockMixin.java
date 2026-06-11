package io.github.realguyman.totally_lit.mixin.campfire;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.access.CampfireBlockEntityAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class AbstractBlockMixin {
    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void hasRandomTicks(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void extinguishCampfireFromRain(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (state.is(BlockTags.CAMPFIRES)) {
            final BlockEntity blockEntity = world.getBlockEntity(pos);
            final boolean isRaining = world.isRainingAt(pos.above());
            final boolean isCampfireBlockEntity = blockEntity instanceof CampfireBlockEntity;
            final boolean isLitCampfire = CampfireBlock.isLitCampfire(state);
            final boolean isChanceInFavor = random.nextFloat() < TotallyLit.CONFIG.campfires.extinguishInRainChance();

            if (TotallyLit.CONFIG.caretakerCheckRadius() > 0 && TotallyLit.isCaretakerPresent(pos, world)) {
                return;
            }

            if (isRaining && isLitCampfire && isCampfireBlockEntity && isChanceInFavor && world.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, false))) {
                CampfireBlock.dowse(null, world, pos, state);
                world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                ((CampfireBlockEntityAccess) blockEntity).totally_lit$setTicksBurntFor(0);
                TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
            }
        }
    }
}
