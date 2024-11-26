package com.sk89q.worldedit.bukkit;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldedit.LocalSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import com.sk89q.worldedit.WorldEdit;

import java.util.HashSet;
import java.util.Set;

public class PlayerPreviewListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        org.bukkit.entity.Player bukkitPlayer = event.getPlayer();

        Player wePlayer = BukkitAdapter.adapt(bukkitPlayer);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(wePlayer);

        if (session != null && session.isPreviewActive()) {
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                .getEditSession(session.getSelectionWorld(), -1);

            // Clear previous preview
            session.clearPreview();

            // Calculate the new preview
            BlockVector3 target = BukkitAdapter.asBlockVector(bukkitPlayer.getLocation());
            Set<BlockVector3> previewBlocks = calculateBrushShape(target);

            // Add the new preview
            session.addPreviewBlocks(previewBlocks, editSession);
        }
    }

    private Set<BlockVector3> calculateBrushShape(BlockVector3 center) {
        Set<BlockVector3> blocks = new HashSet<>();
        int radius = 3; // Example radius
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        blocks.add(center.add(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }
}
