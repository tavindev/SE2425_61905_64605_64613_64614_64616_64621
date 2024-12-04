package com.sk89q.worldedit.fabric.gui.screens;

import com.sk89q.worldedit.fabric.data.WorldEditData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class WorldEditQuickMenu extends Screen {

    private static final Component TITLE = Component.literal("Quick Menu");
    private static final int MENU_WIDTH = 160;
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 18;
    private static final int PADDING = 3;

    public WorldEditQuickMenu() {
        super(TITLE);
    }

    @Override
    protected void init() {
        int centerX = this.width - MENU_WIDTH - PADDING;
        int y = PADDING + 10;

        this.addRenderableWidget(Button.builder(Component.literal("Edit Shortcuts"), btn -> {
            Minecraft.getInstance().setScreen(new WorldEditShortcutSelectionScreen());
        }).bounds(centerX + PADDING + 60, y, 80, BUTTON_HEIGHT).build());

        y += BUTTON_HEIGHT + PADDING;

        String[] shortcutCommands = WorldEditData.getShortcutCommands();
        for (int i = 0; i < shortcutCommands.length; i++) {
            String shortcut = shortcutCommands[i];
            String label = (shortcut != null) ? shortcut : "Shortcut " + (i + 1);
            this.addRenderableWidget(Button.builder(Component.literal(label), btn -> {
                if (shortcut == null) {
                    Minecraft.getInstance().setScreen(new WorldEditShortcutSelectionScreen());
                } else {
                    executeCommand(shortcut);
                }
            }).bounds(centerX + PADDING, y, BUTTON_WIDTH, BUTTON_HEIGHT).build());
            y += BUTTON_HEIGHT + PADDING;
        }

        y += PADDING;

        this.addRenderableWidget(Button.builder(Component.literal("All Commands"), btn -> {
            Minecraft.getInstance().setScreen(new WorldEditCommandScreen());
        }).bounds(centerX + PADDING + 80, y, 60, BUTTON_HEIGHT).build());

        y += BUTTON_HEIGHT + PADDING;

        List<String> recentCommands = WorldEditData.getRecentCommands();
        for (String command : recentCommands) {
            this.addRenderableWidget(Button.builder(Component.literal(command), btn -> executeCommand(command))
                    .bounds(centerX + PADDING, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                    .build());
            y += BUTTON_HEIGHT + PADDING;
        }

        y += 2 * PADDING;

        this.addRenderableWidget(Button.builder(Component.literal("Close"), btn -> this.onClose())
                .bounds(centerX + PADDING, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    private void executeCommand(String command) {
        Minecraft mc = Minecraft.getInstance();
        if ("brush".equals(command)) {
            mc.setScreen(new WorldEditBrushCommandScreen());
        } else if (mc.player != null && mc.getConnection() != null) {
            mc.player.connection.sendCommand(command);
        }
        this.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int centerX = this.width - MENU_WIDTH - PADDING;
        int titleY = PADDING;

        guiGraphics.fill(centerX, 0, centerX + MENU_WIDTH, this.height, 0xC0000000);

        guiGraphics.drawCenteredString(this.font, TITLE.getString(), centerX + MENU_WIDTH / 2, titleY, 0xFFFFFF);

        guiGraphics.drawString(this.font, "Shortcuts", centerX + PADDING, PADDING + 15, 0xFFFFFF);

        guiGraphics.drawString(this.font, "Recently Used", centerX + PADDING, (5 * PADDING) + (5 * BUTTON_HEIGHT), 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }
}