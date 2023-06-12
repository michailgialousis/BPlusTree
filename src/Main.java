import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static BTree<String, Finder> btree = new BTree<>(7);
    public static float comparisons;
    public static float nodeAccesses;

    public static void main(String[] args) {

        int choice;
        String fileName;
        String word;
        Scanner scanner = new Scanner(System.in);

        do {
            //we get the user's choice and based on that we proceed to the switch case
            choice = menu();
            switch (choice) {
                case 1 -> {
                    //the user inserts the files he wishes into our structure
                    System.out.print("Enter the name of the file you want to insert : ");
                    fileName = scanner.next();
                    insertWords(fileName, btree);
                }
                case 2 -> {
                    System.out.print("Enter the word you want to search :");
                    word = scanner.next();
                    searchForWordCustomSearch(word, btree);
                }

                case 3 ->
                        makeSearches();
                case 0 ->
                        System.out.println("Exiting...");
                default ->
                        System.out.println("\nInvalid choice. Please try again.");

            }
            System.out.println();

        } while (choice != 0);
    }

    public static int menu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Menu:");
        System.out.println("1. Option 1 : Enter a txt file name. ");
        System.out.println("2. Option 2 : Search a word.");
        System.out.println("3. Option 3 : Do searches");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        return scanner.nextInt();
    }


    public static String[] extractWordsFromFile(String fileName) {
        ArrayList<String> wordsList = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String word = scanner.next();
                wordsList.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new String[0];
        }

        String[] words = new String[wordsList.size()];
        words = wordsList.toArray(words);
        return words;
    }

    public static String[] insertWords(String fileName, BTree<String, Finder> btree) {
        String[] words = extractWordsFromFile(fileName);
        int position = 0;

        for (int i = 0; i < words.length; i++) {

            Finder finder = btree.search(words[i]);

            if (finder == null) {                         //if word doesn't exist make a new linked list
                finder = new Finder();
                finder.add(fileName, position);
                btree.insert(words[i], finder);
            } else                                        // else just add it
                finder.add(fileName, position);

            position += words[i].length() + 1;            //length of the word + space character

        }

        return extractWordsFromFile(fileName);


    }


    // searchForWordCustomSearch is for the second option where the fileName and position
    // are printed as well as the nodes accessed and the comparisons made with the keys of the tree
    public static void searchForWordCustomSearch(String word, BTree<String, Finder> btree) {
        BTree.comparisons = 0;
        BTree.nodeAccesses = 0;

        Finder finder = btree.search(word);
        if (finder == null) {
            System.out.println("This word doesn't exist.");
        } else {
            finder.display();
        }

        System.out.println("Nodes accessed : " + BTree.nodeAccesses + ", Comparisons made : " + BTree.comparisons);

    }


    // searchForWord100Searches doesn't print anything so the console doesn't get cluttered
    // also there is no need for and if else statement because we know that the 100 words that are
    // going to be searched, do exist
    public static void searchForWord100Searches(String word, BTree<String, Finder> btree) {
        BTree.comparisons = 0;
        BTree.nodeAccesses = 0;

        btree.search(word);
    }

    public static void makeSearches() {

        int[] order = {10, 20};
        for (int j = 0; j < order.length; j++) {
            //initialization
            comparisons = 0;
            nodeAccesses = 0;

            BTree<String, Finder> btreeCustomOrder = new BTree<>(order[j]);

            String[] words1 = insertWords("1.txt", btreeCustomOrder);
            String[] words2 = insertWords("2.txt", btreeCustomOrder);


            String[] wordsFromTxt1 = new String[50];
            String[] wordsFromTxt2 = new String[50];


            //taking 50 random words from each file to make the searches
            for (int i = 0; i < 50; i++) {
                int randomIndex1 = RandomNumberGenerator.generateRandomNumber(words1.length);
                int randomIndex2 = RandomNumberGenerator.generateRandomNumber(words2.length);

                wordsFromTxt1[i] = words1[randomIndex1];
                wordsFromTxt2[i] = words2[randomIndex2];

                searchForWord100Searches(wordsFromTxt1[i], btreeCustomOrder);
                comparisons += BTree.comparisons;
                nodeAccesses += BTree.nodeAccesses;

                searchForWord100Searches(wordsFromTxt2[i], btreeCustomOrder);
                comparisons += BTree.comparisons;
                nodeAccesses += BTree.nodeAccesses;
            }

            System.out.println("For order = " + (order[j] + 1) + " average comparisons made : " + (comparisons / 100));
            System.out.println("For order = " + (order[j] + 1) + " average node accesses made : " + (nodeAccesses / 100));

        }
    }
}