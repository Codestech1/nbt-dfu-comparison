package net.hypejet.nbt.dfu.comparison.test;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.EntityDataSource;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static net.kyori.adventure.text.Component.entityNBT;
import static net.kyori.adventure.text.Component.text;

final class EntityNBTTest {
    @Test
    public void testWithoutSeparator() {
        String nbtPath = "entity-path";
        boolean interpret = true;
        String selector = "@p";

        TestUtil.test(
                Component.nbt(nbtPath, interpret, Optional.empty(), new EntityDataSource(selector)),
                entityNBT().nbtPath(nbtPath).interpret(interpret).selector(selector).build()
        );
    }

    @Test
    public void testWithSeparator() {
        String nbtPath = "another-entity-nbt-path";
        boolean interpret = false;
        String separatorText = " === ";
        String selector = "@r";

        TestUtil.test(
                Component.nbt(
                        nbtPath, interpret,
                        Optional.of(Component.literal(separatorText)),
                        new EntityDataSource(selector)
                ),
                entityNBT()
                        .nbtPath(nbtPath)
                        .interpret(interpret)
                        .separator(text(separatorText))
                        .selector(selector)
                        .build()
        );
    }
}