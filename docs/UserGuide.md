# InventoryBRO v2.0 – User Guide

**InventoryBRO** is a desktop application designed specifically for small shop owners (e.g., BRO) to manage inventory and daily transactions efficiently using a Command Line Interface (CLI).

If you prefer typing commands instead of clicking buttons, InventoryBRO allows you to manage your stock quickly, reliably, and precisely.

* [Quick Start](#quick-start)
* [Notes ABout the Command Format](#notes-about-command-format)
* [Smart Features](#smart-features)
* [Feature List (v2.0)](#feature-list-v20)
   1. [Adding an Item: `addItem`](#1-adding-an-item-additem)
   2. [Deleting an Item: `deleteItem`](#2-deleting-an-item-deleteitem)
   3. [Editing an Item's Description: `editDescription`](#3-editing-an-items-description-editdescription)
   4. [Editing an Item's Price: `editPrice`](#4-editing-an-items-price-editprice)
   5. [Editing an Item's Quantity: `editQuantity`](#5-editing-an-items-quantity-editquantity)
   6. [Viewing All Items: `listItems`](#6-viewing-all-items-listitems)
   7. [Finding an Item: `findItem`](#7-finding-an-item-finditem)
   8. [Filtering Items: `filterItem`](#8-filtering-items-filteritem)
   9. [Recording a Transaction: `transact`](#9-recording-a-transaction-transact)
   10. [Viewing Transaction History: `showHistory`](#10-viewing-transaction-history-showhistory)
   11. [Getting Help: `help`](#11-getting-help-help)
   12. [Exiting the Program: `exit`](#12-exiting-the-program-exit)
* [Saving Data](#saving-data)
* [Command Autocompletion](#command-autocompletion)
* [Typo Detection](#typo-detection)
* [Command Summary](#command-summary)
* [Scope of v2.0](#scope-of-v20)
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
* Parameters prefixed with letters (e.g., `d/`, `q/`, `p/`) must be included exactly as shown.
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
Adds a new product with a name, quantity, and price into the inventory.

* **Format:** `addItem d/NAME q/INITIAL_QUANTITY p/PRICE`
* **Example:** `addItem d/Coke Can q/50 p/1.50`
* **Expected Output:**
  ```text
  Added: Coke Can (Quantity: 50, Price: $1.50)
  ```
* Quantity must be `0` or greater — negative values are rejected.
* Price must be at least `0.01` when rounded to 2 decimal places (e.g. `p/0.001` is rejected).
* Name cannot be empty or whitespace only.
* An item with the same name (case-insensitive) cannot be added twice.

### 2. Deleting an Item: `deleteItem`
Deletes an item permanently from the inventory.

* **Format:** `deleteItem INDEX`
* **Example:** `deleteItem 2`
* **Expected Output:**
  ```text
  Noted, BRO. I've removed this item:
  Grape (Quantity: 200, Price: $0.00)
  Now you have 3 items in the list.
  ```

### 3. Editing an Item's Description: `editDescription`
Updates the description of an existing item in the inventory.

* **Format:** `editDescription INDEX d/NEW_DESCRIPTION`
* **Example:** `editDescription 1 d/Sprite Bottle`
* **Expected Output:**
  ```text
  Item description updated: Sprite Bottle (Quantity: 50, Price: $0.00)
  ```

### 4. Editing an Item's Price: `editPrice`
Updates the price of an existing item in the inventory.

* **Format:** `editPrice INDEX p/NEW_PRICE`
* **Example:** `editPrice 1 p/2.50`
* **Expected Output:**
  ```text
  Item price updated: Coke Can (Quantity: 50, Price: $2.50)
  ```

### 5. Editing an Item's Quantity: `editQuantity`
Updates the quantity of an existing item in the inventory.

* **Format:** `editQuantity INDEX q/NEW_QUANTITY`
* **Example:** `editQuantity 1 q/100`
* **Expected Output:**
  ```text
  Item quantity updated: Coke Can (Quantity: 100, Price: $0.00)
  ```

### 6. Viewing All Items: `listItems`
Displays a chronologically ordered numbered list of all items currently in your inventory. Users can also indicate to view a sorted list of items by an item property and order of their choice. The properties that the items can be sorted by are `quantity` and `price` and the order which they can be sorted in are `high` and `low` to show the item with the highest and lowest of that property first respectively. 

For example, to view the list sorted based on the descending order of item quantity, such that the item with the highest quantity is displayed first, users can enter `listItems quantity high`.

* **Format:** `listItems` or `listItems [PROPERTY] [ORDER]`
* **Example 1 (chronological order of items added):** `listItems`
  ```text
  Here are your current inventory items:
  1. Coke Can (Quantity: 50, Price: $2.00)
  2. Potato Chips (Quantity: 20, Price: $3.00)
  3. Sprite Bottle (Quantity: 30, Price: $1.00)
  ```
* **Example 2 (sorted by the property `quantity` and order `high`):** `listItems quantity high`
  ```text
  Here are your current inventory items based on quantity in decreasing order:
  1. Coke Can (Quantity: 50, Price: $2.00)
  2. Sprite Bottle (Quantity: 30, Price: $1.00)
  3. Potato Chips (Quantity: 20, Price: $3.00)
  ```
* **Example 3 (sorted by the property: `price` and order: `low`):** `listItems price low`
```text
  Here are your current inventory items based on price in increasing order:
  1. Sprite Bottle (Quantity: 30, Price: $1.00)
  2. Coke Can (Quantity: 50, Price: $2.00)
  3. Potato Chips (Quantity: 20, Price: $3.00)
  ```

### 7. Finding an Item: `findItem`
Searches for items whose descriptions contain your specified keyword. This is case-insensitive.

* **Format:** `findItem KEYWORD`
* **Example:** `findItem coke`
* **Expected Output:**
  ```text
  Here are the matching items in your inventory:
  1. Coke Can (Quantity: 50, Price: $1.50)
  ```

### 8. Filtering Items: `filterItem`
Displays only the items that match one or more field-based predicates. Predicates can be combined using `AND` (both must match) and `OR` (either must match). `AND` binds tighter than `OR`.

Supported fields and value types:

| Field         | Operators   | Value format                                                    |
|:--------------|:------------|:----------------------------------------------------------------|
| `description` | `=` `<` `>` | Text enclosed in **single quotes** (e.g. `'Coke'`)              |
| `quantity`    | `=` `<` `>` | Non-negative integer (e.g. `10`)                                |
| `price`       | `=` `<` `>` | Non-negative number with at most 2 decimal places (e.g. `1.50`) |

* **Format:** `filterItem FIELD OPERATOR VALUE [AND|OR FIELD OPERATOR VALUE ...]`
* **Example 1 (single predicate):** `filterItem quantity > 10`
  ```text
  Here are the filtered items:
  1. Coke Can (Quantity: 50, Price: $0.00)
  2. Potato Chips (Quantity: 20, Price: $0.00)
  ```
* **Example 2 (AND — both conditions must hold):** `filterItem quantity > 10 AND quantity < 40`
  ```text
  Here are the filtered items:
  1. Potato Chips (Quantity: 20, Price: $0.00)
  ```
* **Example 3 (OR — either condition matches):** `filterItem description = 'Coke Can' OR description = 'Sprite Bottle'`
  ```text
  Here are the filtered items:
  1. Coke Can (Quantity: 50, Price: $0.00)
  2. Sprite Bottle (Quantity: 30, Price: $0.00)
  ```
* **Example 4 (price filter — integer):** `filterItem price < 5`
  ```text
  Here are the filtered items:
  1. Potato Chips (Quantity: 20, Price: $2.00)
  ```
* **Example 5 (price filter — decimal):** `filterItem price > 1.50`
  ```text
  Here are the filtered items:
  1. Coke Can (Quantity: 50, Price: $2.00)
  2. Potato Chips (Quantity: 20, Price: $3.50)
  ```
* **No match output:**
  ```text
  No items match the given filter.
  ```

> **Note:** Description values must always be wrapped in single quotes. Quantity values must be whole numbers — decimals are not accepted. Price values accept up to 2 decimal places (e.g. `1.50`); values with more than 2 decimal places (e.g. `1.999`) are rejected. Comparison is done on the price rounded to 2 decimal places.

### 9. Recording a Transaction: `transact`
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

### 10. Viewing Transaction History: `showHistory`
Displays a complete, numbered list of all past transactions (sales and restocks) recorded by the application.

* **Format:** `showHistory`
* **Example Output (With History):**
  ```text
  Transaction History:
  1. Sprite Bottle | 10 | 2026-04-01 11:22
  2. Coke Can | -5 | 2026-04-01 11:22
  3. Coke Can | -50 | 2026-04-01 11:22
  ```
* **Example Output (Empty History):**
  ```text
  No transaction history found.
  ```

### 11. Getting Help: `help`
Displays a quick-reference list of all available commands, or provides detailed instructions and examples for a specific command.

* **Format 1 (General Summary):** `help`
    * **Example:** `help`
    * **Expected Output:** Displays a list of all commands (`addItem`, `deleteItem`, `editDescription`, etc.) along with a short summary of what each one does.

* **Format 2 (Detailed Instruction):** `help COMMAND_NAME`
    * **Example:** `help addItem`
    * **Expected Output:**
      ```text
      addItem:
      Adds a new item with a given name, quantity, and price to the current inventory list.
      - Name (d/): cannot be empty.
      - Quantity (q/): must be 0 or greater (negative values are not allowed).
      - Price (p/): must be at least 0.01 when rounded to 2 decimal places (e.g. 0.001 is rejected).

      Format: addItem d/NAME q/INITIAL_QUANTITY p/PRICE

      Example usage: addItem d/Apples q/10 p/1.50
      This adds an item named 'Apples' with quantity '10' and price '$1.50' to the inventory list.
      ```

### 12. Exiting the Program: `exit`
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

There is **no need to manually save**. Data is stored seamlessly in `./data/inventory.txt` and `./data/transactions.txt`. If the folder or file does not exist, InventoryBRO will automatically create it for you upon startup.

---

## Command Autocompletion

InventoryBRO includes a built-in tab-completion engine so you never have to remember or fully type a command keyword.

**How it works:**
* Type the first few letters of any command and press the `Tab` key.
* If only one command matches your prefix, it is completed immediately.
* If multiple commands match, a list of candidates is shown for you to choose from.

**Examples:**

| You type | You press | Result                         |
|:---------|:----------|:-------------------------------|
| `add`    | `Tab`     | Completes to `addItem`         |
| `del`    | `Tab`     | Completes to `deleteItem`      |
| `li`     | `Tab`     | Completes to `listItems`       |
| `f`      | `Tab`     | Shows `filterItem`, `findItem` |

**Things to know:**
* Autocompletion only works on the **command keyword** (the first word). It does not attempt to complete arguments like item names or indices.
* Autocompletion is only available when running the application directly from the JAR file (`java -jar inventorybro.jar`). It is **not** available when running via `./gradlew run` because Gradle pipes stdin, which disables raw terminal mode.
* Matching is **case-insensitive** — typing `ADD` and pressing `Tab` will still complete to `addItem`.

---

## Typo Detection

If you accidentally misspell a command, InventoryBRO will attempt to detect the mistake and suggest the closest valid command rather than silently failing.

**How it works:**
* When you enter an unrecognised command, InventoryBRO compares it to all known commands using a keyboard-aware edit-distance algorithm. Keys that are physically adjacent on a QWERTY keyboard are treated as more similar than keys that are far apart, so common slip-of-the-finger mistakes are caught more reliably.
* If a close enough match is found, you are prompted with a suggestion. Otherwise, the full list of valid commands is shown.

**Examples:**

| You type            | InventoryBRO responds     |
|:--------------------|:--------------------------|
| `adItem d/Coke q/5` | `Do you mean addItem?`    |
| `deletItem 2`       | `Do you mean deleteItem?` |
| `lsitItems`         | `Do you mean listItems?`  |
| `eixt`              | `Do you mean exit?`       |

**Things to know:**
* The suggestion is a hint only — you still need to re-enter the corrected command yourself.
* Very short or highly scrambled inputs may not produce a suggestion if no known command is close enough.
* Typo detection runs automatically on every unrecognised input. There is nothing to configure.

---

## Command Summary

| Action                 | Format                                                                         | Example                                   |
|:-----------------------|:-------------------------------------------------------------------------------|:------------------------------------------|
| **Add item**           | `addItem d/NAME q/QUANTITY p/PRICE`                                            | `addItem d/Coke q/50 p/1.50`              |
| **Delete item**        | `deleteItem INDEX`                                                             | `deleteItem 2`                            |
| **Edit description**   | `editDescription INDEX d/NEW_DESCRIPTION`                                      | `editDescription 1 d/Coke Can`            |
| **Edit price**         | `editPrice INDEX p/NEW_PRICE`                                                  | `editPrice 1 p/2.50`                      |
| **Edit quantity**      | `editQuantity INDEX q/NEW_QUANTITY`                                            | `editQuantity 1 q/100`                    |
| **List items**         | `listItems` - chronological order <br/>`listItems [property] [order]` - sorted | `listItems`<br/>`listItems quantity high` |
| **Find item**          | `findItem KEYWORD`                                                             | `findItem apple`                          |
| **Filter items**       | `filterItem FIELD OP VALUE [AND\|OR ...]`                                      | `filterItem quantity > 10`                |
| **Record transaction** | `transact INDEX q/CHANGE`                                                      | `transact 1 q/-3`                         |
| **Get Help**           | `help`                                                                         | `help`                                    |
| **Exit**               | `exit`                                                                         | `exit`                                    |

---

## Scope of v2.0
InventoryBRO v2.0 officially supports:
* Basic inventory tracking and quantity updates
* Editing description, quantity, or prices of inventory items
* Viewing current stock & finding specific items
* Typo suggestions & Command Tab-autocompletion
* Automatic background saving

**Planned for Future Versions:**
* Viewing earnings
* Low-stock automated alerts
