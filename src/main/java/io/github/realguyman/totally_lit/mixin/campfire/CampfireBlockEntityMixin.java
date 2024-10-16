package io.github.realguyman.totally_lit.mixin.campfire;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.access.CampfireBlockEntityAccess;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin implements CampfireBlockEntityAccess {
    @Unique
    private int ticksBurntFor = 0;

    public int totally_lit$getTicksBurntFor() {
        return ticksBurntFor;
    }

    public void totally_lit$setTicksBurntFor(int ticks) {
        ticksBurntFor = ticks;
    }

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readBurnDurationFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        if (nbt.contains("ticksBurntFor")) {
            ticksBurntFor = nbt.getInt("ticksBurntFor");
        }
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeTicksBurntForToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        nbt.putInt("ticksBurntFor", ticksBurntFor);
    }

    @Inject(method = "litServerTick", at = @At("RETURN"))
    private static void trackTicksBurntFor(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci) {
        var nearbyVillagers = world.getEntitiesByType(
                TypeFilter.instanceOf(VillagerEntity.class),
                new Box(pos).expand(32),
                EntityPredicates.VALID_LIVING_ENTITY
        );

        if (!nearbyVillagers.isEmpty() || !TotallyLit.CONFIG.campfires.extinguishOverTime() || state.isIn(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS)) {
            return;
        }

        CampfireBlockEntityAccess campfireAccessed = (CampfireBlockEntityAccess) campfire;
        final int ticksBurntFor = campfireAccessed.totally_lit$getTicksBurntFor() + 1;
        campfireAccessed.totally_lit$setTicksBurntFor(ticksBurntFor);

        if (ticksBurntFor > TotallyLit.CONFIG.campfires.burnDuration() && world.setBlockState(pos, state.with(Properties.LIT, false))) {
            CampfireBlock.extinguish(null, world, pos, state);
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            campfireAccessed.totally_lit$setTicksBurntFor(0);
        } else if (ticksBurntFor % 300 == 0) {
            campfire.markDirty();
        }
    }
}
