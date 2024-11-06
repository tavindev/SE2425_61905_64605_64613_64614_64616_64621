# Design Patterns Report

## Author: Gustavo Chevrand

---

### 1. Composite

The class RegionIntersection implements the interface region and has a list of Region objects. The getMinimumPoint method loops through the region objects and calls getMinimumPoint on each of them in order to get the minimum point. 

worldedit-core/src/main/java/com/sk89q/worldedit/regions/RegionIntersection.java

```java
public class RegionIntersection extends AbstractRegion {
   private final List<Region> regions = new ArrayList<>();

   // ...

   @Override
   public BlockVector3 getMinimumPoint() {
       BlockVector3 minimum = regions.get(0).getMinimumPoint();
       for (int i = 1; i < regions.size(); i++) {
           minimum = regions.get(i).getMinimumPoint().getMinimum(minimum);
       }
       return minimum;
   }

   // ...
}

```

worldedit-core/src/main/java/com/sk89q/worldedit/regions/AbstractRegion.java

```java
public abstract class AbstractRegion implements Region { // ... }
```

worldedit-core/src/main/java/com/sk89q/worldedit/regions/Region.java

```java
public interface Region extends Iterable<BlockVector3>, Cloneable {
    BlockVector3 getMinimumPoint();

   // ...
}

```


---

### 2. Template

The abstract AssetLoader class defines the method getAsset (template method). This method decides the structurev of the algorithm to getAsset and calls two other abstract methods defined by this class: getAllowedExtensions and loadAssetFromPath; which will be implemented by the subclasses that inherit from AssetLoader, i.e, the subclasses will decide how both methods are implemented and how they’ll behave, while maintaining the structure of getAsset.

worldedit-core/src/main/java/com/sk89q/worldedit/util/asset/AssetLoader.java

```java
@Beta
public abstract class AssetLoader<T>   {
  
   @Nullable
   public T getAsset(String path) {
       // ...

       String[] extensions = this.getAllowedExtensions().toArray(new String[0]);

       // ...


       T asset;
       try {
           asset = loadAssetFromPath(file);
           if (asset == null) {
               return null;
           }
       } catch (Exception e) {
           WorldEdit.logger.error("Error reading asset file directory", e);
           return null;
       }

      // ...
   }

   @Nullable
   protected abstract T loadAssetFromPath(Path path) throws Exception;

   public abstract Set<String> getAllowedExtensions();
}
```

---

### 3. Builder

The EditSessionBuilder class facilitates the construction of an EditSession object through a series of steps. It provides builder methods that enable you to modify the properties used to create the object. Subsequently, it offers a build method that returns a valid EditSession object, having all the configuration previously set by the builder methods. This pattern abstracts the object instantiation with the build method and allows you to instance objects without having to specify all attributes.
  
worldedit-core/src/main/java/com/sk89q/worldedit/EditSessionBuilder.java

```java
public final class EditSessionBuilder {
   private final EventBus eventBus;
   private @Nullable World world;
   private int maxBlocks = -1;
   private @Nullable Actor actor;
   private @Nullable BlockBag blockBag;
   private boolean tracing;

   public EditSessionBuilder actor(@Nullable Actor actor) {
     // ... 
   }

   public EditSessionBuilder blockBag(@Nullable BlockBag blockBag) {
       // ...
   }

   public EditSessionBuilder tracing(boolean tracing) {
       // ...
   }


   public <A extends Actor & Locatable> EditSessionBuilder locatableActor(A locatable) {
      // ...
   }

   public EditSession build() {
       if (WorldEdit.getInstance().getConfiguration().traceUnflushedSessions) {
           return new TracedEditSession(eventBus, world, maxBlocks, blockBag, actor, tracing);
       }
       return new EditSession(eventBus, world, maxBlocks, blockBag, actor, tracing);
   }
}

```
