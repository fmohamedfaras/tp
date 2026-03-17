package seedu.inventorybro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseAdd_validCommand_itemAdded() {
        ItemList items = new ItemList();

        Parser.parseAdd("addItem d/Apple q/10", items);

        Item item = items.getItems().get(0);

        assertEquals("Apple", item.getDescription());
        assertEquals(10, item.getQuantity());
    }

    @Test
    void parseAdd_multiWordName() {
        ItemList items = new ItemList();

        Parser.parseAdd("addItem d/Green Apple q/25", items);

        Item item = items.getItems().get(0);

        assertEquals("Green Apple", item.getDescription());
        assertEquals(25, item.getQuantity());
    }

    @Test
    void parseAdd_invalidFormat_throwsException() {
        ItemList items = new ItemList();

        assertThrows(
            IllegalArgumentException.class,
            () -> Parser.parseAdd("addItem Apple 10", items)
        );
    }

    @Test
    void transact_validSale_quantityDecreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        Parser.parse("transact 1 q/-5", items);

        assertEquals(45, items.getItem(0).getQuantity());
    }

    @Test
    void transact_validRestock_quantityIncreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Sprite Bottle", 30));

        Parser.parse("transact 1 q/10", items);

        assertEquals(40, items.getItem(0).getQuantity());
    }

    @Test
    void transact_exactDepletion_quantityBecomesZero() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        Parser.parse("transact 1 q/-50", items);

        assertEquals(0, items.getItem(0).getQuantity());
    }

    @Test
    void transact_quantityBelowZero_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        // selling more than available should be rejected
        Parser.parse("transact 1 q/-999", items);

        assertEquals(50, items.getItem(0).getQuantity());
    }

    @Test
    void transact_invalidIndex_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        // index 99 does not exist
        Parser.parse("transact 99 q/10", items);

        assertEquals(50, items.getItem(0).getQuantity());
    }

    @Test
    void transact_invalidInputFormats_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        Parser.parse("transact 1 10", items);         // missing q/
        assertEquals(50, items.getItem(0).getQuantity());

        Parser.parse("transact abc q/10", items);     // non-digit index
        assertEquals(50, items.getItem(0).getQuantity());

        Parser.parse("transact 1 q/-", items);        // just minus sign
        assertEquals(50, items.getItem(0).getQuantity());

        Parser.parse("transact 1 q/abc", items);      // non-digit quantity
        assertEquals(50, items.getItem(0).getQuantity());
    }

    @Test
    void transact_zeroChange_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        Parser.parse("transact 1 q/0", items);

        assertEquals(50, items.getItem(0).getQuantity());
    }
  
    @Test
    void parseEdit_validInput_updatesItemCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        Parser.parse("edit 1 d/Orange q/20", items);

        assertEquals("Orange", items.getItem(0).getDescription());
        assertEquals(20, items.getItem(0).getQuantity());
    }

    @Test
    void parseEdit_secondItem_updatesCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        Parser.parse("edit 2 d/Mango q/7", items);

        assertEquals("Mango", items.getItem(1).getDescription());
        assertEquals(7, items.getItem(1).getQuantity());
    }

    @Test
    void parseEdit_indexOutOfBounds_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        // Should print error, not throw
        assertDoesNotThrow(() -> Parser.parse("edit 99 d/Ghost q/0", items));
    }

    @Test
    void parseEdit_zeroIndex_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> Parser.parse("edit 0 d/Apple q/5", items));
    }

    @Test
    void parseEdit_nonNumericIndex_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> Parser.parse("edit abc d/Apple q/5", items));
    }

    @Test
    void parseEdit_nonNumericQuantity_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> Parser.parse("edit 1 d/Apple q/abc", items));
    }

    @Test
    void parseEdit_doesNotAffectOtherItems() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        Parser.parse("edit 1 d/Orange q/99", items);

        // Item 2 harus tetap sama
        assertEquals("Banana", items.getItem(1).getDescription());
        assertEquals(5, items.getItem(1).getQuantity());
    }

    @Test
    void exit_validCommand_callsSystemExit() {
        assertThrows(Exception.class, () -> Parser.parse("exit", new ItemList()));
    }

    @Test
    void parseDelete_validIndex_itemRemoved() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        Parser.parse("deleteItem 1", items);

        // Apple should be gone, list size drops to 1, Banana is now at index 0
        assertEquals(1, items.size());
        assertEquals("Banana", items.getItem(0).getDescription());
    }

    @Test
    void parseDelete_invalidIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parse("deleteItem 99", items)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parse("deleteItem 0", items)
        );

        assertEquals(1, items.size());
        assertEquals("Coke Can", items.getItem(0).getDescription());
    }

    @Test
    void parseDelete_invalidInputFormats_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parse("deleteItem", items)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parse("deleteItem abc", items)
        );
        assertEquals(1, items.size());
    }
}