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
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WorldEditCommandEdit extends Screen {
    private static final Component MODE_LABEL = Component.translatable("Create Brush Tool");
    private static final Component DEFAULT_OPTION = Component.translatable("Select Brush");
    private static final ResourceLocation SLOT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/crafter.png");
    private static final int SLOT_U = 7;
    private static final int SLOT_V = 83;
    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;

    protected EditBox commandEdit;
    protected Button doneButton;
    protected Button cancelButton;
    protected Button dropdownButton;

    private List<Button> optionButtons = new ArrayList<>();
    private boolean isDropdownVisible = false;
    private Component selectedOption = DEFAULT_OPTION;

    private ItemStack toolItemStack = ItemStack.EMPTY;
    private Vec3 playerCamVec;

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


        if(toolItemStack != ItemStack.EMPTY) {
            this.dropdownButton = this.addRenderableWidget(
                    Button.builder(selectedOption, (button) -> toggleDropdown())
                            .bounds(this.width /2 - 75, this.height / 30 + 50, 150, 20)
                            .build()
            );

            // Dropdown Options
            int optionY = this.height / 30 + 72;
            String[] options = {"Cone", "Cube", "Cylinder", "Sphere"};
            for (String option : options) {
                Button optionButton = Button.builder(Component.literal(option), (button) -> selectOption(button))
                        .bounds(this.width /2 - 75, optionY, 150, 20)
                        .build();
                optionButtons.add(optionButton);
                optionY += 22;
            }
        }

        // Command Edit Box
        this.commandEdit = new EditBox(this.font, this.width / 2 - 150, 50, 300, 20, Component.translatable("advMode.command"));
        this.commandEdit.setMaxLength(32500);
        this.addWidget(this.commandEdit);
    }

    private void toggleDropdown() {
        isDropdownVisible = !isDropdownVisible;
    }

    private void selectOption(Button button) {
        this.selectedOption = button.getMessage();
        this.dropdownButton.setMessage(this.selectedOption);
        this.isDropdownVisible = false; // Close dropdown after selection
    }

    protected void onDone() {
        this.minecraft.setScreen(null);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        // Screen Title
        pGuiGraphics.drawCenteredString(this.font, MODE_LABEL, this.width / 2, 10, 16777215);

        // Render dropdown options if visible

        // Display the held tool item within a scaled slot
        if (!toolItemStack.isEmpty()) {
            if (isDropdownVisible) {
                for (Button button : optionButtons) {
                    button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }

            int slotX = this.width / 4;
            int slotY = this.height / 4;
            float scale = 2.0f;

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

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (isDropdownVisible) {
            for (Button button : optionButtons) {
                if (button.isMouseOver(pMouseX, pMouseY)) {
                    button.onClick(pMouseX, pMouseY);
                    return true;
                }
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
