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

import foundationgames.enhancedblockentities.client.resource.ExperimentalResourcePack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public enum ResourceHacks {;
    private static void cropAndPutTexture(ResourceLocation source, ResourceLocation result, ResourceManager manager, ExperimentalResourcePack pack, float u0, float v0, float u1, float v1) throws IOException {
        InputStream image;
        try {
            image = manager.getResource(source).getInputStream();
        } catch (IOException | NoSuchElementException e) {
            return;
        }
        if (image == null) return;

        TextureHacks.cropImage(image, u0, v0, u1, v1).ifPresent(imgBytes -> pack.put(result, imgBytes));
        image.close();
    }

    public static void addChestParticleTexture(String chestName, String chestTexture, ResourceManager manager, ExperimentalResourcePack pack) throws IOException {
        cropAndPutTexture(
                new ResourceLocation("textures/"+chestTexture+".png"), new ResourceLocation("textures/block/"+chestName+"_particle.png"),
                manager, pack,
                42f/64, 33f/64, 58f/64, 49f/64
        );
    }

    public static void addBedParticleTexture(String bedColor, String bedTexture, ResourceManager manager, ExperimentalResourcePack pack) throws IOException {
        cropAndPutTexture(
                new ResourceLocation("textures/"+bedTexture+".png"), new ResourceLocation("textures/block/"+bedColor+"_bed_particle.png"),
                manager, pack,
                18f/64, 6f/64, 34f/64, 22f/64
        );
    }

    public static void addSignParticleTexture(String signType, String signTexture, ResourceManager manager, ExperimentalResourcePack pack) throws IOException {
        cropAndPutTexture(
                new ResourceLocation("textures/"+signTexture+".png"), new ResourceLocation("textures/block/"+signType+"_sign_particle.png"),
                manager, pack,
                0, 3f/32, 16f/64, 19f/32
        );
    }
}