import java.util.List;

public class Book {

    private String title;
    private String author;
    private int year;
    private double price;
    private List<String> genres;

    public Book() {
    }

    public Book(String title, double price, int year, String author, List<String> genres) {
        this.title = title;
        this.genres = genres;
        this.price = price;
        this.year = year;
        this.author = author;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
