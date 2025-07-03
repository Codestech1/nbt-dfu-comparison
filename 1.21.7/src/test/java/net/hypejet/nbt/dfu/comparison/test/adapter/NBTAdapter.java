package net.hypejet.nbt.dfu.comparison.test.adapter;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.ByteArrayBinaryTag;
import net.kyori.adventure.nbt.ByteBinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import net.kyori.adventure.nbt.EndBinaryTag;
import net.kyori.adventure.nbt.FloatBinaryTag;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import net.kyori.adventure.nbt.IntBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.LongArrayBinaryTag;
import net.kyori.adventure.nbt.LongBinaryTag;
import net.kyori.adventure.nbt.ShortBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class NBTAdapter {

    private NBTAdapter() {}

    public static @NotNull BinaryTag convert(@NotNull Tag tag) {
        return switch (tag) {
            case EndTag ignored -> EndBinaryTag.endBinaryTag();
            case ByteTag byteTag -> ByteBinaryTag.byteBinaryTag(byteTag.getAsByte());
            case ShortTag shortTag -> ShortBinaryTag.shortBinaryTag(shortTag.getAsShort());
            case IntTag intTag -> IntBinaryTag.intBinaryTag(intTag.getAsInt());
            case LongTag longTag -> LongBinaryTag.longBinaryTag(longTag.getAsLong());
            case FloatTag floatTag -> FloatBinaryTag.floatBinaryTag(floatTag.getAsFloat());
            case DoubleTag doubleTag -> DoubleBinaryTag.doubleBinaryTag(doubleTag.getAsDouble());
            case ByteArrayTag arrayTag -> ByteArrayBinaryTag.byteArrayBinaryTag(arrayTag.getAsByteArray());
            case StringTag stringTag -> StringBinaryTag.stringBinaryTag(stringTag.getAsString());
            case ListTag listTag -> {
                List<BinaryTag> elements = new ArrayList<>();
                listTag.forEach(element -> elements.add(convert(element)));
                yield ListBinaryTag.from(elements);
            }
            case CompoundTag compoundTag -> {
                CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();

                for (String key : compoundTag.getAllKeys()) {
                    Tag value = compoundTag.get(key);
                    if (value == null) continue;
                    builder.put(key, convert(value));
                }

                yield builder.build();
            }
            case IntArrayTag arrayTag -> IntArrayBinaryTag.intArrayBinaryTag(arrayTag.getAsIntArray());
            case LongArrayTag arrayTag -> LongArrayBinaryTag.longArrayBinaryTag(arrayTag.getAsLongArray());
            default -> throw new IllegalStateException("Unknown tag: " + tag);
        };
    }

    public static @NotNull Tag convert(@NotNull BinaryTag tag) {
        return switch (tag) {
            case EndBinaryTag ignored -> EndTag.INSTANCE;
            case ByteBinaryTag byteTag -> ByteTag.valueOf(byteTag.value());
            case ShortBinaryTag shortTag -> ShortTag.valueOf(shortTag.value());
            case IntBinaryTag intTag -> IntTag.valueOf(intTag.value());
            case LongBinaryTag longTag -> LongTag.valueOf(longTag.value());
            case FloatBinaryTag floatTag -> FloatTag.valueOf(floatTag.value());
            case DoubleBinaryTag doubleTag -> DoubleTag.valueOf(doubleTag.value());
            case ByteArrayBinaryTag arrayTag -> new ByteArrayTag(arrayTag.value());
            case StringBinaryTag stringTag -> StringTag.valueOf(stringTag.value());
            case ListBinaryTag listTag -> {
                ListTag convertedTag = new ListTag();
                listTag.forEach(element -> convertedTag.add(convert(element)));
                yield convertedTag;
            }
            case CompoundBinaryTag compoundTag -> {
                CompoundTag convertedTag = new CompoundTag();
                compoundTag.forEach(entry -> convertedTag.put(entry.getKey(), convert(entry.getValue())));
                yield convertedTag;
            }
            case IntArrayBinaryTag arrayTag -> new IntArrayTag(arrayTag.value());
            case LongArrayBinaryTag arrayTag -> new LongArrayTag(arrayTag.value());
            default -> throw new IllegalStateException("Unknown tag: " + tag);
        };
    }
}