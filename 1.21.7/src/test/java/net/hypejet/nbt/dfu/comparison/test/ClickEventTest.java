package net.hypejet.nbt.dfu.comparison.test;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.event.ClickEvent.changePage;
import static net.kyori.adventure.text.event.ClickEvent.copyToClipboard;
import static net.kyori.adventure.text.event.ClickEvent.openUrl;
import static net.kyori.adventure.text.event.ClickEvent.runCommand;
import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;
import static net.kyori.adventure.text.format.Style.style;

final class ClickEventTest {
    @Test
    public void testOpenUrl() {
        String url = "https://github.com/";
        TestUtil.test(
                Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)),
                style().clickEvent(openUrl(url)).build()
        );
    }

    @Test
    public void testRunCommand() {
        String command = "/say Hello, world!";
        TestUtil.test(
                Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)),
                style().clickEvent(runCommand(command)).build()
        );
    }

    @Test
    public void testSuggestCommand() {
        String command = "/?";
        TestUtil.test(
                Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)),
                style().clickEvent(suggestCommand(command)).build()
        );
    }

    @Test
    public void testChangePage() {
        int page = 56;
        TestUtil.test(
                Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, String.valueOf(page))),
                style().clickEvent(changePage(page)).build()
        );
    }

    @Test
    public void testCopyToClipboard() {
        String contents = "I love JUnit tests!";
        TestUtil.test(
                Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, contents)),
                style().clickEvent(copyToClipboard(contents)).build()
        );
    }
}