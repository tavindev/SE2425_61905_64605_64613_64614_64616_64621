# Design Patterns Report

## Author: [Rildo Franco]
## Date: [11/06/2024]

---

### 1. Adapter

worldedit-bukkit\adapters\adapter-1.20.2\src\main\java\com\sk89q\worldedit\bukkit\adapter\impl\v1_20_R2\PaperWeightServerLevelDelegateProxy.java

The class PaperweightServerLevelDelegateProxy implements the InvocationHandler interface and acts as a proxy for ServerLevel objects. It uses a PaperweightAdapter to adapt and manipulate Minecraft-specific objects and states. The invoke method intercepts method calls on the proxy instance and delegates them to the appropriate methods in the PaperweightAdapter or handles them directly.

```java

@Nullable
private BlockEntity getBlockEntity(BlockPos blockPos) {
    BlockEntity tileEntity = this.serverLevel.getChunkAt(blockPos).getBlockEntity(blockPos);
    if (tileEntity == null) {
        return null;
    }
    BlockEntity newEntity = tileEntity.getType().create(blockPos, getBlockState(blockPos));
    newEntity.load((CompoundTag) adapter.fromNative(this.editSession.getFullBlock(BlockVector3.at(blockPos.getX(), blockPos.getY(), blockPos.getZ())).getNbtReference().getValue()));

    return newEntity;
}

private BlockState getBlockState(BlockPos blockPos) {
    return adapter.adapt(this.editSession.getBlock(BlockVector3.at(blockPos.getX(), blockPos.getY(), blockPos.getZ())));
}

private boolean setBlock(BlockPos blockPos, BlockState blockState) {
    try {
        return editSession.setBlock(BlockVector3.at(blockPos.getX(), blockPos.getY(), blockPos.getZ()), adapter.adapt(blockState));
    } catch (MaxChangedBlocksException e) {
        throw new RuntimeException(e);
    }
}

```

---

### 2. Pattern Name
- **Description:** Description of the pattern.
- **Justification:** Why we you chose this pattern

---

### 3. Pattern Name
- **Description:** Description of the pattern.
- **Justification:** Why we you chose this pattern

---

### Summary
- **Patterns Chosen:** 
- **Benefits:** Do they benefit the system somehow?
