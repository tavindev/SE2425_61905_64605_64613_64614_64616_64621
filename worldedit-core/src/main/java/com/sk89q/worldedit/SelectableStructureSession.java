package com.sk89q.worldedit;

import com.sk89q.worldedit.command.tool.brush.AbstractStructureBrush;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.inventory.BlockBag;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.eventbus.EventBus;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SelectableStructureSession extends EditSession {
    private final List<BlockVector3> positions = new ArrayList<>();
    private BlockType pattern;
    private final AbstractStructureBrush brush;
    private boolean selected = false;
    private BlockVector3 position;

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
    SelectableStructureSession(AbstractStructureBrush brush, EventBus eventBus, World world, int maxBlocks, @Nullable BlockBag blockBag, @Nullable Actor actor, boolean tracing) {
        super(eventBus, world, maxBlocks, blockBag, actor, tracing);

        this.brush = brush;
    }

    public AbstractStructureBrush getBrush() {
        return brush;
    }

    public boolean locationIsInStructure(Location location) {
        for (BlockVector3 position : positions) {
            if (position.equals(location.toVector().toBlockPoint())) {
                return true;
            }
        }

        return false;
    }

    public boolean toggleSelect(Location location) throws WorldEditException {
        if (!locationIsInStructure(location)) {
            return false;
        }


        if (!select()) {
            deselect();
        }

        return true;
    }

    private boolean select() throws WorldEditException {
        if (selected) {
            return false;
        }

        for (BlockVector3 position : positions) {
            try {
                super.setBlock(position, BlockTypes.LIGHT_BLUE_STAINED_GLASS.getDefaultState(), Stage.BEFORE_HISTORY);
            } catch (MaxChangedBlocksException e) {
                throw new RuntimeException(e);
            }
        }

        this.close();

        selected = !selected;

        return true;
    }

    public boolean deselect() throws WorldEditException {
        if (!selected) {
            return false;
        }

        for (BlockVector3 position : positions) {
            try {
                super.setBlock(position, this.pattern.getDefaultState(), Stage.BEFORE_HISTORY);
            } catch (MaxChangedBlocksException e) {
                throw new RuntimeException(e);
            }
        }

        this.close();

        selected = false;

        return true;
    }

    public void resize(EditSession session, double size) throws MaxChangedBlocksException {
        if (this.position == null) {
            return;
        }

        this.brush.build(session, position, this.pattern.getDefaultState(), size);
    }

    public void setPosition(BlockVector3 position) {
        this.position = position;
    }

    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(BlockVector3 position, B block, Stage stage) throws WorldEditException {
        positions.add(position);
        this.pattern = block.getBlockType();

        return super.setBlock(position, block, stage);
    }
}
