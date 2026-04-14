# Jansen Ken Pegrasio - Project Portfolio Page

## Overview

InventoryBRO is a CLI-based inventory management application for small business owners who prefer typing over mouse-driven UIs. It supports adding, editing, deleting, filtering, and transacting inventory items, with built-in tab-completion and typo detection to speed up daily use.

---

## Summary of Contributions

### Code Contributed

[View my code contributions on the tP Code Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=kenpegrasio)

---

### Enhancements Implemented

#### 1. Add Item Command (`addItem`)

- **What it does:** Allows the user to add a new item to the inventory with a name, an initial quantity, a mandatory price, and a category using the format `addItem d/NAME q/INITIAL_QUANTITY p/PRICE c/CATEGORY`.
- **Justification:** This is the core create operation of the inventory — without it, there is nothing to manage. Price was made mandatory so every item has a meaningful value from the moment it is created.
- **Highlights:**
  - Format is enforced via a strict regex (`^addItem\s+d/(.+?)\s+q/(\d+)\s+p/(\d+(\.\d+)?)\s+c/(.+)$`) shared between `AddCommand` and `AddCommandValidator`. Large-but-valid digit strings are passed through the pattern so that specific, actionable error messages are shown instead of the generic format error.
  - Validation checks are applied in a deliberate order: duplicate prefix flag → empty name → single-quote in name → duplicate item → quantity integer overflow → negative quantity → price double overflow → price rounding → category existence.
  - Price validation uses `Math.round(price * 100) <= 0` to reject values like `0.001` that display as `$0.00`, giving the user a clear display-aware error rather than silently accepting a misleading price.
  - Integer overflow on quantity is caught explicitly via `NumberFormatException` (thrown by `Integer.parseInt` when the value exceeds `Integer.MAX_VALUE`) and reported as `"Quantity is too large to be processed by the system."`.
  - Double overflow on price is detected via `Double.isInfinite()` (returned by `Double.parseDouble` when the value exceeds `Double.MAX_VALUE`) and reported as `"Price is too large to be processed by the system."`.
  - Name cannot be empty, whitespace-only, or contain single quotes (`'`) — the trimmed name is checked after regex match. Single quotes are forbidden because `filterItem` uses them as value delimiters for description predicates, which would cause parsing bugs.
  - The specified category must already exist in `CategoryList`; if not, an error directs the user to create it first via `addCategory`.
  - Quantity must be 0 or greater (not strictly positive), allowing items to be pre-registered before stock arrives.
  - Duplicate name detection is handled by `DuplicateItemValidator`, which performs a case-insensitive comparison against all existing items. This prevents silent data corruption from near-identical names like `Apple` and `apple`.
  - Validation is fully separated from execution following the project's SLAP principle: `AddCommandValidator` throws `IllegalArgumentException` before any mutation occurs.
- **Files:** `AddCommand.java`, `AddCommandValidator.java`, `AddCommandTest.java`, `AddCommandValidatorTest.java`

---

#### 2. Filter Item Command (`filterItem`)

- **What it does:** Filters the inventory by one or more predicates on `description`, `quantity`, or `price`, combined with `AND`/`OR` logic. For example: `filterItem quantity > 10 AND quantity < 40`.
- **Justification:** As an inventory grows, a simple list is unwieldy. Structured filtering lets users query exactly what they need without scrolling through hundreds of items.
- **Highlights:**
  - Supports all three comparison operators (`=`, `<`, `>`) across three field types (`description`, `quantity`, `price`).
  - Implements AND-before-OR evaluation (AND binds tighter), achieved by grouping consecutive AND predicates into "AND-groups" and evaluating OR between groups.
  - `FilterCommandValidator` validates structural correctness (gaps between predicates must be exactly `AND` or `OR`), field–value type consistency (description values must be single-quoted; quantity values must be non-negative integers matching `^\d+$`; price values must match `^\d+(\.\d{1,2})?$` — non-negative with at most 2 decimal places), and trailing text.
  - Price comparison is done on values rounded to 2 decimal places using `Math.round(x * 100) / 100.0` and `Double.compare`, so `filterItem price = 2` correctly matches an item stored with price `1.999`.
  - The predicate parser uses a single regex (`(description|quantity|price) (=|<|>) ('.*?'|[^\s']+)`) in both validator and command, following the project convention of no shared utility class.
- **Files:** `FilterCommand.java`, `FilterCommandValidator.java`, `FilterCommandTest.java`, `FilterCommandValidatorTest.java`

---

#### 3. Command Autocompletion (Trie + JLine)

