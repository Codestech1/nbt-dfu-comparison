package net.hypejet.nbt.dfu.comparison.test;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.Style.style;

final class StyleTest {
    @Test
    public void testEmpty() {
        TestUtil.test(Component.empty(), empty());
    }

    @Test
    public void testWithColor() {
        TestUtil.test(
                Component.empty().withStyle(ChatFormatting.BLACK),
                text("", NamedTextColor.BLACK)
        );
    }

    @Test
    public void testWithUnderlinedAndBold() {
        TestUtil.test(
                Component.empty().withStyle(ChatFormatting.UNDERLINE, ChatFormatting.BOLD),
                text().decorate(TextDecoration.UNDERLINED, TextDecoration.BOLD).build()
        );
    }

    @Test
    public void testWithDecorations() {
        String text = "Just a test";
        TestUtil.test(
                Component.literal(text).withStyle(
                        Style.EMPTY
                                .withBold(true)
                                .withItalic(false)
                                .withStrikethrough(false)
                                .withUnderlined(true)
                                .withObfuscated(true)
                ),
                text(
                        text,
                        style()
                                .decoration(TextDecoration.BOLD, true)
                                .decoration(TextDecoration.ITALIC, false)
                                .decoration(TextDecoration.STRIKETHROUGH, false)
                                .decoration(TextDecoration.UNDERLINED, true)
                                .decoration(TextDecoration.OBFUSCATED, true)
                                .build()
                )
        );
    }

    @Test
    public void testWithInsertion() {
        String insertion = "Some insertion";
        TestUtil.test(
                Component.empty().withStyle(Style.EMPTY.withInsertion(insertion)),
                text().insertion(insertion).build()
        );
    }

    @Test
    public void testWithFont() {
        String font = "hypejet:thin";
        TestUtil.test(
                Component.empty().withStyle(Style.EMPTY.withFont(new ResourceLocation(font))),
                text().font(Key.key(font)).build()
        );
    }
}