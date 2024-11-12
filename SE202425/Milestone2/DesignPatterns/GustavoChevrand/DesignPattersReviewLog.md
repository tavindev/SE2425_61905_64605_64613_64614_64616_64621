# Design Patterns Review Log

## 1. Template method

Author: Rodrigo Castro 64621

Although the `AbstractDirectionConverter` class is relatively simple, the template pattern is effectively applied. This is because there is no specification of how the `convertDirection` method is implemented, but rather how it is used in its template method `convert`. The responsibility of providing the implementation of this method lies with the subclasses, and not the parent class. Hence why the abstract keyword is specified when `AbstractDirectionConverter` defines `convertDirection`.

----

## 2. Chain of Responsibility

Author: Nicolas Nascimento 61905

There are some extents that do not fulfill the requirements to be a handler in the CoR pattern, i.e, some extents always delegate to the next extent in the chain. The CoR is characterized by having a chain of handlers that either process or delegate the request to the next handler.

The `SurvivalModeExtent` class is a valid handler. If the condition in the if statement evaluates to `true`, the method executes logic and returns true, terminating the request chain. If it evaluates to `false`, it calls the next handler in the chain.

Location: worldedit-core/src/main/java/com/sk89q/worldedit/extent/world/SurvivalModeExtent.java

```java
public class SurvivalModeExtent extends AbstractDelegateExtent {
    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(BlockVector3 location, B block) throws WorldEditException {
        if (toolUse && block.getBlockType().getMaterial().isAir()) {
            world.simulateBlockMine(location);
            return true;
        } else {
            // Can't be an inlined check due to inconsistent generic return type
            if (stripNbt) {
                return super.setBlock(location, block.toBaseBlock((LinCompoundTag) null));
            } else {
                return super.setBlock(location, block);
            }
        }
    }
}
```

The `ChunkLoadingExtent` class, on the other hand, will always delegate to the next extent in the chain.

Location: worldedit-core/src/main/java/com/sk89q/worldedit/extent/world/ChunkLoadingExtent.java

```java
public class ChunkLoadingExtent extends AbstractDelegateExtent {
    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(BlockVector3 location, B block) throws WorldEditException {
        if (enabled) {
            world.checkLoadedChunk(location);
        }
        return super.setBlock(location, block);
    }
}

```

For that reason, it is not very clear if the CoR is effectively applied, since not all extents fulfill the requirements of a request handler in the chain.

----

## 3. Proxy
Author: Rildo Franco 64605

The `Proxy` pattern is explicitly defined and utilized in the `FabricServerLevelDelegateProxy` class, which acts as a proxy for accessing an underlying ServerLevel instance. The essence of this pattern lies in the `invoke` method, where method calls on the proxy are intercepted. This enables custom handling for specific methods (e.g., `getBlockState`, `setBlock`) or delegating unhandled calls to the actual `ServerLevel` object.
