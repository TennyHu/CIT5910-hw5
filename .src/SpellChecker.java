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
        String dictName = inputDict();
        // Dictionary is successfully opened
        System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, dictName);

        String fileName = inputFile();
        // Dictionary is successfully opened
        System.out.printf(Util.FILE_SUCCESS_NOTIFICATION, fileName, fileName + "_chk");

        inputReader.close();  // DO NOT MODIFY - must be the last line of this method!

    }

    // Helper function to check if valid file
    private boolean validFile(String fileName) {
        try {
            FileInputStream input = new FileInputStream(".src/" + fileName);
            return true;

        } catch (IOException e) {
            System.out.printf(Util.FILE_OPENING_ERROR);
            return false;
        }
    }

    // Helper method to ask user for dictionary to use
    private String inputDict() {
        System.out.printf(Util.DICTIONARY_PROMPT);
        System.out.print(">> ");
        String fileName = inputReader.next();

        // Continue asking user to input dictionary name until successfully opened
        while (!validFile(fileName)) {
            System.out.printf(Util.DICTIONARY_PROMPT);
            System.out.print(">> ");
            fileName = inputReader.next();
        }
        return fileName;
    }

    // Helper method to ask user for file to spellcheck
    private String inputFile() {
        System.out.printf(Util.FILENAME_PROMPT);
        System.out.print(">> ");
        String inputName = inputReader.next();

        // Continue asking user to input file name until successfully opened
        while (!validFile(inputName)) {
            System.out.printf(Util.FILENAME_PROMPT);
            System.out.print(">> ");
            inputName = inputReader.next();
        }
        return inputName;
    }

    public static void main(String[] args) throws FileNotFoundException {
        SpellChecker s = new SpellChecker();
        s.start();
        FileInputStream input = new FileInputStream(".src/engDictionary.txt");


    }
}

