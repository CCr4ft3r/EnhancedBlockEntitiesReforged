package foundationgames.enhancedblockentities.util.duck;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public interface BakedModelManagerAccess {
    BakedModel getModel(ResourceLocation id);
}