# Code Smells Review Log

## Reviewer: Lucas Tobias

---

### Author: Rildo Franco
Code Smell: Data Clumps

#### Location: Methods parameters in worldedit-bukkit/src/main/java/com/sk89q/bukkit/util/DynamicPluginCommand.java

Data Clumps happen when different parts of the code contain identical groups of variables, which is what happens with the methods of the `bPermissionsResolver` class, where it repeats data in its parameters, specifically `OfflinePlayer player` and `String player`, as well as (String name, String permission).
These clumps should be turned into their own classes.




---

### Author: Rodrigo Castro
Code Smell: Duplicate Code

#### Location: lines 852 - 869 worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java
If you check the Code Smell Duplicate Code in the implementation of `undo` and `redo`, which are the same. One way to “remove” these code smells is to create an auxiliary method to do this function and call it in undo and redo to do whatever is necessary.


---
### Author: Nicolas Nascimento
Code Smell: Shotgun Surgery

#### Location: Constructor of EditSession in file: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java

A single change will require changes to multiple classes that depend on EditSession
Use Move Method and Move Field to move existing class behaviors into a single class. If there’s no class appropriate for this, create a new one.



---


