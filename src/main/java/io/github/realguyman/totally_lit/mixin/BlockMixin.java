package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.block.LitLanternBlock;
import io.github.realguyman.totally_lit.block.LitTorchBlock;
import io.github.realguyman.totally_lit.block.LitWallTorchBlock;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
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
public abstract class BlockMixin {
    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void hasRandomTicks(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if ((CampfireBlock.isLitCampfire(state) && (TotallyLit.CONFIG.campfires.extinguishInRainChance() > 0F)) || (AbstractCandleBlock.isLitCandle(state) && (TotallyLit.CONFIG.candles.extinguishInRainChance() > 0F || TotallyLit.CONFIG.candles.extinguishOverTime())) || (state.isOf(Blocks.JACK_O_LANTERN) && (TotallyLit.CONFIG.jackOLanterns.extinguishInRainChance() > 0F || TotallyLit.CONFIG.jackOLanterns.extinguishOverTime())) || ((state.isOf(Blocks.LANTERN) || state.isOf(Blocks.SOUL_LANTERN) || state.getBlock() instanceof LitLanternBlock) && (TotallyLit.CONFIG.lanterns.extinguishInRainChance() > 0F || TotallyLit.CONFIG.lanterns.extinguishOverTime())) || ((state.isOf(Blocks.TORCH) || state.isOf(Blocks.WALL_TORCH) || state.isOf(Blocks.SOUL_TORCH) || state.isOf(Blocks.SOUL_WALL_TORCH) || state.getBlock() instanceof LitTorchBlock || state.getBlock() instanceof LitWallTorchBlock) && (TotallyLit.CONFIG.torches.extinguishInRainChance() > 0F || TotallyLit.CONFIG.torches.extinguishOverTime()))) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        Block unlitBlock = null;

        if (state.isOf(Blocks.LANTERN)) {
            unlitBlock = BlockRegistry.UNLIT_LANTERN;
        } else if (state.isOf(Blocks.SOUL_LANTERN)) {
            unlitBlock = BlockRegistry.UNLIT_SOUL_LANTERN;
        } else if (state.getBlock() instanceof LitLanternBlock litLanternBlock) {
            unlitBlock = litLanternBlock.getUnlitBlock();
        }

        if (Boolean.TRUE.equals(!world.isClient() && unlitBlock != null && state.get(LanternBlock.WATERLOGGED)) && world.setBlockState(pos, unlitBlock.getDefaultState().with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, true))) {
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.0625F, world.getRandom().nextFloat() * 0.5F + 0.125F);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient() && (state.isIn(BlockTags.CANDLES) || state.isIn(BlockTags.CANDLE_CAKES) || state.isOf(Blocks.JACK_O_LANTERN) || state.isOf(Blocks.LANTERN) || state.isOf(Blocks.TORCH) || state.isOf(Blocks.WALL_TORCH) || state.getBlock() instanceof LitLanternBlock || state.getBlock() instanceof LitTorchBlock || state.getBlock() instanceof LitWallTorchBlock)) {
            ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
        }
    }
}
