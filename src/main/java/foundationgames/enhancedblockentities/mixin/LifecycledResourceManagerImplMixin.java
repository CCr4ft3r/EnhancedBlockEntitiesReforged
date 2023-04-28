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
package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(MultiPackResourceManager.class)
public abstract class LifecycledResourceManagerImplMixin {
    @Shadow
    @Final
    private Map<String, FallbackResourceManager> namespacedManagers;

    @ModifyVariable(method = "<init>", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;copyOf(Ljava/util/Collection;)Ljava/util/List;", shift = At.Shift.BEFORE), ordinal = 0)
    private List<PackResources> enhanced_bes$injectRRP(List<PackResources> old) {
        var packs = new ArrayList<>(old);

        int idx = 0;
        if (packs.size() > 0) do {
            idx++;
        } while (idx < packs.size() && !EBEUtil.isVanillaResourcePack(packs.get(idx - 1)));
        packs.add(idx, ResourceUtil.getPack());

        return packs;
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void enhanced_bes$injectExperimentalPack(PackType type, List<PackResources> packs, CallbackInfo ci) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();

        addPack(type, ResourceUtil.getExperimentalPack());
    }

    private void addPack(PackType type, PackResources pack) {
        for (var namespace : pack.getNamespaces(type)) {
            this.namespacedManagers.computeIfAbsent(namespace, n -> new FallbackResourceManager(type, n)).add(pack);
        }
    }
}