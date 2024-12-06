# Code Metrics Review Log

## Reviewer: Nicolas Nascimento
## Date: 11/13/2024

---

### Code Metrics analyzed: Cognitive Complexity
### Author: Gustavo Chevrand

- **Analysis Summary**: The updateServerCUI method in LocalSession.java has a Cognitive Complexity of 9, slightly exceeding the regular threshold of [0..8[ for methods. While the method is not as complex as others like makeSphere in EditSession.java (with a value of 68), it still requires readers to track nested conditions and logic blocks. This moderate complexity may lead to challenges in understanding and maintaining the method as the codebase grows.

- **Suggestions**: The current complexity of updateServerCUI is manageable but could benefit from minor refactoring to enhance readability and maintainability. Consider: Simplifying logic by extracting parts of the conditional checks into smaller helper methods.
Adding comments to clarify the intent behind each condition block.
Reducing nesting by utilizing early returns where applicable to streamline the flow of the method.

---

### Code Metrics analyzed: Condition Nesting Depth (CND)
### Author: Lucas Tobias

- **Analysis Summary**: The method factorial(double) in CompilingVisitor.java has a Condition Nesting Depth (CND) of 1, well within the acceptable range of [0..2]. This indicates that the method has minimal nesting, making it straightforward to understand and maintain. The low complexity of the method ensures that its logic is easy to follow, contributing to better readability and testability.

- **Suggestions**: The method’s low CND reflects good design practices. No immediate changes are needed. However, it is recommended to maintain this simplicity as the method evolves, avoiding deep nesting or unnecessary complexity to ensure continued maintainability.

---

### Code Metrics analyzed: NOM Calculation (Li-Henry Metrics Set) - Class Level
### Author: Rodrigo Castro

- **Analysis Summary**: The class analyzed has a NOM of 94, far exceeding the regular threshold of 7 and the extreme range of 25. This indicates that the class likely has too many responsibilities, leading to decreased readability and higher maintenance demands.

- **Suggestions**: Refactor by distributing functionality across smaller, more cohesive classes. Applying the Single Responsibility Principle would reduce method count, simplify maintenance, and improve code modularity.

---