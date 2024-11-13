# Code Smells Analysis

## Author: Lucas Tobias

---

### Long Method
Location: line 188 to 271 of the file
worldedit-bukkit/src/main/java/com/sk89q/wepif/PermissionsResolverManager.java, method with 83 lines.

```java
    private boolean loadConfig(File file) {
        boolean isUpdated = false;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.warn("Failed to create new configuration file", e);
            }
        }
        config = new YAMLProcessor(file, false, YAMLFormat.EXTENDED);
        try {
            config.load();
        } catch (IOException e) {
            LOGGER.warn("Error loading WEPIF configuration", e);
        }
        List<String> keys = config.getKeys(null);
        config.setHeader(CONFIG_HEADER);

        if (!keys.contains("ignore-nijiperms-bridges")) {
            config.setProperty("ignore-nijiperms-bridges", true);
            isUpdated = true;
        }

        if (!keys.contains("resolvers")) {
            //List<String> resolverKeys = config.getKeys("resolvers");
            List<String> resolvers = new ArrayList<>();
            for (Class<?> clazz : availableResolvers) {
                resolvers.add(clazz.getSimpleName());
            }
            enabledResolvers.addAll(Arrays.asList(availableResolvers));
            config.setProperty("resolvers.enabled", resolvers);
            isUpdated = true;
        } else {
            List<String> disabledResolvers = config.getStringList("resolvers.disabled", new ArrayList<>());
            List<String> stagedEnabled = config.getStringList("resolvers.enabled", null);
            for (Iterator<String> i = stagedEnabled.iterator(); i.hasNext();) {
                String nextName = i.next();
                Class<?> next = null;
                try {
                    next = Class.forName(getClass().getPackage().getName() + "." + nextName);
                } catch (ClassNotFoundException ignored) {
                }

                if (next == null || !PermissionsResolver.class.isAssignableFrom(next)) {
                    LOGGER.warn("WEPIF: Invalid or unknown class found in enabled resolvers: "
                            + nextName + ". Moving to disabled resolvers list.");
                    i.remove();
                    disabledResolvers.add(nextName);
                    isUpdated = true;
                    continue;
                }
                enabledResolvers.add(next.asSubclass(PermissionsResolver.class));
            }

            for (Class<?> clazz : availableResolvers) {
                if (!stagedEnabled.contains(clazz.getSimpleName())
                    && !disabledResolvers.contains(clazz.getSimpleName())) {
                    disabledResolvers.add(clazz.getSimpleName());
                    LOGGER.info("New permissions resolver: "
                        + clazz.getSimpleName() + " detected. "
                        + "Added to disabled resolvers list.");
                    isUpdated = true;
                }
            }
            config.setProperty("resolvers.disabled", disabledResolvers);
            config.setProperty("resolvers.enabled", stagedEnabled);
        }

        if (keys.contains("dinner-perms") || keys.contains("dinnerperms")) {
            config.removeProperty("dinner-perms");
            config.removeProperty("dinnerperms");
            isUpdated = true;
        }
        if (!keys.contains("permissions")) {
            ConfigurationPermissionsResolver.generateDefaultPerms(
                    config.addNode("permissions"));
            isUpdated = true;
        }
        if (isUpdated) {
            LOGGER.info("WEPIF: Updated config file");
            config.save();
        }
        return isUpdated;
    }

```
### Solution
Split the method into smaller methods, each with a specific responsibility, making the code more readable. For example, create methods like these: createFileIfNotExists, loadYAMLProcessor, updateResolvers, updateEnabledResolvers. These would help make the code more readable.



---

### Long Parameter List
Location: line 74 of the file
worldedit-fabric/src/main/java/com/sk89q/worldedit/fabric/mixin/MixinLevelChunkSetBlockHook.java, method `setBlockStateHook` use five parameters.

```java
    public void setBlockStateHook(BlockState target, Level world, BlockPos pos, BlockState old, boolean move) {
    boolean localShouldUpdate;
    MinecraftServer server = world.getServer();
    if (server == null || Thread.currentThread() != server.getRunningThread()) {
        // We're not on the server thread for some reason, WorldEdit will never be here
        // so we'll just ignore our flag
        localShouldUpdate = true;
    } else {
        localShouldUpdate = shouldUpdate;
    }
    if (localShouldUpdate) {
        target.onPlace(world, pos, old, move);
    }
}
```

### Solution
Encapsulate the parameters in an object. We can create a `BlockStateHook` class that contains the parameters `BlockState target, Level world, BlockPos pos, BlockState old, boolean move`. This way, the `setBlockStateHook` method would only accept one parameter of type `BlockStateHook`.


- ---

### Large Class
Class `FabricDataFixer` in the path
worldedit-fabric/src/main/java/com/sk89q/worldedit/fabric/FabricDataFixer.java, the class has 2764 lines.

### Solution
Refactor the class. With a very large class its complexity is greater, I would delegate some actions to other classes to reduce its complexity and a better understanding of the class.


