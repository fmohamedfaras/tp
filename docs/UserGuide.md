# User Guide

## Introduction

InventoryBRO is a command-line inventory management app for small business owners who prefer typing over clicking. It helps you track your items, quantities, and prices quickly and efficiently.

## Quick Start

1. Ensure you have Java 17 or above installed.
2. Download the latest `InventoryBro.jar` from the releases page.
3. Open a terminal and navigate to the folder containing the jar file.
4. Run: `java -jar InventoryBro.jar`

## Features

### Add an item: `addItem`
Adds a new item to the inventory.

Format: `addItem d/NAME q/QUANTITY`

Example: `addItem d/Apple q/10`

---

### List all items: `listItems`
Shows all items currently in the inventory.
Items with a quantity of 5 or below will be flagged with `[LOW STOCK]`.

Format: `listItems`

---

### Edit an item: `editItem`
Updates an existing item's name, quantity, and price.

Format: `editItem INDEX d/NEW_NAME q/NEW_QUANTITY p/NEW_PRICE`

* `INDEX` is the item number shown in `listItems` (starts from 1).
* `NEW_PRICE` must be a non-negative number.

Example: `editItem 1 d/Green Apple q/20 p/1.50`

---

### Find an item: `findItem`
Finds all items whose name contains the given keyword. The search is case-insensitive.

Format: `findItem KEYWORD`

Example: `findItem apple`

---

### Record a transaction: `transact`
Adjusts an item's quantity by a positive or negative amount.

Format: `transact INDEX q/CHANGE`

* Use a negative number for sales, positive for restocks.

Example: `transact 1 q/-3`

---

### Delete an item: `deleteItem`
Removes an item from the inventory.

Format: `deleteItem INDEX`

Example: `deleteItem 1`

---

### Get help: `help`
Displays a summary of all commands, or detailed info on a specific command.

Format: `help` or `help COMMAND_NAME`

Example: `help addItem`

---

### Exit: `exit`
Exits the application.

Format: `exit`

---

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: Copy the save file generated in the same folder as the jar file to your new computer.

## Command Summary

| Command    | Format                                             |
|------------|----------------------------------------------------|
| Add item   | `addItem d/NAME q/QUANTITY`                        |
| List items | `listItems`                                        |
| Edit item  | `editItem INDEX d/NEW_NAME q/NEW_QUANTITY p/PRICE` |
| Find item  | `findItem KEYWORD`                                 |
| Transact   | `transact INDEX q/CHANGE`                          |
| Delete     | `deleteItem INDEX`                                 |
| Help       | `help` or `help COMMAND_NAME`                      |
| Exit