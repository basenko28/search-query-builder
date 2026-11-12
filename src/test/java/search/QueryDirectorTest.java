package search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueryDirectorTest {

    private final QueryDirector director = new QueryDirector();

    @Test
    void cheapLaptopsQuery() {
        SearchQuery query = director.cheapLaptops(SearchQuery.builder());

        assertEquals("ноутбук", query.getText());
        assertEquals(1, query.getRanges().size());
        assertEquals(50000.0, query.getRanges().get(0).getMax());
        assertEquals(1, query.getPage());
        assertFalse(query.isFetchAll());
    }

    @Test
    void exportBrandQuery() {
        SearchQuery query = director.exportBrand(SearchQuery.builder(), "Asus");

        assertEquals("Asus", query.getConditions().get(0).getValue());
        assertTrue(query.isFetchAll());
        assertEquals(0, query.getPage());
    }

    @Test
    void builderCanBeUsedThroughInterface() {
        QueryBuilder builder = SearchQuery.builder();

        SearchQuery query = builder.text("мышь").groupBy("brand").build();

        assertEquals("мышь", query.getText());
        assertEquals("brand", query.getGroupByFields().get(0));
    }
}