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
package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicModelEffects {
    private static final List<DynamicModelEffects> REGISTRY = new ArrayList<>();

    public static final DynamicModelEffects DEFAULT = new DynamicModelEffects() {};

    public static final DynamicModelEffects CHEST = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.chestAO;
        }
    };

    public static final DynamicModelEffects BELL = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.bellAO;
        }
    };

    public static final DynamicModelEffects SHULKER_BOX = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.chestAO;
        }
    };

    public final int id;

    public DynamicModelEffects() {
        this.id = REGISTRY.size();
        REGISTRY.add(this);
    }

    public boolean ambientOcclusion() {
        return true;
    }

    public static DynamicModelEffects fromId(int id) {
        return REGISTRY.get(id);
    }
}