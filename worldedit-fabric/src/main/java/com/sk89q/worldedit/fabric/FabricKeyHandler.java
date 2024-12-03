package com.sk89q.worldedit.fabric;

import com.sk89q.worldedit.fabric.gui.screens.WorldEditBrushCommandScreen;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditCommandScreen;
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
                openCommandListScreen(client);
            }
            if (openQuickMenuKey.isDown()) {

            }

            if (openBrushToolKey.isDown()) {
                openBrushToolScreen(client);
            }

        });
    }

    private void openCommandListScreen(Minecraft client) {
        try {
            if (client.player != null) {
                client.setScreen(new WorldEditCommandScreen());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void openBrushToolScreen(Minecraft client) {
        try {
            if (client.player != null) {
                client.setScreen(new WorldEditBrushCommandScreen());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}