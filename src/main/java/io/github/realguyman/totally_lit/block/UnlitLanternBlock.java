package io.github.realguyman.totally_lit.block;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnlitLanternBlock extends LanternBlock {
    private final Block litBlock;

    public UnlitLanternBlock(Settings settings, Block litBlock) {
        super(settings);
        this.litBlock = litBlock;
    }

    public Block getLitBlock() {
        return litBlock;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        final ItemStack itemInHand = player.getStackInHand(hand);

        if (itemInHand.isIn(TagRegistry.LANTERN_IGNITER_ITEMS) && world.setBlockState(pos, litBlock.getDefaultState())) {
            if (itemInHand.isDamageable()) {
                itemInHand.damage(1, player, playerInScope ->
                        playerInScope.sendToolBreakStatus(hand)
                );
            }

            world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, result);
    }
}
