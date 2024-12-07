# MergedDocReviewLog

## Author: Gustavo Chevrand 64616

## Use cases

### 1. Create Solid Cylinder  

Author: Rodrigo Castro 64621

The player is the primary actor because they issue the command, and there is no secondary actor. The use case is correct.

### 2. Select Snapshot 

Author: Rildo Franco 64605

The player is the primary actor because they issue the command. There is no secondary actor. The use case is correct.

### 3. Unstuck player

Author: Lucas Tobias 64613

The player is the primary actor because they issue the command, and there is no secondary actor. The use case is correct.

### 4. Save Region to Clipboard

Author: Nicolas Nascimento 61905

The player is the primary actor because they issue the command. There is no secondary actor, since the server for example, doesn't do anything. The use case diagram picture displays the server as the secondary actor, which is wrong.

### 5. Apply heightmap

Author: Pedro Amorim 64614

The player is the primary actor because they issue the command, and there is no secondary actor. The use case diagram picture displays the server as the secondary actor, which is wrong.

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
- EditSessionBuilder is a well-implemented Builder pattern class. It demonstrates the Builder pattern by allowing step-by-step configuration of an EditSession object through chained setter methods, with the final build() method handling object creation. This pattern enables flexible and readable construction of complex objects.
---
## Reviewer: [Rodrigo Castro]
## Author: [Lucas Tobias]
## 2. Factory Method
- The factory method in PluginPermissionsResolver is a true Factory Method because it encapsulates the decision-making logic required to create and return a PermissionsResolver instance based on specific conditions.
---
## Reviewer: [Rodrigo Castro]
## Author: [Rildo Franco]
## 3. Observer
- The design aligns with the Observer Pattern as it uses an event-driven model where PlatformManager subscribes to events (i.e., listens for updates) through EventBus. It reacts to these events by taking specific actions, such as updating platform preferences and notifying other components.
## Use Case Diagram Review Log
# Reviewer: [Rodrigo Castro]
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

## Code Metrics Review Log
# Reviewer: [Rodrigo Castro]
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

----
#Reviews Lucas Tobias 64613

**CODE SMELLS**
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

**DESIGN PATTERNS**
# Design Patterns Review Log

---

## 1. Template Method

### Reviewer: Lucas Tobias

### Author: Gustavo Chevrand



---

### General Comments
- It defines the steps of an algorithm in general, deferring the implementation of some steps to subclasses.

---

### Specific Comments
- The getAsset method has two abstract methods as subclasses, getAllowedExtensions and loadAssetFromPath. Each subclass provides its own behavior to determine the allowed file extensions and load assets from a path.


### Conclusion
- **Final Thoughts:** Template Method is correct because the AssetLoader abstract class defines a method that relates to the general algorithm, which defers the implementation of some steps to the subclasses.

---

## 2. Factory Method


### Author: Nicolas Nascimento


---

### General Comments
- Factory Method is a creative design pattern that provides an interface for creating objects in a superclass, but allows subclasses to change the type of objects that will be created.

- We can replace one concrete product class with another with no impact on the client's code.

---

### Specific Comments
- Use of the register method which will create new PatternParser objects such as ClipboardPatternParser, TypeOrStateApplyingPatternParser, RandomStatePatternParser, BlockCategoryPatternParser. This does not impact the rest of the code, regardless of the object.

### Conclusion
- **Final Thoughts:** Design Pattern factory method is correct because the register can create different objects, which hide the creation of instances of a certain type. Where the code doesn't depend on concrete classes.


---

## 3. Adapter


### Author: Pedro Amorim


---

### General Comments
- It facilitates communication between two existing systems by providing a compatible interface.

---

### Specific Comments
- JSON data is supplied as an array and the adapter converts it to the Vector3 object. object that will be used in the program. Without the adapter it wouldn't be possible.

### Conclusion
- **Final Thoughts:** The Adapter pattern is correctly applied because it allows the JSON data to be converted into a Vector3 object, which is used in the program. Without the adapter, this conversion would not be possible.


**USE CASES**

---

### Use Case: Create Pyramid
### Author: Rodrigo Castro

The primary actor is correct because he is the one who performs the action of creating the pyramid, the server is the secondary actor because he will perform the action. Correct description.

---

### Use Case: List Snapshot
### Author: Rildo Franco

The main actor (Player) is correct, he is the one who requests the action of listing the snapshots, Correct use case.

---

