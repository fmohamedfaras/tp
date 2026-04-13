package seedu.inventorybro;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.inventorybro.storage.TransactionStorageHistoryStub;

/**
 * Tests that {@link Parser} routes commands case-insensitively, normalising the keyword
 * to its canonical camelCase form before handing off to each {@code Command}.
 */
class ParserTest {

    private ItemList items;
    private CategoryList categories;
    private Ui ui;
    private TransactionStorageHistoryStub storageStub;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        items = new ItemList();
        categories = new CategoryList();
        ui = new Ui();
        storageStub = new TransactionStorageHistoryStub(new ArrayList<>());
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreOut() {
        System.setOut(originalOut);
    }

    /** Runs {@code input} through the parser and asserts no ERROR line was printed. */
    private void assertParsesOk(String input) {
        Parser.parse(input, items, categories, ui, storageStub);
        assertFalse(outContent.toString().contains("ERROR:"),
                "Expected no error for input: \"" + input + "\"");
        outContent.reset();
    }

    // -------------------------------------------------------------------------
    // addItem
    // -------------------------------------------------------------------------

    @Test
    void parse_addItemAllLowercase_noError() {
        assertParsesOk("additem d/Apple q/10 p/5 c/Others");
    }

    @Test
    void parse_addItemAllUppercase_noError() {
        assertParsesOk("ADDITEM d/Banana q/5 p/2 c/Others");
    }

    @Test
    void parse_addItemMixedCase_noError() {
        assertParsesOk("AddItem d/Cherry q/3 p/1 c/Others");
    }

    @Test
    void parse_addItemAnotherMixedCase_noError() {
        assertParsesOk("addITEM d/Durian q/1 p/8 c/Others");
    }

    // -------------------------------------------------------------------------
    // deleteItem
    // -------------------------------------------------------------------------

