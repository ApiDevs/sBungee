package rip.api.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ComponentBuilderUtils {
    public static BaseComponent[] buildHighlightedTextComponent(String text, String highlightedText) {
        return (new ComponentBuilder(CC.translate(text))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate(highlightedText)))).create();
    }

    public static BaseComponent[] buildHighlightedTextComponentWithClick(String text, String highlightedText, String openUrl) {
        return (new ComponentBuilder(CC.translate(text))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate(highlightedText)))).event(new ClickEvent(ClickEvent.Action.OPEN_URL, openUrl)).create();
    }
}
