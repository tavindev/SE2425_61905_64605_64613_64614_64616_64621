# Code Smells Analysis

## Author: [Gustavo Chevrand]

---

## 1. Long Parameter List

Location: worldedit-core/src/main/java/com/sk89q/worldedit/command/tool/AreaPickaxe.java

The `actPrimary` method accepts 6 parameters.

```java
public class AreaPickaxe implements BlockTool {

  // ...

  public boolean actPrimary(Platform server, LocalConfiguration config, Player player, LocalSession session, Location clicked, @Nullable Direction face) {
              // ...
  }

  // ...
}
```

### Solution:


We can create a abstract class `AbstractBlockTool` that accepts a `Platform` instance as a constructor parameter. This is because the platform remains constant throughout the plugin’s execution, and therefore, it does not need to be passed as a parameter to each method call.

We can also create a `ToolContext` class that contains `LocalConfiguration`, `Player` and `LocalSession`, since they are concepts that change during the execution of the program.

After this refactor we would have the following:

```java
public abstract class AbstractBlockTool implements BlockTool {
  protected Platform server;

  public AbstractBlockTool(Platform server) {
    this.server = server
  }

  public abstract boolean canUse(Actor actor);

  public abstract boolean actPrimary(ToolContext context, Location clicked, @Nullable Direction face);
}

public class ToolContext { 
  private final LocalConfiguration config;
  private final Player player;
  private final LocalSession session;

  public ToolContext(LocalConfiguration config, Player player, LocalSession session) { 
    this.config = config;
    this.player = player;
    this.session = session;
  }

  public LocalConfiguration getConfig() { 
    return config;
  }

  public Player getPlayer() { 
    return player;
  }

  public LocalSession getSession() { 
    return session;
  }
}

public class AreaPickaxe extends AbstractBlockTool {
  public AreaPickaxe(Platform server, int range) {
    super(server);

    this.range = range;
  }

  // ...

  public boolean actPrimary(ToolContext context, Location clicked, @Nullable Direction face) {
    // ...
  }
}
```

Act primary has now only 3 parameters.

----

## 2. Code duplication

Location: worldedit-core/src/main/java/com/sk89q/worldedit/command/BrushCommands.java
Line 660

The `erode` and `dilate` methods have the same functionality as the `morph` method. The only difference is the parameters passed to the `MorphBrush` class, which is code duplication.

```java
@CommandPermissions("worldedit.brush.morph")
public void morph(Player player, LocalSession session,
                @Arg(desc = "The size of the brush", def = "5")
                    double brushSize,
                @Arg(desc = "Minimum number of faces for erosion", def = "3")
                    int minErodeFaces,
                @Arg(desc = "Erode iterations", def = "1")
                    int numErodeIterations,
                @Arg(desc = "Minimum number of faces for dilation", def = "3")
                    int minDilateFaces,
                @Arg(desc = "Dilate iterations", def = "1")
                    int numDilateIterations) throws WorldEditException {
  worldEdit.checkMaxBrushRadius(brushSize);
  BrushTool tool = session.forceBrush(
      player.getItemInHand(HandSide.MAIN_HAND).getType(),
      new MorphBrush(minErodeFaces, numErodeIterations, minDilateFaces, numDilateIterations),
      "worldedit.brush.morph"
  );
  tool.setSize(brushSize);

  player.printInfo(TranslatableComponent.of("worldedit.brush.morph.equip", TextComponent.of((int) brushSize)));
  ToolCommands.sendUnbindInstruction(player, UNBIND_COMMAND_COMPONENT);
}

@Command(
  name = "erode",
  desc = "Erode preset for morph brush, erodes blocks in the area"
)
@CommandPermissions("worldedit.brush.morph")
public void erode(Player player, LocalSession session,
                @Arg(desc = "The size of the brush", def = "5")
                    double brushSize) throws WorldEditException {
  worldEdit.checkMaxBrushRadius(brushSize);
  BrushTool tool = session.forceBrush(
      player.getItemInHand(HandSide.MAIN_HAND).getType(),
      new MorphBrush(2, 1, 5, 1),
      "worldedit.brush.morph"
  );
  tool.setSize(brushSize);

  player.printInfo(TranslatableComponent.of("worldedit.brush.morph.equip", TextComponent.of((int) brushSize)));
  ToolCommands.sendUnbindInstruction(player, UNBIND_COMMAND_COMPONENT);
}

@Command(
  name = "dilate",
  desc = "Dilate preset for morph brush, dilates blocks in the area"
)
@CommandPermissions("worldedit.brush.morph")
public void dilate(Player player, LocalSession session,
                 @Arg(desc = "The size of the brush", def = "5")
                     double brushSize) throws WorldEditException {
  worldEdit.checkMaxBrushRadius(brushSize);
  BrushTool tool = session.forceBrush(
      player.getItemInHand(HandSide.MAIN_HAND).getType(),
      new MorphBrush(5, 1, 2, 1),
      "worldedit.brush.morph"
  );
  tool.setSize(brushSize);

  player.printInfo(TranslatableComponent.of("worldedit.brush.morph.equip", TextComponent.of((int) brushSize)));
  ToolCommands.sendUnbindInstruction(player, UNBIND_COMMAND_COMPONENT);
}
```

## Solution

Rewrite `erode` and `dilate` to call `morph`.

```java
@Command(
  name = "erode",
  desc = "Erode preset for morph brush, erodes blocks in the area"
)
@CommandPermissions("worldedit.brush.morph")
public void erode(Player player, LocalSession session,
                @Arg(desc = "The size of the brush", def = "5")
                    double brushSize) throws WorldEditException {
  morph(player, session, brushSize, 2, 1, 5, 1);
}

@Command(
  name = "dilate",
  desc = "Dilate preset for morph brush, dilates blocks in the area"
)
@CommandPermissions("worldedit.brush.morph")
public void dilate(Player player, LocalSession session,
                 @Arg(desc = "The size of the brush", def = "5")
                     double brushSize) throws WorldEditException {
  morph(player, session, brushSize, 5, 1, 2, 1);
}
```

----

## 3. Primitive Type Obsession

worldedit-core/src/main/java/com/sk89q/worldedit/extension/platform/AbstractPlayerActor.java

Line 319

The method `floatAt` receives 3 int params that represent block coordinates, which could be replaced by a `BlockVector3` class.

```java
public void floatAt(int x, int y, int z, boolean alwaysGlass) {
    // ...
}
```

## Solution

Accept `BlockVector3` as a parameter instead of `x`, `y` and `z`

```java
public void floatAt(BlockVector3 pos, boolean alwaysGlass) {
  // ...
}
```





