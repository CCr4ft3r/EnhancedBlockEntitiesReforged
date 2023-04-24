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
package foundationgames.enhancedblockentities.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ShulkerBoxBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private final Map<DyeColor, BakedModel> models = new HashMap<>();
    private final Consumer<Map<DyeColor, BakedModel>> modelMapFiller;

    public ShulkerBoxBlockEntityRendererOverride(Consumer<Map<DyeColor, BakedModel>> modelMapFiller) {
        this.modelMapFiller = modelMapFiller;
    }

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (models.isEmpty()) modelMapFiller.accept(models);
        if (blockEntity instanceof ShulkerBoxBlockEntity entity) {
            Direction dir = Direction.UP;
            BlockState state = entity.getLevel().getBlockState(entity.getBlockPos());
            if (state.getBlock() instanceof ShulkerBoxBlock) {
                dir = state.getValue(ShulkerBoxBlock.FACING);
            }
            matrices.pushPose();

            float animation = entity.getProgress(tickDelta);

            matrices.translate(0.5, 0.5, 0.5);
            matrices.mulPose(dir.getRotation());
            matrices.mulPose(Vector3f.YP.rotationDegrees(270 * animation));
            matrices.translate(-0.5, -0.5, -0.5);

            matrices.translate(0, animation * 0.5f, 0);

            var lidModel = models.get(entity.getColor());
            if (lidModel != null) {
                EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices, lidModel, light, overlay);
            }

            matrices.popPose();
        }
    }

    @Override
    public void onModelsReload() {
        this.models.clear();
    }
}