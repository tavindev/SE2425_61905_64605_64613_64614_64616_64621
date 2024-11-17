package com.sk89q.worldedit.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditCommandEdit;
import org.lwjgl.glfw.GLFW;

public class FabricKeyHandler implements ClientModInitializer {

    private static KeyMapping openCommandBlockGUIKey;

    @Override
    public void onInitializeClient() {
        openCommandBlockGUIKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.fabricworldedit.open_command_block_gui",
                GLFW.GLFW_KEY_H,
                "category.fabricworldedit.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openCommandBlockGUIKey.isDown()) {
                openCommandBlockScreen(client);
            }
        });
    }


    private void openCommandBlockScreen(Minecraft client) {
        try {
            if (client.player != null) {
                client.setScreen(new WorldEditCommandEdit());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
