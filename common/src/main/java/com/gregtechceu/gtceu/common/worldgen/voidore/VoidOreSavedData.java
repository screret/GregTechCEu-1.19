package com.gregtechceu.gtceu.common.worldgen.voidore;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreFeature;
import com.gregtechceu.gtceu.api.voidore.VoidOreType;
import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class VoidOreSavedData extends SavedData {
    public static VoidOreSavedData getOrCreate(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new VoidOreSavedData(serverLevel, tag), () -> new VoidOreSavedData(serverLevel), GTCEu.MOD_ID + "_void_ores");
    }

    private final Map<ChunkPos, VoidOreType> posToOre = new Object2ObjectLinkedOpenHashMap<>();

    public static final Int2ObjectOpenHashMap<VoidOreType> CHANCE_MAP = new Int2ObjectOpenHashMap<>() {{
        this.put(50, new VoidOreType("coal_ore", new ResourceLocation("blocks/coal_ore")));
        this.put(30, new VoidOreType("iron_ore", new ResourceLocation("blocks/iron_ore")));
        this.put(20, new VoidOreType("gold_ore", new ResourceLocation("blocks/gold_ore")));
        this.put(10, new VoidOreType("lapis_ore", new ResourceLocation("blocks/lapis_ore")));

        for (GTOreFeature.)
    }};
    private final ServerLevel level;

    public VoidOreSavedData(ServerLevel level) {
        this.level = level;
    }

    public VoidOreSavedData(ServerLevel level, CompoundTag tag) {
        this(level);
        this.load(level, tag);
    }

    public VoidOreType getFromBlockPos(BlockPos pos) {
        return posToOre.get(new ChunkPos(pos));
    }

    public void generate(ChunkPos pos, RandomSource random) {
        var objects = new Int2ObjectOpenHashMap<VoidOreType>();
        int key = 0;
        int totalChances = -1;
        for (var obj : CHANCE_MAP.int2ObjectEntrySet()) {
            totalChances += obj.getIntKey();
            for (int i = 0; i < obj.getIntKey(); ++i) {
                objects.put(key, obj.getValue());
                key++;
            }
        }

        int resultIndex = random.nextInt() * key;
        var result = objects.get(resultIndex);
        posToOre.put(pos, result);
    }

    public void load(ServerLevel level, CompoundTag arg) {
        ListTag oresTag = arg.getList("ores", Tag.TAG_COMPOUND);
        for (Tag _tag : oresTag) {
            CompoundTag tag = (CompoundTag) _tag;
            CompoundTag posTag = tag.getCompound("pos");
            ChunkPos pos = new ChunkPos(posTag.getInt("x"), posTag.getInt("z"));
            posToOre.put(pos, VoidOreType.load(level, tag.getCompound("ore")));
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag tag = new ListTag();
        for (Map.Entry<ChunkPos, VoidOreType> ore : posToOre.entrySet()) {
            CompoundTag oreTag = new CompoundTag();
            CompoundTag pos = new CompoundTag();
            pos.putInt("x", ore.getKey().x);
            pos.putInt("z", ore.getKey().z);
            oreTag.put("pos", pos);
            CompoundTag oreType = new CompoundTag();
            ore.getValue().save(oreType);
            oreTag.put("ore", oreType);
            tag.add(oreTag);
        }
        compoundTag.put("ores", tag);
        return compoundTag;
    }
}
