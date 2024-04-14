package io.github.realguyman.totally_lit.mixin.torch;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.block.LitTorchBlock;
import io.github.realguyman.totally_lit.block.LitWallTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
        final boolean isTorch = state.isOf(Blocks.TORCH);
        final boolean isWallTorch = state.isOf(Blocks.WALL_TORCH);
        final boolean isSoulTorch = state.isOf(Blocks.SOUL_TORCH);
        final boolean isSoulWallTorch = state.isOf(Blocks.SOUL_WALL_TORCH);
        final boolean isLitTorch = state.getBlock() instanceof LitTorchBlock;
        final boolean isLitWallTorch = state.getBlock() instanceof LitWallTorchBlock;

        if (!isTorch || !isWallTorch || !isSoulTorch || !isSoulWallTorch || !isLitTorch || !isLitWallTorch) {
            return;
        }

        final boolean canExtinguishOverTime = TotallyLit.CONFIG.torches.extinguishOverTime();
        final boolean canExtinguishInRain = TotallyLit.CONFIG.torches.extinguishInRainChance() > 0F;

        if (canExtinguishOverTime || canExtinguishInRain) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void clearNextScheduledExtinguish(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        final boolean isTorch = state.isOf(Blocks.TORCH);
        final boolean isWallTorch = state.isOf(Blocks.WALL_TORCH);
        final boolean isSoulTorch = state.isOf(Blocks.SOUL_TORCH);
        final boolean isSoulWallTorch = state.isOf(Blocks.SOUL_WALL_TORCH);
        final boolean isLitTorch = state.getBlock() instanceof LitTorchBlock;
        final boolean isLitWallTorch = state.getBlock() instanceof LitWallTorchBlock;

        if (world.isClient() || !isTorch || !isWallTorch || !isSoulTorch || !isSoulWallTorch || !isLitTorch || !isLitWallTorch) {
            return;
        }

        ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
    }
}
