# Design Patterns Report

## Author: Gustavo Chevrand

---

### 1. Composite

The RegionIntersection class implements the Region interface and encapsulates a collection of Region objects. It acts as a composite structure that manages these objects together.

The getMinimumPoint method in RegionIntersection iterates through each Region in the list and calls getMinimumPoint on them. By doing so, it computes the overall minimum point by comparing the minimum points of each individual Region. This approach exemplifies how the Composite Pattern allows you to treat a group of Region objects and individual Region objects uniformly, enabling consistent interaction and aggregation of results across the composite structure.

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
public abstract class AbstractRegion implements Region {
   // ...
}
```

worldedit-core/src/main/java/com/sk89q/worldedit/regions/Region.java

```java
public interface Region extends Iterable<BlockVector3>, Cloneable {
    BlockVector3 getMinimumPoint();

   // ...
}

```

<img width="978" alt="image" src="https://github.com/user-attachments/assets/8fdf9766-d2a7-43dc-8895-12187cfc9ebd">


---

### 2. Template

The AssetLoader abstract class defines a method called getAsset, which serves as a template method. This method outlines the overall structure of the algorithm for loading an asset. It dictates the sequence of operations, including fetching the allowed file extensions and loading the asset from a given path, while leaving the implementation of specific details to its subclasses.

The getAsset method relies on two abstract methods: getAllowedExtensions and loadAssetFromPath. These abstract methods must be implemented by any subclass that inherits from AssetLoader. This design allows each subclass to provide its own behavior for determining allowed file extensions and loading assets from a path. However, the core structure and flow defined in getAsset remain consistent across all subclasses. Thus, the Template Method Pattern ensures that the overall asset-loading process is fixed while allowing flexibility in how certain steps are performed.

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

<img width="793" alt="image" src="https://github.com/user-attachments/assets/213b862c-0cf6-4fb4-9c35-c745e616eb8e">

---

### 3. Builder

The EditSessionBuilder class simplifies the creation of an EditSession object by guiding you through a series of steps. It offers builder methods that allow you to configure the object's properties in a flexible and readable way. These methods, such as actor, blockBag, and tracing, enable you to customize the EditSession by setting only the parameters you need. Each builder method returns the EditSessionBuilder instance, allowing for method chaining and a fluent configuration style.

Once all desired properties are set, the build method is called to create and return a fully-configured EditSession object. This method consolidates all the specified configurations into a single, valid object. By abstracting the object construction process, the Builder Pattern makes it easier to instantiate complex objects without requiring a constructor with numerous parameters, especially when many of those parameters are optional.
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

<img width="865" alt="image" src="https://github.com/user-attachments/assets/6cde2b67-df4f-4a9f-a4ff-71036a65748a">

