import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellChecker {
    // Use this field everytime you need to read user input
    private Scanner inputReader;

    public SpellChecker() {
        inputReader = new Scanner(System.in);
    }

    public void start() {
        String dictName = inputDict();  // Call to method inputDict()
        System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, dictName);  // Dictionary is successfully opened

        String fileName = inputFile();  // Call to method inputFile()

        String outputFile = fileName.substring(0, fileName.length() - 4) + "_chk.txt";  // Call output file INPUT-FILE-NAME_chk.txt
        System.out.printf(Util.FILE_SUCCESS_NOTIFICATION, fileName, outputFile);    // Print output file success msg

        try {
            // Create input stream
            FileInputStream input = new FileInputStream(fileName);
            Scanner scnr = new Scanner(input);

            // Create output writer
            FileOutputStream output = new FileOutputStream(outputFile);
            PrintWriter writer = new PrintWriter(output);

            // Call to method to make dictionary a HashSet
            HashSet<String> dictionarySet = dictionarySet(dictName);

            // Read from input file, handle typos accordingly and write to output file
            while (scnr.hasNext()) {
                String word = scnr.next();
                // Method to check if word exists in the dictionary and returns true
                if (validWord(word, dictionarySet)) {
                    writer.print(word + " ");

                } else {
                    String replaceWordWith = typoHandling(word, dictName);
                    writer.print(replaceWordWith + " ");
                }
            }

            writer.close();
            inputReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("InputStream / OutputStream could not be created");
        }
    }

    // Helper function to check if valid file
    private boolean validFile(String fileName) {
        try {
            FileInputStream input = new FileInputStream(fileName);
            return true;
        } catch (IOException e) {
            System.out.printf(Util.FILE_OPENING_ERROR);
            return false;
        }
    }

    // Helper method to ask user for dictionary to use
    private String inputDict() {
        System.out.printf(Util.DICTIONARY_PROMPT);
//        System.out.print(">> ");
        String fileName = inputReader.nextLine();

        // Continue asking user to input dictionary name until successfully opened
        while (!validFile(fileName)) {
            System.out.printf(Util.DICTIONARY_PROMPT);
//            System.out.print(">> ");
            fileName = inputReader.nextLine();
        }
        return fileName;
    }

    // Helper method to ask user for file to spellcheck
    private String inputFile() {
        System.out.printf(Util.FILENAME_PROMPT);
//        System.out.print(">> ");
        String inputName = inputReader.nextLine();

        // Continue asking user to input file name until successfully opened
        while (!validFile(inputName)) {
            System.out.printf(Util.FILENAME_PROMPT);
//            System.out.print(">> ");
            inputName = inputReader.nextLine();
        }
        return inputName;
    }

    // Method to make dictionary into a set and return this set
    private HashSet<String> dictionarySet(String inputDict) {
        HashSet<String> dictSet = new HashSet<>();
        try {
            FileInputStream dictionary = new FileInputStream(inputDict);
            Scanner dictScnr = new Scanner(dictionary);

            while (dictScnr.hasNext()) {
                String dictWord = dictScnr.next();
                dictSet.add(dictWord);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error in opening dictionary");
        }
        return dictSet;

    }

    // Method to check if word is in the dictionary - returns true if valid
    private boolean validWord(String word, HashSet dictionary) {
        return dictionary.contains(word);
    }

    // Method to ask user what to do when encountered misspelled word and returns replacement word
    private String typoHandling(String word, String dictName) {
        System.out.printf(Util.MISSPELL_NOTIFICATION, word);
        WordRecommender recommender = new WordRecommender(dictName);
        ArrayList<String> suggestions = recommender.getWordSuggestions(word, 2, 0.5, 4);

        // Printing suggestions
        if (suggestions.size() == 0) { // when there are no valid suggestions
            System.out.printf(Util.NO_SUGGESTIONS);
            while (true) {  // Continue asking user for input whilst input is invalid
                System.out.printf(Util.TWO_OPTION_PROMPT);
//                System.out.print(">>");
                String selected = inputReader.nextLine();

                if (selected.equals("t")) {
                    while (true) {
                        System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
//                        System.out.print(">>");
                        String replacement = inputReader.nextLine();
//                        if (replacement.isBlank()) {        // handle the case when input is empty
//                            continue;
//                        }
                        return replacement;
                    }
                } else if (selected.equals("a")) {
                    return word;
                } else {
                    System.out.printf(Util.INVALID_RESPONSE);
                }
            }
        } else {    // When suggestions arraylist is not empty
            System.out.printf(Util.FOLLOWING_SUGGESTIONS);
            for (int i = 0; i < suggestions.size(); i++) {    // for every suggestion in the arraylist, list these for the user to see
                System.out.printf(Util.SUGGESTION_ENTRY, i + 1, suggestions.get(i));
            }
            while (true) {  // continue asking user for input whilst input received is invalid
                System.out.printf(Util.THREE_OPTION_PROMPT);
//                System.out.print(">>");
                String selected = inputReader.nextLine();

                if (selected.equals("t")) {
                    System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
//                    System.out.print(">>");
                    String replacement = inputReader.next();
                    inputReader.nextLine();
                    return replacement;

                } else if (selected.equals("a")) {
                    return word;

                } else if (selected.equals("r")) {
                    String replaceWith = autoReplacement(suggestions);  // call to method autoReplacement()
                    return replaceWith;     // return the word user input corresponds to

                } else {
                    System.out.printf(Util.INVALID_RESPONSE);
                }
            }
        }
    }

    // Method to allow user to pick which of the recommended words to replace misspelled word with. Returns the replacement word
    private String autoReplacement(ArrayList<String> suggestions) {
        while (true) {
            System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
//            System.out.printf(">>");

            String input = inputReader.nextLine();
            // handle the case when input is empty
            if (input.isEmpty()) {
                System.out.printf(Util.INVALID_RESPONSE);
            }

            try {
                int intInput = Integer.parseInt(input);

                if (intInput > suggestions.size() || intInput <= 0) {
                    System.out.printf(Util.INVALID_RESPONSE);
                } else {
                    return suggestions.get(intInput - 1);
                }

            } catch (NumberFormatException e) {
                System.out.printf(Util.INVALID_RESPONSE);
            }
        }
    }
}

