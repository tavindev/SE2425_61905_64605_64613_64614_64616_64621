package com.sk89q.worldedit.fabric.gui.screens;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.List;

public class WorldEditCommandListScreen extends Screen {

    private static final Component TITLE = Component.translatable("WorldEdit Commands");

    private List<Button> commandButtons;

    public WorldEditCommandListScreen() {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    protected void init() {

        commandButtons = List.of(
                this.addRenderableWidget(Button.builder(Component.translatable("command.setblock"), ($$0) -> this.executeCommand("setblock")).bounds(this.width / 2 - 75, 50, 150, 20).build()),
                this.addRenderableWidget(Button.builder(Component.translatable("command.copy"), ($$0) -> this.executeCommand("copy")).bounds(this.width / 2 - 75, 80, 150, 20).build()),
                this.addRenderableWidget(Button.builder(Component.translatable("command.paste"), ($$0) -> this.executeCommand("paste")).bounds(this.width / 2 - 75, 110, 150, 20).build())
        );

        // Botão para sair do menu
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, ($$0) -> this.onClose()).bounds(this.width / 2 - 75, this.height - 50, 150, 20).build());
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        // Título do menu
        pGuiGraphics.drawCenteredString(this.font, TITLE, this.width / 2, 20, 16777215);
    }

    private void executeCommand(String command) {
        //Minecraft.getInstance().player.sendMessage(new TextComponent("Executing command: " + command), false);
        // Aqui você pode implementar a lógica para executar o comando no jogo
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(null); // Fechar a GUI
    }
}