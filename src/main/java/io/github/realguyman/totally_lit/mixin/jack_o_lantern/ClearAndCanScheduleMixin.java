package io.github.realguyman.totally_lit.mixin.jack_o_lantern;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class ClearAndCanScheduleMixin {
    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void canSchedule(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!TotallyLit.JACK_O_LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean canExtinguishOverTime = TotallyLit.CONFIG.jackOLanterns.extinguishOverTime();
        final boolean canExtinguishInRain = TotallyLit.CONFIG.jackOLanterns.extinguishInRainChance() > 0F;

        if (canExtinguishOverTime || canExtinguishInRain) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void clearNextScheduledExtinguish(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient() && TotallyLit.JACK_O_LANTERN_MAP.containsKey(state.getBlock())) {
            ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
        }
    }
}
