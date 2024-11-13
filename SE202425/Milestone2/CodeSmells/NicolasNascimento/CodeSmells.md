# Code Smells Analysis

## Author: [Nicolas Nascimento]
## Date: [11/06/20204]

---

### Identified Code Smells
1. Feature Envy
    - Location: Method in line 852 of file: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java
    - Issue: In the undo() method, EditSession invokes ChangeSetExecutor.createUndo() with a dependency on UndoContext. This shows that EditSession is acting on details of UndoContext and ChangeSetExecutor, which indicates an excessive relationship.
    - Possible Solution: Consider refactoring the undo() method so that the logic associated with the undo command is encapsulated within a specific command class. This separates the process of managing undo actions from the EditSession.

```java
public void undo(EditSession editSession) {
        UndoContext context = new UndoContext();
        context.setExtent(editSession.bypassHistory);
        Operations.completeBlindly(ChangeSetExecutor.createUndo(changeSet, context));
        editSession.internalFlushSession();
    }
```

2. Shotgun Surgery
    - Location: Constructor of EditSession in file: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java
    - Issue: Modifying a function or attribute in the EditSession class may require changes in multiple areas of the code that rely heavily on several Extents. This is because EditSession triggers several instances of Extent in sequence, each with unique responsibilities. If any logic changes, it may be necessary to update several of these Extents, as well as other associated classes (Watchdog, SideEffectExtent, ChunkLoadingExtent, etc.).
    - Example: In the setBlock() method, there are references to bypassNone, bypassHistory, and bypassReorderHistory, which are stage-specific. Any change in the block definition logic for a specific state requires that all extensions and the setBlock() logic be reviewed to ensure consistency.
    - Possible Solution: Create a more modular system where each Extent encapsulates its internal logic and clearly defines its entry points. An intermediate manager could coordinate the order of execution, avoiding direct coupling in the EditSession for each Extent.


3. Large Class
    - Location: The class EditSession, which path is: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java
    - Issue: EditSession has many responsibilities and manages multiple Extent extensions, undo and redo operations, as well as chain processing and block manipulation.
    - Example: The EditSession class handles a lot of logic, including Extent configuration, setBlock(), undo(), redo() methods, as well as managing history and even handling security checks (e.g. Watchdog).
    - Possible Solution: Apply the single responsibility principle, extracting functionality related to Extent management, history, or security checks into separate classes or modules.


---

### Summary
- **Overall Code Health:**
  Overall Code Health: The EditSession class has multiple responsibilities, with excessive dependencies across Extent objects, making it difficult to maintain and understand. The identified code smells, including Feature Envy, Shotgun Surgery, and Large Class, suggest that EditSession can benefit from increased modularity and separation of concerns. This will help reduce coupling, improve maintainability, and make the code easier to update or extend in the future.
