package com.gregtechceu.gtceu.api.capability.fabric;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface LevelApiLookup<A, C> {
    static <A, C> LevelApiLookup<A, C> get(ResourceLocation lookupId, Class<A> apiClass, Class<C> contextClass) {
        return LevelApiLookupImpl.get(lookupId, apiClass, contextClass);
    }
    
    // Find an API instance.
    @Nullable
    A find(Level level, C context);
    
    // Expose the API for some level.
    void register(LevelApiProvider<A, C> provider, Level level);
    
    interface LevelApiProvider<A, C> {
        // Return an API instance if available, or null otherwise.
        @Nullable
        A find(Level level, C context);
    }
}
