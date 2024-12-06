package com.sk89q.worldedit.fabric;

import com.sk89q.worldedit.fabric.gui.screens.WorldEditBrushCommandScreen;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditCommandScreen;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditQuickMenu;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditRebrushCommandScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class FabricKeyHandler implements ClientModInitializer {

    private static KeyMapping openCommandListKey;
    private static KeyMapping openQuickMenuKey;
    private static KeyMapping openBrushToolKey;
    private static KeyMapping openRebrushToolKey;

    @Override
    public void onInitializeClient() {
        openCommandListKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.fabricworldedit.open_command_list",
                GLFW.GLFW_KEY_H,
                "category.fabricworldedit.general"
        ));

        openQuickMenuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.fabricworldedit.open_quick_menu",
                GLFW.GLFW_KEY_G,
                "category.fabricworldedit.general"
        ));

        openBrushToolKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.fabricworldedit.open_brush_tool",
                GLFW.GLFW_KEY_B,
                "category.fabricworldedit.general"
        ));

        openRebrushToolKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.fabricworldedit.open_rebrush_tool",
                GLFW.GLFW_KEY_R,
                "category.fabricworldedit.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openCommandListKey.isDown()) {
                toggleScreen(client, new WorldEditCommandScreen());
            }
            if (openQuickMenuKey.isDown()) {
                toggleScreen(client, new WorldEditQuickMenu());
            }
            if (openBrushToolKey.isDown()) {
                toggleScreen(client, new WorldEditBrushCommandScreen());
            }
            if (openRebrushToolKey.isDown()) {
                toggleScreen(client, new WorldEditRebrushCommandScreen());
            }
        });
    }

    /**
     * Toggles a screen: opens it if it's not open, closes it if it's the current screen.
     */
    private void toggleScreen(Minecraft client, Screen screen) {
        if (client.player != null) {
            if (client.screen != null && client.screen.getClass().equals(screen.getClass())) {
                client.setScreen(null);
            } else {
                client.setScreen(screen);
            }
        }
    }
}