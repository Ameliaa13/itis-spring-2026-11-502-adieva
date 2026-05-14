import java.io.*;
import java.util.Scanner;

public class decoder {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к файлу со структурой: ");
        String structurePath = scanner.nextLine().trim();
        File structureFile = new File(structurePath);

        if (!structureFile.exists() || !structureFile.isFile()) {
            System.err.println("Ошибка: файл не найден: " + structureFile);
            return;
        }

        System.out.print("Введите целевую директорию для восстановления (Enter для текущей): ");
        String targetPath = scanner.nextLine().trim();
        File targetRoot = targetPath.isEmpty() ? new File(".") : new File(targetPath);
        if (!targetRoot.exists()) {
            targetRoot.mkdirs();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(structureFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\t");
                if (parts.length != 2) continue;

                String type = parts[0];
                String relativePath = parts[1];

                if (".".equals(relativePath)) continue;

                String osPath = relativePath.replace('/', File.separatorChar);
                File target = new File(targetRoot, osPath);

                if ("dir".equals(type)) {
                    target.mkdirs();
                } else if ("file".equals(type)) {
                    File parent = target.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    if (!target.exists()) {
                        target.createNewFile();
                    }
                } else {
                    System.err.println("Неизвестный тип: " + type + " для " + relativePath);
                }
            }
        }

        System.out.println("Структура восстановлена в " + targetRoot.getAbsolutePath());
        scanner.close();
    }
}