    @Test
    void parse_deleteItemAllLowercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("deleteitem 1");
    }

    @Test
    void parse_deleteItemAllUppercase_noError() {
        items.addItem(new Item("Banana", 5, categories.getCategory("Others")));
        assertParsesOk("DELETEITEM 1");
    }

    @Test
    void parse_deleteItemMixedCase_noError() {
        items.addItem(new Item("Cherry", 3, categories.getCategory("Others")));
        assertParsesOk("DeleteItem 1");
    }

    // -------------------------------------------------------------------------
    // editDescription
    // -------------------------------------------------------------------------

    @Test
    void parse_editDescriptionAllLowercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("editdescription 1 d/Green Apple");
    }

    @Test
    void parse_editDescriptionAllUppercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("EDITDESCRIPTION 1 d/Green Apple");
    }

    @Test
    void parse_editDescriptionMixedCase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("EditDescription 1 d/Green Apple");
    }

    @Test
    void parse_editDescriptionAnotherMixedCase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("editDESCRIPTION 1 d/Green Apple");
    }

    // -------------------------------------------------------------------------
    // editPrice
    // -------------------------------------------------------------------------

    @Test
    void parse_editPriceAllLowercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("editprice 1 p/3.50");
    }

    @Test
    void parse_editPriceAllUppercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("EDITPRICE 1 p/3.50");
    }

    @Test
    void parse_editPriceMixedCase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("EditPrice 1 p/3.50");
    }

    // -------------------------------------------------------------------------
    // editQuantity
    // -------------------------------------------------------------------------

    @Test
    void parse_editQuantityAllLowercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("editquantity 1 q/20");
    }

    @Test
    void parse_editQuantityAllUppercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("EDITQUANTITY 1 q/20");
    }

    @Test
    void parse_editQuantityMixedCase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("editQUANTITY 1 q/20");
    }

    // -------------------------------------------------------------------------
    // editCategory
    // -------------------------------------------------------------------------

    @Test
    void parse_editCategoryAllLowercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("editcategory 1 c/Others");
    }

    @Test
    void parse_editCategoryAllUppercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("EDITCATEGORY 1 c/Others");
    }

    @Test
    void parse_editCategoryMixedCase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("EditCategory 1 c/Others");
    }

    // -------------------------------------------------------------------------
    // transact
    // -------------------------------------------------------------------------

    @Test
    void parse_transactAllUppercase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("TRANSACT 1 q/5");
    }

    @Test
    void parse_transactMixedCase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("Transact 1 q/5");
    }

    @Test
    void parse_transactAnotherMixedCase_noError() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertParsesOk("tRaNsAcT 1 q/5");
    }

    // -------------------------------------------------------------------------
    // showHistory
    // -------------------------------------------------------------------------

    @Test
    void parse_showHistoryAllLowercase_noError() {
        assertParsesOk("showhistory");
    }

    @Test
    void parse_showHistoryAllUppercase_noError() {
        assertParsesOk("SHOWHISTORY");
    }

    @Test
    void parse_showHistoryMixedCase_noError() {
        assertParsesOk("ShowHistory");
    }

    @Test
    void parse_showHistoryAnotherMixedCase_noError() {
        assertParsesOk("showHISTORY");
    }

    // -------------------------------------------------------------------------
    // listItems
    // -------------------------------------------------------------------------

    @Test
    void parse_listItemsAllLowercase_noError() {
        assertParsesOk("listitems");
    }

    @Test
    void parse_listItemsAllUppercase_noError() {
        assertParsesOk("LISTITEMS");
    }

    @Test
    void parse_listItemsMixedCase_noError() {
        assertParsesOk("ListItems");
    }

    @Test
    void parse_listItemsAnotherMixedCase_noError() {
        assertParsesOk("listITEMS");
    }

    // -------------------------------------------------------------------------
    // findItem
    // -------------------------------------------------------------------------

    @Test
    void parse_findItemAllLowercase_noError() {
        assertParsesOk("finditem apple");
    }

    @Test
    void parse_findItemAllUppercase_noError() {
        assertParsesOk("FINDITEM apple");
    }

    @Test
    void parse_findItemMixedCase_noError() {
        assertParsesOk("FindItem apple");
    }

    @Test
    void parse_findItemAnotherMixedCase_noError() {
        assertParsesOk("findITEM apple");
    }

    // -------------------------------------------------------------------------
    // filterItem
    // -------------------------------------------------------------------------

    @Test
    void parse_filterItemAllLowercase_noError() {
        assertParsesOk("filteritem quantity > 0");
    }

    @Test
    void parse_filterItemAllUppercase_noError() {
        assertParsesOk("FILTERITEM quantity > 0");
    }

    @Test
    void parse_filterItemMixedCase_noError() {
        assertParsesOk("FilterItem quantity > 0");
    }

    @Test
    void parse_filterItemAnotherMixedCase_noError() {
        assertParsesOk("filterITEM quantity > 0");
    }

    // -------------------------------------------------------------------------
    // addCategory
    // -------------------------------------------------------------------------

    @Test
    void parse_addCategoryAllLowercase_noError() {
        assertParsesOk("addcategory c/Food");
    }

    @Test
    void parse_addCategoryAllUppercase_noError() {
        assertParsesOk("ADDCATEGORY c/Drinks");
    }

    @Test
    void parse_addCategoryMixedCase_noError() {
        assertParsesOk("AddCategory c/Snacks");
    }

    @Test
    void parse_addCategoryAnotherMixedCase_noError() {
        assertParsesOk("addCATEGORY c/Dairy");
    }

    // -------------------------------------------------------------------------
    // listCategories
    // -------------------------------------------------------------------------

    @Test
    void parse_listCategoriesAllLowercase_noError() {
        assertParsesOk("listcategories");
    }

    @Test
    void parse_listCategoriesAllUppercase_noError() {
        assertParsesOk("LISTCATEGORIES");
    }

    @Test
    void parse_listCategoriesMixedCase_noError() {
        assertParsesOk("ListCategories");
    }

    @Test
    void parse_listCategoriesAnotherMixedCase_noError() {
        assertParsesOk("listCATEGORIES");
    }

    // -------------------------------------------------------------------------
    // deleteCategory
    // -------------------------------------------------------------------------

    @Test
    void parse_deleteCategoryAllLowercase_noError() {
        categories.addCategory(new Category("Temp"));
        assertParsesOk("deletecategory c/Temp");
    }

    @Test
    void parse_deleteCategoryAllUppercase_noError() {
        categories.addCategory(new Category("Temp2"));
        assertParsesOk("DELETECATEGORY c/Temp2");
    }

    @Test
    void parse_deleteCategoryMixedCase_noError() {
        categories.addCategory(new Category("Temp3"));
        assertParsesOk("DeleteCategory c/Temp3");
    }

    // -------------------------------------------------------------------------
    // help
    // -------------------------------------------------------------------------

    @Test
    void parse_helpAllUppercase_noError() {
        assertParsesOk("HELP");
    }

    @Test
    void parse_helpMixedCase_noError() {
        assertParsesOk("Help");
    }

    @Test
    void parse_helpAnotherMixedCase_noError() {
        assertParsesOk("hElP");
    }

    // -------------------------------------------------------------------------
    // exit
    // -------------------------------------------------------------------------

    @Test
    void parse_exitAllUppercase_throwsExitException() {
        assertThrows(ExitException.class, () -> Parser.parse("EXIT", items, categories, ui, storageStub));
    }

    @Test
    void parse_exitMixedCase_throwsExitException() {
        assertThrows(ExitException.class, () -> Parser.parse("Exit", items, categories, ui, storageStub));
    }

    @Test
    void parse_exitAnotherMixedCase_throwsExitException() {
        assertThrows(ExitException.class, () -> Parser.parse("eXiT", items, categories, ui, storageStub));
    }
}
