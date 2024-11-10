
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
## Author: [Nicolas Nascimento]  
## Date: [11/10/2024]

---

### General Comments
- The code smells report effectively identifies the "Large Class" issue in the `EditSession` class located at `worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java`. It provides a clear explanation of how this code smell can negatively impact code readability, maintainability, and scalability.

### Specific Comments
- **Large Class:** The `EditSession` class currently has 2981 lines of code, which is significantly large and indicates that the class is handling too many responsibilities. This violates the Single Responsibility Principle (SRP) and makes the class difficult to understand, maintain, and extend.

- **Extent Management:** The class handles extent configuration, which could be moved to a separate `ExtentManager` class.

- **Undo/Redo Operations:** The class manages undo and redo operations, which could be moved to a separate `UndoRedoManager` class.


### Conclusion
- **Final Thoughts:** The proposed solution to refactor the `EditSession` class by applying the Single Responsibility Principle and extracting functionality into separate classes is a sound approach. This will make the code more modular, easier to understand, and maintain. Future refactoring efforts should ensure that all references to the `EditSession` class are updated to use the new structure. Overall, addressing this code smell will enhance the code's readability, maintainability, and scalability.