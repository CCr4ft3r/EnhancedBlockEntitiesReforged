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
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.resource_provider.ResourceProviderHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class ModelIdentifiers {
    public static final Predicate<EBEConfig> CHEST_PREDICATE = c -> c.renderEnhancedChests;
    public static final Predicate<EBEConfig> BELL_PREDICATE = c -> c.renderEnhancedChests;
    public static final Predicate<EBEConfig> SHULKER_BOX_PREDICATE = c -> c.renderEnhancedShulkerBoxes;

    public static final ResourceLocation CHEST_CENTER = of("block/chest_center", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_CENTER_TRUNK = of("block/chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_CENTER_LID = of("block/chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT = of("block/chest_left", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT_TRUNK = of("block/chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT_LID = of("block/chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT = of("block/chest_right", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT_TRUNK = of("block/chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT_LID = of("block/chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation TRAPPED_CHEST_CENTER = of("block/trapped_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_CENTER_TRUNK = of("block/trapped_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_CENTER_LID = of("block/trapped_chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT = of("block/trapped_chest_left", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT_TRUNK = of("block/trapped_chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT_LID = of("block/trapped_chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT = of("block/trapped_chest_right", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT_TRUNK = of("block/trapped_chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT_LID = of("block/trapped_chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation CHRISTMAS_CHEST_CENTER = of("block/christmas_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_CENTER_TRUNK = of("block/christmas_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_CENTER_LID = of("block/christmas_chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT = of("block/christmas_chest_left", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT_TRUNK = of("block/christmas_chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT_LID = of("block/christmas_chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT = of("block/christmas_chest_right", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT_TRUNK = of("block/christmas_chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT_LID = of("block/christmas_chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation ENDER_CHEST_CENTER = of("block/ender_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation ENDER_CHEST_CENTER_TRUNK = of("block/ender_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation ENDER_CHEST_CENTER_LID = of("block/ender_chest_center_lid", CHEST_PREDICATE);

    public static final ResourceLocation BELL_BETWEEN_WALLS = of("block/bell_between_walls", BELL_PREDICATE);
    public static final ResourceLocation BELL_CEILING = of("block/bell_ceiling", BELL_PREDICATE);
    public static final ResourceLocation BELL_FLOOR = of("block/bell_floor", BELL_PREDICATE);
    public static final ResourceLocation BELL_WALL = of("block/bell_wall", BELL_PREDICATE);
    public static final ResourceLocation BELL_BETWEEN_WALLS_WITH_BELL = of("block/bell_between_walls_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_CEILING_WITH_BELL = of("block/bell_ceiling_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_FLOOR_WITH_BELL = of("block/bell_floor_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_WALL_WITH_BELL = of("block/bell_wall_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_BODY = of("block/bell_body", BELL_PREDICATE);

    public static final Map<DyeColor, ResourceLocation> SHULKER_BOXES = new HashMap<>();
    public static final Map<DyeColor, ResourceLocation> SHULKER_BOX_BOTTOMS = new HashMap<>();
    public static final Map<DyeColor, ResourceLocation> SHULKER_BOX_LIDS = new HashMap<>();

    static {
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var id = color != null ? "block/" + color.getName() + "_shulker_box" : "block/shulker_box";
            SHULKER_BOXES.put(color, of(id, SHULKER_BOX_PREDICATE));
            SHULKER_BOX_BOTTOMS.put(color, of(id + "_bottom", SHULKER_BOX_PREDICATE));
            SHULKER_BOX_LIDS.put(color, of(id + "_lid", SHULKER_BOX_PREDICATE));
        }
    }

    public static void init() {
    }

    private static ResourceLocation of(String id, Predicate<EBEConfig> condition) {
        ResourceLocation idf = new ResourceLocation(id);
        ResourceProviderHolder.INSTANCE.registerModelProvider((consumer) -> {
            if (condition.test(EnhancedBlockEntities.CONFIG)) consumer.accept(idf);
        });
        return idf;
    }
}