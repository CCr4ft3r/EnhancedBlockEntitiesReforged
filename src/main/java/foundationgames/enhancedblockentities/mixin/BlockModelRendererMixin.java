/*
 * This file is part of EBE Reforged - https://github.com/CCr4ft3r/EnhancedBlockEntitiesReforged
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderContext;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ModelBlockRenderer.class)
public abstract class BlockModelRendererMixin {
    @Unique
    private final ThreadLocal<BlockRenderContext> fabric_contexts = ThreadLocal.withInitial(BlockRenderContext::new);

    @Inject(method = "tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLjava/util/Random;JILnet/minecraftforge/client/model/data/IModelData;)Z"
        , at = @At("HEAD"), remap = false, cancellable = true)
    private void hookRender(BlockAndTintGetter blockView, BakedModel model, BlockState state, BlockPos pos, PoseStack p_111052_, VertexConsumer p_111053_, boolean p_111054_, Random rand, long p_111056_, int p_111057_, IModelData modelData, CallbackInfoReturnable<Boolean> cir) {
        if (!((FabricBakedModel) model).isVanillaAdapter()) {
            BlockRenderContext context = fabric_contexts.get();
            // Note that we do not support face-culling here (so checkSides is ignored)
            context.render(blockView, model, state, pos, p_111052_, p_111053_, p_111054_, rand, p_111056_, p_111057_, modelData);
            cir.setReturnValue(true);
        }
    }
}