package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A library of simple file operations. Feel free to modify this file.
 */
public class FileUtils {
    /**
     * Writes the specified contents to a file with the given filename.
     *
     * @param filename The name of the file to write to.
     * @param contents The contents to write to the file.
     * @throws RuntimeException if an IOException occurs during the write operation.
     */
    public static void writeFile(String filename, String contents) {
        try {
            Files.writeString(new File(filename).toPath(), contents);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Reads the contents of a file with the given filename.
     *
     * @param filename The name of the file to read from.
     * @return The contents of the file as a String.
     * @throws RuntimeException if an IOException occurs during the read operation.
     */
    public static String readFile(String filename) {
        try {
            return Files.readString(new File(filename).toPath());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Checks if a file with the given filename exists.
     *
     * @param filename The name of the file to check for existence.
     * @return true if the file exists, false otherwise.
     */
    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }
}
