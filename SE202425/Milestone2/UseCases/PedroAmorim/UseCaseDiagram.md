### ID: 64614_UC1

- **Name**: Select Shape Brush
- **Description**: Allows the player to select different shape brushes, such as a sphere or cylinder, and apply them to the terrain.
- **Primary Actor**: Player
- **Secondary Actor**: None
- **Pre-conditions**:
    - The player has permissions to use shape brushes.
    - The player holds an item that can be bound with a brush effect.
- **Post-conditions**:
    - The selected shape brush is applied to the player’s item.
    - A confirmation message is sent to the player.

---

### ID: 64614_UC2

- **Name**: Apply Heightmap
- **Description**: Applies a heightmap based on an image to the terrain, using pixel brightness to determine elevation.
- **Primary Actor**: Player
- **Secondary Actor**: Server
- **Pre-conditions**:
    - The player has permissions to use the heightmap tool.
    - The specified heightmap image is accessible and compatible.
    - The area selected does not exceed the size limits set by the system.
- **Post-conditions**:
    - The terrain is modified according to the heightmap image.
    - The player receives confirmation that the heightmap was successfully applied.

---

### ID: 64614_UC3

- **Name**: Modify Biome
- **Description**: Alters the biome of a specific region, modifying environmental factors like vegetation and climate.
- **Primary Actor**: Player
- **Secondary Actor**: Server
- **Pre-conditions**:
    - The player has permissions to change biomes.
    - The selected area and biome type are compatible.
- **Post-conditions**:
    - The region’s biome is updated with the new environmental settings.
    - The player is notified of the successful biome modification.

---

### ID: 64614_UC4

- **Name**: Paste Clipboard Structure
- **Description**: Pastes a previously copied or cut structure from the clipboard at the player’s current position.
- **Primary Actor**: Player
- **Secondary Actor**: Server
- **Pre-conditions**:
    - The player has permissions to paste structures.
    - A structure is stored in the clipboard.
    - The paste area does not exceed system-defined size limits.
- **Post-conditions**:
    - The structure from the clipboard is pasted at the specified location.
    - The player receives a notification confirming the paste action.

---

### ID: 64614_UC5

- **Name**: Manage Mobs
- **Description**: Removes mobs within a specific area, with options to target specific types, such as animals, NPCs, or hostile mobs.
- **Primary Actor**: Player
- **Secondary Actor**: Server
- **Pre-conditions**:
    - The player has permissions to manage mobs.
    - The radius for mob removal is specified and valid.
- **Post-conditions**:
    - The specified types of mobs are removed within the area.
    - The player is informed of the successful mob removal.

---

### ID: 64614_UC6

- **Name**: Apply Terrain Morphology
- **Description**: Modifies terrain morphology by applying transformations like erosion or expansion to smooth or alter block shapes.
- **Primary Actor**: Player
- **Secondary Actor**: Server
- **Pre-conditions**:
    - The player has permissions to alter terrain morphology.
    - The selected area does not exceed maximum size limitations.
- **Post-conditions**:
    - Terrain is modified based on the specified morphology transformations.
    - A message is sent to the player confirming the terrain change.

---

### ID: 64614_UC7

- **Name**: Apply Environmental Features
- **Description**: Spreads natural features, such as grass or flowers, across a selected area.
- **Primary Actor**: Player
- **Secondary Actor**: Server
- **Pre-conditions**:
    - The player has permissions to apply environmental features.
    - The selected area for feature application is within acceptable limits.
- **Post-conditions**:
    - Environmental features are applied according to the specified density and area.
    - The player is notified of the successful application of features.

---

### ID: 64614_UC8

- **Name**: Simulate Gravity
- **Description**: Applies gravity to unsupported blocks within a specified radius, causing them to fall naturally.
- **Primary Actor**: Player
- **Secondary Actor**: Server
- **Pre-conditions**:
    - The player has permissions to apply gravity effects.
    - The area specified for gravity simulation is within size limits.
- **Post-conditions**:
    - Unsupported blocks within the area fall naturally due to the gravity simulation.
    - The player receives feedback confirming the gravity effect application.