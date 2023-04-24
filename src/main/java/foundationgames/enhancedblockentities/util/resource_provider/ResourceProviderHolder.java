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
package foundationgames.enhancedblockentities.util.resource_provider;

import foundationgames.enhancedblockentities.client.model.DynamicModelProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ResourceProviderHolder {

    public final List<Supplier<DynamicModelProvider>> resourceProviders = new ArrayList<>();
    public final List<Consumer<Consumer<ResourceLocation>>> appenders = new ArrayList<>();
    public static final ResourceProviderHolder INSTANCE = new ResourceProviderHolder();

    public void registerResourceProvider(Supplier<DynamicModelProvider> dynamicModelProvider) {
        resourceProviders.add(dynamicModelProvider);
    }

    public void registerModelProvider(Consumer<Consumer<ResourceLocation>> dynamicModelProvider) {
        appenders.add(dynamicModelProvider);
    }
}