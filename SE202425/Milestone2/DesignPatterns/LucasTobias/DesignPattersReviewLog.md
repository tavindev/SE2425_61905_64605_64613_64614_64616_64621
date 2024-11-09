# Design Patterns Review Log

---

## 1. Template Method

### Reviewer: Lucas Tobias

### Author: Gustavo Chevrand

### Date: 28/10/2021

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

### Reviewer: Lucas Tobias

### Author: Nicolas Nascimento

### Date: 28/10/2021

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

### Reviewer: Lucas Tobias

### Author: Pedro Amorim

### Date: 28/10/2021

---

### General Comments
- It facilitates communication between two existing systems by providing a compatible interface.

---

### Specific Comments
- JSON data is supplied as an array and the adapter converts it to the Vector3 object. object that will be used in the program. Without the adapter it wouldn't be possible.

### Conclusion
- **Final Thoughts:** The Adapter pattern is correctly applied because it allows the JSON data to be converted into a Vector3 object, which is used in the program. Without the adapter, this conversion would not be possible.


