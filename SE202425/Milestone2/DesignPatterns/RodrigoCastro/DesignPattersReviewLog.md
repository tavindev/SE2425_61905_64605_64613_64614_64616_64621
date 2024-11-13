# Design Patterns Review Log

## Reviewer: [Rodrigo Castro]
## Author: [Gustavo Chevrand]

## 1. Builder
EditSessionBuilder is a well-implemented Builder pattern class. It demonstrates the Builder pattern by allowing step-by-step configuration of an EditSession object through chained setter methods, with the final build() method handling object creation. This pattern enables flexible and readable construction of complex objects.

---

## Reviewer: [Rodrigo Castro]
## Author: [Lucas Tobias]

## 2. Factory Method
The factory method in PluginPermissionsResolver is a true Factory Method because it encapsulates the decision-making logic required to create and return a PermissionsResolver instance based on specific conditions.

---

## Reviewer: [Rodrigo Castro]
## Author: [Rildo Franco]

## 3. Observer
The design aligns with the Observer Pattern as it uses an event-driven model where PlatformManager subscribes to events (i.e., listens for updates) through EventBus. It reacts to these events by taking specific actions, such as updating platform preferences and notifying other components.
