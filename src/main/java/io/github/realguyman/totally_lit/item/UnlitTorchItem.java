package io.github.realguyman.totally_lit.item;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class UnlitTorchItem extends VerticallyAttachableBlockItem {
    private final Item litItem;

    public UnlitTorchItem(Block standing, Block wall, Settings settings, Direction verticalAttachmentDirection, Item litItem) {
        super(standing, wall, settings, verticalAttachmentDirection);
        this.litItem = litItem;
    }

    public Item getLitItem() {
        return litItem;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if (pos != null && (world.getBlockState(pos).isIn(TagRegistry.TORCH_IGNITER_BLOCKS) || world.getFluidState(pos.offset(context.getSide())).isIn(TagRegistry.TORCH_IGNITER_FLUIDS))) {
            PlayerEntity player = context.getPlayer();

            if (player != null && !player.isSneaking() && player.giveItemStack(new ItemStack(litItem))) {
                player.getStackInHand(context.getHand()).decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(context);
    }
}
