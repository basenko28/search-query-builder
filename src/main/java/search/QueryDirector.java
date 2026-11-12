package search;

public class QueryDirector {

    public SearchQuery cheapLaptops(QueryBuilder builder) {
        return builder
                .text("ноутбук")
                .range("price", 0, 50000)
                .sortBy("price", true)
                .page(1, 20)
                .highlight("title")
                .build();
    }

    public SearchQuery exportBrand(QueryBuilder builder, String brand) {
        return builder
                .where("brand", "=", brand)
                .sortBy("title", true)
                .fetchAll()
                .build();
    }
}