package com.sk89q.worldedit.fabric.gui.screens;

import com.sk89q.worldedit.fabric.data.WorldEditData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WorldEditBindShortcutScreen extends Screen {

    private final String command;

    public WorldEditBindShortcutScreen() {
        this(null);
    }

    public WorldEditBindShortcutScreen(String command) {
        super(Component.literal("Select Shortcut Slot"));
        this.command = command;
    }

    @Override
    protected void init() {
        int xCenter = this.width / 2;
        int yStart = this.height / 3;
        String[] commands = WorldEditData.getShortcutCommands();

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            if (commands[i] != null) {
                this.addRenderableWidget(Button.builder(Component.literal("Replace: " + commands[i]), btn -> {
                    Minecraft.getInstance().setScreen(new WorldEditShortcutSelectionScreen(finalI));
                }).bounds(xCenter - 100, yStart + i * 30, 200, 20).build());
            } else {
                this.addRenderableWidget(Button.builder(Component.literal("Set Slot " + (i + 1)), btn -> {
                    Minecraft.getInstance().setScreen(new WorldEditShortcutSelectionScreen(finalI));
                }).bounds(xCenter - 100, yStart + i * 30, 200, 20).build());
            }
        }


        this.addRenderableWidget(Button.builder(Component.literal("Cancel"), btn -> this.onClose())
                .bounds(xCenter - 100, yStart + 120, 200, 20).build());
    }
}
