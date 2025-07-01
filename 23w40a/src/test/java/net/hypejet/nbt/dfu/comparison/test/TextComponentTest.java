package net.hypejet.nbt.dfu.comparison.test;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.ChatFormatting;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;
import static net.minecraft.network.chat.Component.literal;

final class TextComponentTest {
    @Test
    public void testSimple() {
        String text = "Hello, world!";
        TestUtil.test(literal(text), text(text));
    }

    @Test
    public void testComplex1() {
        String text1 = "a";
        String text2 = "b";
        String text3 = "c";
        String text4 = "d";

        TestUtil.test(
                literal(text1).append(text2).append(text3).append(text4),
                text().content(text1).append(text(text2)).append(text(text3)).append(text(text4)).build()
        );
    }

    @Test
    public void testComplex2() {
        String text1 = "c";
        String text2 = "o";
        String text3 = "l";
        String text4 = "o";
        String text5 = "r";
        String text6 = "s";

        TestUtil.test(
                literal(text1).withStyle(ChatFormatting.AQUA)
                        .append(literal(text2).withStyle(ChatFormatting.BLACK))
                        .append(literal(text3).withStyle(ChatFormatting.BLUE))
                        .append(literal(text4).withStyle(ChatFormatting.DARK_AQUA))
                        .append(literal(text5).withStyle(ChatFormatting.DARK_BLUE))
                        .append(literal(text6).withStyle(ChatFormatting.GREEN)),
                text().content(text1).color(NamedTextColor.AQUA)
                        .append(text(text2, NamedTextColor.BLACK))
                        .append(text(text3, NamedTextColor.BLUE))
                        .append(text(text4, NamedTextColor.DARK_AQUA))
                        .append(text(text5, NamedTextColor.DARK_BLUE))
                        .append(text(text6, NamedTextColor.GREEN))
                        .build()
        );
    }
}