# MergedDocReviewLog

## Author: Gustavo Chevrand 64616

## Use cases

### 1. Create Solid Cylinder  

Author: Rodrigo Castro 64621

The player is the primary actor because they issue the command, while the server is the secondary actor because it performs operations on the world, such as placing blocks. The use case is correct.

### 2. Select Snapshot 

Author: Rildo Franco 64605

The player is the primary actor because they issue the command. There is no secondary actor. The use case is correct.

### 3. Unstuck player

Author: Lucas Tobias 64613

The player is the primary actor because they issue the command, while the server is the secondary actor because it changes the player's position on the world. The use case is correct.

### 4. Save Region to Clipboard

Author: Nicolas Nascimento 61905

The player is the primary actor because they issue the command. There is no secondary actor, since the server for example, doesn't do anythin. The use case is correct.

### 5. Apply heightmap

Author: Pedro Amorim 64614

The player is the primary actor because they issue the command, while the server is the secondary actor because it modifies the world terrain. The use case is correct.


## Design Patterns

### 1. Template method

Author: Rodrigo Castro 64621

Although the `AbstractDirectionConverter` class is relatively simple, the template pattern is effectively applied. This is because there is no specification of how the `convertDirection` method is implemented, but rather how it is used in its template method `convert`. The responsibility of providing the implementation of this method lies with the subclasses, and not the parent class. Hence why the abstract keyword is specified when `AbstractDirectionConverter` defines `convertDirection`.

----

### 2. Chain of Responsibility

Author: Nicolas Nascimento 61905

There are some extents that do not fulfill the requirements to be a handler in the CoR pattern, i.e, some extents always delegate to the next extent in the chain. The CoR is characterized by having a chain of handlers that either process or delegate the request to the next handler.

The `SurvivalModeExtent` class is a valid handler. If the condition in the if statement evaluates to `true`, the method executes logic and returns true, terminating the request chain. If it evaluates to `false`, it calls the next handler in the chain.

Location: worldedit-core/src/main/java/com/sk89q/worldedit/extent/world/SurvivalModeExtent.java

```java
public class SurvivalModeExtent extends AbstractDelegateExtent {
    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(BlockVector3 location, B block) throws WorldEditException {
        if (toolUse && block.getBlockType().getMaterial().isAir()) {
            world.simulateBlockMine(location);
            return true;
        } else {
            // Can't be an inlined check due to inconsistent generic return type
            if (stripNbt) {
                return super.setBlock(location, block.toBaseBlock((LinCompoundTag) null));
            } else {
                return super.setBlock(location, block);
            }
        }
    }
}
```

The `ChunkLoadingExtent` class, on the other hand, will always delegate to the next extent in the chain.

Location: worldedit-core/src/main/java/com/sk89q/worldedit/extent/world/ChunkLoadingExtent.java

```java
public class ChunkLoadingExtent extends AbstractDelegateExtent {
    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(BlockVector3 location, B block) throws WorldEditException {
        if (enabled) {
            world.checkLoadedChunk(location);
        }
        return super.setBlock(location, block);
    }
}

```

For that reason, it is not very clear if the CoR is effectively applied, since not all extents fulfill the requirements of a request handler in the chain.

----

### 3. Proxy
Author: Rildo Franco 64605

The `Proxy` pattern is explicitly defined and utilized in the `FabricServerLevelDelegateProxy` class, which acts as a proxy for accessing an underlying ServerLevel instance. The essence of this pattern lies in the `invoke` method, where method calls on the proxy are intercepted. This enables custom handling for specific methods (e.g., `getBlockState`, `setBlock`) or delegating unhandled calls to the actual `ServerLevel` object.


## Code smells

### 1. Large class

Author: Nicolas Nascimento (num)

Location: worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java

The Large Class code smell is easily identifiable in the `EditSession` class as it has over 2900 LOC. In fact, this suggests that the class has an excessive number of responsibilities and should be refactored into smaller classes and modules.

----

