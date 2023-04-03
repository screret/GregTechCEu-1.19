package com.gregtechceu.gtceu.core.mixins;

import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Registry.class)
public interface IRegistryAccessor<T> extends Keyable, IdMap<T> {

    @Invoker
    static <T, R extends WritableRegistry<T>> R invokeInternalRegister(ResourceKey<? extends Registry<T>> registryKey, R registry, Registry.RegistryBootstrap<T> loader, Lifecycle lifecycle) {
        throw new NotImplementedException("Mixin failed to apply");
    }
}