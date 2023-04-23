package foundationgames.enhancedblockentities.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Arrays;

public enum EBEUtil {
    ;
    private static final RandomSource dummy = RandomSource.create();

    private static final Direction[] ALL_FACES_AND_NULL = Arrays.copyOf(Direction.values(), Direction.values().length + 1);

    // Contains all dye colors, and null
    public static final DyeColor[] DEFAULTED_DYE_COLORS;

    static {
        var dColors = DyeColor.values();
        DEFAULTED_DYE_COLORS = new DyeColor[dColors.length + 1];
        System.arraycopy(dColors, 0, DEFAULTED_DYE_COLORS, 0, dColors.length);
    }

    public static void renderBakedModel(MultiBufferSource vertexConsumers, BlockState state, PoseStack matrices, BakedModel model, int light, int overlay) {
        VertexConsumer vertices = vertexConsumers.getBuffer(ItemBlockRenderTypes.getRenderType(state, false));
        for (int i = 0; i <= 6; i++) {
            for (BakedQuad q : model.getQuads(null, ALL_FACES_AND_NULL[i], dummy)) {
                vertices.putBulkData(matrices.last(), q, 1, 1, 1, light, overlay);
            }
        }
    }

    public static boolean isVanillaResourcePack(PackResources pack) {
        return (pack instanceof VanillaPackResources) ||
            // Terrible quilt compat hack
            ("org.quiltmc.qsl.resource.loader.api.GroupResourcePack$Wrapped".equals(pack.getClass().getName()));
    }

    public static final String DUMP_FOLDER_NAME = "enhanced_bes_dump";
}