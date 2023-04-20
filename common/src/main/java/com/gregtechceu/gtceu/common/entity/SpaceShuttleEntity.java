package com.gregtechceu.gtceu.common.entity;

import com.gregtechceu.gtceu.api.blockentity.PipeBlockEntity;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.space.satellite.Satellite;
import com.gregtechceu.gtceu.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.common.data.GTSatellites;
import com.gregtechceu.gtceu.api.space.satellite.data.SatelliteData;
import com.gregtechceu.gtceu.utils.CustomInventory;
import com.lowdragmc.lowdraglib.syncdata.IManaged;
import com.lowdragmc.lowdraglib.syncdata.IManagedStorage;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.DropSaved;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.FieldManagedStorage;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.syncdata.managed.IRef;
import dev.architectury.injectables.annotations.ExpectPlatform;
import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

import java.util.UUID;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SpaceShuttleEntity
 */
public class SpaceShuttleEntity extends Entity implements IManaged {
    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(SpaceShuttleEntity.class);

    @Getter
    private final FieldManagedStorage syncStorage = new FieldManagedStorage(this);

    @ExpectPlatform
    public static SpaceShuttleEntity create(EntityType<? extends SpaceShuttleEntity> type, Level level) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void onEntityRegister(EntityType<SpaceShuttleEntity> entityType) {
        throw new AssertionError();
    }

    @Getter
    @Persisted
    @DescSynced
    @DropSaved
    private Satellite satellite;
    private final CustomInventory inventory = new CustomInventory(9);

    public SpaceShuttleEntity(EntityType<? extends SpaceShuttleEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.inventory.fromTag(compound.getList("inventory", Tag.TAG_COMPOUND));
        this.satellite = Satellite.deserializeNBT(compound.getCompound("satellite"), this.level);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.put("inventory", this.inventory.createTag());
        compound.put("satellite", satellite.serializeNBT());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onChanged() {
        this.saveWithoutId(new CompoundTag());
    }


}
