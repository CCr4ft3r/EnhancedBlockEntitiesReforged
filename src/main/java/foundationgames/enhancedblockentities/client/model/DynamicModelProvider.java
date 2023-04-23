package foundationgames.enhancedblockentities.client.model;

import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamicModelProvider {
    private final Supplier<DynamicUnbakedModel> model;
    private final ResourceLocation id;

    public DynamicModelProvider(ResourceLocation id, Supplier<DynamicUnbakedModel> model) {
        this.model = model;
        this.id = id;
    }

    public @Nullable UnbakedModel loadModelResource(ResourceLocation identifier) {
        if (identifier.equals(this.id)) return model.get();
        return null;
    }
}