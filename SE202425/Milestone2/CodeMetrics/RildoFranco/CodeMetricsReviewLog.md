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
