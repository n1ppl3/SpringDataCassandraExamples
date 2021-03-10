package ru.n1ppl3.spring.data.cassandra.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * у объекта есть способность порождать свою копию
 * <img style="width: 100%; height: 100%" src="./doc-files/pic.png">
 */
@FunctionalInterface
public interface Copyable<SelfT extends Copyable<SelfT>> {

    SelfT copy();

    default <T extends Copyable<T>> T copy(T obj) {
        return obj != null ? obj.copy() : null;
    }

    default <T extends Copyable<T>> List<T> copy(List<T> list) {
        if (list == null) {
            return null;
        }
        List<T> result = new ArrayList<>(list.size());
        for (T el : list) {
            result.add(el.copy());
        }
        return result;
    }
}
