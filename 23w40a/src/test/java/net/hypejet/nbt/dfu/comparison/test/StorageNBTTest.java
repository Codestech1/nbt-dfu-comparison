package net.hypejet.nbt.dfu.comparison.test;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.StorageDataSource;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static net.kyori.adventure.text.Component.storageNBT;
import static net.kyori.adventure.text.Component.text;

final class StorageNBTTest {
    @Test
    public void testWithoutSeparator() {
        String nbtPath = "some-path";
        boolean interpret = true;
        String storage = "some:storage";

        TestUtil.test(
                Component.nbt(
                        nbtPath, interpret, Optional.empty(),
                        new StorageDataSource(new ResourceLocation(storage))
                ),
                storageNBT(nbtPath, interpret, Key.key(storage))
        );
    }

    @Test
    public void testWithSeparator() {
        String nbtPath = "another-path";
        boolean interpret = false;
        String storage = "doom:apple";
        String separatorText = " - ";

        TestUtil.test(
                Component.nbt(
                        nbtPath, interpret,
                        Optional.of(Component.literal(separatorText).withStyle(
                                Style.EMPTY.withItalic(true).withColor(ChatFormatting.DARK_GREEN)
                        )),
                        new StorageDataSource(new ResourceLocation(storage))
                ),
                storageNBT(
                        nbtPath, interpret,
                        text(separatorText, NamedTextColor.DARK_GREEN, TextDecoration.ITALIC),
                        Key.key(storage)
                )
        );
    }
}