### 2. Data class

Author: Rildo Franco 64605

Location: worldedit-bukkit > src > main > java > com > sk89q > bukkit > util > CommandInfo.java

It is very clear that the `CommandInfo` class is a Data Class due to the fact that it only has getter methods. Hence, the class is anemic and does not contain any functionality.

----

### 3. Speculative Generality

Author: Rodrigo Castro 64621

Location: worldedit-core/src/main/java/com/sk89q/worldedit/scripting/CraftScriptEnvironment.java

Despite being declared as an abstract class, `CraftScriptEnvironment` currently has only one concrete subclass, CraftScriptContext, which suggests that the abstraction may be unnecessary and possibly rooted in speculative future use cases that have not come to fruition. This unnecessary abstraction can introduce unwarranted complexity, making the codebase harder to understand and maintain. It might be beneficial to refactor CraftScriptEnvironment into a concrete class to simplify the class hierarchy, enhance readability, and reduce maintenance overhead unless there are concrete plans to extend its functionality with additional subclasses.

## Metrics

## 1. Response For A Class (Chidamber-Kemerer Metrics Set):

Author: Rildo Franco 64605

The Response For A Class (RFC) value of 361 significantly exceeds the acceptable upper limit of 45, indicating that the class is excessively complex and highly coupled. This high RFC suggests that the class can execute a vast number of methods in response to a message, making it difficult to understand, test, and maintain. Such complexity often results from the class handling too many responsibilities, violating the Single Responsibility Principle. To enhance code quality, it is recommended to refactor the class by decomposing it into smaller, more focused classes, each with a distinct responsibility, thereby reducing coupling and improving maintainability.

## 2. Number of Parameter

Author: Lucas Tobias 64613

The equals(Object) method has a Number of Parameters value of 1, which comfortably falls within the regular acceptable range of 0 to 3. This low parameter count is ideal, as it promotes simplicity and ease of understanding when comparing objects—a fundamental operation in object-oriented programming. The method's design aligns with best practices, making it highly rated in various metric evaluations. Keeping the number of parameters minimal enhances readability, maintainability, and reduces the likelihood of errors. No action is needed for this metric; the method is well-designed and adheres to standard coding conventions.

## 3. CC Calculation

Author: Nicolas Nascimento 61905

The generateStateMap() method has a Cyclomatic Complexity (CC) value of 12, which significantly exceeds both the regular acceptable range of 0 to 3 and the extreme threshold of 7. This high CC indicates that the method contains numerous decision points—such as conditional statements, loops, and switch cases—resulting in a large number of possible execution paths. Such complexity can make the method difficult to understand, test, and maintain, as it increases the effort required to cover all paths during testing and raises the likelihood of bugs. To enhance code quality, it is advisable to refactor generateStateMap() by breaking it down into smaller, more focused methods, each adhering to the Single Responsibility Principle. This will reduce cyclomatic complexity, improve readability, and simplify testing and maintenance efforts.

---------------


# Review Doc Log
## Author: Nicolas Nascimento

---

## Code Metrics Review Log

### Date: 11/13/2024


### Code Metrics analyzed: Cognitive Complexity
### Author: Gustavo Chevrand

