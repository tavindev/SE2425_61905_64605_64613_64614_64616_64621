package com.sk89q.worldedit.fabric.gui.screens;


import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

import java.util.Objects;


public class WorldEditCommandEdit extends Screen {
    private static final Component MODE_LABEL = Component.translatable("Create Brush Tool");
    protected EditBox commandEdit;
    protected Button rightButton;
    protected Button leftButton;
    protected Button doneButton;
    protected Button cancelButton;

    public WorldEditCommandEdit() {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    protected void init() {
        this.doneButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, ($$0x) -> this.onDone()).bounds(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20).build());
        this.cancelButton = this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_CANCEL, p_315822_ -> this.onClose()).bounds(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20).build()
        );
        this.commandEdit = new EditBox(this.font, this.width / 2 - 150, 50, 300, 20, Component.translatable("advMode.command"));
        this.commandEdit.setMaxLength(32500);
        this.addWidget(this.commandEdit);
    }

    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(this.commandEdit);
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.commandEdit.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.commandEdit.setValue(s);
    }



    protected void onDone() {
        this.minecraft.setScreen(null);
    }

    private void drawCenteredStringWithWrap(GuiGraphics pGuiGraphics, Font font, FormattedText formattedText, int i, int j, int k, int l) {
        for(FormattedCharSequence formattedCharSequence : font.split(formattedText, k)) {
            pGuiGraphics.drawString(font, formattedCharSequence, i - font.width(formattedCharSequence) / 2, j, l, false);
            Objects.requireNonNull(font);
            j += 9;
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, MODE_LABEL, this.width / 2, 20, 16777215);
        Component TEXT_TEST = Component.translatable("How many dragons are in the world? The magic is dead here, everyone who stands in these lands are spiritless souls. The last dragon stands in the only world who it can, The End.");
        drawCenteredStringWithWrap(pGuiGraphics, this.font, TEXT_TEST, this.width / 2, 50, this.width / 2, 16777215);
    }

    public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
        this.renderTransparentBackground($$0);
    }

}
