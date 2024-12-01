package com.sk89q.worldedit;

import com.sk89q.worldedit.command.tool.brush.AbstractStructureBrush;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.inventory.BlockBag;
import com.sk89q.worldedit.util.eventbus.EventBus;
import com.sk89q.worldedit.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class RebrushSession extends EditSession {
    private final List<SelectableStructureSession> undoSessions = new LinkedList<>();

    /**
     * Construct the object with a maximum number of blocks and a block bag.
     *
     * @param eventBus  the event bus
     * @param world     the world
     * @param maxBlocks the maximum number of blocks that can be changed, or -1 to use no limit
     * @param blockBag  an optional {@link BlockBag} to use, otherwise null
     * @param actor     the actor that owns the session
     * @param tracing   if tracing is enabled. An actor is required if this is {@code true}
     */
    RebrushSession(EventBus eventBus, World world, int maxBlocks, @Nullable BlockBag blockBag, @Nullable Actor actor, boolean tracing) {
        super(eventBus, world, maxBlocks, blockBag, actor, tracing);
    }

    public void rebrush(SelectableStructureSession session, double scale) throws MaxChangedBlocksException {
        this.undoSessions.add(session);

        session.undo(this);
        session.resize(this, scale);
    }

    @Override
    public void undo(EditSession editSession) {
        super.undo(editSession);

        for (EditSession session : undoSessions) {
            session.redo(session);
        }
    }

    @Override
    public void redo(EditSession editSession) {
        for (EditSession session : undoSessions) {
            session.undo(session);
        }

        super.redo(editSession);
    }


}
