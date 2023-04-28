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

import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public abstract class ModelSelector {
    private static final List<ModelSelector> REGISTRY = new ArrayList<>();

    public static final ModelSelector STATE_HOLDER_SELECTOR = new ModelSelector() {
        @Override
        public int getModelIndex(BlockAndTintGetter view, BlockState state, BlockPos pos, Supplier<Random> rand, RenderContext context) {
            if (view.getBlockEntity(pos) instanceof ModelStateHolder stateHolder) {
                return stateHolder.getModelState();
            }
            return 0;
        }
    };

    public static final ModelSelector CHEST = STATE_HOLDER_SELECTOR;

    public static final ModelSelector CHEST_WITH_CHRISTMAS = new ModelSelector() {
        @Override
        public int getModelIndex(BlockAndTintGetter view, BlockState state, BlockPos pos, Supplier<Random> rand, RenderContext context) {
            int os = DateUtil.isChristmas() ? 2 : 0;
            if (view.getBlockEntity(pos) instanceof ModelStateHolder stateHolder) {
                return stateHolder.getModelState() + os;
            }
            return os;
        }
    };

    public static final ModelSelector BELL = STATE_HOLDER_SELECTOR;

    public static final ModelSelector SHULKER_BOX = STATE_HOLDER_SELECTOR;

    public abstract int getModelIndex(BlockAndTintGetter view, BlockState state, BlockPos pos, Supplier<Random> rand, RenderContext context);

    public final int id;

    public ModelSelector() {
        this.id = REGISTRY.size();
        REGISTRY.add(this);
    }

    public static ModelSelector fromId(int id) {
        return REGISTRY.get(id);
    }
}