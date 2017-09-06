import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Parses a text file.
 */
public class TextFileParser
{
    public static ArrayList<String> parseText( String filePath ) throws
            IOException
    {
        Scanner scanner = new Scanner(new File(filePath));
        ArrayList<String> words = new ArrayList<>();

        while (scanner.hasNext()) {
            words.add(scanner.next());
        }

        return words;
    }


    public static ArrayList<String> parseText( Path filePath ) throws
            IOException
    {
        Scanner scanner = new Scanner(filePath);
        ArrayList<String> words = new ArrayList<>();

        while (scanner.hasNext()) {
            words.add(scanner.next());
        }

        return words;
    }
}
