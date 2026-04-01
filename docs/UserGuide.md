# InventoryBRO v2.0 – User Guide

**InventoryBRO** is a desktop application designed specifically for small shop owners (e.g., BRO) to manage inventory and daily transactions efficiently using a Command Line Interface (CLI).

If you prefer typing commands instead of clicking buttons, InventoryBRO allows you to manage your stock quickly, reliably, and precisely.

---

## Quick Start
1. Ensure you have **Java 17** or above installed on your computer.
2. Download the `inventorybro.jar` file.
3. Place the file in a folder of your choice.
4. Open a terminal and navigate (`cd`) into that folder.
5. Run the application using the following command:
   ```bash
   java -jar inventorybro.jar
   ```
6. Type your commands and press `Enter` to execute them!

---

## Notes About Command Format
* Words in `UPPER_CASE` represent parameters supplied by the user.
    * *Example:* `addItem d/NAME q/QUANTITY`
* Parameters prefixed with letters (e.g., `d/`, `q/`) must be included exactly as shown.
* Parameters can be provided in any order unless stated otherwise.
* Commands are **case-insensitive**. Typing `add` and `ADD` will both execute the same action.
* **INDEX** refers to the number shown in the displayed item list and must be a positive integer (1, 2, 3, ...).

---

## Smart Features
Before diving into the standard commands, here are a few built-in features to make your life easier:
* **Command Autocompletion:** You don't have to type the full command! Just type the first few letters (e.g., `add`) and press the `Tab` key to auto-complete it to `addItem`.
* **Typo Suggestions:** If you accidentally misspell a command (e.g., `deletItem 1`), InventoryBRO will automatically catch it and ask: *"Do you mean deleteItem?"*

---

## Feature List (v2.0)

### 1. Adding an Item: `addItem`
Adds a new product into the inventory.

* **Format:** `addItem d/NAME q/INITIAL_QUANTITY`
* **Example:** `addItem d/Coke Can q/50`
* **Expected Output:**
  ```text
  Item added:
  Name: Coke Can
  Quantity: 50
  Total items in inventory: 1
  ```

### 2. Deleting an Item: `deleteItem`
Deletes an item permanently from the inventory.

* **Format:** `deleteItem INDEX`
* **Example:** `deleteItem 2`
* **Expected Output:**
  ```text
  Item deleted:
  2. Sprite Bottle (Quantity: 30)
  Total items in inventory: 3
  ```

### 3. Editing an Item: `editItem`
Edits the name and/or quantity of an item based on its index. At least one of the optional fields must be provided. Existing values will be overwritten.

* **Format:** `editItem INDEX (n/NAME) (q/QUANTITY)`
* **Examples & Output:**
  ```text
  > editItem 1 n/new Coke Can
  Item edited:
  1. new Coke Can (Quantity: 50)

  > editItem 1 q/200
  Item edited:
  1. new Coke Can (Quantity: 200)

  > editItem 1 n/Coke Can q/50 
  Item edited:
  1. Coke Can (Quantity: 50)
  ```
  ### 4. Viewing All Items: `listItems`
Displays a numbered list of all items currently in your inventory.

* **Format:** `listItems`
* **Example Output:**
  ```text
  Here are your current inventory items:
  1. Coke Can (Quantity: 50)
  2. Sprite Bottle (Quantity: 30)
  3. Potato Chips (Quantity: 20)
  ```

### 5. Finding an Item: `findItem`
Searches for items whose descriptions contain your specified keyword. This is case-insensitive.

* **Format:** `findItem KEYWORD`
* **Example:** `findItem coke`
* **Expected Output:**
  ```text
  Here are the matching items in your inventory:
  1. Coke Can (Quantity: 50)
  ```

### 6. Recording a Transaction: `transact`
Updates the stock quantity after a sale or restock.
* Use a **negative number** for a sale.
* Use a **positive number** for a restock.

* **Format:** `transact INDEX q/CHANGE_IN_QUANTITY`
* **Example 1 (Sale):** `transact 1 q/-5`
  ```text
  Transaction recorded.
  Coke Can new quantity: 45
  ```
* **Example 2 (Restock):** `transact 2 q/10`
  ```text
  Transaction recorded.
  Sprite Bottle new quantity: 40
  ```

### 7. Getting Help: `help`
Displays a quick-reference list of all available commands, or provides detailed instructions and examples for a specific command.

* **Format 1 (General Summary):** `help`
    * **Example:** `help`
    * **Expected Output:** Displays a list of all commands (`addItem`, `deleteItem`, `editItem`, etc.) along with a short summary of what each one does.

* **Format 2 (Detailed Instruction):** `help COMMAND_NAME`
    * **Example:** `help addItem`
    * **Expected Output:**
      ```text
      addItem:
      Adds a new item of a given name and quantity to the current inventory list.
  
      Example usage: addItem d/Apples q/10
      This adds an item named 'Apples' of quantity '10' to the inventory list.
      ```

* **Format:** `help`

### 8. Exiting the Program: `exit`
Safely closes the application.

* **Format:** `exit`
* **Expected Output:**
  ```text
  Exiting InventoryBRO.
  Goodbye!
  ```

---

## Saving Data
InventoryBRO automatically saves your data to the hard disk whenever:
* An item is added
* An item is deleted
* A transaction is recorded

There is **no need to manually save**. Data is stored seamlessly in `./data/inventorybro.txt`. If the folder or file does not exist, InventoryBRO will automatically create it for you upon startup.



## Command Summary

| Action | Format | Example |
| :--- | :--- | :--- |
| **Add item** | `addItem d/NAME q/QUANTITY` | `addItem d/Coke q/50` |
| **Delete item** | `deleteItem INDEX` | `deleteItem 2` |
| **Edit item** | `editItem INDEX (n/NAME) (q/QUANTITY)` | `editItem 2 n/New Coke Name` |
| **List items** | `listItems` | `listItems` |
| **Find item** | `findItem KEYWORD` | `findItem apple` |
| **Record transaction** | `transact INDEX q/CHANGE` | `transact 1 q/-3` |
| **Get Help** | `help` | `help` |
| **Exit** | `exit` | `exit` |

---

## 🔮 Scope of v1.0
InventoryBRO v1.0 officially supports:
* Basic inventory tracking and quantity updates
* Viewing current stock & finding specific items
* Typo suggestions & Command Tab-autocompletion
* Automatic background saving

**Planned for Future Versions:**
* Add price tracking to items
* Low-stock automated alerts