package com.yrbase.adapter;

import java.util.List;

public interface DataIO<T> {

    void addAll(List<T> elements);

    void replaceAll(List<T> elements);

    void clear();

    int getSize();

    void add(T elem);








    void addAt(int location, T elem);


    void addAllAt(int location, List<T> elements);

    void remove(T elem);

    void deleteAll(List<T> elements);

    void removeAt(int index);


    void replace(T oldElem, T newElem);

    void replaceAt(int index, T elem);


    List<T> getAll();

    T get(int position);


    boolean contains(T elem);

}
