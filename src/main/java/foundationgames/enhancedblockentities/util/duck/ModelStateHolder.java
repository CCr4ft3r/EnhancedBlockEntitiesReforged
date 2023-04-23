package foundationgames.enhancedblockentities.util.duck;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ModelStateHolder {
    int getModelState();

    void applyModelState(int state);

    default void setModelState(int state, Level world, BlockPos pos) {
        this.applyModelState(state);
        var bState = world.getBlockState(pos);
        try {
            WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD = true;
            Minecraft.getInstance().levelRenderer.blockChanged(world, pos, bState, bState, 8);
        } catch (NullPointerException ignored) {
            EnhancedBlockEntities.LOG.warn("Error rebuilding chunk at block pos "+pos);
        }
    }
}