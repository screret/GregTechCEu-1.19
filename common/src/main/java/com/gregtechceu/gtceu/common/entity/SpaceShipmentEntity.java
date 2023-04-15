package com.gregtechceu.gtceu.common.entity;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.satellite.Satellite;
import com.gregtechceu.gtceu.api.satellite.SatelliteType;
import com.gregtechceu.gtceu.common.data.GTSatellites;
import com.gregtechceu.gtceu.common.satellite.data.SatelliteData;
import com.gregtechceu.gtceu.utils.CustomInventory;
import dev.architectury.injectables.annotations.ExpectPlatform;
import lombok.Getter;
import net.minecraft.core.BlockPos;
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

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SpaceShipmentEntity
 */
public class SpaceShipmentEntity extends Entity {
    private static final EntityDataSerializer<Satellite> SATELLITE_SERIALIZER = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buffer, Satellite value) {
            buffer.writeResourceLocation(GTRegistries.SATELLITES.getKey(value.getType()));
            buffer.writeResourceKey(value.getLevel());
            buffer.writeBlockPos(value.getData().locationInWorld());
            buffer.writeVarInt(value.getData().range());
        }

        @Override
        public Satellite read(FriendlyByteBuf buffer) {
            SatelliteType<?> type = GTRegistries.SATELLITES.get(buffer.readResourceLocation());
            ResourceKey<Level> levelResourceKey = buffer.readResourceKey(Registry.DIMENSION_REGISTRY);
            BlockPos pos = buffer.readBlockPos();
            int range = buffer.readVarInt();
            return type.getFactory().create(type, new SatelliteData(pos, range), levelResourceKey);
        }

        @Override
        public Satellite copy(Satellite value) {
            return value.copy();
        }
    };
    private static final EntityDataAccessor<Satellite> DATA_SATELLITE = SynchedEntityData.defineId(SpaceShipmentEntity.class, SATELLITE_SERIALIZER);


    @ExpectPlatform
    public static SpaceShipmentEntity create(EntityType<? extends SpaceShipmentEntity> type, Level level) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void onEntityRegister(EntityType<SpaceShipmentEntity> entityType) {
        throw new AssertionError();
    }

    @Getter
    private Satellite satellite;
    private final CustomInventory inventory = new CustomInventory(9);

    public SpaceShipmentEntity(EntityType<? extends SpaceShipmentEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_SATELLITE, GTSatellites.EMPTY.getDefaultInstance());
    }

    public void setSatellite(Satellite satellite) {
        this.getEntityData().set(DATA_SATELLITE, satellite);
        this.satellite = satellite;
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
}
