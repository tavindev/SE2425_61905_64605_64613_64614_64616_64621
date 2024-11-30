package com.sk89q.worldedit.fabric.gui.screens;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.platform.PlatformCommandManager;
import com.sk89q.worldedit.fabric.gui.components.ScrollBar;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
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

    private final Map<String, String> commandDescriptions = new HashMap<>();

    private ScrollBar scrollBar;
    private final int totalButtonHeight;
    private final int maxScrollOffset;

    public WorldEditCommandListScreen() {
        super(GameNarrator.NO_TITLE);
        loadCommands();

        int totalButtons = commandDescriptions.size();
        int rows = (totalButtons / 4) + (totalButtons % 4 == 0 ? 0 : 1); // Divide os botões por 4 colunas
        totalButtonHeight = rows * (BUTTON_HEIGHT + BUTTON_PADDING);
        maxScrollOffset = Math.max(0, totalButtonHeight - this.height + 50); // Ajuste para não ultrapassar a altura da tela
    }

    private void loadCommands() {
        PlatformCommandManager commandManager = WorldEdit.getInstance().getPlatformManager().getPlatformCommandManager();

        commandManager.getCommandManager().getAllCommands().forEach(cmd -> {
            String commandName = cmd.getName();
            String description = String.valueOf(cmd.getDescription()).split("=")[1].split(",")[0];
            commandDescriptions.put(commandName, description);
        });
    }

    @Override
    protected void init() {
        int columns = 4;
        int xStart = (this.width - (columns * (BUTTON_WIDTH + BUTTON_PADDING) - BUTTON_PADDING)) / 2;
        int yStart = 40;

        scrollBar = new ScrollBar(this.width - 15, 40, this.height - 80, totalButtonHeight);
        this.addRenderableWidget(scrollBar);

        // Add buttons using scrollBar.getScrollOffset() to adjust their y position
        int index = 0;
        for (Map.Entry<String, String> entry : commandDescriptions.entrySet()) {
            int x = xStart + (index % columns) * (BUTTON_WIDTH + BUTTON_PADDING);
            int y = yStart + (index / columns) * (BUTTON_HEIGHT + BUTTON_PADDING) - scrollBar.getScrollOffset();

            String command = entry.getKey();
            this.addRenderableWidget(Button.builder(
                            Component.literal(command),
                            btn -> executeCommand(command)
                    ).bounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                    .tooltip(Tooltip.create(Component.literal(entry.getValue())))  // Tooltip com a descrição
                    .build());
            index++;
        }

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> this.onClose())
                .bounds(this.width / 2 - BUTTON_WIDTH / 2, this.height - 30, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, TITLE, this.width / 2, 10, 16777215);
        this.renderToolTip(pGuiGraphics, pMouseX, pMouseY);
    }


    private void renderToolTip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }

    private void executeCommand(String command) {
        //Minecraft.getInstance().player.command("worldedit " + command);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(null);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return scrollBar.mouseScrolled(mouseX, mouseY, delta);
    }
}