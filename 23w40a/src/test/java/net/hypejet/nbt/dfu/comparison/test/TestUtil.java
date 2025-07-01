package net.hypejet.nbt.dfu.comparison.test;

import net.hypejet.nbt.dfu.comparison.test.adapter.NBTAdapter;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.serializer.nbt.NBTComponentSerializer;
import net.kyori.adventure.text.serializer.nbt.NBTSerializerOptions;
import net.kyori.option.OptionState;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.Bootstrap;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;

final class TestUtil {

    private static final NBTComponentSerializer SERIALIZER;

    static {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        int version = SharedConstants.getCurrentVersion().getDataVersion().getVersion();
        OptionState options = NBTSerializerOptions.byDataVersion().at(version);

        SERIALIZER = NBTComponentSerializer.builder().options(options).build();
    }

    private TestUtil() {}

    static void test(@NotNull Component minecraft, net.kyori.adventure.text.@NotNull Component adventure) {
        BinaryTag serializedAdventure = serializeAdventure(adventure);
        Assertions.assertEquals(serializeMinecraft(minecraft), serializedAdventure);
        Assertions.assertEquals(minecraft, deserializeMinecraft(serializedAdventure));
        Assertions.assertEquals(adventure, deserializeAdventure(serializedAdventure));
    }

    private static @NotNull BinaryTag serializeMinecraft(@NotNull Component component) {
        Tag tag = ComponentSerialization.CODEC.encodeStart(NbtOps.INSTANCE, component).result().orElseThrow();
        return NBTAdapter.convert(tag);
    }

    private static @NotNull BinaryTag serializeAdventure(net.kyori.adventure.text.@NotNull Component component) {
        return SERIALIZER.serialize(component);
    }

    private static @NotNull Component deserializeMinecraft(@NotNull BinaryTag tag) {
        Tag convertedTag = NBTAdapter.convert(tag);
        return ComponentSerialization.CODEC.parse(NbtOps.INSTANCE, convertedTag).result().orElseThrow();
    }

    private static net.kyori.adventure.text.@NotNull Component deserializeAdventure(@NotNull BinaryTag tag) {
        return SERIALIZER.deserialize(tag);
    }
}