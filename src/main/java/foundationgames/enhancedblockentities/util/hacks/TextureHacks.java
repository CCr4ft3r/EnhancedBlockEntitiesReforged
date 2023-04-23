package foundationgames.enhancedblockentities.util.hacks;

import com.mojang.blaze3d.platform.NativeImage;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public enum TextureHacks {;
    public static Optional<byte[]> cropImage(@Nullable InputStream image, float u0, float v0, float u1, float v1) throws IOException {
        byte[] r = new byte[0];
        if (image != null) {
            try {
                NativeImage src = NativeImage.read(NativeImage.Format.RGBA, image);

                int w = src.getWidth();
                int h = src.getHeight();
                int x = (int)Math.floor(u0 * w);
                int y = (int)Math.floor(v0 * h);
                int sw = (int)Math.floor((u1 - u0) * w);
                int sh = (int)Math.floor((v1 - v0) * h);
                NativeImage prod = new NativeImage(src.format(), sw, sh, false);
                for (int u = 0; u < sw; u++) {
                    for (int v = 0; v < sh; v++) {
                        prod.setPixelRGBA(u, v, src.getPixelRGBA(x + u, y + v));
                    }
                }
                src.close();
                r = prod.asByteArray();
                prod.close();
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
        return Optional.of(r);
    }
}