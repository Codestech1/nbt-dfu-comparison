package net.hypejet.nbt.dfu.comparison.test;

import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.keybind;

final class KeybindComponentTest {
    @Test
    public void test() {
        String keybind = "key.jump";
        TestUtil.test(Component.keybind(keybind), keybind(keybind));
    }
}