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
public abstract class CampfireBlockEntityMixin implements CampfireBlockEntityAccess {
    private int ticksBurntFor = 0;

    @Override
    public int getTicksBurntFor() {
        return ticksBurntFor;
    }

    @Override
    public void setTicksBurntFor(int value) {
        ticksBurntFor = value;
    }

    @Inject(method = "litServerTick", at = @At("RETURN"))
    private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci) {
        if (!TotallyLit.CONFIG.campfires.extinguishOverTime() || state.isOf(Blocks.SOUL_CAMPFIRE)) {
            return;
        }

        CampfireBlockEntityAccess campfireAccessed = (CampfireBlockEntityAccess) campfire;
        final int burntFor = campfireAccessed.getTicksBurntFor() + 1;
        campfireAccessed.setTicksBurntFor(burntFor);

        if (burntFor > TotallyLit.CONFIG.campfires.burnDuration() && world.setBlockState(pos, state.with(CampfireBlock.LIT, false))) {
            campfireAccessed.setTicksBurntFor(0);
            CampfireBlock.extinguish(null, world, pos, state);
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        } else if (burntFor % 300 == 0) {
            campfire.markDirty();
        }
    }

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("ticksBurntFor")) {
            ticksBurntFor = nbt.getInt("ticksBurntFor");
        }
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("ticksBurntFor", ticksBurntFor);
    }
}
