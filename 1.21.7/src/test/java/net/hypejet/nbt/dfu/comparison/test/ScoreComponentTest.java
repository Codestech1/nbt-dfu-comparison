package net.hypejet.nbt.dfu.comparison.test;

import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.score;

final class ScoreComponentTest {
    @Test
    public void test() {
        String name = "some-score-owner";
        String objective = "kills";
        TestUtil.test(Component.score(name, objective), score(name, objective));
    }
}