### Use Case: Find Snapshot after date
### Author: Rildo Franco

The main actor (Player) is correct, he is the one who requests the action of finding the snapshot after the date, Correct description.

---

### Use Case: Remove Region
### Author: Nicolas Nascimento

The main actor (Player) is requests, he is the one who performs the action of removing the region, and Minecraft serves as a secondary actor, as he is the one who removes the region, correct description.

---

### Use Case: Cut Region
### Author: Gustavo Chevrand

The main actor (Player) is correct, it is he who requests the action of cutting the region, and Minecraft serves as a secondary actor, since it is he who removes the blocks from the region and stores them on an imaginary clipboard. The server does the cutting.



**CODE METRICS**

---

### Author: Gustavo Chevrand
### Code Metrics analyzed: Weighted Methods per Class
Class: EditSession.java
Value: 397, reference: [0, 12)
With a very high value, compared to the regular value. The EditSession class is very complex in terms of code and understanding.

---
### Author: Rildo Franco
### Code Metrics analyzed: Condition Nesting Depth
Class: PaperWeight.java

Value: 1. Reference Value: [0, 2[

The code has a low nesting depth, which makes it easier to understand and maintain.

---
### Author: Rodrigo Castro
### Code Metrics analyzed: CC Calculation (McCabe Cyclomatic Complexity) - Method Level Value: 10, Regular: [0..3]
Class: LocalSession.java

Method: UpdateServerCUI(Actor actor)

Cyclomatic complexity (CC) is a metric that evaluates the complexity of a method by counting the decision points present in it, such as conditional structures, loops and case statements. The method is rated highly, according to the range. This means that the code has more possible paths, which tends to make it more difficult to understand.

---
# Code Metrics Review Log

## Reviewer: [Rildo Franco]
## Author: [Lucas Tobias]
## Date: [11/13/2024]

---

### General Comments
- The Weighted Methods per Class (WMC) value for the class is 92, which is significantly higher than the acceptable range of 0 to 12. This indicates that the class is highly complex.

---

### Specific Comments
- **Complexity:** The high WMC value suggests that the class has many methods or that the methods themselves are complex.
- **Maintability:** A high WMC value can make the class difficult to understand, maintain, and test.
- **Code Quality:** The elevated complexity may indicate issues such as code bloat or low cohesion within the class.
Conclusion

### Conclusion
- **Final Thoughts:** The FabricWorld class has a WMC value of 92, which is well above the acceptable range. This suggests that the class is overly complex and may benefit from refactoring to improve readability, maintainability, and cohesion.

----

## Author: [Rodrigo Castro]
## Date: [11/13/2024]

---

### General Comments
- The Number Of Methods (NOM) value for the class is 94, which is significantly higher than the acceptable range of 0 to 7. This indicates that the class is highly overloaded with methods.

---

### Specific Comments
- **Complexity:** The high NOM value suggests that the class has a large number of methods, making it complex and potentially difficult to understand.
- **Maintability:**  A high NOM value can make the class challenging to maintain and test, as it may be handling too many responsibilities.
- **Code Quality:**  The elevated number of methods may indicate that the class is not adhering to the Single Responsibility Principle, leading to reduced readability and increased maintenance costs.

### Conclusion
- **Final Thoughts:** The class has a NOM value of 94, which is well above the acceptable range. This suggests that the class is overly complex and may benefit from refactoring to distribute functionality across smaller, more cohesive classes. Applying design principles such as Single Responsibility and modular design can improve maintainability and clarity.

----

## Author: [Gustavo Chevrand]
## Date: [11/13/2024]

---

### General Comments
- The Condition Nesting Depth for the method redo(EditSession editSession) is 0, which is within the acceptable range of 0 to 2. This indicates that the method has no nested control structures.

---

### Specific Comments
- **Complexity:** The absence of nested control structures suggests that the method is straightforward and easy to follow.
- **Maintability:** A nesting depth of 0 enhances the maintainability of the code, as it is simpler to understand and modify.
- **Code Quality:** The low nesting depth indicates that the method is well-structured and adheres to good coding practices.

### Conclusion
- **Final Thoughts:** The redo(EditSession editSession) method has a Condition Nesting Depth of 0, which is within the acceptable range. This indicates that the method is simple and easy to understand, contributing positively to the overall readability and maintainability of the code. No immediate changes are necessary.

----

# Code Smells Review Log

## Reviewer: [Rildo Franco]
## Author: [Gustavo Chevrand]
## Date: [11/10/2024]

---

### General Comments
- The code smells report provides a detailed identification of potential issues in the `actPrimary` method in `AreaPickaxe.java`. Specifically, the report recognizes the "Long Parameter List" code smell, which may decrease code readability and maintainability by making the method harder to understand at a glance.

### Specific Comments
- **Long Parameter List:** The `actPrimary` method currently accepts six parameters, which is on the higher side and can make the method challenging to work with. The solution proposes removing the `face` parameter since it is unused, which is a good start for reducing the parameter count.

- **Consistency Check:** Once the `face` parameter is removed, check for other methods or places where `actPrimary` is called to ensure consistency across the codebase.

### Conclusion
- **Final Thoughts:** The proposed solution addresses the immediate issue by removing the unused `face` parameter, which simplifies the parameter list. Future considerations could include further refactoring to encapsulate related parameters into objects where logical. Overall, addressing this code smell will likely improve the code’s readability and maintainability.

---

## Reviewer: [Rildo Franco]
## Author: [Pedro Amorim]
## Date: [11/10/2024]

---

### General Comments
- The code smells report effectively identifies the "Large Class" issue in the `PaperweightDataConverters` class. This code smell occurs when a class has grown too big and is trying to handle too many responsibilities. This can make the code harder to read, understand, and maintain.

### Specific Comments
- **Large Class:** The `PaperweightDataConverters` class currently handles multiple types of data conversions, which violates the Single Responsibility Principle (SRP). This makes the class harder to maintain and test.
- **Refactoring Suggestion:** To address this issue, we can consider breaking down the `PaperweightDataConverters` class into smaller, more focused classes. Each new class should handle a specific type of data conversion. This will improve the code's readability, maintainability, and testability.


### Conclusion
- **Final Thoughts:** Refactoring the PaperweightDataConverters class to reduce its size and focus on single responsibilities will improve the code's readability, maintainability, and testability. By breaking down the class into smaller, more focused classes, you can make the code more modular and easier to work with.

---

## Reviewer: [Rildo Franco]
## Author: [Pedro Amorim]
## Date: [11/10/2024]

### General Comments
- The code smells report effectively identifies the "Message Chain" issue in the `getGroups` method within the `SpongePlayer` class. This code smell occurs when a method calls a series of methods on different objects, creating a long chain of method calls. This can make the code harder to read, understand, and maintain.

### Specific Comments
- **Message Chain:** The `getGroups` method currently calls `SpongeWorldEdit.inst().getPermissionsProvider().getGroups(this.player)`, which is a classic example of a message chain. This tightly couples the `SpongePlayer` class to the `SpongeWorldEdit` singleton and the `PermissionsProvider`, making the code less flexible and harder to test.
- **Refactoring Suggestion:** To address this issue, we can consider introduce a method in the `SpongeWorldEdit` class that directly returns the groups for a player. This will reduce the message chain and make the code more readable and maintainable.

### Conclusion
- **Final Thoughts:** Refactoring the `getGroups` method to reduce the message chain will improve the code's readability, maintainability, and testability. By introducing a method in the `SpongeWorldEdit` class that directly returns the groups for a player, you can decouple the `SpongePlayer` class from the `PermissionsProvider` and make the code more modular.

---
# Design Patterns Review Log

## Reviewer: [Rildo Franco]
## Author: [Gustavo Chevrand]
## Date: [11/06/2024]

---

### General Comments
The Composite pattern is effectively applied in `RegionIntersection`, allowing a collection of `Region` objects to be managed uniformly. This structure simplifies handling individual and composite regions with clarity and flexibility.

---

### Specific Comments
- **Composite Structure**: `RegionIntersection` implements the `Region` interface and holds a list of `Region` objects, which fits well with the Composite pattern’s purpose of treating individual and composite elements uniformly.
- **getMinimumPoint Method**: The method iterates through each region and compares their minimum points, clearly and correctly aggregating results. The implementation is straightforward and understandable.
- **Clarity and Efficiency**: The code is clear and efficient for a moderate number of regions, but performance may be impacted as the dataset grows.

---

### Conclusion
- **Final Thoughts**: The Composite pattern is well-implemented, ensuring both clarity and flexibility. For larger datasets, performance and scalability should be considered, with potential optimizations to maintain efficiency.


## Reviewer: [Rildo Franco]
## Author: [Rodrigo Castro]
## Date: [11/08/2024]

---

### General Comments
The Command pattern is effectively adapted in `HistoryCommands`, `LocalSession`, and `EditSession`, allowing for a streamlined approach to managing undo and redo operations. This design centralizes history manipulation and enhances memory efficiency by using `EditSession` objects as command units.

---

### Specific Comments
- **Command Structure**: `HistoryCommands` acts as the command invoker, providing user-facing commands for undoing, redoing, and clearing history. It delegates execution to `LocalSession`, which manages the command history.
- **Undo Method**: The `undo` method in `HistoryCommands` iterates through the history of `EditSession` instances, performing the undo operations. This method is clear and effectively aggregates results.
- **Clarity and Efficiency**: The code is clear and efficient, avoiding the creation of individual command classes for each action. This adaptation of the Command Pattern streamlines memory usage while maintaining complex undo/redo capabilities.
- **Permission Handling**: The `undo` method includes permission checks for undoing other players' operations, ensuring that only authorized actors can perform such actions.

---

### Conclusion
- **Final Thoughts**: The Command pattern is well-adapted, ensuring both clarity and efficiency. The use of `EditSession` objects as command units simplifies the design and enhances memory efficiency. For larger datasets, performance and scalability should be considered, with potential optimizations to maintain efficiency. The inclusion of permission and error handling further strengthens the robustness of the implementation.


## Reviewer: [Rildo Franco]
## Author: [Nicolas Nascimento]
## Date: [11/11/2024]

---

### General Comments
The builder pattern is well-implemented in this class, effectively showcasing its purpose by progressively configuring an asynchronous command object. The design is clean, and the code adheres to good software engineering principles. The use of nullable fields ensures flexibility while still maintaining strong type safety where necessary. The integration of multiple patterns (Command, Chain of Responsibility, and State) is a good choice to enhance system modularity and flexibility.

---

### Specific Comments
- **Code Structure**: Code Structure: The class is easy to follow, and the method names are descriptive. However, consider adding more inline comments for clarity, especially regarding the purpose of certain nullable fields like `exceptionConverter` and `failureMessage`.

- **Build Method**: The `buildAndExec` method is a key point of the class and is implemented well. The way it integrates the asynchronous execution with potential future progress listeners and task monitoring is impressive. However, you might want to add more exception handling or logging in this method to deal with potential issues in real-world applications.

- **Fluent Interface**: The builder pattern's fluent interface is properly used, allowing for readable and clear method chaining. The configuration options are neatly divided into logically grouped setter methods.

### Conclusion
- **Final Thoughts**: This implementation demonstrates the Builder pattern’s power in a complex, real-world scenario. The code is well-organized and provides clear points for extension. However, we can consider adding more explicit documentation and possibly refining the error handling and logging aspects to make the system more resilient.

---

# Use Case Diagram Review Log

## Reviewer: [Rildo Franco]
## Author: [Gustavo Chevrand]
## Date: [11/13/2024]

---
Flip Region

### General Comments
- The use case describes the "Flip region" functionality, which involves flipping the clipboard based on the player's facing direction and positioning. The primary actor is the player, and there are no secondary actors.

---

### Specific Comments
- **Clarity:** The description is clear and concise, effectively conveying the purpose of the use case.
- **Actors:** The identification of the primary actor as the player is appropriate. The absence of secondary actors is noted and seems reasonable for this use case.
- **Functionality:** The use case focuses on a specific action (flipping the clipboard) and its dependency on the player's orientation, which is well-defined.


---

### Conclusion
- **Final Thoughts:** The use case diagram for "Flip region" is well-defined and clear. It accurately identifies the primary actor and describes the functionality in a concise manner. No additional feedback is necessary at this time.

## Author: [Lucas Tobias]
## Date: [11/13/2024]

---
Teleport to Targeted Block

### General Comments
- The use case outlines the "Teleport to Targeted Block" functionality, where the player is moved to the location of the target block. The primary actor is the user (player), with no secondary actors involved.

---

### Specific Comments
- **Clarity:** The description is straightforward and effectively communicates the purpose of the use case.
- **Actors:** The primary actor is correctly identified as the player. The lack of secondary actors is appropriate for this scenario.
- **Functionality:** The use case centers on a specific action (teleportation) and its direct impact on the player, which is clearly defined.


---

### Conclusion
- **Final Thoughts:** The use case diagram for "Teleport to Targeted Block" is clear and well-defined. It accurately identifies the primary actor and describes the functionality concisely. No additional feedback is necessary at this time.


## Author: [Rodrigo Castro]
## Date: [11/13/2024]

---
Create Hollow Sphere (hsphere)

### General Comments
- This use case details the "Create Hollow Sphere (hsphere)" functionality, where a hollow sphere is generated around the player's feet with specified radii in the x, y, and z directions. The primary actor is the player, and there are no secondary actor

---

### Specific Comments
- **Clarity:** The description is clear and effectively communicates the purpose and functionality of creating a hollow sphere.
- **Actors:** The primary actor is appropriately identified as the player. The absence of secondary actors is suitable for this use case.
- **Functionality:** The use case focuses on the action of generating a hollow sphere around the player, with specific radii parameters, which is well-defined.


---

### Conclusion
- **Final Thoughts:** The use case for "Create Hollow Sphere (hsphere)" is well-articulated and clear. It correctly identifies the primary actor and describes the functionality in a concise manner. No further feedback is required at this time.



## Author: [Nicolas Nascimento]
## Date: [11/13/2024]

---
Save Region to Clipboard

### General Comments
- This use case outlines the "Save Region to Clipboard" functionality, where the player can save a selected region into an in-memory clipboard for later use or modification. The primary actor is the user (player), and there are no secondary actors.


---

### Specific Comments
- **Clarity:** The description is clear and effectively conveys the purpose of saving a region to the clipboard.
- **Actors:** The primary actor is appropriately identified as the user (player). The lack of secondary actors is suitable for this scenario.
- **Functionality:** The use case centers on the action of saving a selected region to an in-memory clipboard, which is well-defined.


---

### Conclusion
- **Final Thoughts:**  The use case for "Save Region to Clipboard" is well-explained and clear. It accurately identifies the primary actor and succinctly describes the functionality. No additional feedback is needed at this time.

---
# Code Metrics Review Log

## Reviewer: [Rildo Franco]
## Author: [Lucas Tobias]
## Date: [11/13/2024]

---

### General Comments
- The Weighted Methods per Class (WMC) value for the class is 92, which is significantly higher than the acceptable range of 0 to 12. This indicates that the class is highly complex.

---

### Specific Comments
- **Complexity:** The high WMC value suggests that the class has many methods or that the methods themselves are complex.
- **Maintability:** A high WMC value can make the class difficult to understand, maintain, and test.
- **Code Quality:** The elevated complexity may indicate issues such as code bloat or low cohesion within the class.
Conclusion

### Conclusion
- **Final Thoughts:** The FabricWorld class has a WMC value of 92, which is well above the acceptable range. This suggests that the class is overly complex and may benefit from refactoring to improve readability, maintainability, and cohesion.

----

## Author: [Rodrigo Castro]
## Date: [11/13/2024]

---

### General Comments
- The Number Of Methods (NOM) value for the class is 94, which is significantly higher than the acceptable range of 0 to 7. This indicates that the class is highly overloaded with methods.

---

### Specific Comments
- **Complexity:** The high NOM value suggests that the class has a large number of methods, making it complex and potentially difficult to understand.
- **Maintability:**  A high NOM value can make the class challenging to maintain and test, as it may be handling too many responsibilities.
- **Code Quality:**  The elevated number of methods may indicate that the class is not adhering to the Single Responsibility Principle, leading to reduced readability and increased maintenance costs.

### Conclusion
- **Final Thoughts:** The class has a NOM value of 94, which is well above the acceptable range. This suggests that the class is overly complex and may benefit from refactoring to distribute functionality across smaller, more cohesive classes. Applying design principles such as Single Responsibility and modular design can improve maintainability and clarity.

----

## Author: [Gustavo Chevrand]
## Date: [11/13/2024]

---

### General Comments
- The Condition Nesting Depth for the method redo(EditSession editSession) is 0, which is within the acceptable range of 0 to 2. This indicates that the method has no nested control structures.

---

### Specific Comments
- **Complexity:** The absence of nested control structures suggests that the method is straightforward and easy to follow.
- **Maintability:** A nesting depth of 0 enhances the maintainability of the code, as it is simpler to understand and modify.
- **Code Quality:** The low nesting depth indicates that the method is well-structured and adheres to good coding practices.

### Conclusion
- **Final Thoughts:** The redo(EditSession editSession) method has a Condition Nesting Depth of 0, which is within the acceptable range. This indicates that the method is simple and easy to understand, contributing positively to the overall readability and maintainability of the code. No immediate changes are necessary.



