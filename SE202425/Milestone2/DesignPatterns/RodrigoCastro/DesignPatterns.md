# Design Patterns Report

## Author: [Rodrigo Castro]
## Date: [MM/DD/YYYY]

---

### 1. Command
- **Description:** The Command Pattern encapsulates a request as an object, allowing for parameterization of clients with queues, requests, and logs, and supporting undoable operations. In WorldEdit, the Command Pattern is adapted to manage undo and redo actions without creating individual command classes for each action.
- **Justification:** This pattern is implemented in WorldEdit to manage command history effectively. The HistoryCommands class acts as the invoker for undo and redo actions. LocalSession holds the command history, allowing undo and redo functionality by re-executing or reversing EditSession instances that represent the actual commands. This approach minimizes memory usage by using existing objects for commands, improving system efficiency.
- Using the undo command as example:
Line 63 of the file
worldedit-core/src/main/java/com/sk89q/worldedit/command/HistoryCommands.java
Line 254 of the file
worldedit-core/src/main/java/com/sk89q/worldedit/LocalSession.java
Line 852 of the file
worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java

```java
public void undo(Actor actor, LocalSession session,
                     @Arg(desc = "Number of undoes to perform", def = "1")
                         int times,
                     @Arg(name = "player", desc = "Undo this player's operations", def = "")
                         String playerName) throws WorldEditException {
        times = Math.max(1, times);
        LocalSession undoSession = session;
        if (playerName != null) {
            actor.checkPermission("worldedit.history.undo.other");
            undoSession = worldEdit.getSessionManager().findByName(playerName);
            if (undoSession == null) {
                actor.printError(TranslatableComponent.of("worldedit.session.cant-find-session", TextComponent.of(playerName)));
                return;
            }
        }
        int timesUndone = 0;
        for (int i = 0; i < times; ++i) {
            BlockBag blockBag = actor instanceof Player ? undoSession.getBlockBag((Player) actor) : null;
            EditSession undone = undoSession.undo(blockBag, actor);
            if (undone != null) {
                timesUndone++;
                worldEdit.flushBlockBag(actor, undone);
            } else {
                break;
            }
        }
        if (timesUndone > 0) {
            actor.printInfo(TranslatableComponent.of("worldedit.undo.undone", TextComponent.of(timesUndone)));
        } else {
            actor.printError(TranslatableComponent.of("worldedit.undo.none"));
        }
    }
```
- Diagram

---

### 2. Template Method
- **Description:** The Template Method Pattern defines the skeleton of an algorithm in a method, deferring some steps to subclasses. It allows subclasses to redefine certain steps without altering the structure of the overall algorithm.
- **Justification:** In WorldEdit, AbstractDirectionConverter provides a template for direction conversion, leaving the specific details of direction computation to subclasses like DirectionConverter and DirectionVectorConverter. This enables a consistent conversion process while allowing flexibility in handling different direction formats.

Line 95 of the file
worldedit-core/src/main/java/com/sk89q/worldedit/command/argument/AbstractDirectionConverter.java

```java
public ConversionResult<D> convert(String argument, InjectedValueAccess context) {
        Player player = context.injectedValue(Key.of(Player.class, OptionalArg.class))
            .orElse(null);
        try {
            return SuccessfulConversion.fromSingle(convertDirection(argument, player, includeDiagonals));
        } catch (Exception e) {
            return FailedConversion.from(e);
        }
    }

protected abstract D convertDirection(String argument, @Nullable Player player, boolean includeDiagonals) throws UnknownDirectionException;
```
Line 46 of the file
worldedit-core/src/main/java/com/sk89q/worldedit/command/argument/DirectionConverter.java

```java
@Override
    protected Direction convertDirection(String argument, @Nullable Player player, boolean includeDiagonals) throws UnknownDirectionException {
        final BlockVector3 vec = includeDiagonals
                ? getWorldEdit().getDiagonalDirection(player, argument)
                : getWorldEdit().getDirection(player, argument);
        return Optional.ofNullable(Direction.findClosest(vec.toVector3(), Direction.Flag.ALL))
                .orElseThrow(() -> new UnknownDirectionException(argument));
    }
```

Line 44 of the file
worldedit-core/src/main/java/com/sk89q/worldedit/command/argument/DirectionVectorConverter.java
```java
@Override
    protected BlockVector3 convertDirection(String argument, @Nullable Player player, boolean includeDiagonals) throws UnknownDirectionException {
        return includeDiagonals
                ? getWorldEdit().getDiagonalDirection(player, argument)
                : getWorldEdit().getDirection(player, argument);
    }

```

- Diagram
  
---

### 3. Composite
- **Description:** The Composite Pattern allows individual objects and compositions of objects to be treated uniformly. It is especially useful in representing part-whole hierarchies.
- **Justification:** In WorldEdit, the MaskIntersection2D class applies the Composite Pattern by aggregating multiple Mask2D objects. This design enables grouping of masks, allowing complex mask operations to be simplified and managed as a single entity. The test method in MaskIntersection2D evaluates all component masks, ensuring cohesive mask handling and facilitating scalability.

worldedit-core/src/main/java/com/sk89q/worldedit/function/mask/MaskIntersection2D.java

```java
public class MaskIntersection2D implements Mask2D {

    private final Set<Mask2D> masks = new HashSet<>();
```
```java
@Override
    public boolean test(BlockVector2 vector) {
        if (masks.isEmpty()) {
            return false;
        }

        for (Mask2D mask : masks) {
            if (!mask.test(vector)) {
                return false;
            }
        }

        return true;
    }
```

- Diagram
  
---

### Summary
- **Patterns Chosen:** Command Pattern, Template Method Pattern, Composite Pattern.
- **Benefits:** These patterns improve code modularity, scalability, and memory efficiency. The Command Pattern centralizes command management for undoable operations, the Template Method Pattern standardizes direction conversion while allowing flexibility, and the Composite Pattern simplifies mask composition, making it easier to add and manage mask components. Overall, these patterns enhance system organization and extensibility.
