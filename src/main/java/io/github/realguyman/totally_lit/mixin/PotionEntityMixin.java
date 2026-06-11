package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractThrownPotion.class)
public abstract class PotionEntityMixin extends ThrowableItemProjectile {
    public PotionEntityMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "dowseFire", at = @At("HEAD"))
    private void extinguishLitBlocks(BlockPos pos, CallbackInfo ci) {
        final BlockState state = level().getBlockState(pos);

        TotallyLit.TORCH_MAP.forEach((lit, unlit) -> {
            if (!state.is(lit)) {
                return;
            }

            if (!level().setBlockAndUpdate(pos, unlit.withPropertiesOf(state))) {
                TotallyLit.LOGGER.warn("Wasn't able to extinguish torch at {} with splash potion", pos.toShortString());
                return;
            }

            level().playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.125F, random.nextFloat() * 0.5F + 0.125F);
        });
    }
}
