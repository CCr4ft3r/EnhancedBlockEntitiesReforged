package foundationgames.enhancedblockentities.mixin;

import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignRenderer.class)
public interface SignBlockEntityRenderAccessor {
    @Invoker("getDarkColor")
    static int enhanced_bes$getColor(SignBlockEntity sign) {
        throw new AssertionError("Mixin has severely broken, seek help");
    }

    @Accessor("OUTLINE_RENDER_DISTANCE")
    static int enhanced_bes$getRenderDistance() {
        throw new AssertionError("Mixin has severely broken, seek help");
    }
}