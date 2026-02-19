package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String readFileToString(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line.trim());
        }

        reader.close();
        return sb.toString();
    }
}