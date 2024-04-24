package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ThrownItemEntity {
    public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "extinguishFire", at = @At("HEAD"))
    private void extinguishLitBlocks(BlockPos pos, CallbackInfo ci) {
        final BlockState state = getWorld().getBlockState(pos);

        TotallyLit.TORCH_MAP.forEach((lit, unlit) -> {
            if (!state.isOf(lit)) {
                return;
            }

            if (!getWorld().setBlockState(pos, unlit.getStateWithProperties(state))) {
                TotallyLit.LOGGER.debug("Wasn't able to extinguish torch at {} with splash potion", pos.toShortString());
                return;
            }

            getWorld().playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.125F, random.nextFloat() * 0.5F + 0.125F);
        });
    }
}
