package io.github.realguyman.totally_lit.mixin.lantern;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.tick.WorldTickScheduler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OxidizableLanternBlock.class)
public class OxidizableLanternBlockMixin extends LanternBlock {
    public OxidizableLanternBlockMixin(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void schedule(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (!TotallyLit.LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean isRaining = world.hasRain(pos.up());
        final boolean isChanceInFavor = random.nextFloat() < TotallyLit.CONFIG.lanterns.extinguishInRainChance();
        final boolean canExtinguishOverTime = TotallyLit.CONFIG.lanterns.extinguishOverTime();

        if ((isRaining && isChanceInFavor) || state.get(LanternBlock.WATERLOGGED)) {
            scheduledTick(state, world, pos, random);
        } else if (canExtinguishOverTime && !state.isIn(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS)) {
            WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
            Block block = state.getBlock();

            if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                world.scheduleBlockTick(pos, block, TotallyLit.CONFIG.lanterns.burnDuration());
            }
        }
    }

    @ModifyExpressionValue(method = "hasRandomTicks", at = @At(value = "INVOKE", target = "java/util/Optional.isPresent()Z"))
    private boolean modifyLantern(boolean original) {
        return original || TotallyLit.CONFIG.lanterns.extinguishOverTime() || TotallyLit.CONFIG.lanterns.extinguishInRainChance() > 0.0F;
    }
}
