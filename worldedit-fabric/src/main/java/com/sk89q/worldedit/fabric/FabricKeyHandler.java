package com.sk89q.worldedit.fabric;

import com.sk89q.worldedit.fabric.gui.screens.WorldEditBrushCommandScreen;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditCommandScreen;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditQuickMenu;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class FabricKeyHandler implements ClientModInitializer {

    private static KeyMapping openCommandListKey;
    private static KeyMapping openQuickMenuKey;
    private static KeyMapping openBrushToolKey;

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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openCommandListKey.isDown()) {
                toggleScreen(client, WorldEditCommandScreen.class, () -> openCommandListScreen(client));
            }
            if (openQuickMenuKey.isDown()) {
                toggleScreen(client, WorldEditQuickMenu.class, () -> openQuickMenuScreen(client));
            }
            if (openBrushToolKey.isDown()) {
                toggleScreen(client, WorldEditBrushCommandScreen.class, () -> openBrushToolScreen(client));
            }
        });
    }

    /**
     * Toggles a screen: opens it if it's not open, closes it if it's the current screen.
     */
    private void toggleScreen(Minecraft client, Class<?> screenClass, Runnable openScreen) {
        if (screenClass.isInstance(client.screen)) {
            client.setScreen(null);
        } else {
            openScreen.run();
        }
    }

    private void openCommandListScreen(Minecraft client) {
        if (client.player != null) {
            client.setScreen(new WorldEditCommandScreen());
        }
    }

    private void openQuickMenuScreen(Minecraft client) {
        if (client.player != null) {
            client.setScreen(new WorldEditQuickMenu());
        }
    }

    private void openBrushToolScreen(Minecraft client) {
        if (client.player != null) {
            client.setScreen(new WorldEditBrushCommandScreen());
        }
    }
}
