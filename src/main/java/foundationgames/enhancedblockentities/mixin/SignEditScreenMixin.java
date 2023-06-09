/*
 * This file is part of EBE Reforged - https://github.com/CCr4ft3r/EnhancedBlockEntitiesReforged
 * Copyright (C) 2023 FoundationGames and contributors: https://github.com/FoundationGames/EnhancedBlockEntities/tree/1.19/src/main/java/foundationgames/enhancedblockentitiesl
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {
    @Shadow private SignRenderer.SignModel signModel;
    @Shadow @Final private SignBlockEntity sign;

    @Inject(method = "render", at = @At("HEAD"))
    private void enhanced_bes$renderBakedModelSign(PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        boolean enhanceSigns = EnhancedBlockEntities.CONFIG.renderEnhancedSigns;

        var state = this.sign.getBlockState();
        if (!EnhancedBlockEntityRegistry.BLOCKS.contains(state.getBlock())) return;

        this.signModel.root.visible = !enhanceSigns;

        if (enhanceSigns) {
            var self = (SignEditScreen)(Object)this;
            var models = Minecraft.getInstance().getModelManager().getBlockModelShaper();
            var buffers = Minecraft.getInstance().renderBuffers().bufferSource();
            if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
            } else if (state.hasProperty(BlockStateProperties.ROTATION_16)) {
                state = state.setValue(BlockStateProperties.ROTATION_16, 0);
            }

            var signModel = models.getBlockModel(state);

            matrices.pushPose();
            matrices.translate(self.width / 2d, 0.0D, 50.0D);

            matrices.scale(93.75f, -93.75f, 93.75f);
            matrices.translate(-0.5, -1.8125, -1);

            EBEUtil.renderBakedModel(buffers, state, matrices, signModel, 15728880, OverlayTexture.NO_OVERLAY);

            matrices.popPose();
        }
    }
}