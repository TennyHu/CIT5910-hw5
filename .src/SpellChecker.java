import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellChecker {
    // Use this field everytime you need to read user input
    private Scanner inputReader; // DO NOT MODIFY

    public SpellChecker() {
        inputReader = new Scanner(System.in); // DO NOT MODIFY - must be included in this method
        // TODO: Complete the body of this constructor, as necessary.
    }

    public void start() throws FileNotFoundException {
        // TODO: Complete the body of this method, as necessary
        String dictName = inputDict();
        // Dictionary is successfully opened
        System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, dictName);

        String fileName = inputFile();
        // Dictionary is successfully opened
        String outputFile = fileName.substring(0, fileName.length() - 4) + "_chk.txt"; // Call output file INPUT-FILE-NAME_chk.txt
        System.out.printf(Util.FILE_SUCCESS_NOTIFICATION, fileName, outputFile);

        // Create input stream
        FileInputStream input = new FileInputStream(fileName);
        Scanner scnr = new Scanner(input);

        // Create output writer
        FileOutputStream output = new FileOutputStream(outputFile);
        PrintWriter writer = new PrintWriter(output);

        // Call to method to make dictionary a HashSet
        HashSet<String> dictionarySet = dictionarySet(dictName);

        while (scnr.hasNext()) {
            String word = scnr.next();
            // Method to check if word exists in the dictionary and returns true
            if (validWord(word, dictionarySet)) {
                writer.print(word);

            } else {
                // TODO: Call to exceptions method - return word to add
                writer.print(word);
            }

        }

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

    // Method to make dictionary into a set and return this set
    private HashSet<String> dictionarySet(String inputDict) throws FileNotFoundException {
        HashSet<String> dictSet = new HashSet<>();
        FileInputStream dictionary = new FileInputStream(inputDict);
        Scanner dictScnr = new Scanner(dictionary);

        while (dictScnr.hasNext()) {
            String dictWord = dictScnr.next();
            dictSet.add(dictWord);
        }
        return dictSet;
    }

    // Method to check if word is in the dictionary - returns true if valid
    private boolean validWord(String word, HashSet dictionary) {

        dictionary.contains(word);

        return false;
    }

    // TODO: complete typoHandling method -> state word is misspelled and call to WordRecommender
    private String typoHandling(String word) {
        System.out.printf(Util.MISSPELL_NOTIFICATION, word);
        // Call to wordRecommender
        // Asks user how to deal with the typo
        // Returns word to add to the file
        return "";
    }

    public static void main(String[] args) throws FileNotFoundException {
        SpellChecker s = new SpellChecker();
        s.start();
        FileInputStream input = new FileInputStream(".src/engDictionary.txt");


    }
}

