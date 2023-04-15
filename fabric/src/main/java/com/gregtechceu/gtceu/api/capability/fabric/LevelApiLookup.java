package com.gregtechceu.gtceu.api.capability.fabric;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface LevelApiLookup<A> {
    static <A> LevelApiLookup<A> get(ResourceLocation lookupId, Class<A> apiClass) {
        return LevelApiLookupImpl.get(lookupId, apiClass);
    }
    
    // Find an API instance.
    @Nullable
    A find(Level level);  
    
    // Expose the API for some level.
    void register(LevelApiProvider<A> provider, Level level);  
    
    interface LevelApiProvider<A> {    
        // Return an API instance if available, or null otherwise.
        @Nullable
        A find(Level stack);
    }
}
