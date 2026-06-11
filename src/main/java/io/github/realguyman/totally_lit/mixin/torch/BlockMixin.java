package io.github.realguyman.totally_lit.mixin.torch;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "playerWillDestroy", at = @At("HEAD"))
    private void clearNextScheduledExtinguish(Level world, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClientSide() && TotallyLit.TORCH_MAP.containsKey(state.getBlock())) {
            ((ServerLevel) world).getBlockTicks().clearArea(new BoundingBox(pos));
            TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
        }
    }
}
