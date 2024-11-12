# Code Smells Analysis

## Author: [Rodrigo Castro]
## Date: [MM/DD/YYYY]

---

### Identified Code Smells
The code smells identified and the location, what's the issue.

### 1. Speculative Generality
- location: worldedit-core/src/main/java/com/sk89q/worldedit/scripting/CraftScriptEnvironment.java and worldedit-core/src/main/java/com/sk89q/worldedit/scripting/CraftScriptContext.java
  

- The CraftScriptEnvironment class is marked as abstract but currently has only one concrete subclass, CraftScriptContext. The existence of a single subclass suggests that CraftScriptEnvironment may be over-generalized or created with speculative future use cases in mind that have not materialized.

```java
public abstract class CraftScriptEnvironment {

    protected WorldEdit controller;
    protected Player player;
    protected LocalConfiguration config;
    protected LocalSession session;
    protected Platform server;

    public CraftScriptEnvironment(WorldEdit controller, Platform server, LocalConfiguration config, LocalSession session, Player player) {
        this.controller = controller;
        this.player = player;
        this.config = config;
        this.server = server;
        this.session = session;
    }

}
```
## Solution:
Merge CraftScriptEnvironment and CraftScriptContext if there’s no foreseeable subclassing.

---
### 2. Data Class
- Location: worldedit-core/src/main/java/com/sk89q/worldedit/history/UndoContext.java

- This class only contains:
A single Extent field.
A getter and setter for extent.
Without any additional behavior, it essentially serves as a passive data holder.

```java
public class UndoContext {

    private Extent extent;

    /**
     * Get the extent set on this context.
     *
     * @return an extent or null
     */
    public @Nullable Extent getExtent() {
        return extent;
    }

    /**
     * Set the extent on this context.
     *
     * @param extent an extent or null
     */
    public void setExtent(@Nullable Extent extent) {
        this.extent = extent;
    }
}
```
## Solution: 
To improve UndoContext, could be added meaningful methods to encapsulate behavior related to extent, particularly around how it should interact with undo/redo operations. 

---
### 3. Duplicate Code
- Location:
  Lines 852 - 869
  worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java

- The undo and redo methods share the same logic, differing only in the specific action (createUndo vs. createRedo).

```java
public void undo(EditSession editSession) {
        UndoContext context = new UndoContext();
        context.setExtent(editSession.bypassHistory);
        Operations.completeBlindly(ChangeSetExecutor.createUndo(changeSet, context));
        editSession.internalFlushSession();
    }

    /**
     * Sets to new state.
     *
     * @param editSession a new {@link EditSession} to perform the redo in
     */
    public void redo(EditSession editSession) {
        UndoContext context = new UndoContext();
        context.setExtent(editSession.bypassHistory);
        Operations.completeBlindly(ChangeSetExecutor.createRedo(changeSet, context));
        editSession.internalFlushSession();
    }
```
## Solution:
- Define an Enum for the Action: Define an ActionType enum to represent the operation.
- Create a Helper Method: Add a helper method to handle the shared logic based on the action.
```java
public enum ActionType {
    UNDO,
    REDO
}

private void performAction(EditSession editSession, ActionType actionType) {
    UndoContext context = new UndoContext();
    context.setExtent(editSession.bypassHistory);

    if (actionType == ActionType.UNDO) {
        Operations.completeBlindly(ChangeSetExecutor.createUndo(changeSet, context));
    } else if (actionType == ActionType.REDO) {
        Operations.completeBlindly(ChangeSetExecutor.createRedo(changeSet, context));
    }

    editSession.internalFlushSession();
}

public void undo(EditSession editSession) {
    performAction(editSession, ActionType.UNDO);
}

public void redo(EditSession editSession) {
    performAction(editSession, ActionType.REDO);
}
```
----

### Summary
- **Overall Code Health:** Summary
