package io.github.realguyman.totally_lit.mixin.lantern;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    private void extinguishLanternWhenPlacedInWater(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (world.isClientSide() || !state.hasProperty(BlockStateProperties.WATERLOGGED) || !state.getValue(BlockStateProperties.WATERLOGGED)) {
            return;
        }

        TotallyLit.LANTERN_MAP.forEach((lit, unlit) -> {
            if (state.is(lit) && world.setBlockAndUpdate(pos, unlit.withPropertiesOf(state))) {
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
                TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
            }
        });
    }

    @Inject(method = "playerWillDestroy", at = @At("HEAD"))
    private void clearNextScheduledExtinguishForLantern(Level world, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClientSide() && TotallyLit.LANTERN_MAP.containsKey(state.getBlock())) {
            ((ServerLevel) world).getBlockTicks().clearArea(new BoundingBox(pos));
            TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
        }
    }
}
