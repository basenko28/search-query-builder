package search;

public class Main {

    public static void main(String[] args) {
        QueryDirector director = new QueryDirector();
        SearchEngine engine = new ConsoleSearchEngine();

        engine.search(director.cheapLaptops(SearchQuery.builder()));
        engine.search(director.exportBrand(SearchQuery.builder(), "Asus"));
    }
}