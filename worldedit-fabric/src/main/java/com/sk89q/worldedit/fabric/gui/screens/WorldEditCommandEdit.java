package com.sk89q.worldedit.fabric.gui.screens;


import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WorldEditCommandEdit extends Screen {
    private static final Component MODE_LABEL = Component.translatable("Create Brush Tool");
    private static final ResourceLocation SLOT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/crafter.png");
    private static final int SLOT_U = 7; // U coordinate in the texture
    private static final int SLOT_V = 83; // V coordinate in the texture
    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;

    protected EditBox commandEdit;
    protected Button doneButton;
    protected Button cancelButton;
    private ItemStack toolItemStack = ItemStack.EMPTY;

    public WorldEditCommandEdit() {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();
        ItemStack heldItem = mc.player.getMainHandItem();

        if (heldItem.getItem() != null) {
            toolItemStack = heldItem;
        }

        this.doneButton = this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, ($$0x) -> this.onDone())
                        .bounds(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20)
                        .build()
        );
        this.cancelButton = this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_CANCEL, p_315822_ -> this.onClose())
                        .bounds(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20)
                        .build()
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

    private void drawCenteredStringWithWrap(GuiGraphics pGuiGraphics, Component text, int x, int y, int width, int color) {
        for (var line : this.font.split(text, width)) {
            pGuiGraphics.drawString(this.font, line, x - this.font.width(line) / 2, y, color, false);
            y += 9;
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, MODE_LABEL, this.width / 2, 20, 16777215);

        // Display the held tool item within a scaled slot
        if (!toolItemStack.isEmpty()) {
            int slotX = this.width / 2;
            int slotY = this.height / 2;
            float scale = 3.0f;

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(slotX, slotY, 0);
            pGuiGraphics.pose().scale(scale, scale, 1.0f);
            pGuiGraphics.pose().translate(-SLOT_WIDTH / 2.0f, -SLOT_HEIGHT / 2.0f, 0);
            pGuiGraphics.blit(SLOT_TEXTURE, 0, 0, SLOT_U, SLOT_V, SLOT_WIDTH, SLOT_HEIGHT);
            pGuiGraphics.pose().popPose();

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(slotX, slotY, 0);
            pGuiGraphics.pose().scale(scale, scale, 1.0f);
            pGuiGraphics.pose().translate(-8, -8, 0);
            pGuiGraphics.renderItem(toolItemStack, 0, 0);
            pGuiGraphics.pose().popPose();
        } else {
            pGuiGraphics.drawCenteredString(this.font, Component.translatable("No tool in hand"), this.width / 2, this.height / 2, 16711680);
        }
    }


    public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
        this.renderTransparentBackground($$0);
    }
}

