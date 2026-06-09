package io.github.realguyman.totally_lit.mixin.campfire;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.access.CampfireBlockEntityAccess;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin implements CampfireBlockEntityAccess {
    @Unique
    private int ticksBurntFor = 0;

    @Inject(method = "litServerTick", at = @At("RETURN"))
    private static void trackTicksBurntFor(ServerWorld world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, ServerRecipeManager.MatchGetter<SingleStackRecipeInput, CampfireCookingRecipe> recipeMatchGetter, CallbackInfo ci) {
        if (TotallyLit.CONFIG.caretakerCheckRadius() > 0 && TotallyLit.isCaretakerPresent(pos, world)) {
            return;
        }

        if (!TotallyLit.CONFIG.campfires.extinguishOverTime() || state.isIn(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS)) {
            return;
        }

        CampfireBlockEntityAccess campfireAccessed = (CampfireBlockEntityAccess) campfire;
        final Optional<Integer> ticksBurntFor = campfireAccessed.totally_lit$getTicksBurntFor();
        campfireAccessed.totally_lit$setTicksBurntFor(ticksBurntFor.orElse(0) + 1);

        if (ticksBurntFor.orElse(0) > TotallyLit.CONFIG.campfires.burnDuration() && world.setBlockState(pos, state.with(Properties.LIT, false))) {
            CampfireBlock.extinguish(null, world, pos, state);
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            campfireAccessed.totally_lit$setTicksBurntFor(0);
            TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
        } else if (ticksBurntFor.orElse(0) % 300 == 0) {
            campfire.markDirty();
        }
    }

    public void totally_lit$setTicksBurntFor(int ticks) {
        ticksBurntFor = ticks;
    }

    public Optional<Integer> totally_lit$getTicksBurntFor() {
        return Optional.of(ticksBurntFor);
    }

    @Inject(method = "readData", at = @At("RETURN"))
    private void readTicksBurntFor(ReadView view, CallbackInfo ci) {
        if (view.contains("ticksBurntFor")) {
            ticksBurntFor = view.getInt("ticksBurntFor", 0);
        }
    }

    @Inject(method = "writeData", at = @At("RETURN"))
    private void writeTicksBurntFor(WriteView view, CallbackInfo ci) {
        view.putInt("ticksBurntFor", ticksBurntFor);
    }
}
