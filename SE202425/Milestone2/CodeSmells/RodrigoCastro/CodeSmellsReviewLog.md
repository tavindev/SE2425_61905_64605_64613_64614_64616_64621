# Code Smells Review Log

## Reviewer: [Rodrigo Castro]

## 1. Primitive Type Obssession
## Author: [Gustavo Chevrand]

It's indeed a Primitive Type Obsession because it uses separate int values to represent coordinates instead of encapsulating them in a single, meaningful object (BlockVector3).

---

## 2. Long Parameter List
## Author: [Lucas Tobias]

The Long Parameter List smell is apparent in the setBlockStateHook method, which has five parameters. This indicates the method could be simplified by encapsulating these parameters into a single object, reducing complexity and improving readability.
---

## 3. Large Class
## Author: [Lucas Tobias]


