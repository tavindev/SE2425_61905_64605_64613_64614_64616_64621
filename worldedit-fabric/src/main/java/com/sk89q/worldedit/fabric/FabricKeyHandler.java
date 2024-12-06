package com.sk89q.worldedit.fabric;

import com.sk89q.worldedit.fabric.gui.screens.WorldEditBrushCommandScreen;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditCommandScreen;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditQuickMenu;
import com.sk89q.worldedit.fabric.gui.screens.WorldEditRebrushCommandScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class FabricKeyHandler implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        registerScreen(new WorldEditCommandScreen(), "key.fabricworldedit.open_command_list", GLFW.GLFW_KEY_H, "category.fabricworldedit.general");
        registerScreen(new WorldEditQuickMenu(), "key.fabricworldedit.open_quick_menu", GLFW.GLFW_KEY_G, "category.fabricworldedit.general");
        registerScreen(new WorldEditBrushCommandScreen(), "key.fabricworldedit.open_brush_tool", GLFW.GLFW_KEY_B, "category.fabricworldedit.general");
        registerScreen(new WorldEditRebrushCommandScreen(), "key.fabricworldedit.open_rebrush_tool", GLFW.GLFW_KEY_R, "category.fabricworldedit.general");
    }

    private void registerScreen(Screen screen, String string1, int i, String string2) {
        KeyMapping keyMapping = new KeyMapping(string1, i, string2);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!keyMapping.isDown() || client.player == null) return;

            if (client.screen != null && client.screen.getClass().equals(screen.getClass())) {
                client.setScreen(null);
            } else {
                client.setScreen(screen);
            }
        });
    }

}
