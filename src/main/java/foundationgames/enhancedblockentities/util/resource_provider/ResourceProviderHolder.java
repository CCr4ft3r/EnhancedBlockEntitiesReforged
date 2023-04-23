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