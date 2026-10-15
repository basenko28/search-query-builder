package search;

/**
 * Диапазон значений числового поля, например: price от 1000 до 5000.
 */
public class Range {

    private final String field;
    private final double min;
    private final double max;

    public Range(String field, double min, double max) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Не указано поле");
        }
        if (min > max) {
            throw new IllegalArgumentException("Минимум больше максимума: " + min + " > " + max);
        }
        this.field = field;
        this.min = min;
        this.max = max;
    }

    public String getField() {
        return field;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    @Override
    public String toString() {
        return field + " от " + min + " до " + max;
    }
}
