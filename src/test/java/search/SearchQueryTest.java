package search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchQueryTest {

    @Test
    void buildFullQuery() {
        SearchQuery query = SearchQuery.builder()
                .text("ноутбук")
                .where("brand", "=", "Lenovo")
                .range("price", 30000, 80000)
                .sortBy("price", true)
                .page(2, 10)
                .highlight("title")
                .groupBy("brand")
                .build();

        assertEquals("ноутбук", query.getText());
        assertEquals(1, query.getConditions().size());
        assertEquals("brand", query.getConditions().get(0).getField());
        assertEquals(1, query.getRanges().size());
        assertEquals(1, query.getSorts().size());
        assertEquals(2, query.getPage());
        assertEquals(10, query.getPageSize());
        assertFalse(query.isFetchAll());
        assertEquals("title", query.getHighlightFields().get(0));
        assertEquals("brand", query.getGroupByFields().get(0));
    }

    @Test
    void firstQueryDoesNotChangeWhenBuilderContinues() {
        SearchQuery.Builder builder = SearchQuery.builder()
                .text("ноутбук")
                .where("brand", "=", "Lenovo");

        SearchQuery first = builder.build();

        // дописываем в строитель ещё одно условие и собираем второй запрос
        builder.where("inStock", "=", true);
        SearchQuery second = builder.build();

        assertEquals(1, first.getConditions().size());
        assertEquals("brand", first.getConditions().get(0).getField());
        assertEquals(2, second.getConditions().size());
    }

    @Test
    void cannotBuildEmptyQuery() {
        SearchQuery.Builder builder = SearchQuery.builder()
                .sortBy("price", true)
                .page(1, 20);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void textOrConditionOrRangeIsEnough() {
        SearchQuery byText = SearchQuery.builder().text("мышь").build();
        SearchQuery byCondition = SearchQuery.builder().where("brand", "=", "Asus").build();
        SearchQuery byRange = SearchQuery.builder().range("price", 0, 1000).build();

        assertEquals("мышь", byText.getText());
        assertEquals(1, byCondition.getConditions().size());
        assertEquals(1, byRange.getRanges().size());
    }

    @Test
    void pageAndFetchAllTogetherNotAllowed() {
        SearchQuery.Builder builder = SearchQuery.builder()
                .text("мышь")
                .page(1, 20)
                .fetchAll();

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void fetchAllWithoutPageIsAllowed() {
        SearchQuery query = SearchQuery.builder().text("мышь").fetchAll().build();
        assertTrue(query.isFetchAll());
        assertEquals(0, query.getPage());
    }

    @Test
    void listsOfQueryCannotBeModified() {
        SearchQuery query = SearchQuery.builder()
                .text("телефон")
                .where("brand", "=", "Apple")
                .build();

        assertThrows(UnsupportedOperationException.class,
                () -> query.getConditions().add(new Condition("brand", "=", "Samsung")));
        assertThrows(UnsupportedOperationException.class,
                () -> query.getSorts().add(new Sort("price", true)));
    }

    @Test
    void wrongRangeNotAllowed() {
        assertThrows(IllegalArgumentException.class,
                () -> SearchQuery.builder().range("price", 5000, 100));
    }

    @Test
    void wrongPageNotAllowed() {
        assertThrows(IllegalArgumentException.class,
                () -> SearchQuery.builder().page(0, 20));
    }
}
