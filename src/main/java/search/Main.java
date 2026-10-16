package search;

public class Main {

    public static void main(String[] args) {
        SearchQuery.Builder builder = SearchQuery.builder()
                .text("ноутбук")
                .where("brand", "=", "Lenovo")
                .range("price", 30000, 80000)
                .sortBy("price", true)
                .page(1, 20)
                .highlight("title")
                .groupBy("brand");

        SearchQuery first = builder.build();
        System.out.println(first);

        SearchQuery second = builder.where("inStock", "=", true).build();
        System.out.println(second);

        System.out.println("Первый запрос не изменился, условий в нём: "
                + first.getConditions().size());

        try {
            SearchQuery.builder().sortBy("price", true).build();
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            SearchQuery.builder().text("мышь").page(1, 20).fetchAll().build();
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
