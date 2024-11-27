package com.sk89q.worldedit.fabric.gui.screens;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldEditCommandListScreen extends Screen {

    private static final Component TITLE = Component.translatable("WorldEdit Commands");
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_PADDING = 5;

    private List<String> commands = List.of("setblock", "copy", "paste", "undo", "redo", "fill", "replace");
    private Map<String, String> commandDescriptions = new HashMap<>();

    public WorldEditCommandListScreen() {
        super(GameNarrator.NO_TITLE);
        commandDescriptions.put("setblock", "Coloca um bloco em uma posição específica.");
        commandDescriptions.put("copy", "Copia a seleção atual.");
        commandDescriptions.put("paste", "Cola a seleção copiada.");
        commandDescriptions.put("undo", "Desfaz a última ação.");
        commandDescriptions.put("redo", "Refaz a última ação desfeita.");
        commandDescriptions.put("fill", "Preenche uma área com blocos.");
        commandDescriptions.put("replace", "Substitui blocos em uma área.");
    }

    @Override
    protected void init() {
        int columns = 4;
        int xStart = (this.width - (columns * (BUTTON_WIDTH + BUTTON_PADDING) - BUTTON_PADDING)) / 2;
        int yStart = 40;

        for (int i = 0; i < commands.size(); i++) {
            int x = xStart + (i % columns) * (BUTTON_WIDTH + BUTTON_PADDING);
            int y = yStart + (i / columns) * (BUTTON_HEIGHT + BUTTON_PADDING);

            String command = commands.get(i);
            this.addRenderableWidget(Button.builder(
                            Component.translatable(command),
                            btn -> executeCommand(command)
                    ).bounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                    .tooltip(Tooltip.create(Component.literal(commandDescriptions.get(command)))) // Adiciona a tooltip
                    .build());
        }

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> this.onClose())
                .bounds(this.width / 2 - BUTTON_WIDTH / 2, this.height - 30, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, TITLE, this.width / 2, 10, 16777215);
        this.renderToolTip(pGuiGraphics, pMouseX, pMouseY); // Renderiza o tooltip se necessário
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
}