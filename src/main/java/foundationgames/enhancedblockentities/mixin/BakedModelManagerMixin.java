package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerAccess {

    @Shadow private Map<ResourceLocation, BakedModel> bakedRegistry;

    @Override
    public BakedModel getModel(ResourceLocation id) {
        return this.bakedRegistry.get(id);
    }

    @Inject(method = "apply(Lnet/minecraft/client/resources/model/ModelBakery;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("TAIL"))
    public void enhanced_bes$invokeReloadEvent(ModelBakery modelLoader, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
       // EBEEvents.RELOAD_MODELS.invoker().onReload(modelLoader, resourceManager, profiler);
    }
}