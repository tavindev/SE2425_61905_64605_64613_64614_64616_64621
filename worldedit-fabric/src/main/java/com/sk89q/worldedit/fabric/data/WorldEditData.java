package com.sk89q.worldedit.fabric.data;

import java.util.ArrayList;
import java.util.List;

public class WorldEditData {

    private static final List<String> recentCommands = new ArrayList<>();
    private static final String[] shortcutCommands = new String[3];

    public static List<String> getRecentCommands() {
        return recentCommands;
    }

    public static void addRecentCommand(String command) {
        recentCommands.remove(command);
        recentCommands.addFirst(command);
        if (recentCommands.size() > 4) {
            recentCommands.removeLast();
        }
    }

    public static String[] getShortcutCommands() {
        return shortcutCommands;
    }

    public static void setShortcutCommand(int index, String command) {
        if (index >= 0 && index < shortcutCommands.length) {
            shortcutCommands[index] = command;
        }
    }
}
