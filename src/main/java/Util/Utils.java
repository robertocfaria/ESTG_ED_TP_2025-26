package Util;

import Map.Maze;
import Structures.Interfaces.ListADT;

import java.util.Iterator;

/**
 * A utility class containing static helper methods used across the application
 * for general purposes, such as waiting for user input and retrieving elements
 * from an iterable list structure by index.
 */
public class Utils {

    /**
     * Halts program execution until the user presses the ENTER key.
     * This is useful for controlling the pace of console output.
     */
    public static void waitEnter() {
        System.out.print("\nPressione ENTER para continuar...");
        try {
            while (System.in.read() != '\n') ;
        } catch (Exception e) {
        }
    }

    /**
     * Retrieves an element from a {@link ListADT} by its zero-based index.
     * This method traverses the list using its {@link Iterator} until the
     * desired index is reached.
     *
     * @param <T>   The type of elements in the list.
     * @param index The zero-based index of the element to retrieve.
     * @param list  The {@link ListADT} from which to retrieve the element.
     * @return The element at the specified index, or {@code null} if the index is out of bounds.
     */
    public static <T> T getListADTObject(int index, ListADT<T> list) {
        Iterator<T> iterator = list.iterator();
        T element;

        int i = 0;
        while (iterator.hasNext()) {
            element = iterator.next();

            if (index == i) {
                return element;
            }
            i++;
        }

        return null;
    }
}