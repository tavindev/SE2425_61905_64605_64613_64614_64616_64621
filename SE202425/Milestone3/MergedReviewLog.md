# MergedDocReviewLog

## Author: Gustavo Chevrand 64616

## Use cases

### 1. Create Solid Cylinder  

Author: Rodrigo Castro 64621

The player is the primary actor because they issue the command, while the server is the secondary actor because it performs operations on the world, such as placing blocks. The use case is correct.

### 2. Select Snapshot 

Author: Rildo Franco 64605

The player is the primary actor because they issue the command. There is no secondary actor. The use case is correct.

### 3. Unstuck player

Author: Lucas Tobias 64613

The player is the primary actor because they issue the command, while the server is the secondary actor because it changes the player's position on the world. The use case is correct.

### 4. Save Region to Clipboard

Author: Nicolas Nascimento 61905

The player is the primary actor because they issue the command. There is no secondary actor, since the server for example, doesn't do anythin. The use case is correct.

### 5. Apply heightmap

Author: Pedro Amorim 64614

The player is the primary actor because they issue the command, while the server is the secondary actor because it modifies the world terrain. The use case is correct.


## Design Patterns

### 1. Template method

Author: Rodrigo Castro 64621

Although the `AbstractDirectionConverter` class is relatively simple, the template pattern is effectively applied. This is because there is no specification of how the `convertDirection` method is implemented, but rather how it is used in its template method `convert`. The responsibility of providing the implementation of this method lies with the subclasses, and not the parent class. Hence why the abstract keyword is specified when `AbstractDirectionConverter` defines `convertDirection`.

----

### 2. Chain of Responsibility

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

### 3. Proxy
Author: Rildo Franco 64605

The `Proxy` pattern is explicitly defined and utilized in the `FabricServerLevelDelegateProxy` class, which acts as a proxy for accessing an underlying ServerLevel instance. The essence of this pattern lies in the `invoke` method, where method calls on the proxy are intercepted. This enables custom handling for specific methods (e.g., `getBlockState`, `setBlock`) or delegating unhandled calls to the actual `ServerLevel` object.


## Code smells

### 1. Large class

Author: Nicolas Nascimento (num)

Location: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java

The Large Class code smell is easily identifiable in the `EditSession` class as it has over 2900 LOC. In fact, this suggests that the class has an excessive number of responsibilities and should be refactored into smaller classes and modules.

----

### 2. Data class

Author: Rildo Franco 64605

Location: worldedit-bukkit > src > main > java > com > sk89q > bukkit > util > CommandInfo.java

It is very clear that the `CommandInfo` class is a Data Class due to the fact that it only has getter methods. Hence, the class is anemic and does not contain any functionality.

----

### 3. Speculative Generality

Author: Rodrigo Castro 64621

Location: worldedit-core/src/main/java/com/sk89q/worldedit/scripting/CraftScriptEnvironment.java

Despite being declared as an abstract class, `CraftScriptEnvironment` currently has only one concrete subclass, CraftScriptContext, which suggests that the abstraction may be unnecessary and possibly rooted in speculative future use cases that have not come to fruition. This unnecessary abstraction can introduce unwarranted complexity, making the codebase harder to understand and maintain. It might be beneficial to refactor CraftScriptEnvironment into a concrete class to simplify the class hierarchy, enhance readability, and reduce maintenance overhead unless there are concrete plans to extend its functionality with additional subclasses.

## Metrics

## 1. Response For A Class (Chidamber-Kemerer Metrics Set):

Author: Rildo Franco 64605

The Response For A Class (RFC) value of 361 significantly exceeds the acceptable upper limit of 45, indicating that the class is excessively complex and highly coupled. This high RFC suggests that the class can execute a vast number of methods in response to a message, making it difficult to understand, test, and maintain. Such complexity often results from the class handling too many responsibilities, violating the Single Responsibility Principle. To enhance code quality, it is recommended to refactor the class by decomposing it into smaller, more focused classes, each with a distinct responsibility, thereby reducing coupling and improving maintainability.

## 2. Number of Parameter

Author: Lucas Tobias 64613

The equals(Object) method has a Number of Parameters value of 1, which comfortably falls within the regular acceptable range of 0 to 3. This low parameter count is ideal, as it promotes simplicity and ease of understanding when comparing objects—a fundamental operation in object-oriented programming. The method's design aligns with best practices, making it highly rated in various metric evaluations. Keeping the number of parameters minimal enhances readability, maintainability, and reduces the likelihood of errors. No action is needed for this metric; the method is well-designed and adheres to standard coding conventions.

## 3. CC Calculation

Author: Nicolas Nascimento 61905

The generateStateMap() method has a Cyclomatic Complexity (CC) value of 12, which significantly exceeds both the regular acceptable range of 0 to 3 and the extreme threshold of 7. This high CC indicates that the method contains numerous decision points—such as conditional statements, loops, and switch cases—resulting in a large number of possible execution paths. Such complexity can make the method difficult to understand, test, and maintain, as it increases the effort required to cover all paths during testing and raises the likelihood of bugs. To enhance code quality, it is advisable to refactor generateStateMap() by breaking it down into smaller, more focused methods, each adhering to the Single Responsibility Principle. This will reduce cyclomatic complexity, improve readability, and simplify testing and maintenance efforts.

---------------
