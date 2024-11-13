# Use Case Diagram Report

# ID: 64616_UC1

Name: Copy region

Description: Duplicate the selected region blocks to an in-memory clipboard. Additionally, it records the player’s position at the time of copying.

Primary Actor: Player

Secondary Actor: None

# ID: 64616_UC2

Name: Cut region

Description: Remove the region blocks and store them in an imaginary clipboard. Additionally, it records the player’s position at the point of block removal.

Primary Actor: Player

Secondary Actor: Server

# ID: 64616_UC3

Name: Paste region

Description: Pastes the region from the clipboard based on the player’s position, where it was cut or copied previously.

Primary Actor: Player

Secondary Actor: Server

# ID: 64616_UC4

Name: Rotate region

Description: Rotates the imaginary clipboard based on the player’s position when the clipboard is cut or copied.

Primary Actor: Player

Secondary Actor: None

# ID: 64616_UC5

Name: Flip region

Description: Flips the clipboard, adapting its orientation based on the player’s facing direction and positioning.

Primary Actor: Player

Secondary Actor: None