- **What it does:** When the user presses `Tab` in the terminal, InventoryBRO completes or suggests command keywords based on the prefix typed so far (e.g., `add` → `addItem`; `f` → `filterItem`, `findItem`).
- **Justification:** Command autocompletion reduces the cognitive load of remembering exact command names and speeds up repeated use.
- **Highlights:**
  - Built a `Trie` data structure from scratch (`Trie.java`, `TrieNode.java`) that stores command keywords in lowercase and retrieves them with original casing. Prefix traversal is O(k) where k is the prefix length; full word collection is DFS over the remaining subtree.
  - `Autocompleter` wraps the `Trie` and derives its word list from the `CommandWord` enum — the single source of truth — so adding a new command keyword to the enum automatically makes it tab-completable with no additional wiring.
  - Integrated with JLine3's `Completer` API in `Ui`, which intercepts `Tab` keystrokes in interactive terminal sessions (JAR path only; gracefully degrades to no-op when stdin is piped).
  - Matching is case-insensitive: typing `ADD` still completes to `addItem`.
- **Files:** `Autocompleter.java`, `Trie.java`, `TrieNode.java`, `AutocompleterTest.java`, `TrieTest.java`

---

#### 4. Typo Detection (`TypoDetector`)

- **What it does:** When the user enters an unrecognised command (e.g., `adItem`), InventoryBRO automatically suggests the closest known command: `"Do you mean addItem?"`.
- **Justification:** Typos are a common source of frustration in CLI tools. A helpful suggestion dramatically reduces the feedback loop for mistyped commands.
- **Highlights:**
  - Implements a **QWERTY-weighted edit distance** algorithm: the substitution cost between two characters is their Manhattan distance on a standard QWERTY keyboard layout (e.g., replacing `s` with `d` costs less than replacing `s` with `p`). This means physically adjacent key swaps — the most common typing errors — are penalised less than arbitrary substitutions.
  - Uses a dynamic-programming approach (Wagner–Fischer) with floating-point costs to accumulate fractional keyboard distances across the full word.
  - A configurable `TYPO_THRESHOLD_FACTOR` (0.2 × max word length) prevents the detector from suggesting commands that are not genuinely similar, avoiding false positives for completely unrelated inputs.
  - Runs automatically on every unrecognised command with no configuration required.
- **Files:** `TypoDetector.java`, `TypoDetectorTest.java`

---

### User Guide Contributions

I authored the following sections of the User Guide:

- **Section 1 — Adding an Item (`addItem`):** format, constraints, and example output
- **Section 12 — Filtering Items (`filterItem`):** format, operator reference table, and five worked examples (single predicate, AND, OR, integer price filter, decimal price filter); updated to reflect that price accepts up to 2 decimal places; added note on lexicographic ordering for description `<`/`>`
- **Command Autocompletion section:** how tab-completion works, the behavior table, and the limitations note (JAR-only, keyword-only, case-insensitive)
- **Typo Detection section:** explanation of the QWERTY distance model, the suggestion behavior table, and the note that it runs automatically

---

### Developer Guide Contributions

I authored the following sections of the Developer Guide:

- **Add Item — Design & Implementation:** step-by-step sequence walkthrough from user input through `Parser` → `AddCommand` → `AddCommandValidator` → `DuplicateItemValidator` → `ItemList`, with the class and sequence diagrams
- **Filter Item — Design & Implementation:** predicate extraction, AND-group building, OR evaluation, validator gap-checking, and the class and sequence diagrams
- **Command Autocompletion (Trie & JLine) — Design & Implementation:** Trie construction sequence, tab-completion sequence (JLine → `Autocompleter` → `Trie`), and the class and sequence diagrams (Figures 26–27)
- **Typo Detection — Design & Implementation:** weighted edit distance algorithm walkthrough, threshold calculation, and the class and sequence diagrams (Figures 28–29)

---

### Team-Based Tasks

- Set up the command and validator layer separation (SLAP principle) as the standard pattern
- Reviewed and provided feedback on PRs related to the edit item and help command
- Resolved test coverage bug where `System.exit(0)` terminates the entire JVM

#### Issues Raised for Teammates

Raised the following bugs and improvement requests to help teammates improve their implementations:

