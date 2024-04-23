package io.github.realguyman.totally_lit.mixin.campfire;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.access.CampfireBlockEntityAccess;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class ExtinguishInRainMixin {
    @Inject(method = "randomTick", at = @At("HEAD"))
    private void extinguish(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.isIn(BlockTags.CAMPFIRES)) {
            final BlockEntity blockEntity = world.getBlockEntity(pos);
            final boolean isRaining = world.hasRain(pos.up());
            final boolean isCampfireBlockEntity = blockEntity instanceof CampfireBlockEntity;
            final boolean isLitCampfire = CampfireBlock.isLitCampfire(state);
            final boolean isChanceInFavor = random.nextFloat() < TotallyLit.CONFIG.campfires.extinguishInRainChance();

            var nearbyVillagers = world.getEntitiesByType(
                    TypeFilter.instanceOf(VillagerEntity.class),
                    new Box(pos).expand(32),
                    EntityPredicates.VALID_LIVING_ENTITY
            );

            if (!nearbyVillagers.isEmpty()) {
                return;
            }

            if (isRaining && isLitCampfire && isCampfireBlockEntity && isChanceInFavor && world.setBlockState(pos, state.with(CampfireBlock.LIT, false))) {
                CampfireBlock.extinguish(null, world, pos, state);
                world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                ((CampfireBlockEntityAccess) blockEntity).totally_lit$setTicksBurntFor(0);
            }
        }
    }
}
