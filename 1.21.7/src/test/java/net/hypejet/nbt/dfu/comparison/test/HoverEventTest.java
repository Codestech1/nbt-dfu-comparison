package net.hypejet.nbt.dfu.comparison.test;

import net.hypejet.nbt.dfu.comparison.test.adapter.NBTAdapter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTagTypes;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.UUID;

import static net.hypejet.nbt.dfu.comparison.test.TestUtil.TAG_STRING_CODEC;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.HoverEvent.showEntity;
import static net.kyori.adventure.text.event.HoverEvent.showItem;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.Style.style;

final class HoverEventTest {

    private static Constructor<HoverEvent.ItemStackInfo> itemStackInfoConstructor;

    @BeforeAll
    public static void setup() throws NoSuchMethodException {
        itemStackInfoConstructor = HoverEvent.ItemStackInfo.class
                .getDeclaredConstructor(Item.class, int.class, CompoundTag.class);
        itemStackInfoConstructor.setAccessible(true);
    }

    @Test
    public void testSimpleShowText() {
        String text = "This is a test!";
        TestUtil.test(
                Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(text))),
                style().hoverEvent(showText(text(text))).build()
        );
    }

    @Test
    public void testShowTextWithColorAndBold() {
        String text = "A test... A what?";
        TestUtil.test(
                Style.EMPTY.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        Component.literal(text).withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD)
                )),
                style().hoverEvent(showText(text(text, NamedTextColor.DARK_RED, TextDecoration.BOLD))).build()
        );
    }

    @Test
    public void testShowItemWithoutNbt() throws ReflectiveOperationException {
        int count = 30;
        TestUtil.test(
                Style.EMPTY.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_ITEM,
                        itemStackInfoConstructor.newInstance(Items.DIAMOND_SWORD, count, null)
                )),
                style().hoverEvent(showItem(Key.key("diamond_sword"), count)).build()
        );
    }

    @Test
    public void testShowItemWithNbtAndCountOfOne() throws ReflectiveOperationException, IOException {
        int count = 1;

        CompoundBinaryTag tag = CompoundBinaryTag.builder()
                .putInt("Damage", 23)
                .put(
                        "Enchantments",
                        ListBinaryTag.builder(BinaryTagTypes.COMPOUND)
                                .add(CompoundBinaryTag.builder()
                                        .putString("id", "minecraft:mending")
                                        .putShort("lvl", (short) 1)
                                        .build())
                                .add(CompoundBinaryTag.builder()
                                        .putString("id", "minecraft:sharpness")
                                        .putShort("lvl", (short) 3)
                                        .build())
                                .build()
                )
                .build();

        TestUtil.test(
                Style.EMPTY.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_ITEM,
                        itemStackInfoConstructor.newInstance(Items.COBWEB, count, NBTAdapter.convert(tag))
                )),
                style().hoverEvent(showItem(
                        Key.key("cobweb"), count, BinaryTagHolder.encode(tag, TAG_STRING_CODEC)
                )).build()
        );
    }

    @Test
    public void testShowEntityWithoutName() {
        UUID uuid = UUID.randomUUID();
        TestUtil.test(
                Style.EMPTY.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_ENTITY,
                        new HoverEvent.EntityTooltipInfo(EntityType.BLAZE, uuid, Optional.empty())
                )),
                style().hoverEvent(showEntity(Key.key("blaze"), uuid)).build()
        );
    }

    @Test
    public void testShowEntityWithName() {
        UUID uuid = UUID.randomUUID();
        TestUtil.test(
                Style.EMPTY.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_ENTITY,
                        new HoverEvent.EntityTooltipInfo(
                                EntityType.CHICKEN, uuid,
                                Component.empty()
                                        .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)
                                        .append(Component.literal("| ").withStyle(ChatFormatting.OBFUSCATED))
                                        .append(Component.literal("Chicken Jockey"))
                                        .append(Component.literal(" |").withStyle(ChatFormatting.OBFUSCATED))
                        )
                )),
                style().hoverEvent(showEntity(
                        Key.key("chicken"), uuid,
                        text().color(NamedTextColor.GOLD)
                                .decorate(TextDecoration.BOLD)
                                .append(text().content("| ").decorate(TextDecoration.OBFUSCATED))
                                .append(text("Chicken Jockey"))
                                .append(text().content(" |").decorate(TextDecoration.OBFUSCATED))
                                .build()
                )).build()
        );
    }

    @AfterAll
    public static void close() {
        itemStackInfoConstructor.setAccessible(false);
    }
}