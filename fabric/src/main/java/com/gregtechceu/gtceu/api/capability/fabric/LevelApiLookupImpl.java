package com.gregtechceu.gtceu.api.capability.fabric;

import com.gregtechceu.gtceu.GTCEu;
import net.fabricmc.fabric.api.lookup.v1.custom.ApiLookupMap;
import net.fabricmc.fabric.api.lookup.v1.custom.ApiProviderMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LevelApiLookupImpl<A, C> implements LevelApiLookup<A, C> {
    private static final ApiLookupMap<LevelApiLookup<?, ?>> LOOKUPS = ApiLookupMap.create(LevelApiLookupImpl::new);

    @SuppressWarnings("unchecked")
    public static <A, C> LevelApiLookup<A, C> get(ResourceLocation lookupId, Class<A> apiClass, Class<C> contextClass) {
        // Null checks are already handled by ApiLookupMap#get.
        return (LevelApiLookup<A, C>) LOOKUPS.getLookup(lookupId, apiClass, contextClass);
    }

     private LevelApiLookupImpl(ResourceLocation identifier, Class<?> apiClass, Class<?> contextClass) {
         // We don't use these parameters, so nothing to do here.
         // In practice, these parameters should be stored and exposed with identifier(), apiClass() and contextClass() getter functions.
     }

     // We will use an ApiProviderMap to store the providers.
     private final ApiProviderMap<Level, LevelApiProvider<A, C>> providerMap = ApiProviderMap.create();

     @Nullable
     public A find(Level level, C context) {
         LevelApiProvider<A, C> provider = providerMap.get(level);
         if (provider == null) {
             return null;
         } else {
             return provider.find(level, context);
         }
     }

     public void register(LevelApiProvider<A, C> provider, Level level) {
         Objects.requireNonNull(provider, "LevelApiProvider may not be null.");
         Objects.requireNonNull(level, "Level may not be null.");

         if (providerMap.putIfAbsent(level, provider) != null) {
             GTCEu.LOGGER.warn("Encountered duplicate API provider registration for level " + level.dimension().location() +  ".");
         }
     }
}
