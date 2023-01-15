package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.access.CampfireBlockEntityAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin implements CampfireBlockEntityAccess {
    private int burningTicks = 0;

    @Override
    public int getBurningTicks() {
        return burningTicks;
    }

    @Override
    public void setBurningTicks(int value) {
        burningTicks = value;
    }

    @Inject(method = "litServerTick", at = @At("RETURN"))
    private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci) {
        if (!TotallyLit.getConfiguration().campfireConfiguration.extinguishOverTime || state.isOf(Blocks.SOUL_CAMPFIRE)) {
            return;
        }

        CampfireBlockEntityAccess campfireAccessed = (CampfireBlockEntityAccess) campfire;
        final int burntFor = campfireAccessed.getBurningTicks() + 1;
        campfireAccessed.setBurningTicks(burntFor);

        if (burntFor > TotallyLit.getConfiguration().campfireConfiguration.burnDuration * 6_000 && world.setBlockState(pos, state.with(CampfireBlock.LIT, false))) {
            campfireAccessed.setBurningTicks(0);
            CampfireBlock.extinguish(null, world, pos, state);
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        } else if (burntFor % 300 == 0) {
            campfire.markDirty();
        }
    }

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("burningTicks")) {
            burningTicks = nbt.getInt("burningTicks");
        }
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("burningTicks", burningTicks);
    }
}
