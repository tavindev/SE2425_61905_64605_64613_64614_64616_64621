package com.sk89q.worldedit.fabric.gui.screens;

import com.sk89q.worldedit.fabric.data.WorldEditData;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WorldEditShortcutSelectionScreen extends Screen {

    private final String command;

    public WorldEditShortcutSelectionScreen() {
        this(null);
    }

    public WorldEditShortcutSelectionScreen(String command) {
        super(Component.literal("Select Shortcut Slot"));
        this.command = command;
    }

    @Override
    protected void init() {
        int xCenter = this.width / 2;
        int yStart = this.height / 3;

        for (int i = 0; i < 3; i++) {
            int slotIndex = i;
            this.addRenderableWidget(Button.builder(Component.literal("Set Slot " + (i + 1)), btn -> {
                WorldEditData.setShortcutCommand(slotIndex, command);
                this.onClose();
            }).bounds(xCenter - 100, yStart + i * 30, 200, 20).build());
        }

        this.addRenderableWidget(Button.builder(Component.literal("Cancel"), btn -> this.onClose())
                .bounds(xCenter - 100, yStart + 120, 200, 20).build());
    }
}
