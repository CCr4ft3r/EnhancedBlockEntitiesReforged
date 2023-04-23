package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BuiltinModelItemRendererMixin {
    @Inject(method = "renderByItem", at = @At("HEAD"), cancellable = true)
    private void enhanced_bes$renderBeds(ItemStack stack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if (EnhancedBlockEntities.CONFIG.renderEnhancedBeds &&
                stack.getItem() instanceof BlockItem item && item.getBlock() instanceof BedBlock bed) {
            var models = Minecraft.getInstance().getModelManager().getBlockModelShaper();

            var bedState = bed.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
            var footState = bedState.setValue(BlockStateProperties.BED_PART, BedPart.FOOT);
            var footModel = models.getBlockModel(footState);
            var headState = bedState.setValue(BlockStateProperties.BED_PART, BedPart.HEAD);
            var headModel = models.getBlockModel(headState);

            matrices.pushPose();
            EBEUtil.renderBakedModel(vertexConsumers, headState, matrices, headModel, light, overlay);
            matrices.translate(0, 0, -1);
            EBEUtil.renderBakedModel(vertexConsumers, footState, matrices, footModel, light, overlay);
            matrices.popPose();

            ci.cancel();
        }
    }
}