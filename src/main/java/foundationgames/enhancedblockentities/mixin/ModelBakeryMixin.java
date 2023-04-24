/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.resource_provider.ResourceProviderHolder;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Final
    @Shadow
    public static ModelResourceLocation MISSING_MODEL_LOCATION;

    @Shadow
    private void cacheAndQueueDependencies(ResourceLocation id, UnbakedModel unbakedModel) {
    }

    @Shadow public abstract UnbakedModel getModel(ResourceLocation p_119342_);

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> unbakedCache;

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> topLevelModels;

    @Inject(at = @At("HEAD"), method = "loadModel", cancellable = true)
    private void loadModelHook(ResourceLocation id, CallbackInfo ci) {
        UnbakedModel customModel = loadModelFromVariant(id);

        if (customModel != null) {
            cacheAndQueueDependencies(id, customModel);
            ci.cancel();
        }
    }
    @Inject(at = @At("HEAD"), method = "loadTopLevel")
    private void addModelHook(ModelResourceLocation id, CallbackInfo info) {
        if (id == MISSING_MODEL_LOCATION) {
            for (Consumer<Consumer<ResourceLocation>> appender : ResourceProviderHolder.INSTANCE.appenders) {
                appender.accept(this::addModel);
            }
        }
    }

    public void addModel(ResourceLocation id) {
        if (id instanceof ModelResourceLocation) {
            addModel((ModelResourceLocation) id);
        } else {
            // The vanilla addModel method is arbitrarily limited to ModelIdentifiers,
            // but it's useful to tell the game to just load and bake a direct model path as well.
            // Replicate the vanilla logic of addModel here.
            UnbakedModel unbakedModel = getModel(id);
            this.unbakedCache.put(id, unbakedModel);
            this.topLevelModels.put(id, unbakedModel);
        }
    }


    @Nullable
    public UnbakedModel loadModelFromResource(ResourceLocation resourceId) {
        return loadCustomModel((r) -> r.get().loadModelResource(resourceId), ResourceProviderHolder.INSTANCE.resourceProviders, "resource provider");
    }

    private <T> UnbakedModel loadCustomModel(Function<T, UnbakedModel> function, Collection<T> loaders, String debugName) {
        for (T provider : loaders) {
            UnbakedModel model = function.apply(provider);
            if (model != null) {
                return model;
            }
        }
        return null;
    }

    @Nullable
    public UnbakedModel loadModelFromVariant(ResourceLocation variantId) {
        if (!(variantId instanceof ModelResourceLocation)) {
            return loadModelFromResource(variantId);
        } else {
            ModelResourceLocation modelId = (ModelResourceLocation) variantId;
            UnbakedModel model = null;

            // Replicating the special-case from ModelLoader as loadModelFromJson is insufficiently patchable
            if (Objects.equals(modelId.getVariant(), "inventory")) {
                ResourceLocation resourceId = new ResourceLocation(modelId.getNamespace(), "item/" + modelId.getPath());
                model = loadModelFromResource(resourceId);

                if (model != null) {
                    return model;
                }
            }

            return null;
        }
    }
}