import  java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Book> books = List.of(
                new Book("Book", 250, 1830, "Author1", List.of("fantasy")),
                new Book("Book1", 300, 2005, "Author1", List.of("fantasy")),
                new Book("Book2", 300, 2016, "Author2", List.of("fantasy", "romantic")),
                new Book("Book3", 100, 1995, "Author3", List.of("fantasy")),
                new Book("BookAboutJava", 900, 2010, "Author4", List.of("study")),
                new Book("BookAboutJava2", 400, 2015, "Author4", List.of("study")),
                new Book("Book4", 250, 2021, "Author2", List.of("fantasy"))


        );

        System.out.println(solve1(books));
        System.out.println(solve2(books));
        System.out.println(solve3(books));
        System.out.println(solve4(books));
        solve5(books);

    }

    public static List<String> solve1(List<Book> books) {
        return books.stream()
                .filter(p -> p.getPrice() < 500 && p.getYear() > 2010)
                .map(a -> a.getAuthor())
                .distinct()
                .sorted()
                .toList();
    }


    public static Map<String, List<String>> solve2(List<Book> books) {
        return books.stream()
                .flatMap(b -> b.getGenres().stream()
                        .map(g -> new AbstractMap.SimpleEntry<>(g, b.getTitle())))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

    }

    public static OptionalDouble solve3(List<Book> books) {
        return books.stream()
                .filter(b -> b.getGenres().contains("fantasy") && b.getYear() <= 2000)
                .mapToDouble(p -> p.getPrice())
                .average();
        //.orElse(0);
    }

    public static Book solve4(List<Book> books) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains("java"))
                .max(Comparator.comparingDouble(Book::getPrice))
                .orElse(new Book());
    }


    public static void solve5(List<Book> books) {

        books.stream()
                .collect(Collectors.groupingBy(Book::getAuthor))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .peek(entry -> System.out.println(entry.getKey() + ":"))
                .flatMap(entry -> entry.getValue().stream()
                        .sorted(Comparator.comparingInt(Book::getYear))
                        .peek(book -> System.out.println("  " + book.getTitle())))
                .count();
    }

}



