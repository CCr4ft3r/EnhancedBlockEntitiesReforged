package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class ShulkerBoxBlockEntityMixin extends BlockEntity implements ModelStateHolder {
    @Shadow
    public abstract float getProgress(float delta);

    @Unique
    private int enhanced_bes$modelState = 0;

    public ShulkerBoxBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "updateAnimation", at = @At("TAIL"))
    private void enhanced_bes$updateModelState(Level world, BlockPos pos, BlockState state, CallbackInfo ci) {
        int mState = this.getProgress(0) > 0 ? 1 : 0;
        if (mState != enhanced_bes$modelState) setModelState(mState, world, pos);
    }

    @Override
    public int getModelState() {
        return enhanced_bes$modelState;
    }

    @Override
    public void applyModelState(int state) {
        this.enhanced_bes$modelState = state;
    }
}