| Issue | Title | Summary |
| :--- | :--- | :--- |
| [#44](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/44) | Unaligned listItems semantic | User guide stated the command is `listItems` but the system required `list`, causing a documentation–behaviour mismatch |
| [#45](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/45) | Incorrect error message for editItem | Error message incorrectly instructed users to include an `INDEX` parameter when the actual format does not use one |
| [#46](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/46) | Unaligned editItem behaviour and unhandled exception | Providing only `quantity` to `editItem` gave an error; providing only `description` caused an unhandled `ArrayIndexOutOfBoundsException` |
| [#97](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/97) | No test for help command | Flagged a gap in test coverage — the help command had no automated tests |
| [#98](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/98) | Saved notification message | `listItems` was incorrectly displaying a save notification message, blurring the boundary between command output and storage side-effects |
| [#99](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/99) | HelpCommandValidator code quality | `validateCommandSpecified` in `HelpCommandValidator` manually matched command strings instead of reusing the existing `CommandWord` enum |
| [#100](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/100) | Missing editQuantity, editDescription, and editPrice help messages | The three edit sub-commands were absent from the help output entirely |
| [#101](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/101) | showHistory is not in help | `showHistory` was missing from the help menu and its documentation |
| [#102](https://github.com/AY2526S2-CS2113-W09-3/tp/issues/102) | Show history format improvement | Transaction history output format was hard to read; proposed a clearer format such as `2026-04-09 16:45 Buy 5 Apple` |

---

## Contributions to the User Guide (Extracts)

### Adding an Item: `addItem`

Adds a new item to the inventory.

**Format:** `addItem d/NAME q/INITIAL_QUANTITY p/PRICE c/CATEGORY`

| Parameter | Meaning |
|---|---|
| `NAME` | The item's display name. Must not be empty, must not contain single quotes (`'`), and must not already exist in the inventory (case-insensitive). |
| `INITIAL_QUANTITY` | A non-negative integer (0 or greater) representing the starting stock level. Must not exceed `Integer.MAX_VALUE`. |
| `PRICE` | A decimal price of at least `0.01` when rounded to 2 decimal places (e.g. `0.001` is rejected). Must not exceed `Double.MAX_VALUE`. |
| `CATEGORY` | The category to assign the item to. The category must already exist (use `addCategory` to create it first). |

**Example:** `addItem d/Coke Can q/50 p/1.50 c/Beverages`

```
Added: [BEVERAGES] Coke Can (Quantity: 50, Price: $1.50)
```

---

### Filtering Items: `filterItem`

Filters and displays inventory items matching one or more field–operator–value predicates.

**Format:** `filterItem FIELD OPERATOR VALUE [AND|OR FIELD OPERATOR VALUE ...]`

| FIELD | OPERATOR | VALUE |
|---|---|---|
| `description` | `=`, `<`, `>` | Text enclosed in single quotes, e.g. `'Coke Can'` |
| `quantity` | `=`, `<`, `>` | Non-negative integer, e.g. `10` |
| `price` | `=`, `<`, `>` | Non-negative number with at most 2 decimal places, e.g. `1.50` |

AND binds before OR: `A AND B OR C` means `(A AND B) OR C`.

**Example — single predicate:**
```
filterItem quantity > 10
```
**Example — AND (both conditions must hold):**
```
filterItem quantity > 10 AND quantity < 40
```
**Example — OR (either condition matches):**
```
filterItem description = 'Coke Can' OR description = 'Sprite Bottle'
```

---

## Contributions to the Developer Guide (Extracts)

### Command Autocompletion (Trie & JLine)

The autocompletion mechanism is handled by the `Autocompleter` class, which wraps a custom `Trie` data structure and integrates with JLine3's `Completer` API.

**Construction sequence:**
1. `Ui` constructs a new `Autocompleter`.
2. `Autocompleter` calls `buildTrie()`, which iterates every `CommandWord.values()` entry and inserts each keyword into the `Trie` (lowercased character-by-character; original casing stored at the terminal node).

**Tab-completion sequence (user presses Tab):**
1. JLine3 intercepts the keystroke and calls `Ui.complete()`.
2. `Ui.complete()` calls `autocompleter.getMatches(partial)`.
3. `Autocompleter` delegates to `trie.findWithPrefix(prefix.toLowerCase())`.
4. `Trie.navigateTo()` walks down to the prefix node; `collectWordsRecursively()` performs DFS to collect all terminal nodes in the subtree.
5. JLine3 displays the results inline or completes unambiguously.

The `Autocompleter` derives its word list from `CommandWord` — the single source of truth — so any new command keyword added to the enum is automatically tab-completable.

---

### Typo Detection

When a user enters an unrecognised command, `Parser.handleUnknownCommand()` extracts the first word and calls `TypoDetector.findClosestMatch()`.

**Algorithm:**
- Computes a **QWERTY-weighted edit distance** for each known command using dynamic programming (Wagner–Fischer). The substitution cost between two characters is their Manhattan distance on a standard QWERTY keyboard layout, so physically adjacent key swaps cost less than arbitrary substitutions.
- The set of known commands (`KNOWN_COMMANDS`) is built at class-load time from `CommandWord.values()` — the single source of truth — so it always reflects the current set of supported commands with no hard-coded list to maintain.
- Selects the command with the lowest weighted distance.
- Applies a threshold (`0.2 × max(inputLength, commandLength)`): only suggests if the best distance is below this threshold, preventing false positives for completely unrelated input.

**Design rationale:** Using keyboard distance rather than uniform substitution cost (Levenshtein) makes the detector more accurate for the most common real-world typos (adjacent-key swaps like `addItem` → `adItem`) while remaining resistant to suggesting commands for inputs that are simply not commands at all.
