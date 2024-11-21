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
