# Wu Zi En Elliot John - Project Portfolio Page

## Overview

InventoryBRO is a CLI-based inventory management application for small business owners who prefer typing over mouse-driven UIs. It supports adding, editing, deleting, filtering, and transacting inventory items, with built-in tab-completion and typo detection to speed up daily use.



## Summary of Contributions

### Code Contributed

[View my code contributions on the tP Code Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=elliotjohnwu)



### Enhancements Implemented

#### 1. Transaction Command (`transact`)

*Updates item quantity with validation and logs changes.*
**Files:** `TransactCommand.java`, `TransactCommandValidator.java`


#### 2. Transaction History Feature (`showHistory`)

*Displays stored transaction logs with empty-state handling.*
**Files:** `ShowTransactionHistoryCommand.java`, `ShowTransactionHistoryCommandValidator.java`


#### 3. Storage Architecture (`Storage<T>`)

*Base abstraction for reusable, fault-tolerant persistence.*
**Files:** `Storage.java`

#### 4. Transaction Storage System

*Append-only persistence model for audit-safe transaction tracking.*
**Files:** `TransactionStorage.java`


#### 5. Inventory Storage (`ArrayStorage`)

*Handles persistence of inventory items with encode/decode and category consistency checks.*
**Files:** `ArrayStorage.java`


#### 6. Storage Test Stubs

*Mock storage layer for isolated and deterministic testing.*
**Files:**
`TransactionStorageStub.java`,`TransactionStorageHistoryStub.java`,`ArrayStorageStub.java`

## Team-Based Tasks

### Contributions to Team-Based Tasks
* Designed and implemented the **storage subsystem architecture**
* Established patterns for **fault-tolerant file handling**
* Created and refined **UML diagrams for DG**
* Set up the team GitHub repository
* Drafted initial User Guide (v1.0)
* Managed build process and resolved issues for `tp.jar` (v1.0)


### Review / Mentoring Contributions
* Reviewed and approved PRs for team members, ensuring their commands adhered to the decoupled Validator/Command architecture.
* *Example:* 
[#22](https://github.com/AY2526S2-CS2113-W09-3/tp/pull/22), 
[#63](https://github.com/AY2526S2-CS2113-W09-3/tp/pull/63),
[#180](https://github.com/AY2526S2-CS2113-W09-3/tp/pull/180), 
[#190](https://github.com/AY2526S2-CS2113-W09-3/tp/pull/190#pullrequestreview-4102739741)

 
## Contributions Beyond the Project Team
* Reported bugs and provided feedback on other teams’ products
* *Example:*
[#17](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/17), 
[#8](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/8), 
[#1](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/1), 
[#7](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/7), [#4](https://github.com/NUS-CS2113-AY2526-S2/ped-elliotjohnwu/issues/4)




## Contributions to the User Guide (UG)

* Wrote **Transact Command (`transact`)**

    * Command usage, format, and examples
    * Explanation of positive/negative quantity changes

* **Storage Architecture (UG sections)**

    * Explained how data is saved and loaded automatically

* Drafted UG v1.0


## Contributions to the Developer Guide (DG)

I contributed both **implementation documentation and UML diagrams**, focusing on the storage system and transaction pipeline.

### **Diagrams Added / Updated**

#### Command & Feature Diagrams
* [TransactCommand Class Diagram](../diagrams/TransactCommandClassDiagram.png) 
* [TransactCommand Sequence Diagram](../diagrams/TransactCommandSequenceDiagram.png) 
* [ShowTransactionHistoryCommand Class Diagram](../diagrams/ShowTransactionHistoryCommandClassDiagram.png) 
* [ShowTransactionHistoryCommand Sequence Diagram](../diagrams/ShowTransactionHistoryCommandSequenceDiagram.png) 

#### Storage System Diagrams

* [Storage Class Diagram](../diagrams/StorageClassDiagram.png)
* [ArrayStorage Sequence Diagram](../diagrams/ArrayStorageSequenceDiagram.png)
* [TransactionStorage Sequence Diagram](../diagrams/TransactionStorageSequenceDiagram.png) 



### **Sections Written**

* Transact Command (implementation details)
* Transaction History feature
* Storage architecture
* Array Storage
* TransactionStorage





