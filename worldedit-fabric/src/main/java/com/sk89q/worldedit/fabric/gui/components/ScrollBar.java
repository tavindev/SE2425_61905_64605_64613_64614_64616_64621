package com.sk89q.worldedit.fabric.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ScrollBar extends AbstractWidget implements GuiEventListener {

    private final int contentHeight;
    private int scrollOffset = 0;
    private final int viewHeight;
    private final int barHeight;
    private final int barWidth = 10;
    private boolean isDragging = false;
    private int dragStartY;

    public ScrollBar(int x, int y, int viewHeight, int contentHeight) {
        super(x, y, 10, viewHeight, Component.empty());
        this.viewHeight = viewHeight;
        this.contentHeight = contentHeight;
        this.barHeight = Math.max(10, (int) ((float) viewHeight / contentHeight * viewHeight));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int barY = this.getY() + (int) ((float) scrollOffset / (contentHeight - viewHeight) * (viewHeight - barHeight));
        guiGraphics.fill(this.getX(), barY, this.getX() + barWidth, barY + barHeight, 0xFFAAAAAA);
    }

    public void scroll(double delta) {
        scrollOffset = (int) Math.max(0, Math.min(scrollOffset - delta * 10, contentHeight - viewHeight));
    }


    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (isMouseOver(mouseX, mouseY)) {
            this.scroll(delta * 10); // Aumente ou diminua o multiplicador conforme desejado.
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX >= this.getX() && mouseX <= this.getX() + barWidth && mouseY >= this.getY() && mouseY <= this.getY() + viewHeight) {
            isDragging = true;
            dragStartY = (int) mouseY;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging) {
            int dragDelta = (int) (mouseY - dragStartY);
            scrollOffset = Math.max(0, Math.min(scrollOffset + dragDelta, contentHeight - viewHeight));
            dragStartY = (int) mouseY;
            return true;
        }
        return false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public int getScrollOffset() {
        return scrollOffset;
    }
}