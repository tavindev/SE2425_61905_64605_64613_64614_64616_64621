package com.sk89q.worldedit.command.tool.brush;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.SelectableStructureSession;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;

public abstract class AbstractStructureBrush implements Brush {
    public final void build(EditSession editSession, BlockVector3 position, Pattern pattern, double size) throws MaxChangedBlocksException {
        // TODO: Find a better way to set the position
        if (editSession instanceof SelectableStructureSession session)
            session.setPosition(position);


        this.createStructure(editSession, position, pattern, size);
    }

    abstract int createStructure(EditSession editSession, BlockVector3 position, Pattern pattern, double size) throws MaxChangedBlocksException;

    protected static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    protected static double lengthSq(double x, double z) {
        return (x * x) + (z * z);
    }

    public SelectableStructureSession createEditSession(LocalSession session, Actor actor) {
        return session.createSelectableStructureSession(actor, this);
    }
}
