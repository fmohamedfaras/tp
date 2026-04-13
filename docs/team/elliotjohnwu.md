# Wu Zi En Elliot John - Project Portfolio Page

## Overview

InventoryBRO is a CLI-based inventory management application for small business owners who prefer typing over mouse-driven UIs. It supports adding, editing, deleting, filtering, and transacting inventory items, with built-in tab-completion and typo detection to speed up daily use.

---

## Summary of Contributions

### Code Contributed

[View my code contributions on the tP Code Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=elliotjohnwu)

---

### Enhancements Implemented

#### 1. Transaction Command (`transact`)

* **What it does:** Adjusts the quantity of an item using a signed integer (positive for restock, negative for sale) via the format `transact INDEX q/CHANGE`.
* **Justification:** Inventory systems require accurate tracking of stock movement. A dedicated transaction command ensures all quantity changes are explicit and recorded.

    * Parses user input into item index and quantity change using controlled string splitting.
    * Updates item quantity while ensuring it does not become negative (validated beforehand).
    * Automatically records each transaction via `TransactionStorage`.
    * Integrates with `TransactCommandValidator` to enforce correct format and prevent invalid operations.
    * Logs operations using Java `Logger` for traceability.
    * Designed to integrate directly with the storage sequence described in **DG Figure 30 (TransactionStorage Sequence Diagram)**.
* **Files:** `TransactCommand.java`, `TransactCommandValidator.java`

---

#### 2. Transaction History Feature (`showHistory`)

* **What it does:** Displays all past transactions stored in persistent storage.
* **Justification:** Users need visibility into past stock changes for auditing and tracking purposes.

    * Loads transaction history from storage using `TransactionStorage.load()`.
    * Gracefully handles empty history by displaying a user-friendly message.
    * Outputs indexed history entries for readability.
    * Supports dependency injection for testing via an alternate constructor.
    * Behaviour aligns with the loading flow shown in **DG Figure 30 (TransactionStorage Sequence Diagram)**.
* **Files:** `ShowTransactionHistoryCommand.java`, `ShowTransactionHistoryCommandValidator.java`

---

#### 3. Storage Architecture (`Storage<T>`)

* **What it does:** Provides a reusable abstraction for file-based persistence across different data types.
* **Justification:** Avoids duplication of file I/O logic and standardises error handling across the system.

    * Implements common operations:

        * `saveArray()` (overwrite)
        * `saveHistory()` (append)
        * `load()` (read with fault tolerance)
    * Uses abstract methods `encode()` and `decode()` for type-specific logic.
    * Automatically creates directories if they do not exist.
    * Integrates logging for both normal operations and failures.
    * Central abstraction illustrated in **DG Figure 26 (Storage Class Diagram)**.
    * Shared save/load flow reused by all subclasses, shown in **DG Figure 29 (ArrayStorage Sequence Diagram)** and **Figure 30**.
* **Files:** `Storage.java`

---

#### 4. Transaction Storage System

* **What it does:** Handles persistent storage of transaction history as formatted strings.
* **Justification:** Separating transaction storage from command logic improves modularity and maintainability.

    * Stores entries in the format:

      ```
      itemName | quantityChange | timestamp
      ```
    * Automatically generates timestamps using `LocalDateTime`.
    * Extends a generic `Storage<String>` class to reuse file handling logic.
    * Includes validation in `decode()` to skip corrupted lines safely.
    * Logs warnings for malformed entries instead of crashing.
    * Implements an **append-only persistence model**, as illustrated in **DG Figure 30 (TransactionStorage Sequence Diagram)**.
    * Design is documented structurally in **DG Figure 28 (TransactionStorage Class Diagram)**.
* **Files:** `TransactionStorage.java`

---

#### 5. Inventory Storage (`ArrayStorage`)

