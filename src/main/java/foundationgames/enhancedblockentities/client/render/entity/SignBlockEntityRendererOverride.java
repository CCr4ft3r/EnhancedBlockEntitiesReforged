package foundationgames.enhancedblockentities.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.SignBlockEntityRenderAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public SignBlockEntityRendererOverride() {}

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (blockEntity instanceof SignBlockEntity be) {
            matrices.pushPose();

            float signAngle;
            matrices.translate(0.5D, 0.5D, 0.5D);
            if (be.getBlockState().getBlock() instanceof SignBlock) {
                signAngle = - ((float)(be.getBlockState().getValue(StandingSignBlock.ROTATION) * 360) / 16);
                matrices.mulPose(Vector3f.YP.rotationDegrees(signAngle));
            } else {
                signAngle = - be.getBlockState().getValue(WallSignBlock.FACING).toYRot();
                matrices.mulPose(Vector3f.YP.rotationDegrees(signAngle));
                matrices.translate(0.0D, -0.3125, -0.4375);
            }
            matrices.translate(0.0, 0.333333, 0.0466666);
            matrices.scale(0.01f, -0.01f, 0.01f);
            var tr = Minecraft.getInstance().font;
            var orderedTexts = be.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (text) -> {
                var list = tr.split(text, 90);
                return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
            });
            int outlineColor = SignBlockEntityRenderAccessor.enhanced_bes$getColor(be);
            int textColor;
            boolean outline;
            int textLight;
            if (be.hasGlowingText()) {
                textColor = be.getColor().getTextColor();
                outline = true;
                textLight = 15728880;
            } else {
                textColor = outlineColor;
                outline = false;
                textLight = light;
            }

            for(int i = 0; i < 4; ++i) {
                var orderedText = orderedTexts[i];
                float t = (float)(-tr.width(orderedText) / 2);
                if (outline) {
                    tr.drawInBatch8xOutline(orderedText, t, (float)((i * 10) - 20), textColor, outlineColor, matrices.last().pose(), vertexConsumers, textLight);
                } else {
                    tr.drawInBatch(orderedText, t, (float)((i * 10) - 20), textColor, false, matrices.last().pose(), vertexConsumers, false, 0, textLight);
                }
            }

            matrices.popPose();
        }
    }
}