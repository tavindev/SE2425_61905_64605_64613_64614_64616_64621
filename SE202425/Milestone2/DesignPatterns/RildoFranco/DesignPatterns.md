# Design Patterns Report

## Author: [Rildo Franco]
## Date: [11/06/2024]

---

### 1. Adapter

worldedit-bukkit\adapters\adapter-1.20.2\src\main\java\com\sk89q\worldedit\bukkit\adapter\impl\v1_20_R2\PaperWeightServerLevelDelegateProxy.java

The class PaperweightServerLevelDelegateProxy uses a PaperweightAdapter to adapt and manipulate Minecraft-specific objects and states. The invoke method intercepts method calls and delegates them to the appropriate methods in the PaperweightAdapter or handles them directly.

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

worldedit-bukkit\adapters\adapter-1.20.2\src\main\java\com\sk89q\worldedit\bukkit\adapter\impl\v1_20_R2\PaperWeightAdapter.java

```java
Tag fromNative(LinTag<?> foreign) {
        if (foreign == null) {
            return null;
        }
        if (foreign instanceof LinCompoundTag compoundTag) {
            net.minecraft.nbt.CompoundTag tag = new CompoundTag();
            for (var entry : compoundTag.value().entrySet()) {
                tag.put(entry.getKey(), fromNative(entry.getValue()));
            }
            return tag;
        } else if (foreign instanceof LinByteTag byteTag) {
            return ByteTag.valueOf(byteTag.valueAsByte());
        } else if (foreign instanceof LinByteArrayTag byteArrayTag) {
            return new ByteArrayTag(byteArrayTag.value());
        } else if (foreign instanceof LinDoubleTag doubleTag) {
            return DoubleTag.valueOf(doubleTag.valueAsDouble());
        } else if (foreign instanceof LinFloatTag floatTag) {
            return FloatTag.valueOf(floatTag.valueAsFloat());
        } else if (foreign instanceof LinIntTag intTag) {
            return IntTag.valueOf(intTag.valueAsInt());
        } else if (foreign instanceof LinIntArrayTag intArrayTag) {
            return new IntArrayTag(intArrayTag.value());
        } else if (foreign instanceof LinLongArrayTag longArrayTag) {
            return new LongArrayTag(longArrayTag.value());
        } else if (foreign instanceof LinListTag<?> listTag) {
            net.minecraft.nbt.ListTag tag = new ListTag();
            for (var t : listTag.value()) {
                tag.add(fromNative(t));
            }
            return tag;
        } else if (foreign instanceof LinLongTag longTag) {
            return LongTag.valueOf(longTag.valueAsLong());
        } else if (foreign instanceof LinShortTag shortTag) {
            return ShortTag.valueOf(shortTag.valueAsShort());
        } else if (foreign instanceof LinStringTag stringTag) {
            return StringTag.valueOf(stringTag.value());
        } else if (foreign instanceof LinEndTag) {
            return EndTag.INSTANCE;
        } else {
            throw new IllegalArgumentException("Don't know how to make NMS " + foreign.getClass().getCanonicalName());
        }
    }

    public net.minecraft.world.level.block.state.BlockState adapt(BlockState blockState) {
        int internalId = BlockStateIdAccess.getBlockStateId(blockState);
        return Block.stateById(internalId);
    }
```

---

### 2. Proxy

worldedit-fabric\src\main\java\com\sk89q\worldedit\fabric\internal\FabricServerLevelDelegateProxy.java

The Proxy Pattern is used in the FabricServerLevelDelegateProxy class to control access to the ServerLevel class. The proxy class wraps the ServerLevel class and may handle some lighter responsibilities itself, while delegating substantive requests to the ServerLevel class. This allows for additional functionality to be added without modifying the ServerLevel class directly. Both the proxy class and the ServerLevel class implement a common interface, allowing for polymorphism. It Wraps the real subject class (ServerLevel).

The newInstance method creates a proxy instance of WorldGenLevel that uses FabricServerLevelDelegateProxy as its invocation handler.

```java
public static WorldGenLevel newInstance(EditSession editSession, ServerLevel serverLevel) {
    return (WorldGenLevel) Proxy.newProxyInstance(
        serverLevel.getClass().getClassLoader(),
        serverLevel.getClass().getInterfaces(),
        new FabricServerLevelDelegateProxy(editSession, serverLevel)
    );
}

```

The FabricServerLevelDelegateProxy class implements the InvocationHandler interface, which allows it to intercept method calls on the proxy instance.

The FabricServerLevelDelegateProxy class provides implementations for methods such as getBlockEntity, getBlockState, setBlock, removeBlock, and addEntity, which are called by the invoke method.

```java
@Nullable
private BlockEntity getBlockEntity(BlockPos blockPos) {
    BlockEntity tileEntity = this.serverLevel.getChunkAt(blockPos).getBlockEntity(blockPos);
    if (tileEntity == null) {
        return null;
    }
    BlockEntity newEntity = tileEntity.getType().create(blockPos, getBlockState(blockPos));
    newEntity.loadWithComponents(
        NBTConverter.toNative(
            this.editSession.getFullBlock(FabricAdapter.adapt(blockPos)).getNbtReference().getValue()
        ),
        this.serverLevel.registryAccess()
    );

    return newEntity;
}

private BlockState getBlockState(BlockPos blockPos) {
    return FabricAdapter.adapt(this.editSession.getBlock(FabricAdapter.adapt(blockPos)));
}

private boolean setBlock(BlockPos blockPos, BlockState blockState) {
    try {
        return editSession.setBlock(FabricAdapter.adapt(blockPos), FabricAdapter.adapt(blockState));
    } catch (MaxChangedBlocksException e) {
        throw new RuntimeException(e);
    }
}

private boolean removeBlock(BlockPos blockPos, boolean bl) {
    try {
        return editSession.setBlock(FabricAdapter.adapt(blockPos), BlockTypes.AIR.getDefaultState());
    } catch (MaxChangedBlocksException e) {
        throw new RuntimeException(e);
    }
}

private boolean addEntity(Entity entity) {
    Vector3 pos = FabricAdapter.adapt(entity.getPosition(0.0f));
    Location location = new Location(FabricAdapter.adapt(serverLevel), pos.x(), pos.y(), pos.z());
    BaseEntity baseEntity = new FabricEntity(entity).getState();
    return editSession.createEntity(location, baseEntity) != null;
}
```

---

### 3. Pattern Name
- **Description:** Description of the pattern.
- **Justification:** Why we you chose this pattern

---

### Summary
- **Patterns Chosen:** 
- **Benefits:** Do they benefit the system somehow?
