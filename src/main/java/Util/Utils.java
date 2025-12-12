package Util;

import Map.Maze;
import Structures.Interfaces.ListADT;

import java.util.Iterator;

public class Utils {

    public static void waitEnter() {
            System.out.print("\nPressione ENTER para continuar...");
        try {
            while (System.in.read() != '\n');
        } catch (Exception e) {}
    }

    public static <T> T getListADTObject(int index, ListADT<T> list) {
        Iterator<T> iterator = list.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            if (index == i) {
                return

            }

            i++;
        }
    }


}