* **What it does:** Handles saving and loading of `Item` objects.
* **Justification:** Provides persistence for the main inventory data while integrating with categories.

    * Encodes items using `Item.toSaveFormat()`.
    * Decodes items from file with validation for:

        * Quantity
        * Price
        * Description
        * Category
    * Ensures category consistency:

        * Automatically creates missing categories during loading.
    * Skips corrupted lines without interrupting the load process.
    * Bridges between `ArrayList<Item>` and `ItemList`, aligning with the flow in **DG Figure 29 (ArrayStorage Sequence Diagram)**.
    * Structure and relationships are documented in **DG Figure 27 (ArrayStorage Class Diagram)**.
    * Enforces **referential integrity via shared `CategoryList`**, a key design decision highlighted in the DG.
* **Files:** `ArrayStorage.java`

---

#### 6. Test Stubs for Storage

* **What it does:** Provides isolated storage implementations for testing.
* **Justification:** Prevents tests from modifying real data and enables deterministic testing.

    * `TransactionStorageStub` writes to a test-specific file path.
    * `TransactionStorageHistoryStub` returns predefined entries.
    * `ArrayStorageStub` redirects inventory storage to a test file.
    * Supports cleanup via test file deletion.
    * Allows independent verification of flows shown in **DG Figures 29 and 30** without affecting production data.
* **Files:**
  `TransactionStorageStub.java`,
  `TransactionStorageHistoryStub.java`,
  `ArrayStorageStub.java`

## Contributions to the User Guide (UG)

* Wrote **Transact Command (`transact`)**

    * Command usage, format, and examples
    * Explanation of positive/negative quantity changes

* **Show History Command (`showHistory`)**

    * User-facing behaviour and output format

* **Storage & Persistence (UG sections)**

    * Explained how data is saved and loaded automatically
  
---

## Contributions to the Developer Guide (DG)

I contributed both **implementation documentation and UML diagrams**, focusing on the storage system and transaction pipeline.

### **Diagrams Added / Updated**

#### Command & Feature Diagrams

* [TransactCommand Class Diagram](diagrams/TransactCommandClassDiagram.png) *(Figure 18)*
* [TransactCommand Sequence Diagram](diagrams/TransactCommandSequenceDiagram.png) *(Figure 19)*
* [ShowTransactionHistoryCommand Class Diagram](diagrams/ShowTransactionHistoryCommandClassDiagram.png) *(Figure 20)*
* [ShowTransactionHistoryCommand Sequence Diagram](diagrams/ShowTransactionHistoryCommandSequenceDiagram.png) *(Figure 21)*

#### Storage System Diagrams

* [Storage Class Diagram](diagrams/StorageClassDiagram.png) *(Figure 26)*
* [ArrayStorage Sequence Diagram](diagrams/ArrayStorageSequenceDiagram.png) *(Figure 27)*
* [TransactionStorage Sequence Diagram](diagrams/TransactionStorageSequenceDiagram.png) *(Figure 28)*

---

### **Sections Written**

* Storage System (full design + justification)
* Transact Command (implementation details)
* Transaction History feature
* Storage architecture and persistence design

---

## Team-Based Tasks

## Contributions to Team-Based Tasks

* Designed and implemented the **storage subsystem architecture**
* Established patterns for **fault-tolerant file handling**
* Created and refined **UML diagrams for DG**
* Set up the team GitHub repository
* Drafted initial User Guide (v1.0)
* Managed build process and resolved issues for `tp.jar` (v1.0)

---

### Review / Mentoring Contributions
* Reviewed and approved PRs for team members, ensuring their commands adhered to the decoupled Validator/Command architecture.
  *Example:* [#63](https://github.com/AY2526S2-CS2113-W09-3/tp/pull/63), [#22](https://github.com/AY2526S2-CS2113-W09-3/tp/pull/22), [#180](https://github.com/AY2526S2-CS2113-W09-3/tp/pull/180)

  
## Contributions Beyond the Project Team

* Reported bugs and provided feedback on other teams’ products
* [#17](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/17), [#8](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/8), [#1](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/1), [#7](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/7), [#4](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/4)

---

