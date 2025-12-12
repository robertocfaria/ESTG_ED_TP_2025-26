package Menus;

import Reader.Reader;

public class SelectGameHistory {

    public SelectGameHistory(){
        Reader reader = new Reader();
        // 1. Obter lista
        String[] files = Importers.GameHistoryImporter.getHistoryFiles();

        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i]);
        }

        int choice = reader.readInt(1, files.length, "Qual jogo quer ler: ");
        String selectedFile = files[choice - 1];

        Importers.GameHistoryImporter.printHistoryFile(selectedFile);
    }


}
