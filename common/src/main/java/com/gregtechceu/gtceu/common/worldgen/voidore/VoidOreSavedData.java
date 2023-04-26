package com.gregtechceu.gtceu.common.worldgen.voidore;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreFeatureConfiguration;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreFeatureEntry;
import it.unimi.dsi.fastutil.floats.Float2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;

public class VoidOreSavedData extends SavedData {
    public static VoidOreSavedData getOrCreate(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new VoidOreSavedData(serverLevel, tag), () -> new VoidOreSavedData(serverLevel), GTCEu.MOD_ID + "_void_ores");
    }

    private final Map<ChunkPos, GTOreFeatureConfiguration> posToOre = new Object2ObjectLinkedOpenHashMap<>();

    private final ServerLevel level;

    public VoidOreSavedData(ServerLevel level) {
        this.level = level;
    }

    public VoidOreSavedData(ServerLevel level, CompoundTag tag) {
        this(level);
        this.load(tag);
    }

    public GTOreFeatureConfiguration getFromBlockPos(BlockPos pos) {
        return posToOre.get(new ChunkPos(pos));
    }

    public void generate(ChunkPos pos, RandomSource random, GTOreFeatureConfiguration config) {
        posToOre.put(pos, config);
        /*var objects = new Float2ObjectOpenHashMap<GTOreFeatureEntry>();
        int key = 0;
        int totalChances = -1;
        for (var obj : GTOreFeatureEntry.ALL.values()) {
            totalChances += obj.frequency;
            for (int i = 0; i < obj.frequency; ++i) {
                objects.put(key, obj);
                key++;
            }
        }

        int resultIndex = random.nextInt(totalChances);
        var result = objects.get(resultIndex);*/
    }

    public void load(CompoundTag arg) {
        ListTag oresTag = arg.getList("ores", Tag.TAG_COMPOUND);
        for (int i = 0; i < oresTag.size(); i++) {
            CompoundTag tag = oresTag.getCompound(i);
            CompoundTag posTag = tag.getCompound("pos");
            ChunkPos pos = new ChunkPos(posTag.getInt("x"), posTag.getInt("z"));
            posToOre.put(pos, (GTOreFeatureConfiguration) BuiltinRegistries.CONFIGURED_FEATURE.get(new ResourceLocation(tag.getString("type"))).config());
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag tag = new ListTag();
        for (Map.Entry<ChunkPos, GTOreFeatureConfiguration> ore : posToOre.entrySet()) {
            CompoundTag oreTag = new CompoundTag();
            CompoundTag pos = new CompoundTag();
            pos.putInt("x", ore.getKey().x);
            pos.putInt("z", ore.getKey().z);
            oreTag.put("pos", pos);
            oreTag.putString("type", ore.getValue().getEntry().id.toString());
            tag.add(oreTag);
        }
        compoundTag.put("ores", tag);
        return compoundTag;
    }
}
