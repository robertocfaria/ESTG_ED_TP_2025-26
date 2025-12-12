package Menus;

import Reader.Reader;

/**
 * Represents a menu utility that allows the user to view the list of saved
 * game history files and select one to display its contents.
 * <p>
 * This class interacts directly with the {@code GameHistoryImporter} to
 * retrieve the files and print their logs.
 */
public class SelectGameHistory {

    /**
     * Constructs a SelectGameHistory menu, prompts the user to choose a file,
     * and prints the contents of the selected history file to the console.
     * <p>
     * The process involves:
     * <ol>
     * <li>Retrieving the list of available history files (JSON files).</li>
     * <li>Displaying the list of files to the user.</li>
     * <li>Reading the user's integer choice.</li>
     * <li>Calling the importer to print the formatted history of the chosen file.</li>
     * </ol>
     */
    public SelectGameHistory() {
        Reader reader = new Reader();

        String[] files = Importers.GameHistoryImporter.getHistoryFiles();

        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i]);
        }

        int choice = reader.readInt(1, files.length, "Qual jogo quer ler: ");
        String selectedFile = files[choice - 1];

        Importers.GameHistoryImporter.printHistoryFile(selectedFile);
    }
}
