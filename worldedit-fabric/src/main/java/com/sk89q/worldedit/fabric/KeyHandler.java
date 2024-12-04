package com.sk89q.worldedit.fabric;


import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.sk89q.worldedit.entity.Player;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import com.sk89q.worldedit.command.tool.SelectionWand;

public class KeyHandler implements ClientModInitializer {

    private static KeyMapping keySelectStructure;

    @Override
    public void onInitializeClient() {
        keySelectStructure = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.worldedit.select_structure",
                GLFW.GLFW_KEY_M,
                "key.categories.worldedit"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(keySelectStructure.isDown()){
                handlePrimarySelection();
            }
        });

    }

    private void handlePrimarySelection() {
        try{
            Minecraft mc = Minecraft.getInstance();
            SelectionWand selectionWand = new SelectionWand();
            FabricPlatform serverP = new FabricPlatform(FabricWorldEdit.inst);
            assert mc.player != null;
            selectionWand.actPrimary(serverP, null, (Player) mc.player.clientLevel, null, ((Player) mc.player.clientLevel).getBlockOn(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
        public boolean actSecondary(Platform server, LocalConfiguration config, Player player, LocalSession session, Location clicked, @Nullable Direction face) {
        RegionSelector selector = session.getRegionSelector(player.getWorld());
        BlockVector3 blockPoint = clicked.toVector().toBlockPoint();

        if (!session.toggleSelectStructure(clicked) && selector.selectPrimary(blockPoint, ActorSelectorLimits.forActor(player))) {
            selector.explainPrimarySelection(player, session, blockPoint);
        }

        return true;
    }

    @Override
    public boolean actPrimary(Platform server, LocalConfiguration config, Player player, LocalSession session, Location clicked, @Nullable Direction face) {
        RegionSelector selector = session.getRegionSelector(player.getWorld());
        BlockVector3 blockPoint = clicked.toVector().toBlockPoint();

        if (selector.selectSecondary(blockPoint, ActorSelectorLimits.forActor(player))) {
            selector.explainSecondarySelection(player, session, blockPoint);
        }
        return true;
    }
     */



}
