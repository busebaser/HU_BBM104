import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IO {
    /**
     * Reads data from a file and returns it as a String array.
     * Each line is split into an array of strings where each word is an element.
     * If the last line is not "Z_REPORT", "Z_REPORT" line is added to the end.
     */
    public static String[][] readFile(String path) {
        List<String[]> allLines = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {

                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] words = line.trim().split("\t"); // Split the line by tabs
                allLines.add(words);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return allLines.toArray(new String[0][]);
    }


    /**
     * Checks if the last line of the file at the given path is "Z_REPORT".
     * @param path A String representing the file path.
     * @return True if the last line of the file is "Z_REPORT", false otherwise.
     */
    public static boolean isLastZReport(String path) {
        boolean isZ_reportExist = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            String lastLine = null;
            while ((line = reader.readLine()) != null) {
                if(!line.trim().isEmpty()){
                    lastLine = line;
                }
            }
            reader.close();

            if (lastLine != null && lastLine.trim().equals("Z_REPORT")) {
                isZ_reportExist = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isZ_reportExist;
    }

    // Method to write content to a specified path.
    // Throws IOException if path is not found.
    public static void writeToFile(String path, String content){
        try(FileWriter writer = new FileWriter(path,true)){
            writer.write(content);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Removes the newline character at the end of a line in the file.
    // This ensures that the last line doesn't move to the next line when editing the file.
    public static void removeLastNewLine(String path) {
        try {
            // Read the file
            RandomAccessFile file = new RandomAccessFile(path, "rw");

            // Search for the newline character from the end of the file
            long length = file.length();
            if (length == 0) return;

            // Search for the newline character backwards from the end
            file.seek(length - 1);
            if (file.readByte() == '\n') {
                // When the newline character is found, reduce the length of the file to remove it
                file.setLength(length - 1);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


