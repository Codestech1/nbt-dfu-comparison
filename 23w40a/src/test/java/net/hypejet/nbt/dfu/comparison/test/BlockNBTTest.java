package net.hypejet.nbt.dfu.comparison.test;

import net.kyori.adventure.text.BlockNBTComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.BlockDataSource;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static net.kyori.adventure.text.Component.blockNBT;
import static net.kyori.adventure.text.Component.text;

final class BlockNBTTest {
    @Test
    public void testWithoutSeparator() {
        String nbtPath = "abc";
        boolean interpret = false;
        String position = "45 100 542";

        TestUtil.test(
                Component.nbt(nbtPath, interpret, Optional.empty(), new BlockDataSource(position)),
                blockNBT(nbtPath, interpret, BlockNBTComponent.Pos.fromString(position))
        );
    }

    @Test
    public void testWithSeparator() {
        String nbtPath = "qwerty";
        boolean interpret = true;
        String position = "5630 -65 -24450";
        String separatorText = ",";

        TestUtil.test(
                Component.nbt(
                        nbtPath, interpret,
                        Optional.of(Component.literal(separatorText).withStyle(ChatFormatting.BOLD)),
                        new BlockDataSource(position)
                ),
                blockNBT(
                        nbtPath, interpret,
                        text(separatorText, Style.style(TextDecoration.BOLD)),
                        BlockNBTComponent.Pos.fromString(position)
                )
        );
    }
}