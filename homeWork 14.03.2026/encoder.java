import java.io.*;
import java.util.Scanner;

public class encoder {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к папке или файлу: ");
        String sourcePath = scanner.nextLine().trim();
        File source = new File(sourcePath).getAbsoluteFile();

        if (!source.exists()) {
            System.err.println("Ошибка: источник не существует: " + source);
            return;
        }

        System.out.print("Введите путь для сохранения структуры (Enter для 'structure.txt'): ");
        String outputPath = scanner.nextLine().trim();
        File output = outputPath.isEmpty() ? new File("structure.txt") : new File(outputPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            saveStructure(source, ".", writer);
        }

        System.out.println("Структура сохранена в " + output.getAbsolutePath());
        scanner.close();
    }

    private static void saveStructure(File current, String relativePath, BufferedWriter writer) throws IOException {
        String type = current.isDirectory() ? "dir" : "file";
        writer.write(type + "\t" + relativePath);
        writer.newLine();

        if (current.isDirectory()) {
            File[] children = current.listFiles();
            if (children != null) {
                for (File child : children) {
                    String childRelative = relativePath.equals(".")
                            ? child.getName()
                            : relativePath + "/" + child.getName();
                    saveStructure(child, childRelative, writer);
                }
            }
        }
    }
}
