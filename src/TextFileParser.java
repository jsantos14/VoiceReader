import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class TextFileParser
{
    public static ArrayList<String> parseText (String filePath) throws
            FileNotFoundException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        

        ArrayList<String> words = new ArrayList<>();

        return words;
    }
}
