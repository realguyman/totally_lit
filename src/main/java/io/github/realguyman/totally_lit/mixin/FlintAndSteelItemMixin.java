package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.api.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
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
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void lightUnlitTorchBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Hand hand = context.getHand();
        boolean updated = false;

        if(state.getBlock() instanceof UnlitLanternBlock && Boolean.TRUE.equals(!state.get(LanternBlock.WATERLOGGED))) {
            updated = world.setBlockState(pos, ((UnlitLanternBlock) state.getBlock()).getLitBlock().getDefaultState().with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)));
        } else if (state.getBlock() instanceof UnlitTorchBlock) {
            updated = world.setBlockState(pos, ((UnlitTorchBlock) state.getBlock()).getLitBlock().getDefaultState());
        } else if (state.getBlock() instanceof UnlitWallTorchBlock) {
            updated = world.setBlockState(pos, ((UnlitWallTorchBlock) state.getBlock()).getLitBlock().getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
        }

        if (updated) {
            PlayerEntity player = context.getPlayer();

            if (player != null) {
                ItemStack itemInHand = player.getStackInHand(hand);

                if (itemInHand != null) {
                    itemInHand.damage(1, player, playerInScope -> playerInScope.sendToolBreakStatus(hand));
                    world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}
