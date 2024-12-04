package com.sk89q.worldedit.fabric.gui.screens;

import com.sk89q.worldedit.fabric.gui.components.ScrollBar;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class WorldEditCommandEdit extends Screen {
    private static final Component MODE_LABEL = Component.translatable("Create Brush Tool");
    private static final Component DEFAULT_OPTION = Component.translatable("Select Brush");
    private static final Component DEFAULT_ITEM_OPTION = Component.translatable("Select Item");
    private static final ResourceLocation SLOT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/crafter.png");
    private static final int SLOT_U = 7;
    private static final int SLOT_V = 83;
    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;

    protected Button doneButton;
    protected Button cancelButton;
    protected Button dropdownButton;
    protected Button itemDropdownButton;

    private final List<Button> optionButtons = new ArrayList<>();
    private final List<Button> itemOptionButtons = new ArrayList<>();
    private final List<EditBox> brushInputs = new ArrayList<>();
    private boolean isDropdownVisible = false;
    private boolean isItemDropdownVisible = false;
    private Component selectedOption = DEFAULT_OPTION;
    private Component selectedItemOption = DEFAULT_ITEM_OPTION;
    private ScrollBar itemScrollBar;
    private int itemScrollOffset = 0;
    private static final int ITEMS_PER_PAGE = 5;
    private static final int ITEM_BUTTON_HEIGHT = 22;

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

        if (toolItemStack != ItemStack.EMPTY) {
            this.dropdownButton = this.addRenderableWidget(
                    Button.builder(selectedOption, (button) -> toggleDropdown())
                            .bounds(this.width / 2 - 150, this.height / 30 + 50, 140, 20)
                            .build()
            );

            int optionY = this.height / 30 + 72;
            String[] options = {"Cone", "Cube", "Cylinder", "Sphere"};
            for (String option : options) {
                Button optionButton = Button.builder(Component.literal(option), (button) -> selectOption(button))
                        .bounds(this.width / 2 - 150, optionY, 140, 20)
                        .build();
                optionButtons.add(optionButton);
                optionY += 22;
            }
        }

        this.itemDropdownButton = this.addRenderableWidget(
                Button.builder(selectedItemOption, (button) -> toggleItemDropdown())
                        .bounds(this.width / 2 + 20, this.height / 30 + 50, 140, 20)
                        .build()
        );

        BuiltInRegistries.ITEM.stream()
        .filter(item -> item.getDescriptionId().startsWith("block"))
        .forEach(item -> {
            item.getTooltipImage(new ItemStack(item));
            String itemName = item.getDescription().getString();
            Button itemOptionButton = Button.builder(Component.literal(itemName), (button) -> selectItemOption(button))
                    .bounds(this.width / 2 + 20, 0, 140, 20)
                    .build();
            itemOptionButtons.add(itemOptionButton);
        });

        setupItemDropdown();

        updateInputsBasedOnBrush(selectedOption.getString());
    }

    private void setupItemDropdown() {
        int contentHeight = itemOptionButtons.size() * ITEM_BUTTON_HEIGHT;
        int visibleHeight = ITEMS_PER_PAGE * ITEM_BUTTON_HEIGHT;

        itemScrollBar = new ScrollBar(this.width / 2 + 160, this.height / 30 + 50, visibleHeight, contentHeight);
        this.addRenderableWidget(itemScrollBar);

        itemScrollOffset = 0;
        updateItemOptionsDisplay();
    }

    private void toggleDropdown() {
        isDropdownVisible = !isDropdownVisible;
    }

    private void toggleItemDropdown() {
        isItemDropdownVisible = !isItemDropdownVisible;

        if (isItemDropdownVisible) {
            itemScrollOffset = 0;
            updateItemOptionsDisplay();
        } else {
            this.itemOptionButtons.forEach(this::removeWidget); // Hide buttons
        }
    }


    private void updateItemOptionsDisplay() {
        if (!isItemDropdownVisible) {
            return;
        }

        this.itemOptionButtons.forEach(this::removeWidget);

        int startIndex = itemScrollOffset / ITEM_BUTTON_HEIGHT;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, itemOptionButtons.size());

        for (int i = startIndex; i < endIndex; i++) {
            Button button = itemOptionButtons.get(i);
            button.setY(this.height / 30 + 72 + (i - startIndex) * ITEM_BUTTON_HEIGHT);
            this.addRenderableWidget(button);
        }
    }


    private void updateInputsBasedOnBrush(String brushType) {
        brushInputs.forEach(this::removeWidget);
        brushInputs.clear();

        int inputY = this.height / 30 + 100;

        switch (brushType) {
            case "Cone":
                brushInputs.add(createInput("brush.cone.base", inputY, 140));
                brushInputs.add(createInput("brush.cone.height", inputY, 140 + 150));
                break;
            case "Cube":
                brushInputs.add(createInput("brush.cube.width", inputY, 140));
                brushInputs.add(createInput("brush.cube.height", inputY, 140 + 150));
                brushInputs.add(createInput("brush.cube.depth", inputY + 30, 140));
                break;
            case "Cylinder":
                brushInputs.add(createInput("brush.cylinder.radius", inputY, 140));
                brushInputs.add(createInput("brush.cylinder.height", inputY, 140 + 150));
                break;
            case "Sphere":
                brushInputs.add(createInput("brush.sphere.radius", inputY, 140));
                break;
            default:
                break;
        }

        brushInputs.forEach(this::addRenderableWidget);
    }

    private EditBox createInput(String translationKey, int yPosition, int xPosition) {
        EditBox input = new EditBox(this.font, xPosition, yPosition, 140, 20, Component.translatable(translationKey));
        input.setMaxLength(50);
        return input;
    }

    private void selectOption(Button button) {
        this.selectedOption = button.getMessage();
        this.dropdownButton.setMessage(this.selectedOption);
        this.isDropdownVisible = false;
        updateInputsBasedOnBrush(selectedOption.getString());
    }

    private void selectItemOption(Button button) {
        this.selectedItemOption = button.getMessage();
        this.itemDropdownButton.setMessage(this.selectedItemOption);
        this.isItemDropdownVisible = false;
    }

    protected void onDone() {
        this.minecraft.setScreen(null);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.drawCenteredString(this.font, MODE_LABEL, this.width / 2, 10, 16777215);

        if (!toolItemStack.isEmpty()) {
            if (isDropdownVisible) {
                optionButtons.forEach(button -> button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick));
            }

            if (isItemDropdownVisible) {
                itemScrollBar.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

                // Render visible buttons and item images
                int startIndex = itemScrollOffset / ITEM_BUTTON_HEIGHT;
                int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, itemOptionButtons.size());

                for (int i = startIndex; i < endIndex; i++) {
                    Button button = itemOptionButtons.get(i);
                    button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

                    // Render item image next to the button
                    int itemX = button.getX() - 20;
                    int itemY = button.getY() + 2;

                    ItemStack itemStack = new ItemStack(BuiltInRegistries.ITEM.byId(i));
                    pGuiGraphics.renderItem(itemStack, itemX, itemY);
                }
            }
        }
    }

    private void adjustScrollOffset() {
        int maxOffset = Math.max(0, itemOptionButtons.size() - ITEMS_PER_PAGE);
        itemScrollOffset = Math.min(itemScrollOffset, maxOffset);
        itemScrollBar.setScrollOffset(itemScrollOffset / maxOffset);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (itemScrollBar.isMouseOver(pMouseX, pMouseY) && isItemDropdownVisible) {
            itemScrollBar.mouseClicked(pMouseX, pMouseY, pButton);
            return true;
        }

        if (isItemDropdownVisible) {
            for (Button button : itemOptionButtons) {
                if (button.isMouseOver(pMouseX, pMouseY)) {
                    button.onClick(pMouseX, pMouseY);
                    return true;
                }
            }
        }

        if (itemDropdownButton.isMouseOver(pMouseX, pMouseY)) {
            toggleItemDropdown();
            return true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (itemScrollBar.isMouseOver(mouseX, mouseY) && button == 0) {
            itemScrollBar.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

            // Adjust scroll offset directly
            int maxOffset = Math.max(0, itemOptionButtons.size() * ITEM_BUTTON_HEIGHT - ITEMS_PER_PAGE * ITEM_BUTTON_HEIGHT);
            itemScrollOffset = Math.max(0, Math.min(itemScrollOffset + (int) deltaY, maxOffset));

            updateItemOptionsDisplay(); // Update buttons based on new offset
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }



}
