package foundationgames.enhancedblockentities.event;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public enum EBEEvents {;
/*
    public static final Event<Reload> RELOAD_MODELS = EventFactory.createArrayBacked(Reload.class, (callbacks) -> (loader, manager, profiler) -> {
        for(Reload event : callbacks) {
            event.onReload(loader, manager, profiler);
        }
    });
*/

    public interface Reload {
        void onReload(ModelBakery loader, ResourceManager manager, ProfilerFiller profiler);
    }
}