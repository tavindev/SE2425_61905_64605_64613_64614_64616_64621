package com.sk89q.worldedit.fabric.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ScrollBar extends AbstractWidget {
    private final int contentHeight;
    private int scrollOffset = 0;
    private final int trackHeight;
    private boolean dragging = false;
    private int dragStartY = 0;
    private int dragStartOffset = 0;

    public ScrollBar(int x, int y, int height, int contentHeight) {
        super(x, y, 12, height, Component.empty());
        this.trackHeight = height;
        this.contentHeight = contentHeight;
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void handleMouseScroll(double delta) {
        int scrollAmount = (int) -(delta * 10);
        scrollOffset = Math.max(0, Math.min(scrollOffset + scrollAmount, contentHeight - trackHeight));
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        handleMouseScroll(delta);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isMouseOver(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragStartY = (int) mouseY;
            dragStartOffset = scrollOffset;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging && button == 0) {
            int dy = (int) (mouseY - dragStartY);
            scrollOffset = Math.max(0, Math.min(dragStartOffset + dy, contentHeight - trackHeight));
            return true;
        }
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int scrollbarHeight = Math.max((int) ((float) trackHeight / contentHeight * trackHeight), 10);
        int scrollbarY = this.getY() + (int) ((float) scrollOffset / contentHeight * (trackHeight - scrollbarHeight));

        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.trackHeight, 0xFF444444);

        guiGraphics.fill(this.getX(), scrollbarY, this.getX() + this.width, scrollbarY + scrollbarHeight, 0xFFAAAAAA);
        guiGraphics.fill(this.getX() + 1, scrollbarY + 1, this.getX() + this.width - 1, scrollbarY + scrollbarHeight - 1, 0xFF666666);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.HINT, Component.translatable("narration.scrollbar"));
    }
}