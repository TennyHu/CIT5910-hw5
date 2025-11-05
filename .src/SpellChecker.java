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
        FileInputStream input = new FileInputStream(".src/" + fileName);
        Scanner scnr = new Scanner(input);

        // Create output writer
        FileOutputStream output = new FileOutputStream(".src/" + outputFile);
        PrintWriter writer = new PrintWriter(output);

        // Call to method to make dictionary a HashSet
        HashSet<String> dictionarySet = dictionarySet(dictName);

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
        FileInputStream dictionary = new FileInputStream(".src/" + inputDict);
        Scanner dictScnr = new Scanner(dictionary);

        while (dictScnr.hasNext()) {
            String dictWord = dictScnr.next();
            dictSet.add(dictWord);
        }
        return dictSet;
    }

    // Method to check if word is in the dictionary - returns true if valid
    private boolean validWord(String word, HashSet dictionary) {
        return dictionary.contains(word);
    }

    // Mthod to ask user what to do when encountered misspelled word and returns replacement word
    private String typoHandling(String word, String dictName) throws FileNotFoundException {
        System.out.printf(Util.MISSPELL_NOTIFICATION, word);
        WordRecommender recommender = new WordRecommender(".src/" + dictName);
        ArrayList<String> suggestions = recommender.getWordSuggestions(word, 2, 0.5, 4);

        // Printing suggestions
        if (suggestions.size() == 0) { // when there are no valid suggestions
            System.out.printf(Util.NO_SUGGESTIONS);
            while (true) {
                System.out.printf(Util.TWO_OPTION_PROMPT);
                System.out.print(">>");
                String selected = inputReader.next();

                if (selected.equals("t")) {
                    System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                    System.out.print(">>");
                    String replacement = inputReader.next();
                    return replacement;

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
            while (true) {
                System.out.printf(Util.THREE_OPTION_PROMPT);
                System.out.print(">>");
                String selected = inputReader.next();

                if (selected.equals("t")) {
                    System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                    System.out.print(">>");
                    String replacement = inputReader.next();
                    return replacement;

                } else if (selected.equals("a")) {
                    return word;

                } else if (selected.equals("r")) {
                    String replaceWith = autoReplacement(suggestions);
                    return replaceWith;     // return the word user input corresponds to

                } else {
                    System.out.printf(Util.INVALID_RESPONSE);
                }
            }
        }
    }

    // Method to allow user to pick which of the recommended words to replace misspelled word with. Returns the replacement word
    private String autoReplacement(ArrayList<String> suggestions) {
        System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
        while (true) {
            //System.out.printf(">>");
//            inputReader.nextLine(); // Added in this line and prevented infinite loop!!! Check WHY!
//            // System.out.println("I'm after the nextLine()!");
//            if (!inputReader.hasNextInt()) {
//                System.out.println("I'm in 1st if statement");
//                System.out.printf(Util.INVALID_RESPONSE); // Ask for another response when invalid input
//            } else {
//                int intInput = inputReader.nextInt();
//                if (intInput > suggestions.size() || intInput <= 0) {   // Invalid number is input
//                    System.out.println("I'm in 2nd if statement");
//                    System.out.printf(Util.INVALID_RESPONSE);
//                } else {
//                    return suggestions.get(intInput - 1);   // Return word at corresponding index of the array
//                }
//            }
            System.out.printf(">>");

            String input = inputReader.nextLine();
            if (input.isEmpty()) {
                System.out.printf(Util.INVALID_RESPONSE);

            }

            try {
                int intInput = Integer.parseInt(input);
                System.out.println("The input int is: " + intInput);

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

    public static void main(String[] args) throws FileNotFoundException {
        SpellChecker s = new SpellChecker();
        s.start();
        FileInputStream input = new FileInputStream(".src/engDictionary.txt");
    }
}

