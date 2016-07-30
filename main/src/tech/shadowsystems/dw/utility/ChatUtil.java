package tech.shadowsystems.dw.utility;

import org.bukkit.ChatColor;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class ChatUtil {

    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formatWithPrefix(String text) {
        return format("&c&lDeathSwap &6>> &f" + text);
    }

}
