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
package foundationgames.enhancedblockentities.client.resource;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;

public class ExperimentalResourcePack implements PackResources {
    private static final String PACK_DESC = "Experimental Resource Pack for Enhanced Block Entities";

    public final String PACK_META = String.format("{\"pack\":{\"pack_format\":%s,\"description\":\"%s\"}}", PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion()), PACK_DESC);

    private final Map<ResourceLocation, byte[]> resources = new Object2ObjectOpenHashMap<>();
    private final Set<String> namespaces = new HashSet<>();

    public void put(ResourceLocation id, byte[] resource) {
        this.resources.put(id, resource);
        namespaces.add(id.getNamespace());
    }

    @Override
    public InputStream getRootResource(String fileName) throws IOException {
        if ("pack.mcmeta".equals(fileName)) {
            return IOUtils.toInputStream(PACK_META, Charsets.UTF_8);
        }
        throw new FileNotFoundException("No such file '" + fileName + "' in EBE experimental resources");
    }

    @Override
    public InputStream getResource(PackType type, ResourceLocation id) throws IOException {
        if (type == PackType.CLIENT_RESOURCES) {
            if (resources.containsKey(id)) {
                return new ByteArrayInputStream(resources.get(id));
            }
        }
        throw new FileNotFoundException("No such resource '" + id.toString() + "' of type '" + type.toString() + "' in EBE experimental resources");
    }

    @Override
    public Collection<ResourceLocation> getResources(PackType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
        if (type == PackType.CLIENT_RESOURCES) {
            ImmutableList.Builder<ResourceLocation> r = ImmutableList.builder();
            resources.keySet().forEach(id -> {
                if (
                    id.getNamespace().equals(namespace) &&
                        id.getPath().startsWith(prefix) &&
                        pathFilter.test(id.getPath()) &&
                        (id.getPath().split("[/\\\\]").length <= maxDepth)
                ) {
                    r.add(id);
                }
            });
        }
        return Collections.emptyList();
    }

    @Override
    public boolean hasResource(PackType type, ResourceLocation id) {
        if (type == PackType.CLIENT_RESOURCES) {
            return resources.containsKey(id);
        }
        return false;
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return this.namespaces;
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> metaReader) throws IOException {
        return AbstractPackResources.getMetadataFromStream(metaReader, getRootResource("pack.mcmeta"));
    }

    @Override
    public String getName() {
        return "ebe:exp_resources";
    }

    @Override
    public void close() {
    }
}