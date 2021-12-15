package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {
    @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
    private void lightUnlitTorchBlock(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        Level      level     = useOnContext.getLevel();
        BlockPos   pos       = useOnContext.getClickedPos();
        BlockState state     = level.getBlockState(pos);
        Block      block     = state.getBlock();
        Player     player    = useOnContext.getPlayer();
        ItemStack itemInHand = player.getItemInHand(useOnContext.getHand());

        if (block.equals(BlockRegistry.UNLIT_TORCH)) {
            // Change the block to minecraft:torch (Lit Torch).
            level.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());

            // Play a flint and steel sound.
            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.75F + 0.25F);

            // Damage/break item in player's hand.
            if (itemInHand != null) {
                itemInHand.hurtAndBreak(1, player, (playerInScope) -> {
                    playerInScope.broadcastBreakEvent(useOnContext.getHand());
                });
            }

            // Return a successful interaction.
            cir.setReturnValue(InteractionResult.SUCCESS);

        } else if (block.equals(BlockRegistry.UNLIT_WALL_TORCH)) {
            // Change the block to minecraft:wall_torch (Lit Wall Torch).
            level.setBlockAndUpdate(pos, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, state.getValue(WallTorchBlock.FACING)));

            // Play a flint and steel sound.
            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.75F + 0.25F);

            // Damage/break item in player's hand.
            if (itemInHand != null) {
                itemInHand.hurtAndBreak(1, player, (playerInScope) -> {
                    playerInScope.broadcastBreakEvent(useOnContext.getHand());
                });
            }

            // Return a successful interaction.
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}