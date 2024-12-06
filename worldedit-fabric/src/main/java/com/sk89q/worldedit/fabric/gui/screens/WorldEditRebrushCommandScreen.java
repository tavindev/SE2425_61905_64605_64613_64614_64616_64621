package com.sk89q.worldedit.fabric.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WorldEditRebrushCommandScreen extends Screen {

    private static final Component TITLE = Component.literal("Select Rebrush Scale");
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 20;
    private static final int PADDING = 10;

    public WorldEditRebrushCommandScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 2 - (BUTTON_HEIGHT * 3 + PADDING * 2);

        for (int i = 1; i <= 6; i++) {
            int level = i;
            this.addRenderableWidget(Button.builder(Component.literal("Scale " + level), btn -> executeRebrushCommand(level))
                    .bounds(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                    .build());
            y += BUTTON_HEIGHT + PADDING;
        }

        this.addRenderableWidget(Button.builder(Component.literal("Close"), btn -> this.onClose())
                .bounds(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    private void executeRebrushCommand(int level) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.getConnection() != null) {
            mc.player.connection.sendCommand("rebrush " + level);
            this.onClose();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.fill(0, 0, this.width, this.height, 0x90000000);

        guiGraphics.drawCenteredString(this.font, TITLE.getString(), this.width / 2, PADDING, 0xFFFFFF);

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