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
package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.*;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.BellBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.ChestBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.ShulkerBoxBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.SignBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import foundationgames.enhancedblockentities.util.resource_provider.ResourceProviderHolder;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;

public enum EBESetup {;

    public static void setupRRPChests() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        ResourceUtil.addChestBlockStates("chest", p);
        ResourceUtil.addChestBlockStates("trapped_chest", p);
        ResourceUtil.addChestBlockStates("christmas_chest", p);
        ResourceUtil.addSingleChestOnlyBlockStates("ender_chest", p);

        ResourceUtil.addSingleChestModels("entity/chest/normal", "chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/normal_left", "entity/chest/normal_right", "chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/trapped", "trapped_chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/trapped_left", "entity/chest/trapped_right", "trapped_chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/christmas", "christmas_chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/christmas_left", "entity/chest/christmas_right", "christmas_chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/ender", "ender_chest", p);

        p.addResource(PackType.CLIENT_RESOURCES, new ResourceLocation("models/item/chest.json"), ResourceUtil.CHEST_ITEM_MODEL_RESOURCE.getBytes());
        p.addModel(JModel.model("block/trapped_chest_center"), new ResourceLocation("item/trapped_chest"));
        p.addModel(JModel.model("block/ender_chest_center"), new ResourceLocation("item/ender_chest"));
    }

    public static void setupRRPSigns() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        ResourceUtil.addSignBlockStates("oak_sign", "oak_wall_sign", p);
        ResourceUtil.addSignBlockStates("birch_sign", "birch_wall_sign", p);
        ResourceUtil.addSignBlockStates("spruce_sign", "spruce_wall_sign", p);
        ResourceUtil.addSignBlockStates("jungle_sign", "jungle_wall_sign", p);
        ResourceUtil.addSignBlockStates("acacia_sign", "acacia_wall_sign", p);
        ResourceUtil.addSignBlockStates("dark_oak_sign", "dark_oak_wall_sign", p);
        ResourceUtil.addSignBlockStates("mangrove_sign", "mangrove_wall_sign", p);
        ResourceUtil.addSignBlockStates("crimson_sign", "crimson_wall_sign", p);
        ResourceUtil.addSignBlockStates("warped_sign", "warped_wall_sign", p);

        ResourceUtil.addSignModels("entity/signs/oak", "oak_sign", "oak_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/birch", "birch_sign", "birch_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/spruce", "spruce_sign", "spruce_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/jungle", "jungle_sign", "jungle_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/acacia", "acacia_sign", "acacia_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/dark_oak", "dark_oak_sign", "dark_oak_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/mangrove", "mangrove_sign", "mangrove_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/crimson", "crimson_sign", "crimson_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/warped", "warped_sign", "warped_wall_sign", p);
    }

    public static void setupRRPBells() {
        ResourceUtil.addBellBlockState(ResourceUtil.getPack());
    }

    public static void setupRRPBeds() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        for (DyeColor color : DyeColor.values()) {
            ResourceUtil.addBedBlockState(color, p);
            ResourceUtil.addBedModels(color, p);
        }
    }

    public static void setupRRPShulkerBoxes() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var id = color != null ? color.getName() + "_shulker_box" : "shulker_box";
            ResourceUtil.addShulkerBoxBlockStates(color, p);
            ResourceUtil.addShulkerBoxModels(color, p);
            p.addModel(JModel.model("block/" + id), new ResourceLocation("item/" + id));
        }
    }

    public static void setupResourceProviders() {
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "chest_center"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.CHEST_CENTER,
                    ModelIdentifiers.CHEST_CENTER_TRUNK,
                    ModelIdentifiers.CHRISTMAS_CHEST_CENTER,
                    ModelIdentifiers.CHRISTMAS_CHEST_CENTER_TRUNK
                },
                ModelSelector.CHEST_WITH_CHRISTMAS,
                DynamicModelEffects.CHEST
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "chest_left"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.CHEST_LEFT,
                    ModelIdentifiers.CHEST_LEFT_TRUNK,
                    ModelIdentifiers.CHRISTMAS_CHEST_LEFT,
                    ModelIdentifiers.CHRISTMAS_CHEST_LEFT_TRUNK
                },
                ModelSelector.CHEST_WITH_CHRISTMAS,
                DynamicModelEffects.CHEST
            )
        ));

        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "chest_right"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.CHEST_RIGHT,
                    ModelIdentifiers.CHEST_RIGHT_TRUNK,
                    ModelIdentifiers.CHRISTMAS_CHEST_RIGHT,
                    ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_TRUNK
                },
                ModelSelector.CHEST_WITH_CHRISTMAS,
                DynamicModelEffects.CHEST
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "trapped_chest_center"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.TRAPPED_CHEST_CENTER,
                    ModelIdentifiers.TRAPPED_CHEST_CENTER_TRUNK
                },
                ModelSelector.CHEST,
                DynamicModelEffects.CHEST
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "trapped_chest_left"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.TRAPPED_CHEST_LEFT,
                    ModelIdentifiers.TRAPPED_CHEST_LEFT_TRUNK
                },
                ModelSelector.CHEST,
                DynamicModelEffects.CHEST
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "trapped_chest_right"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.TRAPPED_CHEST_RIGHT,
                    ModelIdentifiers.TRAPPED_CHEST_RIGHT_TRUNK
                },
                ModelSelector.CHEST,
                DynamicModelEffects.CHEST
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "ender_chest_center"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.ENDER_CHEST_CENTER,
                    ModelIdentifiers.ENDER_CHEST_CENTER_TRUNK
                },
                ModelSelector.CHEST,
                DynamicModelEffects.CHEST
            )
        ));

        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "bell_between_walls"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.BELL_BETWEEN_WALLS_WITH_BELL,
                    ModelIdentifiers.BELL_BETWEEN_WALLS
                },
                ModelSelector.BELL,
                DynamicModelEffects.BELL
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "bell_ceiling"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.BELL_CEILING_WITH_BELL,
                    ModelIdentifiers.BELL_CEILING
                },
                ModelSelector.BELL,
                DynamicModelEffects.BELL
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "bell_floor"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.BELL_FLOOR_WITH_BELL,
                    ModelIdentifiers.BELL_FLOOR
                },
                ModelSelector.BELL,
                DynamicModelEffects.BELL
            )
        ));
        ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
            new ResourceLocation("builtin", "bell_wall"),
            () -> new DynamicUnbakedModel(
                new ResourceLocation[]{
                    ModelIdentifiers.BELL_WALL_WITH_BELL,
                    ModelIdentifiers.BELL_WALL
                },
                ModelSelector.BELL,
                DynamicModelEffects.BELL
            )
        ));
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            ResourceProviderHolder.INSTANCE.registerResourceProvider(() -> new DynamicModelProvider(
                new ResourceLocation("builtin", color != null ? color.getName() + "_shulker_box" : "shulker_box"),
                () -> new DynamicUnbakedModel(
                    new ResourceLocation[]{
                        ModelIdentifiers.SHULKER_BOXES.get(color),
                        ModelIdentifiers.SHULKER_BOX_BOTTOMS.get(color)
                    },
                    ModelSelector.SHULKER_BOX,
                    DynamicModelEffects.CHEST
                )
            ));
        }
    }

    public static void setupChests() {
        ItemBlockRenderTypes.setRenderLayer(Blocks.CHEST, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(Blocks.TRAPPED_CHEST, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(Blocks.ENDER_CHEST, RenderType.cutoutMipped());
        EnhancedBlockEntityRegistry.register(Blocks.CHEST, BlockEntityType.CHEST, BlockEntityRenderCondition.CHEST,
            new ChestBlockEntityRendererOverride(() -> {
                ModelManager manager = Minecraft.getInstance().getModelManager();
                return new BakedModel[]{
                    manager.getModel(ModelIdentifiers.CHEST_CENTER_LID),
                    manager.getModel(ModelIdentifiers.CHEST_LEFT_LID),
                    manager.getModel(ModelIdentifiers.CHEST_RIGHT_LID),
                    manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_CENTER_LID),
                    manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_LEFT_LID),
                    manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_LID)
                };
            }, entity -> {
                int os = DateUtil.isChristmas() ? 3 : 0;
                ChestType type = entity.getBlockState().getValue(BlockStateProperties.CHEST_TYPE);
                return type == ChestType.RIGHT ? 2 + os : type == ChestType.LEFT ? 1 + os : os;
            })
        );
        EnhancedBlockEntityRegistry.register(Blocks.TRAPPED_CHEST, BlockEntityType.TRAPPED_CHEST, BlockEntityRenderCondition.CHEST,
            new ChestBlockEntityRendererOverride(() -> {
                BakedModelManagerAccess manager = (BakedModelManagerAccess) Minecraft.getInstance().getModelManager();
                return new BakedModel[]{
                    manager.getModel(ModelIdentifiers.TRAPPED_CHEST_CENTER_LID),
                    manager.getModel(ModelIdentifiers.TRAPPED_CHEST_LEFT_LID),
                    manager.getModel(ModelIdentifiers.TRAPPED_CHEST_RIGHT_LID)
                };
            }, entity -> {
                ChestType type = entity.getBlockState().getValue(BlockStateProperties.CHEST_TYPE);
                return type == ChestType.RIGHT ? 2 : type == ChestType.LEFT ? 1 : 0;
            })
        );
        EnhancedBlockEntityRegistry.register(Blocks.ENDER_CHEST, BlockEntityType.ENDER_CHEST, BlockEntityRenderCondition.CHEST,
            new ChestBlockEntityRendererOverride(() -> {
                BakedModelManagerAccess manager = (BakedModelManagerAccess) Minecraft.getInstance().getModelManager();
                return new BakedModel[]{manager.getModel(ModelIdentifiers.ENDER_CHEST_CENTER_LID)};
            }, entity -> 0)
        );
    }

    public static void setupSigns() {
        EnhancedBlockEntityRegistry.register(Blocks.OAK_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.OAK_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.BIRCH_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.BIRCH_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.SPRUCE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.SPRUCE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.JUNGLE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.JUNGLE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.ACACIA_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.ACACIA_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.DARK_OAK_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.DARK_OAK_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.MANGROVE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.MANGROVE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.CRIMSON_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.CRIMSON_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.WARPED_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.WARPED_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
            new SignBlockEntityRendererOverride()
        );
    }

    public static void setupBells() {
        EnhancedBlockEntityRegistry.register(Blocks.BELL, BlockEntityType.BELL, BlockEntityRenderCondition.BELL,
            new BellBlockEntityRendererOverride()
        );
    }

    public static void setupBeds() {
        EnhancedBlockEntityRegistry.register(Blocks.BLACK_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.BLUE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.BROWN_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.CYAN_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.GRAY_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.GREEN_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.LIGHT_BLUE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.LIGHT_GRAY_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.LIME_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.MAGENTA_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.ORANGE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.PINK_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.PURPLE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.RED_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.WHITE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.YELLOW_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
    }

    public static void setupShulkerBoxes() {
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var block = ShulkerBoxBlock.getBlockByColor(color);
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped());
            EnhancedBlockEntityRegistry.register(block, BlockEntityType.SHULKER_BOX, BlockEntityRenderCondition.SHULKER_BOX,
                new ShulkerBoxBlockEntityRendererOverride((map) -> {
                    var models = (BakedModelManagerAccess) Minecraft.getInstance().getModelManager();
                    for (DyeColor dc : EBEUtil.DEFAULTED_DYE_COLORS) {
                        map.put(dc, models.getModel(ModelIdentifiers.SHULKER_BOX_LIDS.get(dc)));
                    }
                })
            );
        }
    }
}