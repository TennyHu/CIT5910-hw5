import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SpellChecker {
    // Use this field everytime you need to read user input
    private Scanner inputReader; // DO NOT MODIFY

    public SpellChecker() {
        inputReader = new Scanner(System.in); // DO NOT MODIFY - must be included in this method
        // TODO: Complete the body of this constructor, as necessary.
    }

    public void start() {
        // TODO: Complete the body of this method, as necessary
        System.out.printf(Util.DICTIONARY_PROMPT);
        System.out.print(">> ");
        String fileName = inputReader.next();

        // Continue asking user to input dictionary name until successfully opened
        while(!inputDict(fileName)){
            System.out.printf(Util.DICTIONARY_PROMPT);
            System.out.print(">> ");
            fileName = inputReader.next();
        }
        // Dictionary is successfully opened
        System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, fileName);

        inputReader.close();  // DO NOT MODIFY - must be the last line of this method!

    }

    // You can of course write other methods as well.
    private boolean inputDict(String fileName){
        try {
            FileInputStream input = new FileInputStream(".src/"+fileName);
            return true;

        } catch (IOException e) {
            System.out.printf(Util.FILE_OPENING_ERROR);
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        SpellChecker s = new SpellChecker();
        s.start();
        FileInputStream input = new FileInputStream(".src/engDictionary.txt");


    }
}

