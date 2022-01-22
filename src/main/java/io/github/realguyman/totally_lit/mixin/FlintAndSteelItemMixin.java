package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {
    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    private void lightUnlitTorchBlock(ItemUsageContext useOnContext, CallbackInfoReturnable<ActionResult> cir) {
        World world = useOnContext.getWorld();
        BlockPos pos = useOnContext.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        PlayerEntity player = useOnContext.getPlayer();
        Hand hand = useOnContext.getHand();
        boolean updated = false;

        if (block.equals(BlockRegistry.UNLIT_TORCH)) {
            updated = world.setBlockState(pos, Blocks.TORCH.getDefaultState());
        } else if (block.equals(BlockRegistry.UNLIT_WALL_TORCH)) {
            updated = world.setBlockState(pos, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
        }

        if (updated) {
            if (player != null) {
                ItemStack itemInHand = player.getStackInHand(hand);

                if (itemInHand != null) {
                    itemInHand.damage(1, player, (playerInScope) -> playerInScope.sendToolBreakStatus(hand));
                }
            }

            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.75F + 0.25F);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
