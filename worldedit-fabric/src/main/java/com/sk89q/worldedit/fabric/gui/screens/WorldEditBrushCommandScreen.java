package com.sk89q.worldedit.fabric.gui.screens;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.fabric.gui.components.ScrollBar;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractCommandBlockEditScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldEditBrushCommandScreen extends Screen {
    private static final Component MODE_LABEL = Component.translatable("Create Brush Tool");
    private static final Component DEFAULT_OPTION = Component.translatable("Select Brush");
    private static final Component DEFAULT_ITEM_OPTION = Component.translatable("Select Item");
    private static final Component NO_TOOL_SELECTED = Component.translatable("No Tool Selected");
    private static final ResourceLocation SLOT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/crafter.png");
    private static final int SLOT_U = 7;
    private static final int SLOT_V = 83;
    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;

    protected Button doneButton;
    protected Button cancelButton;
    protected Button dropdownButton;
    protected Button itemDropdownButton;
    protected EditBox radiusBox;

    private final Map<String, ItemStack> itemStackCache = new HashMap<>();

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
    private ItemStack selectedBlockStack = ItemStack.EMPTY;
    private ItemStack toolItemStack = ItemStack.EMPTY;
    private Minecraft mc;

    private String brushTypeSelected = "cuboid";

    public WorldEditBrushCommandScreen() {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    protected void init() {
        this.mc = Minecraft.getInstance();
        cacheItemStacks();
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
                            .bounds(this.width /2 - 150, this.height / 30 + 50, 150, 20)
                            .build()
            );
            int optionY = this.height / 30 + 72;
            String[] options = {"Cone", "Cube", "Cylinder", "Sphere"};
            for (String option : options) {
                Button optionButton = Button.builder(Component.literal(option), (button) -> selectOption(button))
                        .bounds(this.width /2 - 150, optionY, 150, 20)
                        .build();
                optionButtons.add(optionButton);
                optionY += 22;
            }

            this.itemDropdownButton = this.addRenderableWidget(
                    Button.builder(selectedItemOption, (button) -> toggleItemDropdown())
                            .bounds(this.width / 2 + 14, this.height / 30 + 50, 140, 20)
                            .build()
            );


            BuiltInRegistries.ITEM.stream()
                    .filter(item -> item.getDescriptionId().startsWith("block"))
                    .forEach(item -> {
                        item.getTooltipImage(new ItemStack(item));
                        String itemName = item.getDescription().getString();
                        Button itemOptionButton = Button.builder(Component.literal(itemName), (button) -> selectItemOption(button))
                                .bounds(this.width / 2 + 14, 0, 140, 20)
                                .build();
                        itemOptionButtons.add(itemOptionButton);
                    });

            this.radiusBox = new EditBox(this.font, this.width / 2 - 150, 100, 300, 20, Component.literal("RADIUS")) {};
            this.radiusBox.setMaxLength(32500);
            this.addRenderableWidget(this.radiusBox);
            setupItemDropdown();
        }
    }

    private void cacheItemStacks() {
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item.getDescriptionId().startsWith("block"))
                .forEach(item -> {
                    String itemName = item.getDescription().getString();
                    itemStackCache.put(itemName, new ItemStack(item));
                });
    }


    private void setupItemDropdown() {
        if (itemOptionButtons.isEmpty()) {
            itemScrollBar = null;
            return;
        }

        int contentHeight = itemOptionButtons.size() * ITEM_BUTTON_HEIGHT;
        int visibleHeight = ITEMS_PER_PAGE * ITEM_BUTTON_HEIGHT;

        itemScrollBar = new ScrollBar(this.width / 2 + 180, this.height / 30 + 50, visibleHeight, contentHeight);
        this.addRenderableWidget(itemScrollBar);

        itemScrollOffset = 0;
        updateItemOptionsDisplay();
    }



    private void toggleDropdown() {
        this.isDropdownVisible = !this.isDropdownVisible;
        System.out.println("Dropdown visibility toggled: " + this.isDropdownVisible);
    }

    private void toggleItemDropdown() {
        isItemDropdownVisible = !isItemDropdownVisible;

        if (isItemDropdownVisible) {
            if (itemScrollBar == null && !itemOptionButtons.isEmpty()) {
                setupItemDropdown();
            }
            itemScrollOffset = 0;
            updateItemOptionsDisplay();
        } else {
            this.itemOptionButtons.forEach(this::removeWidget);
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
                brushInputs.add(createInput("Base Radius", inputY, this.width / 2 - 150));
                brushInputs.add(createInput("Height", inputY + 30, this.width / 2 - 150));
                break;
            case "Cube":
                brushInputs.add(createInput("Width", inputY, this.width / 2 - 150));
                brushInputs.add(createInput("Height", inputY + 30, this.width / 2 - 150));
                brushInputs.add(createInput("Depth", inputY + 60, this.width / 2 - 150));
                break;
            case "Cylinder":
                brushInputs.add(createInput("Radius", inputY, this.width / 2 - 150));
                brushInputs.add(createInput("Height", inputY + 30, this.width / 2 - 150));
                break;
            case "Sphere":
                brushInputs.add(createInput("Radius", inputY, this.width / 2 - 150));
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

        switch (button.getMessage().getString()) {
            case "Sphere":
                this.brushTypeSelected = "sphere";
                break;
            case "Cylinder":
                this.brushTypeSelected = "cyl";
                break;
            case "Cube":
                this.brushTypeSelected = "cuboid";
                break;
            default:
                this.brushTypeSelected = "unknown";
                break;
        }

        this.isDropdownVisible = false;
    }


    private void selectItemOption(Button button) {
        this.selectedItemOption = button.getMessage();
        this.itemDropdownButton.setMessage(this.selectedItemOption);

        String itemName = button.getMessage().getString();
        this.selectedBlockStack = itemStackCache.getOrDefault(itemName, ItemStack.EMPTY);

        this.isItemDropdownVisible = false;
    }

    protected void onDone() {
        String radiusText = radiusBox.getValue();
        int radius = Integer.parseInt(radiusText);
        assert this.mc.player != null;
        if(radius > 0 && radius < 7) {
            this.mc.player.sendSystemMessage(Component.literal("radius: " + radiusText + ";" + "block: " + this.selectedBlockStack.getDescriptionId() + ";" ));
        } else {
            this.mc.player.sendSystemMessage(Component.literal("Invalid radius! Using default value of 0."));
        }

        String command = "/brush apply " + this.brushTypeSelected + " " + radius + " set " + this.selectedBlockStack.getDescriptionId().replace("block.minecraft.", "");
        mc.player.connection.sendCommand(command);

        this.minecraft.setScreen(null);

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.drawCenteredString(this.font, MODE_LABEL, this.width / 2, 10, 16777215);

        if (toolItemStack != ItemStack.EMPTY) {
            if (isDropdownVisible) {
                for (Button button : optionButtons) {
                    button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }
            }

            int frameX = this.width / 2 - 100;
            int frameY = 10;
            float scale = 1.5f;

            int scaledFrameX = (int) (frameX / scale);
            int scaledFrameY = (int) (frameY / scale);

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().scale(scale, scale, 1.0f);
            pGuiGraphics.blit(SLOT_TEXTURE, scaledFrameX, scaledFrameY, SLOT_U, SLOT_V, SLOT_WIDTH, SLOT_HEIGHT);
            pGuiGraphics.pose().translate(0.5, 0.5, 0.5);
            pGuiGraphics.renderItem(toolItemStack, scaledFrameX, scaledFrameY);
            pGuiGraphics.pose().popPose();


            if (!selectedBlockStack.isEmpty()) {

                frameX = this.width / 2 + 100;
                frameY = 10;
                scale = 1.5f;

                scaledFrameX = (int) (frameX / scale);
                scaledFrameY = (int) (frameY / scale);

                pGuiGraphics.pose().pushPose();
                pGuiGraphics.pose().scale(scale, scale, 1.0f);

                pGuiGraphics.blit(SLOT_TEXTURE, scaledFrameX, scaledFrameY, SLOT_U, SLOT_V, SLOT_WIDTH, SLOT_HEIGHT);
                pGuiGraphics.pose().translate(0.5, 0.5, 0.5);
                pGuiGraphics.renderItem(selectedBlockStack, scaledFrameX, scaledFrameY);
                pGuiGraphics.pose().popPose();
            }

            if (isItemDropdownVisible) {
                itemScrollBar.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

                int startIndex = itemScrollOffset / ITEM_BUTTON_HEIGHT;
                int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, itemOptionButtons.size());

                for (int i = startIndex; i < endIndex; i++) {
                    Button button = itemOptionButtons.get(i);
                    button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

                    int itemX = button.getX() - 20;
                    int itemY = button.getY() + 2;

                    String itemName = button.getMessage().getString();
                    System.out.println(itemName);
                    ItemStack cachedStack = itemStackCache.getOrDefault(itemName, ItemStack.EMPTY);
                    pGuiGraphics.renderItem(cachedStack, itemX, itemY);
                }
            }
        } else {
            pGuiGraphics.drawCenteredString(this.font, NO_TOOL_SELECTED, this.width / 2, this.height / 2 - this.font.lineHeight, 16711680);
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

        if (isItemDropdownVisible && itemScrollBar != null && itemScrollBar.isMouseOver(pMouseX, pMouseY)) {
            itemScrollBar.mouseClicked(pMouseX, pMouseY, pButton);
            return true;
        }

        if (isItemDropdownVisible && itemOptionButtons != null) {
            for (Button button : itemOptionButtons) {
                if (button.isMouseOver(pMouseX, pMouseY)) {
                    button.onClick(pMouseX, pMouseY);
                    return true;
                }
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (isItemDropdownVisible && itemScrollBar != null && itemScrollBar.isMouseOver(mouseX, mouseY) && button == 0) {
            // Increase scroll speed by multiplying deltaY
            int scrollSpeed = 3; // Adjust this value for faster or slower scrolling
            itemScrollOffset = Math.max(0, Math.min(itemScrollOffset + (int) (deltaY * scrollSpeed),
                    Math.max(0, itemOptionButtons.size() * ITEM_BUTTON_HEIGHT - ITEMS_PER_PAGE * ITEM_BUTTON_HEIGHT)));

            updateItemOptionsDisplay();
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }





}
