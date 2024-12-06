package com.sk89q.worldedit.fabric.data;

import java.util.ArrayList;
import java.util.List;
public abstract class WorldEditData<E> {

    private final List<E> data = new ArrayList<>();

    public List<E> getData() {
        return data;
    }

    public E get(int n) {
        return data.get(n);
    }

    public void addItem(E value) {
        data.add(value);
    }

    public void clearShortcutCommands() {
        for (E d : data) {
            data.remove(d);
        }
    }
}