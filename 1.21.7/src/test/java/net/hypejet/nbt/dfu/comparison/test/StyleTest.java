package net.hypejet.nbt.dfu.comparison.test;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.format.Style.empty;
import static net.kyori.adventure.text.format.Style.style;

final class StyleTest {
    @Test
    public void testEmpty() {
        TestUtil.test(Style.EMPTY, empty());
    }

    @Test
    public void testWithColor() {
        TestUtil.test(
                Style.EMPTY.withColor(ChatFormatting.BLACK),
                style(NamedTextColor.BLACK)
        );
    }

    @Test
    public void testWithUnderlinedAndBold() {
        TestUtil.test(
                Style.EMPTY.withUnderlined(true).withBold(true),
                style(TextDecoration.UNDERLINED, TextDecoration.BOLD)
        );
    }

    @Test
    public void testWithDecorations() {
        TestUtil.test(
                Style.EMPTY
                        .withBold(true)
                        .withItalic(false)
                        .withStrikethrough(false)
                        .withUnderlined(true)
                        .withObfuscated(true),
                style()
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false)
                        .decoration(TextDecoration.STRIKETHROUGH, false)
                        .decoration(TextDecoration.UNDERLINED, true)
                        .decoration(TextDecoration.OBFUSCATED, true)
                        .build()
        );
    }

    @Test
    public void testWithInsertion() {
        String insertion = "Some insertion";
        TestUtil.test(
                Style.EMPTY.withInsertion(insertion),
                style().insertion(insertion).build()
        );
    }

    @Test
    public void testWithFont() {
        String font = "hypejet:thin";
        TestUtil.test(
                Style.EMPTY.withFont(new ResourceLocation(font)),
                style().font(Key.key(font)).build()
        );
    }
}