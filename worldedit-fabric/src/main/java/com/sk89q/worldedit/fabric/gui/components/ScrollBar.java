package com.sk89q.worldedit.fabric.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ScrollBar extends AbstractWidget implements GuiEventListener {

    private final int contentHeight;
    private int scrollOffset = 0;
    private final int viewHeight;
    private final int barHeight;
    private final int barWidth = 10;
    private GuiGraphics guiGraphics;
    private int mouseX;
    private int mouseY;
    private float partialTicks;

    public ScrollBar(int x, int y, int viewHeight, int contentHeight) {
        super(x, y, 10, viewHeight, Component.empty());
        this.viewHeight = viewHeight;
        this.contentHeight = contentHeight;
        this.barHeight = Math.max(10, (int) ((float) viewHeight / contentHeight * viewHeight));
    }


//    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
//        this.guiGraphics = guiGraphics;
//        this.mouseX = mouseX;
//        this.mouseY = mouseY;
//        this.partialTicks = partialTicks;
//        int barY = this.getY() + (int) ((float) scrollOffset / contentHeight * viewHeight);
//        guiGraphics.fill(this.getX(), barY, this.getX() + barWidth, barY + barHeight, 0xFFAAAAAA);
//    }



    public void scroll(double delta) {
        scrollOffset = (int) Math.max(0, Math.min(scrollOffset - delta * 10, contentHeight - viewHeight));
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.scroll(delta);
        return true;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {

    }

//    @Override
//    public void updateNarration(NarrationElementOutput narrationElementOutput) {
//        // Implement narration if needed
//    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}