
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Solve7 {


    public static void copyDirectory(File source, File destination) throws IOException {
        if (!source.exists()) {
            throw new IOException("Исходная директория не существует: " + source.getAbsolutePath());
        }

        if (!source.isDirectory()) {
            throw new IOException("Source должен быть директорией: " + source.getAbsolutePath());
        }

        if (!destination.exists()) {
            boolean created = destination.mkdirs();
            if (!created) {
                throw new IOException("Не удалось создать директорию: " + destination.getAbsolutePath());
            }
        }

        File[] files = source.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            File destFile = new File(destination, file.getName());

            if (file.isDirectory()) {
                copyDirectory(file, destFile);
            } else {
                copyFile(file, destFile);
            }
        }
    }

    private static void copyFile(File source, File destination) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination)) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void main(String[] args) {
        try {
            File source = new File("C:\\Users\\user\\Desktop\\ControlWork");
            File destination = new File("C:\\Users\\user\\Desktop\\ForSolve7");

            copyDirectory(source, destination);
            System.out.println("Директория успешно скопирована!");

        } catch (IOException e) {
            System.err.println("Ошибка при копировании: " + e.getMessage());
            e.printStackTrace();
        }
    }
}





