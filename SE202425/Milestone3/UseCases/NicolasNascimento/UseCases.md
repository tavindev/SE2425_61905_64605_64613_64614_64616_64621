# User Interface

## Use Cases 
[Nicolas Nascimento 61905]

### Name: Access All Commands
- **Id**: access_all_commands
- **Description**: Players can navigate to a full command list interface from the GUI.
- **Actors**: Player (Main actor)
- **Preconditions**: None
- **Postconditions**: The “All Commands” screen is displayed.
- **Main Flow**:
    1. The player uses the 'H’ key to open the screen.
    2. The player scrolls to see more commands or uses the search box to filter a specific command.
    3. The player can see and select the recent commands used.
- **Alternative Flows**: None

### Name: Quick Menu
- **Id**: access_quick_menu
- **Description**: Shows a screen where the player can see recent actions and bind shortcuts.
- **Actors**: Player (Main actor)
- **Preconditions**: None
- **Postconditions**: The “Quick Menu” screen is displayed.
- **Main Flow**:
    1. The player can quickly redo some actions by the recents tab or by the shortcut tab.
    2. To bind a shortcut, the player can select it in the menu and follow the next screen flow.
- **Alternative Flows**: None

### Name: Bind Shortcut
- **Id**: access_shortcuts_screen
- **Description**: Shows a screen where the player can see the current shortcuts and bind new ones.
- **Actors**: Player (Main actor)
- **Preconditions**: None
- **Postconditions**: The “Bind Shortcut” screen is displayed.
- **Main Flow**:
    1. The player selects a slot to store the command.
- **Alternative Flows**: None

### Name: View Recent Commands
- **Id**: access_recents_screen
- **Description**: Shows the last commands that the user did (maximum of four depending on the screen).
- **Actors**: Player (Main actor)
- **Preconditions**: Player is in Quick Menu Screen or All Commands Screen.
- **Postconditions**: Runs the command selected.
- **Main Flow**:
    1. The player sees and can select their last commands.
    2. If in Quick Menu, the player can see four of the previous actions.
- **Alternative Flows**: None

### Name: Brush Screen
- **Id**: access_brush_screen
- **Description**: [Description needed]
- **Actors**: Player (Main actor)
- **Preconditions**: [Preconditions needed]
- **Postconditions**: [Postconditions needed]
- **Main Flow**: [Main flow needed]
- **Alternative Flows**: None

### Name: Rebrush Screen
- **Id**: access_rebrush_screen
- **Description**: [Description needed]
- **Actors**: Player (Main actor)
- **Preconditions**: [Preconditions needed]
- **Postconditions**: [Postconditions needed]
- **Main Flow**: [Main flow needed]
- **Alternative Flows**: None