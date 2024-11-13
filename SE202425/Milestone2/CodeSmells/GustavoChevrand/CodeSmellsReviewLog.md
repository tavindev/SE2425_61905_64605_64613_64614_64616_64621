# Code Smells Review Log

## 1. Large class

Author: Nicolas Nascimento (num)

Location: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java

The Large Class code smell is easily identifiable in the `EditSession` class as it has over 2900 LOC. In fact, this suggests that the class has an excessive number of responsibilities and should be refactored into smaller classes and modules.

----

## 2. Data class

Author: Rildo Franco 64605

Location: worldedit-bukkit > src > main > java > com > sk89q > bukkit > util > CommandInfo.java

It is very clear that the `CommandInfo` class is a Data Class due to the fact that it only has getter methods. Hence, the class is anemic and does not contain any functionality.

----

## 3. Speculative Generality

Author: Rodrigo Castro 64621

Location: worldedit-core/src/main/java/com/sk89q/worldedit/scripting/CraftScriptEnvironment.java

Despite being declared as an abstract class, `CraftScriptEnvironment` currently has only one concrete subclass, CraftScriptContext, which suggests that the abstraction may be unnecessary and possibly rooted in speculative future use cases that have not come to fruition. This unnecessary abstraction can introduce unwarranted complexity, making the codebase harder to understand and maintain. It might be beneficial to refactor CraftScriptEnvironment into a concrete class to simplify the class hierarchy, enhance readability, and reduce maintenance overhead unless there are concrete plans to extend its functionality with additional subclasses.
