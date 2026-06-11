package io.github.realguyman.totally_lit.mixin.campfire;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.access.CampfireBlockEntityAccess;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

    @Inject(method = "cookTick", at = @At("RETURN"))
    private static void trackTicksBurntFor(ServerLevel world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> recipeMatchGetter, CallbackInfo ci) {
        if (TotallyLit.CONFIG.caretakerCheckRadius() > 0 && TotallyLit.isCaretakerPresent(pos, world)) {
            return;
        }

        if (!TotallyLit.CONFIG.campfires.extinguishOverTime() || state.is(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS)) {
            return;
        }

        CampfireBlockEntityAccess campfireAccessed = (CampfireBlockEntityAccess) campfire;
        final Optional<Integer> ticksBurntFor = campfireAccessed.totally_lit$getTicksBurntFor();
        campfireAccessed.totally_lit$setTicksBurntFor(ticksBurntFor.orElse(0) + 1);

        if (ticksBurntFor.orElse(0) > TotallyLit.CONFIG.campfires.burnDuration() && world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, false))) {
            CampfireBlock.dowse(null, world, pos, state);
            world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
            campfireAccessed.totally_lit$setTicksBurntFor(0);
            TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
        } else if (ticksBurntFor.orElse(0) % 300 == 0) {
            campfire.setChanged();
        }
    }

    public void totally_lit$setTicksBurntFor(int ticks) {
        ticksBurntFor = ticks;
    }

    public Optional<Integer> totally_lit$getTicksBurntFor() {
        return Optional.of(ticksBurntFor);
    }

    @Inject(method = "loadAdditional", at = @At("RETURN"))
    private void readTicksBurntFor(ValueInput view, CallbackInfo ci) {
        if (view.contains("ticksBurntFor")) {
            ticksBurntFor = view.getIntOr("ticksBurntFor", 0);
        }
    }

    @Inject(method = "saveAdditional", at = @At("RETURN"))
    private void writeTicksBurntFor(ValueOutput view, CallbackInfo ci) {
        view.putInt("ticksBurntFor", ticksBurntFor);
    }
}
