# Use Case Diagram Review Log

## Reviewer: [Rodrigo Castro]

## Use Case: Rotate Region
### Author: Gustavo Chevrand 64616

- The Player is the primary actor, as they initiate the command to rotate the selected region, while Minecraft acts as the secondary actor, executing the rotation of the region within the game world. 

- Evaluation: The use case correctly outlines the roles of both the Player and Minecraft, and the description accurately captures the conditions and expected results for this command.

- Status: The use case is correct.

---
### Use Case: Apply Heightmap
### Author: Pedro Amorim 64614

- The Player is the primary actor since they initiate the heightmap command, while the Server acts as the secondary actor, handling the processing required to apply the heightmap to the terrain. The pre-conditions are well-defined, ensuring that the player has the necessary permissions, the heightmap image is accessible, and the area size is within system limits. Post-conditions confirm the terrain is modified according to the image and that the player receives confirmation.

- Evaluation: This use case is well-documented, capturing the interaction accurately between Player and Server. All steps and conditions are appropriate.

- Status: The use case is correct.

### Use Case: Modify Biome
### Author: Pedro Amorim 64614

- The Player is the primary actor, as they issue the command to alter the biome, and the Server is the secondary actor, applying the changes to environmental factors in the specified region. Pre-conditions ensure permissions and compatibility with area and biome type, while post-conditions confirm that the biome has been updated and the player receives notification.

- Evaluation: The use case accurately reflects the actions taken by both the Player and Server, and the conditions effectively cover access control and compatibility checks.

- Status: The use case is correct.

### Use Case: Paste Clipboard Structure
### Author: Pedro Amorim 64614

- The Player is the primary actor, initiating the paste action, and the Server is the secondary actor, executing the structure paste in the game world. Pre-conditions ensure that the player has paste permissions, a structure is available in the clipboard, and the area size is within system constraints. Post-conditions confirm that the structure is successfully pasted, with confirmation provided to the player.

- Evaluation: This use case is clearly outlined, with pre- and post-conditions that provide necessary constraints and completion feedback to the player. The roles of the Player and Server are well-defined.

- Status: The use case is correct.

### Use Case: Manage Mobs
### Author: Pedro Amorim 64614

- The Player is the primary actor, initiating mob management, while the Server acts as the secondary actor, handling mob removal within the specified area. Pre-conditions verify permissions and specify that the area is valid. Post-conditions ensure that the targeted mobs are removed and that the player receives confirmation.

- Evaluation: This use case is properly defined, with clear pre- and post-conditions to ensure the action completes as intended. The Player and Server roles are accurately represented.

- Status: The use case is correct.

---

