package com.sk89q.worldedit.fabric.gui.screens;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.platform.PlatformCommandManager;
import com.sk89q.worldedit.fabric.gui.components.ScrollBar;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;

import java.util.HashMap;
import java.util.Map;

public class WorldEditCommandListScreen extends Screen {

    private static final Component TITLE = Component.translatable("WorldEdit Commands");
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_PADDING = 5;
    private static final int SCROLLBAR_WIDTH = 12;

    private final Map<String, String> commandDescriptions = new HashMap<>();
    private ScrollBar scrollBar;
    private int totalButtonHeight;
    private int maxScrollOffset;

    public WorldEditCommandListScreen() {
        super(GameNarrator.NO_TITLE);
        loadCommands();
        calculateTotalHeight();
    }

    private void loadCommands() {
        PlatformCommandManager commandManager = WorldEdit.getInstance().getPlatformManager().getPlatformCommandManager();
        commandManager.getCommandManager().getAllCommands().forEach(cmd -> {
            String commandName = cmd.getName();
            String description = String.valueOf(cmd.getDescription()).split("=")[1].split(",")[0];
            commandDescriptions.put(commandName, description);
        });
    }

    private void calculateTotalHeight() {
        int totalButtons = commandDescriptions.size();
        int rows = (totalButtons / 4) + (totalButtons % 4 == 0 ? 0 : 1);
        totalButtonHeight = rows * (BUTTON_HEIGHT + BUTTON_PADDING);
        maxScrollOffset = Math.max(0, totalButtonHeight - this.height + 60);
    }

    @Override
    protected void init() {
        int columns = 4;
        int xStart = (this.width - (columns * (BUTTON_WIDTH + BUTTON_PADDING) + SCROLLBAR_WIDTH)) / 2;
        int yStart = 40;

        scrollBar = new ScrollBar(this.width - SCROLLBAR_WIDTH - 5, 40, this.height - 80, totalButtonHeight);
        this.addRenderableWidget(scrollBar);

        int index = 0;
        for (Map.Entry<String, String> entry : commandDescriptions.entrySet()) {
            int x = xStart + (index % columns) * (BUTTON_WIDTH + BUTTON_PADDING);
            int y = yStart + (index / columns) * (BUTTON_HEIGHT + BUTTON_PADDING);
            String command = entry.getKey();
            this.addRenderableWidget(Button.builder(
                            Component.literal(command),
                            btn -> executeCommand(command)
                    ).bounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                    .tooltip(Tooltip.create(Component.literal(entry.getValue())))
                    .build());
            index++;
        }

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> this.onClose())
                .bounds(this.width / 2 - BUTTON_WIDTH / 2, this.height - 30, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        updateButtonPositions();
    }

    private void updateButtonPositions() {
        int yStart = 40;
        int columns = 4;
        int xStart = (this.width - (columns * (BUTTON_WIDTH + BUTTON_PADDING) + SCROLLBAR_WIDTH)) / 2;
        int index = 0;

        for (GuiEventListener listener : this.children()) {
            if (listener instanceof Button button && !button.getMessage().equals(CommonComponents.GUI_DONE)) {
                int y = yStart + (index / columns) * (BUTTON_HEIGHT + BUTTON_PADDING) - scrollBar.getScrollOffset();
                button.setY(y);
                index++;
            }
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (scrollBar.mouseScrolled(mouseX, mouseY, delta)) {
            updateButtonPositions();
            return true;
        }
        return scrollBar.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        scrollBar.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, TITLE, this.width / 2, 10, 0xFFFFFF);
    }

    private void executeCommand(String command) {
        // Implementar a execução do comando aqui.
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(null);
    }
}