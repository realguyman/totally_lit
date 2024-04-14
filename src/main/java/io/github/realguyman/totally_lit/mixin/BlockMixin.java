package io.github.realguyman.totally_lit.mixin;

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
public abstract class BlockMixin {
    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void hasRandomTicks(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if ((state.isOf(Blocks.JACK_O_LANTERN) && (TotallyLit.CONFIG.jackOLanterns.extinguishInRainChance() > 0F || TotallyLit.CONFIG.jackOLanterns.extinguishOverTime()))) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient() && state.isOf(Blocks.JACK_O_LANTERN)) {
            ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
        }
    }
}