- **Analysis Summary**: The updateServerCUI method in LocalSession.java has a Cognitive Complexity of 9, slightly exceeding the regular threshold of [0..8[ for methods. While the method is not as complex as others like makeSphere in EditSession.java (with a value of 68), it still requires readers to track nested conditions and logic blocks. This moderate complexity may lead to challenges in understanding and maintaining the method as the codebase grows.

- **Suggestions**: The current complexity of updateServerCUI is manageable but could benefit from minor refactoring to enhance readability and maintainability. Consider: Simplifying logic by extracting parts of the conditional checks into smaller helper methods.
  Adding comments to clarify the intent behind each condition block.
  Reducing nesting by utilizing early returns where applicable to streamline the flow of the method.


### Code Metrics analyzed: Condition Nesting Depth (CND)
### Author: Lucas Tobias

- **Analysis Summary**: The method factorial(double) in CompilingVisitor.java has a Condition Nesting Depth (CND) of 1, well within the acceptable range of [0..2]. This indicates that the method has minimal nesting, making it straightforward to understand and maintain. The low complexity of the method ensures that its logic is easy to follow, contributing to better readability and testability.

- **Suggestions**: The method’s low CND reflects good design practices. No immediate changes are needed. However, it is recommended to maintain this simplicity as the method evolves, avoiding deep nesting or unnecessary complexity to ensure continued maintainability.

### Code Metrics analyzed: NOM Calculation (Li-Henry Metrics Set) - Class Level
### Author: Rodrigo Castro

- **Analysis Summary**: The class analyzed has a NOM of 94, far exceeding the regular threshold of 7 and the extreme range of 25. This indicates that the class likely has too many responsibilities, leading to decreased readability and higher maintenance demands.

- **Suggestions**: Refactor by distributing functionality across smaller, more cohesive classes. Applying the Single Responsibility Principle would reduce method count, simplify maintenance, and improve code modularity.

---

## Code Smells Review Log

### Date: 11/10/2024

### 1. Dead Code

**Location:** Class AbstractPattern in the file
worldedit-core/src/main/java/com/sk89q/worldedit/function/pattern/AbstractPattern.java, empty class

**Identified by:** Pedro Amorim

**Review:** Clearly the class AbstractPattern is a Dead Code. In this case, the code smell is about a whole class that's empty. Dead code is a code smell where portions of code are written but never used or executed in the program. Dead code makes the codebase harder to maintain, increases complexity, and may cause confusion for future developers. It’s best removed to improve clarity and performance.

### 2. Code Duplication

**Location:** worldedit-core/src/main/java/com/sk89q/worldedit/command/BrushCommands.java
Line 660

**Identified by:** Gustavo Chevrand

**Review:** The identified portion of code is indeed a code duplication issue between the erode, dilate, and morph methods. Since erode and dilate only differ from morph in the parameters they pass to the MorphBrush class, duplicating their code leads to redundancy without additional functionality. The proposed solution to rewrite erode and dilate as calls to morph is effective. It reduces repetition, aligns with DRY principles, and makes the code easier to maintain. This approach maintains the unique behavior of each command while consolidating the core logic in one place, morph, which is cleaner and more sustainable in the long term

### 3. Primitive Type Obsession


**Identified by:** Rildo Franco

**Review:** The observation of Primitive Type Obsession in the DynamicPluginCommand constructor and setPermissions method is spot-on. The use of String[] for permissions limits the expressiveness and flexibility of the code, making it harder to maintain and interpret. Replacing these primitive arrays with more descriptive collections, such as List<String>, or creating custom types specifically for permissions and aliases would encapsulate these elements more effectively. This change would improve readability, support future enhancements, and enable the addition of methods that can directly manage permissions or aliases without relying on separate utility functions like StringUtil.joinString(). Overall, adopting more expressive types would lead to a cleaner and more adaptable codebase in this context.

---

## Design Patterns Review Log


## 1. Adapter

## Author: Rildo Franco
## Date: 11/07/2024



### General Comments
- The use of the Adapter pattern in PaperWeightServerLevelDelegateProxy and PaperWeightAdapter is appropriate, as it enables compatibility between WorldEdit and Minecraft’s internal structures by adapting types and data.



### Specific Comments
- **Adapter Implementation**: PaperWeightServerLevelDelegateProxy effectively serves as an Adapter, transforming EditSession calls into Minecraft-compatible methods. Its methods, like getBlockEntity, getBlockState, and setBlock, use PaperWeightAdapter to convert WorldEdit data into Minecraft-native structures.
- **Complex Data Adaptation**: In fromNative within PaperWeightAdapter, complex Minecraft data types (e.g., CompoundTag, ByteTag) are efficiently adapted, which exemplifies the Adapter pattern’s role in handling diverse data types for compatibility.
- **Error handling in setBlock**: Exception handling in setBlock is suitable but could improve with additional context. Alternatively, using a custom exception type could make adapter errors clearer.
- **Coupling**: Though effective, a possible improvement could involve introducing an interface for PaperWeightAdapter to reduce coupling and facilitate future adaptations.

### Conclusion
- **Final Thoughts:** This review correctly identifies the Adapter pattern and its effective use in facilitating compatibility. Small adjustments to error handling and decoupling could enhance flexibility, but the implementation solidly demonstrates the Adapter pattern’s intended purpose.


## 2. Prototype

## Author: Pedro Amorim
## Date: 11/07/2024



### General Comments
- The use of the Prototype pattern in EditSessionEvent is appropriate, especially in cases where similar event instances with minor variations are frequently required. The pattern is implemented clearly, with the clone method allowing for efficient duplication with customizable properties.



### Specific Comments
- **Efficient Cloning**: The clone(Stage stage) method enables the creation of a new EditSessionEvent instance while reusing existing properties, which can improve performance and reduce redundant object construction when only a slight variation (i.e., Stage) is needed.
- **Simplification of Object Creation**: By allowing EditSessionEvent instances to be cloned with a modified Stage, the Prototype pattern simplifies the process of creating similar events without duplicating code, making the event handling more adaptable.
- **Customizability**: The stage parameter in the clone method is a smart choice for flexibility, allowing variations in event stages without affecting other properties, which makes the EditSessionEvent class more modular and adaptable.

### Conclusion
- **Final Thoughts:** The Prototype pattern is effectively applied here, with the clone method providing a straightforward approach to creating event variations. This design is a good fit for scenarios needing frequent but minor adjustments, enhancing reusability and maintainability.

## 3. Composite

## Author: Rodrigo Castro
## Date: 11/07/2024


### General Comments
- The Composite pattern is well-suited for MaskIntersection2D, as it allows multiple Mask2D objects to be grouped and managed as a single, cohesive entity. This design supports flexibility and simplifies complex mask operations by treating each mask uniformly.



### Specific Comments
- **Effective Composition**: The masks set efficiently holds multiple Mask2D instances, enabling MaskIntersection2D to iterate over each mask and apply the same test operation. This approach showcases the Composite pattern’s strength in unifying and simplifying the use of multiple objects.
- **Clear Implementation of Composite Logic**: The test method correctly applies intersection logic by returning false if any mask’s test method fails. This implementation is clear and efficient, following Composite principles to aggregate results from individual Mask2D components.
- **Potential for Extendability**: With the masks collection, additional Mask2D elements can be added easily, allowing for further customization and scalability of mask combinations, making the Composite design highly adaptable.

### Conclusion
- **Final Thoughts**: The Composite pattern is applied effectively in MaskIntersection2D, enabling a structured, cohesive way to handle multiple masks. This design approach promotes code simplicity and flexibility, aligning well with the requirements for complex 2D mask operations.

---


# Use Case Diagram Review Log
## Date: 11/13/2024


### Use Case: Ascend Levels
### Author: Lucas Tobias

### General Comments
- **Clarity**: The use case is straightforward, and the description is concise. However, specifying more detail about the platform’s function or how it is defined could make the purpose of ascending more explicit for players and developers.
- **Completeness**: The primary and secondary actors are appropriately listed, and the description aligns with the expected functionality.

### Specific Comments
- **Preconditions:** Adding a precondition about floor availability would clarify what happens when the player is on the highest level.
- **Postconditions:** It would be beneficial to state that the player’s position is updated and saved at the new level.
- **Alternative Flows:** Consider an alternative flow for when the target level is obstructed or inaccessible.


### Conclusion
- **Final Thoughts:** This use case is clear but could benefit from additional details in the preconditions and alternative flows to cover boundary scenarios.


### Use Case: Descend Levels
### Author: Lucas Tobias

### General Comments
- Clarity: The purpose of this use case is clear, though a brief explanation about how floors are structured in the environment would be useful.
- Consistency: The flow aligns well with the corresponding “Ascend Levels” use case, maintaining consistent structure.

### Specific Comments
- Preconditions: Similar to “Ascend Levels,” a precondition about floor availability (i.e., not being on the lowest level) would add clarity.
- Alternative Flows: Mentioning what happens if there is an obstacle on the destination floor could enhance the understanding of potential limitations.
### Conclusion
- **Final Thoughts:** This use case is easy to understand but could be improved by addressing scenarios for when the target floor is unavailable or obstructed.


### Use Case: Copy region
### Author: Gustavo Chevrand

### General Comments

- Clarity: The description is very clear, and the actions taken by the player and system are well-defined.
-	Accuracy: The steps align with expected clipboard functionalities in similar applications, making it intuitive.

### Specific Comments

- 	Alternative Flows: Adding an alternative flow for what happens if there is already content in the clipboard could clarify clipboard overwrite behavior.
-    Postconditions: Mentioning whether or not the copied content is retained after the player logs out might be helpful for long-term storage implications.

### Conclusion
- **Final Thoughts:** This use case is strong and comprehensive. Small clarifications on clipboard management could enhance it even further.


### Use Case: Restore Snapshot
### Author: Rildo Franco

### General Comments

- Clarity: The description provides a solid understanding of the restore process, making it clear what the expected outcome is.
- Completeness: The use case is complete, covering both actors and actions in sufficient detail.

### Specific Comments

- 	Alternative Flows: Including an alternative flow in case of an unsuccessful restore (e.g., corrupted snapshot) would account for potential issues.
-    Preconditions: A note on permissions required to access snapshots could be helpful for understanding restrictions.

### Conclusion
- **Final Thoughts:** This is a well-documented use case that could be even more robust with scenarios for errors during the restoration process.


### Use Case: Create Hollow Cylinder (hcyl)
### Author: Rodrigo Castro

### General Comments

- Clarity: The purpose and functionality of this use case are clear, especially for players familiar with similar construction tools.
-    Precision: The parameters of radius and height are specified, making it straightforward for implementation.

### Specific Comments

- Preconditions: Including a precondition specifying if the player needs edit permissions for the area would be helpful.
- Alternative Flows: An alternative flow for what happens if the area is obstructed (e.g., by other structures) could add depth.

----


  ## Code Smells Review Log
# Reviewer: [Rodrigo Castro]
## 1. Primitive Type Obsession
## Author: [Gustavo Chevrand]
- It's indeed a Primitive Type Obsession because it uses separate int values to represent coordinates instead of encapsulating them in a single, meaningful object (BlockVector3).
---
## 2. Long Parameter List
## Author: [Lucas Tobias]
- The Long Parameter List smell is apparent in the setBlockStateHook method, which has five parameters. This indicates the method could be simplified by encapsulating these parameters into a single object, reducing complexity and improving readability.
---
## 3. Large Class
## Author: [Lucas Tobias]
- The FabricDataFixer class is a Large Class code smell, containing 2764 lines. This excessive length suggests it may be overloaded with multiple responsibilities, which increases its complexity and reduces maintainability. With a high Response For a Class (RFC) value of 118—well above the recommended limit of 45—the class likely has numerous method calls, further indicating high coupling and complexity.

## Design Patterns Review Log
## Reviewer: [Rodrigo Castro]
## Author: [Gustavo Chevrand]
## 1. Builder
EditSessionBuilder is a well-implemented Builder pattern class. It demonstrates the Builder pattern by allowing step-by-step configuration of an EditSession object through chained setter methods, with the final build() method handling object creation. This pattern enables flexible and readable construction of complex objects.
---
## Reviewer: [Rodrigo Castro]
## Author: [Lucas Tobias]
## 2. Factory Method
The factory method in PluginPermissionsResolver is a true Factory Method because it encapsulates the decision-making logic required to create and return a PermissionsResolver instance based on specific conditions.
---
## Reviewer: [Rodrigo Castro]
## Author: [Rildo Franco]
## 3. Observer
The design aligns with the Observer Pattern as it uses an event-driven model where PlatformManager subscribes to events (i.e., listens for updates) through EventBus. It reacts to these events by taking specific actions, such as updating platform preferences and notifying other components.
# Use Case Diagram Review Log
## Reviewer: [Rodrigo Castro]
## Use Case: Rotate Region
### Author: Gustavo Chevrand 64616
- The Player is the primary actor, as they initiate the command to rotate the selected region, while Minecraft acts as the secondary actor, executing the rotation of the region within the game world.
- Evaluation: The use case correctly outlines the roles of both the Player and Minecraft, and the description accurately captures the conditions and expected results for this command.
- Status: The use case is correct.
---
### Use Case: Apply Heightmap
### Author: Pedro Amorim 64614
- The Player is the primary actor since they initiate the heightmap command, while the Server acts as the secondary actor, handling the processing required to apply the heightmap to the terrain. The pre-conditions are well-defined, ensuring that the player has the necessary permissions, the heightmap image is accessible, and the area size is within system limits. Post-conditions confirm the terrain is modified according to the image and that the player receives confirmation.
- Evaluation: This use case is well-documented, capturing the interaction accurately between Player and Server. All steps and conditions are appropriate.
- Status: The use case is correct.
### Use Case: Modify Biome
### Author: Pedro Amorim 64614
- The Player is the primary actor, as they issue the command to alter the biome, and the Server is the secondary actor, applying the changes to environmental factors in the specified region. Pre-conditions ensure permissions and compatibility with area and biome type, while post-conditions confirm that the biome has been updated and the player receives notification.
- Evaluation: The use case accurately reflects the actions taken by both the Player and Server, and the conditions effectively cover access control and compatibility checks.
- Status: The use case is correct.
### Use Case: Paste Clipboard Structure
### Author: Pedro Amorim 64614
- The Player is the primary actor, initiating the paste action, and the Server is the secondary actor, executing the structure paste in the game world. Pre-conditions ensure that the player has paste permissions, a structure is available in the clipboard, and the area size is within system constraints. Post-conditions confirm that the structure is successfully pasted, with confirmation provided to the player.
- Evaluation: This use case is clearly outlined, with pre- and post-conditions that provide necessary constraints and completion feedback to the player. The roles of the Player and Server are well-defined.
- Status: The use case is correct.
### Use Case: Manage Mobs
### Author: Pedro Amorim 64614
- The Player is the primary actor, initiating mob management, while the Server acts as the secondary actor, handling mob removal within the specified area. Pre-conditions verify permissions and specify that the area is valid. Post-conditions ensure that the targeted mobs are removed and that the player receives confirmation.
- Evaluation: This use case is properly defined, with clear pre- and post-conditions to ensure the action completes as intended. The Player and Server roles are accurately represented.
- Status: The use case is correct.

# Code Metrics Review Log
## Reviewer: [Rodrigo Castro]
## Author: [Nicolas Nascimento]
## RFC Review:
- The RFC for BlockState is 152, well above the recommended range, indicating strong coupling and high complexity. This makes the class harder to test and maintain. Refactoring could reduce dependencies and improve modularity.
---
## Author: [Nicolas Nascimento]
## NOM Review:
- With a NOM of 87, the BlockState class is overloaded with methods, suggesting excessive responsibilities. Reducing method count by splitting the class into smaller, focused components would enhance readability and maintainability.
---
## Author: [Rildo Franco]
## Cognitive Complexity Review:
- The Cognitive Complexity of 98 is well above the recommended range, indicating that the code is likely too intricate and hard to follow. Simplifying the logic and breaking down complex methods would improve readability and maintainability, making the code more approachable and easier to work with.

---

### Conclusion
- **Final Thoughts:** This use case is well-documented, and a few additional conditions would make it even more comprehensive.
