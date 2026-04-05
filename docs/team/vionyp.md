# Viony - Project Portfolio Page

## Project: InventoryBRO

InventoryBRO is a desktop application for small shop owners to manage their inventory efficiently via a Command Line Interface (CLI). It supports adding, editing, deleting, and transacting items, with features like autocompletion and typo detection.

---

## Summary of Contributions

### Code Contributed
[View my code on the tP Code Dashboard](https://nus-cs2113-ay2526s2.github.io/tp-dashboard/?search=vionyp)

---

### Enhancements Implemented

#### 1. Edit Item Feature (`editItem`)
- **What it does:** Allows the user to update an existing item's name, quantity, and price in a single command using the format `editItem INDEX d/NAME q/QUANTITY p/PRICE`.
- **Justification:** Store owners frequently need to correct item details or update prices. This command makes it efficient to update all fields at once.
- **Highlights:**
  - Implemented `EditCommand.java` which delegates all validation to `EditCommandValidator` before parsing, keeping the command class focused purely on execution.
  - Integrated price updating (`p/PRICE`) as part of the edit flow, which required adding `setPrice()` and `getPrice()` methods to the `Item` class.
  - The command updates the item in-place using `setDescription()`, `setQuantity()`, and `setPrice()`, ensuring no unnecessary object creation.
- **Difficulty:** Medium — required careful integration with the existing `Item` class and ensuring the validator correctly rejects invalid inputs (negative price, out-of-bounds index, non-numeric values).

#### 2. Price Field in `Item` class
- **What it does:** Added a `price` field (stored as `double`) to the `Item` class, with `setPrice()` and `getPrice()` methods, and updated `toString()` to display the price in `$X.XX` format.
- **Justification:** Without price tracking, the inventory system would be incomplete for real-world shop use cases.

---

### Contributions to the User Guide

- Wrote the **Editing an Item (`editItem`)** section, including:
  - Format description with parameter table
  - Example inputs and expected outputs
  - Note explaining that all three fields must be provided

---

### Contributions to the Developer Guide

- Wrote the **Editing an Item** section under Implementation, including:
  - Step-by-step execution walkthrough
  - PlantUML sequence diagram for `EditCommand`
  - Code snippet showing the parsing logic
  - Design considerations table comparing "all fields required" vs "optional fields" approach
  - Edit Command Class Diagram reference

---

### Contributions to Team-Based Tasks

- Helped resolve merge conflicts in `Parser.java` during the v1.0 iteration.
- Updated `Parser.java` to register the `editItem` command keyword correctly.
- Added `//@@author vionyp` tags to all code I authored to ensure accurate RepoSense attribution.

---

### Review/Mentoring Contributions

- Reviewed teammates' PRs on GitHub and provided comments on code correctness and formatting.

---

### Contributions Beyond the Project Team

- Smoke-tested CATcher for the practical exam preparation.
- Reported bugs found in peer teams' products during PE dry run activities.