package search;

public interface QueryBuilder {

    QueryBuilder text(String text);

    QueryBuilder where(String field, String operator, Object value);

    QueryBuilder range(String field, double min, double max);

    QueryBuilder sortBy(String field, boolean ascending);

    QueryBuilder page(int page, int pageSize);

    QueryBuilder fetchAll();

    QueryBuilder highlight(String field);

    QueryBuilder groupBy(String field);

    SearchQuery build();
}