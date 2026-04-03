import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Finder {

    public static List<String> solve(String file) throws IOException {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b(WARN|ERROR|SEVERE)\\b");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    result.add(line);
                }
            }
        }
        return result;
    }


    public static void main(String[] args) throws IOException {
        String file = "src\\example.log";
        solve(file).forEach(System.out::println);
    }
}
