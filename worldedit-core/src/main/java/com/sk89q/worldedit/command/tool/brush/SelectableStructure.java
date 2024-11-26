package com.sk89q.worldedit.command.tool.brush;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.history.change.BlockChange;
import com.sk89q.worldedit.history.change.Change;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.block.BlockTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectableStructure {
    private boolean selected = false;
    private final EditSession session;
    private Pattern pattern;

    List<BlockVector3> positions = new ArrayList<>();

    public SelectableStructure(EditSession session) {
        this.session = session;
        this.pattern = BlockTypes.COBBLESTONE.getDefaultState();

        for (Iterator<Change> it = session.getChangeSet().forwardIterator(); it.hasNext(); ) {
            Change change = it.next();

            if (change instanceof BlockChange blockChange) {
                BlockVector3 position = blockChange.position();
                positions.add(position);

                pattern = blockChange.current();
            }
        }

    }

    private boolean locationIsInStructure(Location location) {
        for (BlockVector3 position : positions) {
            if (position.equals(location.toVector().toBlockPoint())) {
                return true;
            }
        }

        return false;
    }

    public boolean select(Location clicked) {
        if (!locationIsInStructure(clicked)) {
            return false;
        }

        Pattern pattern = selected ? this.pattern : BlockTypes.LIGHT_BLUE_STAINED_GLASS.getDefaultState();

        for (BlockVector3 position : positions) {
            try {
                session.setBlock(position, pattern);
            } catch (MaxChangedBlocksException e) {
                throw new RuntimeException(e);
            }
        }

        session.close();

        selected = !selected;

        return true;
    }

    public boolean deselect() {
        if (!selected) {
            return false;
        }

        for (BlockVector3 position : positions) {
            try {
                session.setBlock(position, pattern);
            } catch (MaxChangedBlocksException e) {
                throw new RuntimeException(e);
            }
        }

        session.close();

        selected = false;

        return true;
    }


}
