package search;

import java.util.ArrayList;
import java.util.List;

/**
 * Поисковый запрос к магазину.
 *
 * Объект неизменяемый: все поля final, сеттеров нет,
 * конструктор приватный. Создать запрос можно только через Builder.
 */
public class SearchQuery {

    private final String text;
    private final List<Condition> conditions;
    private final List<Range> ranges;
    private final List<Sort> sorts;
    private final int page;          // 0 - постраничная выдача не задана
    private final int pageSize;
    private final boolean fetchAll;
    private final List<String> highlightFields;
    private final List<String> groupByFields;

    // Конструктор приватный - вызывается только из Builder.build()
    private SearchQuery(Builder builder) {
        this.text = builder.text;
        // Копируем списки, чтобы изменения в строителе не влияли на запрос.
        // List.copyOf возвращает неизменяемый список.
        this.conditions = List.copyOf(builder.conditions);
        this.ranges = List.copyOf(builder.ranges);
        this.sorts = List.copyOf(builder.sorts);
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.fetchAll = builder.fetchAll;
        this.highlightFields = List.copyOf(builder.highlightFields);
        this.groupByFields = List.copyOf(builder.groupByFields);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getText() {
        return text;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Range> getRanges() {
        return ranges;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isFetchAll() {
        return fetchAll;
    }

    public List<String> getHighlightFields() {
        return highlightFields;
    }

    public List<String> getGroupByFields() {
        return groupByFields;
    }

    @Override
    public String toString() {
        String result = "Запрос:\n";
        if (text != null) {
            result += "  текст: " + text + "\n";
        }
        if (!conditions.isEmpty()) {
            result += "  условия: " + conditions + "\n";
        }
        if (!ranges.isEmpty()) {
            result += "  диапазоны: " + ranges + "\n";
        }
        if (!sorts.isEmpty()) {
            result += "  сортировка: " + sorts + "\n";
        }
        if (fetchAll) {
            result += "  выдача: всё найденное\n";
        } else if (page > 0) {
            result += "  выдача: страница " + page + ", по " + pageSize + " товаров\n";
        }
        if (!highlightFields.isEmpty()) {
            result += "  подсветка: " + highlightFields + "\n";
        }
        if (!groupByFields.isEmpty()) {
            result += "  подсчёт по группам: " + groupByFields + "\n";
        }
        return result;
    }

    /**
     * Строитель запроса. Одним строителем можно собрать несколько запросов подряд.
     */
    public static class Builder {

        private String text;
        private final List<Condition> conditions = new ArrayList<>();
        private final List<Range> ranges = new ArrayList<>();
        private final List<Sort> sorts = new ArrayList<>();
        private int page;
        private int pageSize;
        private boolean fetchAll;
        private final List<String> highlightFields = new ArrayList<>();
        private final List<String> groupByFields = new ArrayList<>();

        private Builder() {
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder where(String field, String operator, Object value) {
            conditions.add(new Condition(field, operator, value));
            return this;
        }

        public Builder range(String field, double min, double max) {
            ranges.add(new Range(field, min, max));
            return this;
        }

        public Builder sortBy(String field, boolean ascending) {
            sorts.add(new Sort(field, ascending));
            return this;
        }

        public Builder page(int page, int pageSize) {
            if (page < 1 || pageSize < 1) {
                throw new IllegalArgumentException("Номер и размер страницы должны быть больше 0");
            }
            this.page = page;
            this.pageSize = pageSize;
            return this;
        }

        public Builder fetchAll() {
            this.fetchAll = true;
            return this;
        }

        public Builder highlight(String field) {
            highlightFields.add(field);
            return this;
        }

        public Builder groupBy(String field) {
            groupByFields.add(field);
            return this;
        }

        public SearchQuery build() {
            // обязательная часть: текст, условие или диапазон
            boolean hasText = text != null && !text.isEmpty();
            if (!hasText && conditions.isEmpty() && ranges.isEmpty()) {
                throw new IllegalStateException("Нужно указать текст, условие или диапазон");
            }
            // взаимоисключающие параметры
            if (fetchAll && page > 0) {
                throw new IllegalStateException("Нельзя одновременно выдавать по страницам и всё подряд");
            }
            return new SearchQuery(this);
        }
    }
}
