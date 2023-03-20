package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.block.UnlitLanternBlock;
import io.github.realguyman.totally_lit.block.UnlitTorchBlock;
import io.github.realguyman.totally_lit.block.UnlitWallTorchBlock;
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
public abstract class FlintAndSteelItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Hand hand = context.getHand();
        boolean updated = false;

        if(state.getBlock() instanceof UnlitLanternBlock unlitLanternBlock && Boolean.TRUE.equals(!state.get(LanternBlock.WATERLOGGED))) {
            updated = world.setBlockState(pos, unlitLanternBlock.getLitBlock().getDefaultState().with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)));
        } else if (state.getBlock() instanceof UnlitTorchBlock unlitTorchBlock) {
            updated = world.setBlockState(pos, unlitTorchBlock.getLitBlock().getDefaultState());
        } else if (state.getBlock() instanceof UnlitWallTorchBlock unlitWallTorchBlock) {
            updated = world.setBlockState(pos, unlitWallTorchBlock.getLitBlock().getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
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
