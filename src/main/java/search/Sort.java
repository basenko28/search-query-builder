package search;

public class Sort {

    private final String field;
    private final boolean ascending;

    public Sort(String field, boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }

    public String getField() {
        return field;
    }

    public boolean isAscending() {
        return ascending;
    }

    @Override
    public String toString() {
        return field + (ascending ? " по возрастанию" : " по убыванию");
    }
}
