package Interfaces;

import Structures.Interfaces.UnorderedListADT;

public interface UnorderedListWithGetADT<T> extends UnorderedListADT<T> {
    T get(int index);
}