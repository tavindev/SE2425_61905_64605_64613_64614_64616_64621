# Scenario Based Tests

## 1. Activating All Commands Screen

- **Pre-cond**: None
- **Test Case ID**: all_commands
- **Steps**:
    1. Press the ‘H’ key to open the WorldEdit GUI.
    2. Scroll through the command list or use the search bar to find a specific command.
    3. Optionally, view and select a recent command from the list.
- **Expected Results**:
    - The “All Commands” screen opens.
    - Commands are listed and can be scrolled or filtered.
    - Recent commands are displayed and executable.

---

## 2. Activating Quick Menu Screen

- **Pre-cond**: None
- **Test Case ID**: quick_menu
- **Steps**:
    1. Press the ‘H’ key to open the WorldEdit GUI.
    2. Select the “Quick Menu” option.
    3. View the “Recents” tab to see previous actions.
    4. Use the “Shortcuts” tab to bind or execute commands.
- **Expected Results**:
    - The “Quick Menu” screen is displayed.
    - Recent actions are listed for quick access.
    - Commands can be bound or executed from the “Shortcuts” tab.
    - Can access All Commands screen via a tab.

---

## 3. Binding a Shortcut

- **Pre-cond**: None
- **Test Case ID**: shortcuts_screen
- **Steps**:
    1. Open the Quick Menu Screen using the 'G’ key.
    2. Select the 'Bind Shortcut’ tab.
    3. Select the slot to store the command.
    4. Select the command to store in the slot.
    5. Confirm changes.
- **Expected Results**:
    - The “Bind Shortcut” screen is displayed.
    - The All Commands selection screen is displayed when binding.
    - A new command is successfully linked to the shortcut slot.

---

## 4. Using Brush Command via Interface

- **Pre-cond**: None
- **Test Case ID**: [Test case ID needed]
- **Steps**: [Steps needed]
- **Expected Results**: [Expected results needed]

---

## 5. Using Rebrush Command via Interface

- **Pre-cond**: None
- **Test Case ID**: [Test case ID needed]
- **Steps**: [Steps needed]
- **Expected Results**: [Expected results needed]

---

## 6. Learning What a Command Does

- **Pre-cond**: Opened All Commands Screen
- **Test Case ID**: [Test case ID needed]
- **Steps**:
    1. Open All Commands Screen.
    2. Select the desired command.
    3. Hover the mouse over the command to display a short description.
- **Expected Results**:
    - Shows a brief description of the command when the mouse is hovered.