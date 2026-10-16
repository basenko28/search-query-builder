package search;

public class Condition {

    private final String field;
    private final String operator;
    private final Object value;

    public Condition(String field, String operator, Object value) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Не указано поле");
        }
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return field + " " + operator + " " + value;
    }
}
