import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private File tempDir;
    private File sourceDir;
    private File targetDir;
    private File structureFile;

    private InputStream originalIn;

    @BeforeEach
    void setUp() throws IOException {

        String tmpDirPath = System.getProperty("java.io.tmpdir");
        tempDir = new File(tmpDirPath, "encoder_test_" + System.currentTimeMillis());
        tempDir.mkdirs();

        sourceDir = new File(tempDir, "source");
        targetDir = new File(tempDir, "target");
        structureFile = new File(tempDir, "structure.txt");

        sourceDir.mkdirs();
        targetDir.mkdirs();

        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        deleteRecursively(tempDir);
    }

    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }

    @Test
    void testSingleFile() throws IOException {
        File file = new File(sourceDir, "test.txt");
        file.createNewFile();

        runEncoder(sourceDir, structureFile);
        runDecoder(structureFile, targetDir);

        assertStructureEquals(sourceDir, targetDir);
    }

    @Test
    void testNestedFolders() throws IOException {
        File sub = new File(sourceDir, "sub");
        sub.mkdirs();
        File subsub = new File(sub, "subsub");
        subsub.mkdirs();
        File file1 = new File(sourceDir, "file1.txt");
        File file2 = new File(sub, "file2.txt");
        File file3 = new File(subsub, "file3.txt");
        file1.createNewFile();
        file2.createNewFile();
        file3.createNewFile();

        runEncoder(sourceDir, structureFile);
        runDecoder(structureFile, targetDir);

        assertStructureEquals(sourceDir, targetDir);
    }

    @Test
    void testEmptyDirectory() throws IOException {
        runEncoder(sourceDir, structureFile);
        runDecoder(structureFile, targetDir);
        assertStructureEquals(sourceDir, targetDir);
    }


    private void runEncoder(File source, File output) throws IOException {
        String input = source.getAbsolutePath() + "\n" + output.getAbsolutePath() + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        encoder.main(new String[0]);
    }

    private void runDecoder(File structure, File target) throws IOException {
        String input = structure.getAbsolutePath() + "\n" + target.getAbsolutePath() + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        decoder.main(new String[0]);
    }

    private void assertStructureEquals(File expectedRoot, File actualRoot) {
        Set<String> expectedPaths = collectPaths(expectedRoot);
        Set<String> actualPaths = collectPaths(actualRoot);
        assertEquals(expectedPaths, actualPaths, "Структуры не совпадают");
    }

    private Set<String> collectPaths(File root) {
        Set<String> paths = new HashSet<>();
        collectPathsRecursive(root, root, paths);
        return paths;
    }

    private void collectPathsRecursive(File root, File current, Set<String> paths) {
        if (!current.equals(root)) {
            String relative = getRelativePath(root, current);
            String type = current.isDirectory() ? "dir" : "file";
            paths.add(type + ":" + relative);
        }
        if (current.isDirectory()) {
            File[] children = current.listFiles();
            if (children != null) {
                for (File child : children) {
                    collectPathsRecursive(root, child, paths);
                }
            }
        }
    }

    private String getRelativePath(File root, File file) {
        String rootPath = root.getAbsolutePath();
        String filePath = file.getAbsolutePath();
        if (!filePath.startsWith(rootPath)) {
            throw new IllegalArgumentException("Файл не находится внутри корня");
        }
        String relative = filePath.substring(rootPath.length());
        if (relative.startsWith(File.separator)) {
            relative = relative.substring(1);
        }
        relative = relative.replace(File.separatorChar, '/');
        return relative;
    }
}
