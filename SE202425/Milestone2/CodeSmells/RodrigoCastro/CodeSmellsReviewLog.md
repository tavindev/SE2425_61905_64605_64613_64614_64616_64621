# Code Smells Review Log

## Reviewer: [Rodrigo Castro]

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

