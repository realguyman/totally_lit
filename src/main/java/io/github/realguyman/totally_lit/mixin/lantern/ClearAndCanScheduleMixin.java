package io.github.realguyman.totally_lit.mixin.lantern;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.block.LitLanternBlock;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class ClearAndCanScheduleMixin {
    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void canSchedule(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        final boolean isLantern = state.isOf(Blocks.LANTERN);
        final boolean isSoulLantern = state.isOf(Blocks.SOUL_LANTERN);
        final boolean isLitLantern = state.getBlock() instanceof LitLanternBlock;

        if (!isLantern || !isSoulLantern || !isLitLantern) {
            return;
        }

        final boolean canExtinguishInRain = TotallyLit.CONFIG.lanterns.extinguishInRainChance() > 0F;
        final boolean canExtinguishOverTime = TotallyLit.CONFIG.lanterns.extinguishOverTime();

        if (canExtinguishOverTime || canExtinguishInRain) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void extinguishWhenPlacedInWater(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        Block unlitBlock = null;

        if (state.isOf(Blocks.LANTERN)) {
            unlitBlock = BlockRegistry.UNLIT_LANTERN;
        } else if (state.isOf(Blocks.SOUL_LANTERN)) {
            unlitBlock = BlockRegistry.UNLIT_SOUL_LANTERN;
        } else if (state.getBlock() instanceof LitLanternBlock litLanternBlock) {
            unlitBlock = litLanternBlock.getUnlitBlock();
        }

        if (!world.isClient() && unlitBlock != null && state.get(LanternBlock.WATERLOGGED) && world.setBlockState(pos, unlitBlock.getDefaultState().with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, true))) {
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.0625F, world.getRandom().nextFloat() * 0.5F + 0.125F);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void clearNextScheduledExtinguish(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient() && (state.isOf(Blocks.LANTERN) || state.getBlock() instanceof LitLanternBlock)) {
            ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
        }
    }
}
