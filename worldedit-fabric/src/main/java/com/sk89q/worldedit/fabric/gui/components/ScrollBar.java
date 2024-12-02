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

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int scrollbarHeight = Math.max((int) ((float) trackHeight / contentHeight * trackHeight), 10);
        int scrollbarY = this.getY() + (int) ((float) scrollOffset / contentHeight * (trackHeight - scrollbarHeight));

        guiGraphics.fill(this.getX(), scrollbarY, this.getX() + this.width, scrollbarY + scrollbarHeight, 0xFF888888); // Scrollbar body
        guiGraphics.fill(this.getX() + 1, scrollbarY + 1, this.getX() + this.width - 1, scrollbarY + scrollbarHeight - 1, 0xFFCCCCCC); // Inner
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.HINT, Component.translatable("narration.scrollbar"));
    }
}