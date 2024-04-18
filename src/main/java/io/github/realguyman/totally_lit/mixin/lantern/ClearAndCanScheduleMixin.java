package io.github.realguyman.totally_lit.mixin.lantern;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
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
        if (!MyModInitializer.LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean canExtinguishInRain = MyModInitializer.CONFIG.lanterns.extinguishInRainChance() > 0F;
        final boolean canExtinguishOverTime = MyModInitializer.CONFIG.lanterns.extinguishOverTime();

        if (canExtinguishOverTime || canExtinguishInRain) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void extinguishWhenPlacedInWater(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (world.isClient() || !state.contains(Properties.WATERLOGGED) || !state.get(Properties.WATERLOGGED)) {
            return;
        }

        MyModInitializer.LANTERN_MAP.forEach((lit, unlit) -> {
            if (state.isOf(lit) && world.setBlockState(pos, unlit.getStateWithProperties(state))) {
                world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            }
        });
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void clearNextScheduledExtinguish(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient() && MyModInitializer.LANTERN_MAP.containsKey(state.getBlock())) {
            ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
        }
    }
}
