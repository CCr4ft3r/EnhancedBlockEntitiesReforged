package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderContext;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBlockRenderer.class)
public abstract class BlockModelRendererMixin {
    @Unique
    private final ThreadLocal<BlockRenderContext> fabric_contexts = ThreadLocal.withInitial(BlockRenderContext::new);

    @Inject(method = "tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;JILnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;Z)V"
        , at = @At("HEAD"), remap = false, cancellable = true)
    private void hookRender(BlockAndTintGetter blockView, BakedModel model, BlockState state, BlockPos pos, PoseStack p_111052_, VertexConsumer p_111053_, boolean p_111054_, RandomSource rand, long p_111056_, int p_111057_, ModelData modelData, RenderType renderType, boolean queryModelSpecificData, CallbackInfo ci) {
        if (!((FabricBakedModel) model).isVanillaAdapter()) {
            BlockRenderContext context = fabric_contexts.get();
            // Note that we do not support face-culling here (so checkSides is ignored)
            context.render(blockView, model, state, pos, p_111052_, p_111053_, p_111054_, rand, p_111056_, p_111057_, modelData, renderType, queryModelSpecificData);
            ci.cancel();
        }
    }
}