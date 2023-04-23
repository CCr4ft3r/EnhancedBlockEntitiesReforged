package foundationgames.enhancedblockentities.client.render;

import net.minecraft.client.renderer.GameRenderer;

public class SignRenderManager {
    private static int lastRenderedSigns = 0;
    public static int renderedSigns = 0;

    public static int getRenderedSignAmount() {
        return lastRenderedSigns;
    }

    public static void endFrame(GameRenderer ctx) {
        lastRenderedSigns = renderedSigns;
        renderedSigns = 0;
    }
}