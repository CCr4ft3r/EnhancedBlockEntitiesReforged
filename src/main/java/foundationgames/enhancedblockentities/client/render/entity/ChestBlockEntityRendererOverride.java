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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.function.Function;
import java.util.function.Supplier;

public class ChestBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private BakedModel[] models = null;
    private final Supplier<BakedModel[]> modelGetter;
    private final Function<BlockEntity, Integer> modelSelector;

    public ChestBlockEntityRendererOverride(Supplier<BakedModel[]> modelGetter, Function<BlockEntity, Integer> modelSelector) {
        this.modelGetter = modelGetter;
        this.modelSelector = modelSelector;
    }

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (models == null) models = modelGetter.get();
        if (blockEntity instanceof LidBlockEntity) {
            matrices.pushPose();

            LidBlockEntity chest = getAnimationProgress(blockEntity, tickDelta);
            matrices.translate(0.5f, 0, 0.5f);
            Direction dir = blockEntity.getBlockState().getValue(ChestBlock.FACING);
            matrices.mulPose(Vector3f.YP.rotationDegrees(180 - dir.toYRot()));
            matrices.translate(-0.5f, 0, -0.5f);
            float yPiv = 9f / 16;
            float zPiv = 15f / 16;
            matrices.translate(0, yPiv, zPiv);
            float rot = chest.getOpenNess(tickDelta);
            rot = 1f - rot;
            rot = 1f - (rot * rot * rot);
            matrices.mulPose(Vector3f.XP.rotationDegrees(rot * 90));
            matrices.translate(0, -yPiv, -zPiv);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices, models[modelSelector.apply(blockEntity)], light, overlay);

            matrices.popPose();
        }
    }

    public static LidBlockEntity getAnimationProgress(BlockEntity blockEntity, float tickDelta) {
        LidBlockEntity chest = (LidBlockEntity) blockEntity;

        BlockState state = blockEntity.getBlockState();
        if (state.hasProperty(ChestBlock.TYPE) && state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
            BlockEntity neighbor = null;
            BlockPos pos = blockEntity.getBlockPos();
            Direction facing = state.getValue(ChestBlock.FACING);
            switch (state.getValue(ChestBlock.TYPE)) {
                case LEFT -> neighbor = blockEntity.getLevel().getBlockEntity(pos.relative(facing.getClockWise()));
                case RIGHT ->
                    neighbor = blockEntity.getLevel().getBlockEntity(pos.relative(facing.getCounterClockWise()));
            }
            if (neighbor instanceof LidBlockEntity) {
                float nAnim = ((LidBlockEntity) neighbor).getOpenNess(tickDelta);
                if (nAnim > chest.getOpenNess(tickDelta)) {
                    chest = ((LidBlockEntity) neighbor);
                }
            }
        }

        return chest;
    }

    @Override
    public void onModelsReload() {
        this.models = null;
    }
}