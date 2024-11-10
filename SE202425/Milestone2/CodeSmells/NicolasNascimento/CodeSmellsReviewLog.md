# Code Smells Review Log

## Reviewer: Nicolas Nascimento
## Date: 11/10/2024

---

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

**Location:**

**Identified by:** Rildo Franco

**Review:** The observation of Primitive Type Obsession in the DynamicPluginCommand constructor and setPermissions method is spot-on. The use of String[] for permissions limits the expressiveness and flexibility of the code, making it harder to maintain and interpret. Replacing these primitive arrays with more descriptive collections, such as List<String>, or creating custom types specifically for permissions and aliases would encapsulate these elements more effectively. This change would improve readability, support future enhancements, and enable the addition of methods that can directly manage permissions or aliases without relying on separate utility functions like StringUtil.joinString(). Overall, adopting more expressive types would lead to a cleaner and more adaptable codebase in this context.

---

### General Comments
- Feedback on the code smells report.

### Conclusion
- **Final Thoughts:** Wrap-up comments.
