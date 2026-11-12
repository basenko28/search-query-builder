package search;

public class ConsoleSearchEngine implements SearchEngine {

    @Override
    public void search(SearchQuery query) {
        System.out.println("Выполняется поиск по запросу:");
        System.out.println(query);
    }
}