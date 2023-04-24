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