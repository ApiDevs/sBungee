package rip.api.util;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class CC {
    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines)
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines) {
            if (line != null)
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return toReturn;
    }

    public static final String BLUE = ChatColor.BLUE.toString();

    public static final String AQUA = ChatColor.AQUA.toString();

    public static final String YELLOW = ChatColor.YELLOW.toString();

    public static final String RED = ChatColor.RED.toString();

    public static final String GRAY = ChatColor.GRAY.toString();

    public static final String GOLD = ChatColor.GOLD.toString();

    public static final String GREEN = ChatColor.GREEN.toString();

    public static final String WHITE = ChatColor.WHITE.toString();

    public static final String BLACK = ChatColor.BLACK.toString();

    public static final String BOLD = ChatColor.BOLD.toString();

    public static final String ITALIC = ChatColor.ITALIC.toString();

    public static final String STRIKE_THROUGH = ChatColor.STRIKETHROUGH.toString();

    public static final String RESET = ChatColor.RESET.toString();

    public static final String MAGIC = ChatColor.MAGIC.toString();

    public static final String DARK_BLUE = ChatColor.DARK_BLUE.toString();

    public static final String DARK_AQUA = ChatColor.DARK_AQUA.toString();

    public static final String DARK_GRAY = ChatColor.DARK_GRAY.toString();

    public static final String DARK_GREEN = ChatColor.DARK_GREEN.toString();

    public static final String DARK_PURPLE = ChatColor.DARK_PURPLE.toString();

    public static final String DARK_RED = ChatColor.DARK_RED.toString();

    public static final String LIGHT_PURPLE = ChatColor.LIGHT_PURPLE.toString();

    public static final String CHAT_BAR;

    public static final String MENU_BAR = ChatColor.STRIKETHROUGH.toString() + "------------------------";

    public static final String SB_BAR;

    public static String CONSOLE_NAME;

    public static String BLANK_LINE;

    public static  String prefix;

    public static final String GRAY_LINE = " ";

    static {
        CHAT_BAR = ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";
        SB_BAR = ChatColor.STRIKETHROUGH.toString() + "----------------------";
        String consoleName = CONSOLE_NAME;
    }
}