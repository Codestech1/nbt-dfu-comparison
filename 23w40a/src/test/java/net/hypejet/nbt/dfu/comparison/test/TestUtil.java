package net.hypejet.nbt.dfu.comparison.test;

import net.hypejet.nbt.dfu.comparison.test.adapter.NBTAdapter;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.serializer.nbt.NBTComponentSerializer;
import net.kyori.adventure.text.serializer.nbt.NBTSerializerOptions;
import net.kyori.option.OptionState;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.Style;
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
        BinaryTag serializedAdventure = SERIALIZER.serialize(adventure);
        Tag convertedSerializedAdventure = NBTAdapter.convert(serializedAdventure);

        Tag serializedMinecraft = ComponentSerialization.CODEC.encodeStart(NbtOps.INSTANCE, minecraft)
                .result()
                .orElseThrow();
        BinaryTag convertedSerializedMinecraft = NBTAdapter.convert(serializedMinecraft);

        // Serialized Minecraft - Serialized Adventure test
        Assertions.assertEquals(convertedSerializedMinecraft, serializedAdventure);
        Assertions.assertEquals(serializedMinecraft, convertedSerializedAdventure);

        // Minecraft - Deserialized Minecraft to Minecraft test
        Assertions.assertEquals(minecraft, deserializeComponent(serializedMinecraft));

        // Minecraft - Deserialized Adventure to Minecraft test
        Assertions.assertEquals(minecraft, deserializeComponent(convertedSerializedAdventure));

        // Adventure - Deserialized Minecraft to Adventure test
        Assertions.assertEquals(adventure, deserializeComponent(convertedSerializedMinecraft));

        // Adventure - Deserialized Adventure to Adventure test
        Assertions.assertEquals(adventure, deserializeComponent(serializedAdventure));

        // Deserialized Minecraft to Adventure - Deserialized Adventure to Adventure test
        Assertions.assertEquals(
                deserializeComponent(convertedSerializedMinecraft),
                deserializeComponent(serializedAdventure)
        );

        // Deserialized Minecraft to Minecraft - Deserialized Adventure to Minecraft test
        Assertions.assertEquals(
                deserializeComponent(serializedMinecraft),
                deserializeComponent(convertedSerializedAdventure)
        );
    }

    static void test(@NotNull Style minecraft, net.kyori.adventure.text.format.@NotNull Style adventure) {
        BinaryTag serializedAdventure = SERIALIZER.serializeStyle(adventure);
        Tag convertedSerializedAdventure = NBTAdapter.convert(serializedAdventure);

        Tag serializedMinecraft = Style.Serializer.CODEC.encodeStart(NbtOps.INSTANCE, minecraft)
                .result()
                .orElseThrow();
        BinaryTag convertedSerializedMinecraft = NBTAdapter.convert(serializedMinecraft);

        // Serialized Minecraft - Serialized Adventure test
        Assertions.assertEquals(convertedSerializedMinecraft, serializedAdventure);
        Assertions.assertEquals(serializedMinecraft, convertedSerializedAdventure);

        // Minecraft - Deserialized Minecraft to Minecraft test
        Assertions.assertEquals(minecraft, deserializeStyle(serializedMinecraft));

        // Minecraft - Deserialized Adventure to Minecraft test
        Assertions.assertEquals(minecraft, deserializeStyle(convertedSerializedAdventure));

        // Adventure - Deserialized Minecraft to Adventure test
        Assertions.assertEquals(adventure, deserializeStyle(convertedSerializedMinecraft));

        // Adventure - Deserialized Adventure to Adventure test
        Assertions.assertEquals(adventure, deserializeStyle(serializedAdventure));

        // Deserialized Minecraft to Adventure - Deserialized Adventure to Adventure test
        Assertions.assertEquals(
                deserializeStyle(convertedSerializedMinecraft),
                deserializeStyle(serializedAdventure)
        );

        // Deserialized Minecraft to Minecraft - Deserialized Adventure to Minecraft test
        Assertions.assertEquals(
                deserializeStyle(serializedMinecraft),
                deserializeStyle(convertedSerializedAdventure)
        );
    }

    private static @NotNull Component deserializeComponent(@NotNull Tag tag) {
        return ComponentSerialization.CODEC.parse(NbtOps.INSTANCE, tag).result().orElseThrow();
    }

    private static net.kyori.adventure.text.@NotNull Component deserializeComponent(@NotNull BinaryTag tag) {
        return SERIALIZER.deserialize(tag);
    }

    private static @NotNull Style deserializeStyle(@NotNull Tag tag) {
        return Style.Serializer.CODEC.parse(NbtOps.INSTANCE, tag).result().orElseThrow();
    }

    private static net.kyori.adventure.text.format.@NotNull Style deserializeStyle(@NotNull BinaryTag tag) {
        return SERIALIZER.deserializeStyle((CompoundBinaryTag) tag);
    }
}