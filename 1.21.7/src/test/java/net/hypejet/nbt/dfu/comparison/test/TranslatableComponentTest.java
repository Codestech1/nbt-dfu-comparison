package net.hypejet.nbt.dfu.comparison.test;

import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

final class TranslatableComponentTest {
    @Test
    public void testSimple() {
        String translationKey = "translatable.message";
        TestUtil.test(Component.translatable(translationKey), translatable(translationKey));
    }

    @Test
    public void testWithFallback() {
        String translationKey = "message.death";
        String fallback = "Someone died";

        TestUtil.test(
                Component.translatableWithFallback(translationKey, fallback),
                translatable(translationKey, fallback)
        );
    }

    @Test
    public void testWithArguments() {
        String translationKey = "translation.key";

        long firstArgument = 12L;
        String secondArgument = "argument 1";
        String thirdArgument = "argument 2";

        TestUtil.test(
                Component.translatable(
                        translationKey, firstArgument,
                        Component.literal(secondArgument).withStyle(ChatFormatting.AQUA),
                        thirdArgument
                ),
                translatable(
                        translationKey,
                        TranslationArgument.numeric(firstArgument),
                        TranslationArgument.component(text(secondArgument, NamedTextColor.AQUA)),
                        TranslationArgument.component(text(thirdArgument))
                )
        );
    }

    @Test
    public void testWithFallbackAndArgument() {
        String translationKey = "fallback.and.arguments";
        String fallback = "Fallback AND Argument!!!!";
        byte argument = 90;

        TestUtil.test(
                Component.translatableWithFallback(translationKey, fallback, argument),
                translatable(translationKey, fallback, TranslationArgument.numeric(argument))
        );
    }
}