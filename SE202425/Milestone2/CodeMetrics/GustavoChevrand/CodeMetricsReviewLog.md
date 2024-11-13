# Code Metrics Review Log

## 1. Response For A Class (Chidamber-Kemerer Metrics Set):

Author: Rildo Franco 64605

The Response For A Class (RFC) value of 361 significantly exceeds the acceptable upper limit of 45, indicating that the class is excessively complex and highly coupled. This high RFC suggests that the class can execute a vast number of methods in response to a message, making it difficult to understand, test, and maintain. Such complexity often results from the class handling too many responsibilities, violating the Single Responsibility Principle. To enhance code quality, it is recommended to refactor the class by decomposing it into smaller, more focused classes, each with a distinct responsibility, thereby reducing coupling and improving maintainability.

## 2. Number of Parameter

Author: Lucas Tobias 64613

The equals(Object) method has a Number of Parameters value of 1, which comfortably falls within the regular acceptable range of 0 to 3. This low parameter count is ideal, as it promotes simplicity and ease of understanding when comparing objects—a fundamental operation in object-oriented programming. The method's design aligns with best practices, making it highly rated in various metric evaluations. Keeping the number of parameters minimal enhances readability, maintainability, and reduces the likelihood of errors. No action is needed for this metric; the method is well-designed and adheres to standard coding conventions.

## 3. CC Calculation

Author: Nicolas Nascimento 61905

The generateStateMap() method has a Cyclomatic Complexity (CC) value of 12, which significantly exceeds both the regular acceptable range of 0 to 3 and the extreme threshold of 7. This high CC indicates that the method contains numerous decision points—such as conditional statements, loops, and switch cases—resulting in a large number of possible execution paths. Such complexity can make the method difficult to understand, test, and maintain, as it increases the effort required to cover all paths during testing and raises the likelihood of bugs. To enhance code quality, it is advisable to refactor generateStateMap() by breaking it down into smaller, more focused methods, each adhering to the Single Responsibility Principle. This will reduce cyclomatic complexity, improve readability, and simplify testing and maintenance efforts.
