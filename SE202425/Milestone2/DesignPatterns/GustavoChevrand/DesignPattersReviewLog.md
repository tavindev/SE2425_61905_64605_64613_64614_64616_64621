# Design Patterns Review Log

## 1. Template method
Author: Rodrigo Castro 64621

Although the `AbstractDirectionConverter` class is relatively simple, the template pattern is effectively applied. This is because there is no specification of how the `convertDirection` method is implemented, but rather how it is used in its template method `convert`. The responsibility of providing the implementation of this method lies with the subclasses, and not the parent class. Hence why the abstract keyword is specified when `AbstractDirectionConverter` defines `convertDirection`.

----

## 2. State
Author: Nicolas Nascimento 61905

----

## 3. Proxy
Author: Rildo Franco 64605

The `Proxy` pattern is explicitly defined and utilized in the `FabricServerLevelDelegateProxy` class, which acts as a proxy for accessing an underlying ServerLevel instance. The essence of this pattern lies in the `invoke` method, where method calls on the proxy are intercepted. This enables custom handling for specific methods (e.g., `getBlockState`, `setBlock`) or delegating unhandled calls to the actual `ServerLevel` object.
