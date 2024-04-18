package io.github.realguyman.totally_lit.mixin.torch;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
        if (!MyModInitializer.TORCH_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean canExtinguishOverTime = MyModInitializer.CONFIG.torches.extinguishOverTime();
        final boolean canExtinguishInRain = MyModInitializer.CONFIG.torches.extinguishInRainChance() > 0F;

        if (canExtinguishOverTime || canExtinguishInRain) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void clearNextScheduledExtinguish(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient() && MyModInitializer.TORCH_MAP.containsKey(state.getBlock())) {
            ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
        }
    }
}
