# Code Smells Review Log

## Reviewer: [Reviewer’s Name]
## Author: [Author’s Name]
## Date: [11/13/2024]

## 1. Long method

Author: Lucas Tobias

Location: line 188 to 271 of the file
worldedit-bukkit/src/main/java/com/sk89q/wepif/PermissionsResolverManager.java, method with 83 lines.

The code smell was effectively identified as Long Method in the `loadConfig` method within the `PermissionsResolverManager` class, noting that this 83-line method handles multiple tasks—such as file creation, YAML loading, configuration updating, and resolver management—that would benefit from being split into smaller, single-responsibility methods. By proposing helper methods like `createFileIfNotExists`, `loadYAMLProcessor`, and `updateResolvers`, Lucas’s solution enhances readability, maintainability, and testability. His approach aligns well with best practices, as each suggested method encapsulates a distinct part of the `loadConfig` process, making the main method more concise and each function independently modifiable and testable. This structured refactoring would lead to a more modular and clear codebase, making Lucas’s recommendations highly valuable for improving the code.

## 2. Large Class

Author: Nicolas Nascimento

Location: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java

The suggestion to break down `EditSession` into separate, focused classes or modules is well-aligned with SRP and would significantly enhance the codebase. By extracting responsibilities—such as `Extent` configuration, history management, and security checks—into independent classes, the `EditSession` class would become more modular, easier to understand, and more manageable.

## 2. Long Parameter List

Author: Lucas Tobias

Location: line 74 of the file, worldedit-fabric/src/main/java/com/sk89q/worldedit/fabric/mixin/MixinLevelChunkSetBlockHook.java, method `setBlockStateHook`

The analysis correctly identifies a **Long Parameter List** code smell in the `setBlockStateHook` method, which uses five parameters: `BlockState target, Level world, BlockPos pos, BlockState old, boolean move`. Passing multiple parameters like this can make the method more challenging to use and understand, especially as parameter lists grow and if parameter types are similar.

