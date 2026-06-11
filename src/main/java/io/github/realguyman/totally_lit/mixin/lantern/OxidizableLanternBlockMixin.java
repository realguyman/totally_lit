package io.github.realguyman.totally_lit.mixin.lantern;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.WeatheringLanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.LevelTicks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatheringLanternBlock.class)
public class OxidizableLanternBlockMixin extends LanternBlock {
    public OxidizableLanternBlockMixin(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void scheduleCopperLantern(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!TotallyLit.LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean isRaining = world.isRainingAt(pos.above());
        final boolean isChanceInFavor = random.nextFloat() < TotallyLit.CONFIG.lanterns.extinguishInRainChance();
        final boolean canExtinguishOverTime = TotallyLit.CONFIG.lanterns.extinguishOverTime();

        if ((isRaining && isChanceInFavor) || state.getValue(LanternBlock.WATERLOGGED)) {
            tick(state, world, pos, random);
        } else if (canExtinguishOverTime && !state.is(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS)) {
            LevelTicks<Block> scheduler = world.getBlockTicks();
            Block block = state.getBlock();

            if (!scheduler.hasScheduledTick(pos, block) && !scheduler.willTickThisTick(pos, block)) {
                world.scheduleTick(pos, block, TotallyLit.CONFIG.lanterns.burnDuration());
            }
        }
    }

    @ModifyExpressionValue(method = "isRandomlyTicking", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"))
    private boolean canScheduleCopperLantern(boolean original) {
        return original || TotallyLit.CONFIG.lanterns.extinguishOverTime() || TotallyLit.CONFIG.lanterns.extinguishInRainChance() > 0.0F;
    }
}
