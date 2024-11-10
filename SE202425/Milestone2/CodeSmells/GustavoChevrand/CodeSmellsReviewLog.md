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

## 3.

Author:
