package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EnhancedBlockEntities.ID)
public final class EnhancedBlockEntities {
    public static final String ID = "enhancedblockentities";
    public static final Logger LOG = LogManager.getLogger("Enhanced Block Entities");
    public static final EBEConfig CONFIG = new EBEConfig();

    public EnhancedBlockEntities() {
        ModelIdentifiers.init();
        EBESetup.setupResourceProviders();
        ItemProperties.register(Items.CHEST, new ResourceLocation("is_christmas"), (stack, world, entity, seed) -> DateUtil.isChristmas() ? 1 : 0);

        load();
    }

    public static void reload(ReloadType type) {
        load();
        if (type == ReloadType.WORLD) {
            Minecraft.getInstance().levelRenderer.allChanged();
        } else if (type == ReloadType.RESOURCES) {
            Minecraft.getInstance().reloadResourcePacks();
        }
    }

    public static void load() {
        CONFIG.load();

        EnhancedBlockEntityRegistry.clear();
        ResourceUtil.resetPack();

        if (CONFIG.renderEnhancedChests) {
            EBESetup.setupChests();
            EBESetup.setupRRPChests();
        }

        if (CONFIG.renderEnhancedSigns) {
            EBESetup.setupSigns();
            EBESetup.setupRRPSigns();
        }

        if (CONFIG.renderEnhancedBells) {
            EBESetup.setupBells();
            EBESetup.setupRRPBells();
        }

        if (CONFIG.renderEnhancedBeds) {
            EBESetup.setupBeds();
            EBESetup.setupRRPBeds();
        }

        if (CONFIG.renderEnhancedShulkerBoxes) {
            EBESetup.setupShulkerBoxes();
            EBESetup.setupRRPShulkerBoxes();
        }
    }
}