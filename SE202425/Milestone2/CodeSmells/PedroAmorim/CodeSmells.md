# Code Smells Analysis

## Author: [Pedro Amorim]
## Date: [11/09/2024]

---

## 1. Large Class

Class PaperweightDataConverters in the file
`worldedit-bukkit/adapters/adapter-1.20.4/src/main/java/com/sk89q/worldedit/bukkit/adapter/impl/v1_20_R3/PaperweightDataConverters.java`, having more than 2802 lines

### Solution:

There are a bunch of static functions and private classes on this class. I would delegate most of its features to another classes and completely disolve it, I belive refactory it is the best solution because of its complexity.

## 2. Message Chain

Line 188 of the file
`worldedit-sponge/src/main/java/com/sk89q/worldedit/sponge/SpongePlayer`.java

```java
public String[] getGroups() {
    return SpongeWorldEdit.inst().getPermissionsProvider().getGroups(this.player);
}
```

## Solution

Extract the method would be a better way to solve it.

```java
    //In SpogeWorldEdit
    public getGroups(Player player) {
        this.getPermissionsProvider().getGroups();
    }
```


## 3. Dead Code

Class AbstractPattern in the file `worldedit-core/src/main/java/com/sk89q/worldedit/function/pattern/AbstractPattern.java`, empty class, which does not add anything to the feature.

## Solution

Delete the class. It's never used and is not even implemented. So there's no reason for this class to exist.
