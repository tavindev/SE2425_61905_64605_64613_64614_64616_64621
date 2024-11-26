package com.sk89q.worldedit.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditCommandListScreen;
import org.lwjgl.glfw.GLFW;

public class FabricKeyHandler implements ClientModInitializer {

    private static KeyMapping openCommandListKey;

    @Override
    public void onInitializeClient() {
        openCommandListKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.fabricworldedit.open_command_list",
                GLFW.GLFW_KEY_H,
                "category.fabricworldedit.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openCommandListKey.isDown()) {
                openCommandListScreen(client);
            }
        });
    }

    private void openCommandListScreen(Minecraft client) {
        try {
            if (client.player != null) {
                client.setScreen(new WorldEditCommandListScreen());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}