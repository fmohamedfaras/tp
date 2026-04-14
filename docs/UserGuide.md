# InventoryBRO v2.1 – User Guide

**InventoryBRO** is a desktop application designed specifically for small shop owners (e.g., BRO) to manage inventory and daily transactions efficiently using a Command Line Interface (CLI).

If you prefer typing commands instead of clicking buttons, InventoryBRO allows you to manage your stock quickly, reliably, and precisely.

* [Quick Start](#quick-start)
* [Notes About the Command Format](#notes-about-command-format)
* [Smart Features](#smart-features)
* [Feature List (v2.1)](#feature-list-v21)
    1. [Adding an Item: `addItem`](#1-adding-an-item-additem)
    2. [Deleting an Item: `deleteItem`](#2-deleting-an-item-deleteitem)
    3. [Editing an Item's Description: `editDescription`](#3-editing-an-items-description-editdescription)
    4. [Editing an Item's Price: `editPrice`](#4-editing-an-items-price-editprice)
    5. [Editing an Item's Quantity: `editQuantity`](#5-editing-an-items-quantity-editquantity)
    6. [Editing an Item's Category: `editCategory`](#6-editing-an-items-category-editcategory)
    7. [Adding a Category: `addCategory`](#7-adding-a-category-addcategory)
    8. [Deleting a Category: `deleteCategory`](#8-deleting-a-category-deletecategory)
    9. [Listing all Categories: `listCategories`](#9-listing-all-categories-listcategories)
    10. [Viewing All Items: `listItems`](#10-viewing-all-items-listitems)
    11. [Finding an Item: `findItem`](#11-finding-an-item-finditem)
    12. [Filtering Items: `filterItem`](#12-filtering-items-filteritem)
    13. [Recording a Transaction: `transact`](#13-recording-a-transaction-transact)
    14. [Viewing Transaction History: `showHistory`](#14-viewing-transaction-history-showhistory)
    15. [Getting Help: `help`](#15-getting-help-help)
    16. [Exiting the Program: `exit`](#16-exiting-the-program-exit)
* [Saving Data](#saving-data)
* [Command Autocompletion](#command-autocompletion)
* [Typo Detection](#typo-detection)
* [Command Summary](#command-summary)
* [Scope of v2.1](#scope-of-v21)

---

## Quick Start (Without preloaded data)
1. Ensure you have **Java 17** or above installed on your computer.
2. Download the `inventory-bro.jar` file.
3. Place the file in a folder of your choice.
4. Open a terminal and navigate (`cd`) into that folder.
5. Run the application using the following command:
   ```bash
   java -jar inventory-bro.jar
   ```
6. Type your commands and press `Enter` to execute them!

---

## Quick Start (With preloaded data)
1. Ensure you have **Java 17** or above installed on your computer.
2. Download the `inventory-bro.jar` file and the `data` folder.
3. Place the file and data folder in a folder of your choice.
4. Open a terminal and navigate (`cd`) into that folder.
5. Run the application using the following command:
   ```bash
   java -jar inventory-bro.jar
   ```
6. Type your commands and press `Enter` to execute them!

---

## Notes About Command Format
* Words in `UPPER_CASE` represent parameters supplied by the user.
    * *Example:* `addItem d/NAME q/QUANTITY`
* Parameters prefixed with letters (e.g., `d/`, `q/`, `p/`, `c/`) must be included exactly as shown.
* Commands are **case-sensitive**.
* **INDEX** refers to the number shown in the displayed item list and must be a positive integer (1, 2, 3, ...).

---

## Smart Features
Before diving into the standard commands, here are a few built-in features to make your life easier:
* **Command Autocompletion:** You don't have to type the full command! Just type the first few letters (e.g., `add`) and press the `Tab` key to auto-complete it to `addItem`.
* **Typo Suggestions:** If you accidentally misspell a command (e.g., `deletItem 1`), InventoryBRO will automatically catch it and ask: *"Do you mean deleteItem?"*

---

## Feature List (v2.1)

### 1. Adding an Item: `addItem`
Adds a new product with a name, quantity, price, and an optional category into the inventory. If no category is specified, it defaults to `[OTHERS]`.

* **Format:** `addItem d/NAME q/INITIAL_QUANTITY p/PRICE c/CATEGORY_NAME`
* **Example:** `addItem d/Sprite Bottle q/30 p/1.50 c/Beverages`
* **Expected Output:**
  ```text
  Added: [BEVERAGES] Sprite Bottle (Quantity: 30, Price: $1.50)
  ```
* Quantity must be `0` or greater — negative values are rejected.
* Price must be at least `0.01` when rounded to 2 decimal places (e.g. `p/0.001` is rejected).
* Name cannot be empty or whitespace only.
* Name cannot contain single quotes (`'`).
* An item with the same name (case-insensitive) cannot be added twice.
* If a category is specified, that category **must already exist** (see `addCategory`).

### 2. Deleting an Item: `deleteItem`
Deletes an item permanently from the inventory.

* **Format:** `deleteItem INDEX`
* **Example:** `deleteItem 1`
* **Expected Output:**
  ```text
    Deleted: [BEVERAGES] Sprite Bottle (Quantity: 30, Price: $1.50)
  ```

### 3. Editing an Item's Description: `editDescription`
Updates the description of an existing item in the inventory.

* **Format:** `editDescription INDEX d/NEW_DESCRIPTION`
* **Example:** `editDescription 1 d/Sprite Bottle`
* **Expected Output:**
  ```text
  Item description updated: [BEVERAGES] Sprite Bottle (Quantity: 50, Price: $1.50)
  ```
* Description cannot contain single quotes (`'`).
  ### 4. Editing an Item's Price: `editPrice`
Updates the price of an existing item in the inventory.

* **Format:** `editPrice INDEX p/NEW_PRICE`
* **Example:** `editPrice 1 p/2.50`
* **Expected Output:**
  ```text
  Item price updated: [BEVERAGES] Coke Can (Quantity: 50, Price: $2.50)
  ```
* Price can be given with any number of decimal places, but it is rounded to 2 decimal places internally. The rounded value must be at least `0.01` (e.g. `p/0.001` is rejected, but `p/0.005` is accepted as it rounds to `0.01`).

### 5. Editing an Item's Quantity: `editQuantity`
Updates the quantity of an existing item in the inventory.

* **Format:** `editQuantity INDEX q/NEW_QUANTITY`
* **Example:** `editQuantity 1 q/100`
* **Expected Output:**
  ```text
  Item quantity updated: [BEVERAGES] Coke Can (Quantity: 100, Price: $2.50)
  ```

### 6. Editing an Item's Category: `editCategory`
Moves an existing item into a different category. The target category must already exist.

* **Format:** `editCategory INDEX c/NEW_CATEGORY`
* **Example:** `editCategory 1 c/Food`
* **Expected Output:**
  ```text
  Item category updated: [FOOD] Coke Can (Quantity: 100, Price: $2.50)
  ```

### 7. Adding a Category: `addCategory`
Creates a new custom category that items can be assigned to.

* **Format:** `addCategory c/CATEGORY_NAME`
* **Example:** `addCategory c/Drinks`
* **Expected Output:**
  ```text
  Successfully added new category: [DRINKS]
  ```
* Category names are case-insensitive and cannot be duplicates.

### 8. Deleting a Category: `deleteCategory`
Deletes an existing custom category. Any items currently assigned to this category will be safely moved back into the default `[OTHERS]` category.

* **Format:** `deleteCategory c/CATEGORY_NAME`
* **Example:** `deleteCategory c/Drinks`
* **Expected Output:**
  ```text
  Successfully deleted the [DRINKS] category.
  Moved 2 orphaned item(s) back into the [OTHERS] category.
  ```
* Note: The default system category `[OTHERS]` cannot be deleted.

### 9. Listing all Categories: `listCategories`
Displays a numbered list of all currently available categories in the system.

* **Format:** `listCategories`
* **Example:** `listCategories`
* **Expected Output:**
  ```text
  Here are your current categories:
  1. [OTHERS]
  2. [FOOD]
  3. [BEVERAGES]
  ```

### 10. Viewing All Items: `listItems`
Displays a chronologically ordered numbered list of all items currently in your inventory. You can optionally filter this list to only show items from a specific category, and you can sort the resulting list by `quantity` or `price` in `high` or `low` order.

* **Format:** `listItems [c/CATEGORY_NAME] [PROPERTY] [ORDER]`

* **Example 1 (View all items):** `listItems`
  ```text
  Here are your current inventory items:
  1. [BEVERAGES] Coke Can (Quantity: 50, Price: $2.00)
  2. [FOOD] Potato Chips (Quantity: 20, Price: $3.00)
  3. [BEVERAGES] Sprite Bottle (Quantity: 30, Price: $1.00)
  ```
* **Example 2 (View only items in a specific category):** `listItems c/Beverages`
  ```text
  Here are your current inventory items in [BEVERAGES]:
  1. [BEVERAGES] Coke Can (Quantity: 50, Price: $2.00)
  2. [BEVERAGES] Sprite Bottle (Quantity: 30, Price: $1.00)
  ```
* **Example 3 (Filter by category AND sort by quantity):** `listItems c/Beverages quantity low`
  ```text
  Here are your current inventory items in [BEVERAGES] by quantity in increasing order:
  1. [BEVERAGES] Sprite Bottle (Quantity: 30, Price: $1.00)
  2. [BEVERAGES] Coke Can (Quantity: 50, Price: $2.00)
  ```
  ### 11. Finding an Item: `findItem`
Searches for items whose descriptions contain your specified keyword. This is case-insensitive.

* **Format:** `findItem KEYWORD`
* **Example:** `findItem coke`
* **Expected Output:**
  ```text
  Here are the matching items in your inventory:
  1. [BEVERAGES] Coke Can (Quantity: 50, Price: $1.50)
  ```

### 12. Filtering Items: `filterItem`
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
  1. [BEVERAGES] Coke Can (Quantity: 50, Price: $1.50)
  2. [FOOD] Potato Chips (Quantity: 20, Price: $3.00)
  ```
* **Example 2 (AND — both conditions must hold):** `filterItem quantity > 10 AND quantity < 40`
  ```text
  Here are the filtered items:
  1. [FOOD] Potato Chips (Quantity: 20, Price: $3.00)
  ```
* **Example 3 (OR — either condition matches):** `filterItem description = 'Coke Can' OR description = 'Sprite Bottle'`
  ```text
  Here are the filtered items:
  1. [BEVERAGES] Coke Can (Quantity: 50, Price: $1.50)
  2. [BEVERAGES] Sprite Bottle (Quantity: 30, Price: $1.00)
  ```
* **Example 4 (price filter — integer):** `filterItem price < 5`
  ```text
  Here are the filtered items:
  1. [FOOD] Potato Chips (Quantity: 20, Price: $3.00)
  ```
* **Example 5 (price filter — decimal):** `filterItem price > 1.50`
  ```text
  Here are the filtered items:
  1. [BEVERAGES] Coke Can (Quantity: 50, Price: $2.00)
  2. [FOOD] Potato Chips (Quantity: 20, Price: $3.50)
  ```
* **No match output:**
  ```text
  No items match the given filter.
  ```

> **Note:** Description values must always be wrapped in single quotes. Quantity values must be whole numbers — decimals are not accepted. Price values accept up to 2 decimal places (e.g. `1.50`); values with more than 2 decimal places (e.g. `1.999`) are rejected. Comparison is done on the price rounded to 2 decimal places.
>
> **Description comparisons (`<` and `>`) are lexicographic (alphabetical order).** For example, `description < 'Milo'` matches any item whose description comes before `'Milo'` alphabetically — so `'Coke Can'` would match but `'Sprite Bottle'` would not. This is the same ordering used in a dictionary: comparison proceeds character by character from left to right, using each character's Unicode value. Uppercase letters come before lowercase letters (e.g. `'Apple'` < `'apple'`).

### 13. Recording a Transaction: `transact`
Updates the stock quantity after a sale or restock.
* Use a **negative number** for a sale.
* Use a **positive number** for a restock.
* **Note:** Transactions with a quantity of `0` are invalid and will be rejected.

* **Format:** `transact INDEX q/CHANGE_IN_QUANTITY`
* **Example 1 (Sale):** `transact 1 q/-5`
  ```text
  Sale successful! The quantity of 'Coke Can' is now 45.
  ```
* **Example 2 (Restock):** `transact 1 q/10`
  ```text
  Restock successful! The quantity of 'Coke Can' is now 55.
  ```

### 14. Viewing Transaction History: `showHistory`
Displays a complete, numbered list of all past transactions (sales and restocks) recorded by the application in a detailed point-form layout.

* **Format:** `showHistory`
* **Example Output (With History):**
  ```text
  Here is your transaction history:
  ========================================
  Transaction 1
    Date & Time : 2026-04-14 14:30
    Type        : RESTOCK
    Qty         : 10
    Description : Sprite Bottle
  ----------------------------------------
  Transaction 2
    Date & Time : 2026-04-14 15:45
    Type        : SALE
    Qty         : 5
    Description : Coke Can
  ========================================
  ```
* **Example Output (Empty History):**
  ```text
  Transaction history is currently empty.
  ```

### 15. Getting Help: `help`
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
      ... (Displays the detailed format and examples for the specified command)
      ```

### 16. Exiting the Program: `exit`
Safely closes the application.

* **Format:** `exit`
* **Expected Output:**
  ```text
  Bye! See you next time.
  ```
  ---

## Saving Data
InventoryBRO automatically saves your data to the hard disk whenever:
* An item is added
* An item is deleted
* A transaction is recorded
* A category is created or deleted
* Item details (description, price, quantity, category) are edited

There is **no need to manually save**. Data is stored seamlessly in `./data/inventory.txt`, `./data/transactions.txt`, and `./data/categories.txt`. If the folder or files do not exist, InventoryBRO will automatically create them for you upon startup.

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
| `add`    | `Tab`     | Shows `addItem`, `addCategory` |
| `del`    | `Tab`     | Shows `deleteItem`, `deleteCategory` |
| `li`     | `Tab`     | Shows `listItems`, `listCategories` |
| `f`      | `Tab`     | Shows `filterItem`, `findItem` |

**Things to know:**
* Autocompletion only works on the **command keyword** (the first word). It does not attempt to complete arguments like item names or indices.
* Autocompletion is only available when running the application directly from the JAR file (`java -jar inventory-bro.jar`). It is **not** available when running via `./gradlew run` because Gradle pipes stdin, which disables raw terminal mode.
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

**Things to know:**
* The suggestion is a hint only — you still need to re-enter the corrected command yourself.
* Very short or highly scrambled inputs may not produce a suggestion if no known command is close enough.
* Typo detection runs automatically on every unrecognised input. There is nothing to configure.

---

## Command Summary

| Action                 | Format                                                                         | Example                                       |
|:-----------------------|:-------------------------------------------------------------------------------|:----------------------------------------------|
| **Add item** | `addItem d/NAME q/QUANTITY p/PRICE [c/CATEGORY]`                               | `addItem d/Coke q/50 p/1.50 c/Drinks`         |
| **Delete item** | `deleteItem INDEX`                                                             | `deleteItem 2`                                |
| **Edit description** | `editDescription INDEX d/NEW_DESCRIPTION`                                      | `editDescription 1 d/Coke Can`                |
| **Edit price** | `editPrice INDEX p/NEW_PRICE`                                                  | `editPrice 1 p/2.50`                          |
| **Edit quantity** | `editQuantity INDEX q/NEW_QUANTITY`                                            | `editQuantity 1 q/100`                        |
| **Edit category** | `editCategory INDEX c/NEW_CATEGORY`                                            | `editCategory 1 c/Food`                       |
| **Add category** | `addCategory c/CATEGORY_NAME`                                                  | `addCategory c/Food`                          |
| **Delete category** | `deleteCategory c/CATEGORY_NAME`                                               | `deleteCategory c/Food`                       |
| **List categories** | `listCategories`                                                               | `listCategories`                              |
| **List items** | `listItems [c/CATEGORY_NAME] [PROPERTY] [ORDER]`                               | `listItems c/Food quantity high`              |
| **Find item** | `findItem KEYWORD`                                                             | `findItem apple`                              |
| **Filter items** | `filterItem FIELD OP VALUE [AND\|OR ...]`                                      | `filterItem quantity > 10`                    |
| **Record transaction** | `transact INDEX q/CHANGE`                                                      | `transact 1 q/-3`                             |
| **Get Help** | `help` or `help COMMAND`                                                       | `help addItem`                                |
| **Exit** | `exit`                                                                         | `exit`                                        |

---

## Scope of v2.1
InventoryBRO v2.1 officially supports:
* Basic inventory tracking and quantity updates
* Editing description, quantity, or prices of inventory items
* Viewing current stock & finding specific items
* Typo suggestions & Command Tab-autocompletion
* Automatic background saving

**Planned for Future Versions:**
* Viewing earnings
* Low-stock automated alerts