package NewSctructures;

import Structures.List.ArrayUnorderedList;

public class ArrayUnorderedListWithGet<T> extends ArrayUnorderedList<T> implements UnorderedListWithGetADT<T> {
    public T get(int index) {
        if (index < 0 || index > count) {
            return null;
        }

        return array[index];
    }
}
