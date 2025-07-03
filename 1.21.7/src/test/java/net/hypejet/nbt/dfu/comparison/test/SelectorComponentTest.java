package net.hypejet.nbt.dfu.comparison.test;

import net.kyori.adventure.text.format.TextColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static net.kyori.adventure.text.Component.selector;
import static net.kyori.adventure.text.Component.text;

final class SelectorComponentTest {
    @Test
    public void testWithoutSeparator() {
        String pattern = "@p";
        TestUtil.test(Component.selector(pattern, Optional.empty()), selector(pattern));
    }

    @Test
    public void testWithSeparator() {
        String pattern = "@e";

        String separatorText = ",";
        int separatorColor = 0x2F3AC;

        TestUtil.test(
                Component.selector(
                        pattern,
                        Optional.of(Component.literal(separatorText).withStyle(Style.EMPTY.withColor(separatorColor)))
                ),
                selector(pattern, text(separatorText, TextColor.color(separatorColor)))
        );
    }
}