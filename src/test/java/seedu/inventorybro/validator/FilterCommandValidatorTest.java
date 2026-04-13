package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Validation tests for {@link FilterCommandValidator}.
 */
class FilterCommandValidatorTest {

    private final CategoryList categories = new CategoryList();

    private ItemList buildItems() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        items.addItem(new Item("Banana", 5, categories.getCategory("Others")));
        return items;
    }

    @Test
    void validate_descriptionEquals_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem description = 'Apple'").validate(buildItems(), categories));
    }

    @Test
    void validate_descriptionLessThan_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem description < 'Mango'").validate(buildItems(), categories));
    }

    @Test
    void validate_descriptionGreaterThan_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem description > 'Apple'").validate(buildItems(), categories));
    }

    @Test
    void validate_descriptionMultiWordValue_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem description = 'Green Apple'")
                        .validate(buildItems(), categories));
    }

    @Test
    void validate_quantityEquals_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem quantity = 10").validate(buildItems(), categories));
    }

    @Test
    void validate_quantityLessThan_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem quantity < 20").validate(buildItems(), categories));
    }

    @Test
    void validate_quantityGreaterThan_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem quantity > 3").validate(buildItems(), categories));
    }

    @Test
    void validate_priceEquals_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem price = 10").validate(buildItems(), categories));
    }

    @Test
    void validate_priceLessThan_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem price < 20").validate(buildItems(), categories));
    }

    @Test
    void validate_priceGreaterThan_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem price > 3").validate(buildItems(), categories));
    }

    @Test
    void validate_invalidFormat_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem").validate(buildItems(), categories));
    }

    @Test
    void validate_unknownField_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem name = 'Apple'").validate(buildItems(), categories));
    }

    @Test
    void validate_unknownOperator_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem quantity != 10").validate(buildItems(), categories));
    }

    @Test
    void validate_descriptionValueWithoutQuotes_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem description = Apple").validate(buildItems(), categories));
    }

    @Test
    void validate_quantityValueNonInteger_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem quantity = abc").validate(buildItems(), categories));
    }

    @Test
    void validate_negativeQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem quantity = -5").validate(buildItems(), categories));
    }

    @Test
    void validate_priceValueNonNumeric_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem price = abc").validate(buildItems(), categories));
    }

    @Test
    void validate_negativePrice_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem price = -5").validate(buildItems(), categories));
    }

    @Test
    void validate_priceWithOneDecimalPlace_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem price = 5.9").validate(buildItems(), categories));
    }

    @Test
    void validate_priceWithTwoDecimalPlaces_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem price = 5.99").validate(buildItems(), categories));
    }

    @Test
    void validate_priceWithThreeDecimalPlaces_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem price = 5.999").validate(buildItems(), categories));
    }

    @Test
    void validate_priceDecimalInMultiPredicate_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem price > 1.50 AND price < 5.99")
                        .validate(buildItems(), categories));
    }

    @Test
    void validate_quantityWithDecimal_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator("filterItem quantity = 5.5").validate(buildItems(), categories));
    }

    @Test
    void validate_andCombination_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem description = 'Apple' AND quantity > 5")
                        .validate(buildItems(), categories));
    }

    @Test
    void validate_orCombination_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator("filterItem description = 'Apple' OR description = 'Banana'")
                        .validate(buildItems(), categories));
    }

    @Test
    void validate_mixedAndOrCombination_noException() {
        assertDoesNotThrow(() ->
                new FilterCommandValidator(
                        "filterItem quantity > 3 AND quantity < 15 OR description = 'Cherry'")
                        .validate(buildItems(), categories));
    }

    @Test
    void validate_invalidOperatorBetweenPredicates_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator(
                        "filterItem description = 'Apple' NAND quantity > 5").validate(buildItems(), categories));
    }

    @Test
    void validate_negativeQuantityInMultiPredicate_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FilterCommandValidator(
                        "filterItem description = 'Apple' AND quantity > -1").validate(buildItems(), categories));
    }
}
