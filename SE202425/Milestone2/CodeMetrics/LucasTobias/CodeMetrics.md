# Code Metrics Analysis

## Author: Lucas Tobias

Class: CompilingVisitor
---
## LCOM (Lack of Cohesion of Methods) (Chidamber-Kemerer Metrics Set):
- Value collected: 7
- Reference values(Regular): The median of the entire project is 2, but the value found for CompilingVisitor is not even in the top 400 of all classes.

- What It Means: 
    LCOM is a metric that measures the lack of cohesion in a class. A high LCOM value indicates that the class is not cohesive, meaning that its methods are not closely related to each other. This can make the class harder to understand, test, and maintain.
    

- What It Indicates:
    An LCOM value of 7 is higher than the median, but you can see that it's not too high compared to the highest value, which in this case is 64. So compared to all the classes in general, it shows a little more lack of cohesion. But not an astronomical level.
 

## Observations

- Improvement Suggestions: 
    Consider refactoring the class to improve cohesion. This can be done by splitting the class into smaller, more focused classes or by moving methods to other classes where they are more closely related. This can help improve readability, maintainability, and testability.
  

---

## CND (Condition Nesting Depth) (Chidamber-Kemerer Metrics Set) - Method Level:
Method: factorial(double)
- Value collected: 1
- Reference values (Regular): [0..2]

  - What It Means: 
  This metric measures the maximum nesting depth of condition statements. When statements are nested too deeply in the code they become difficult to understand. This is because understanding a line that is deeply nested requires an understanding of the context of that line. A method that contains a high level of nesting can be very difficult to understand.

  - What It Indicates: 
  A CND value of 1 is within the reference range of 0 to 2, indicating that the method has a low level of nesting and is relatively easy to understand. This can make the method easier to maintain and test.
    A value of 1 is the highest found in this class, which ranges from 0 to 1.

## Observations

- Improvement Suggestions: 
  The method `factorial(double)` has a low level of nesting, which is a positive sign for maintainability and readability.

---

## WMC (Weighted Methods per Class) (Chidamber-Kemerer Metrics Set) - Class Level:
- Value collected: 99 (10th highest WMC value. Compared to all classes, a median of 7 is obtained.)
- Reference values (Regular): [0..12]

- What It Means:
    Weighted Methods per Class (WMC) is a metric that calculates the sum of the complexities of all methods in a class. It provides an overall measure of the class's complexity based on the number of methods and their complexity.

- What It Indicates:
    A WMC value of 99 is significantly higher than the reference range of 0 to 12, indicating that this class is complex and may have too many methods or methods with high complexity. This can make the class harder to understand, test, and maintain.

## Observations

- Improvement Suggestions:
    Consider refactoring the class to reduce the number of methods and the complexity of individual methods. Splitting the class into smaller, more focused classes can help improve readability, maintainability, and testability.

---

### Summary
- **Overall Assessment:**
  You can see that the CompilingVisitor class has some metrics with high values, with many above average, but it's not a very “robust” class when compared to other classes that have a higher level of complexity, such as EditSession, and PaperweightAdapter.
