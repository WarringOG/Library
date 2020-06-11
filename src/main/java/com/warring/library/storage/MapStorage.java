package com.warring.library.storage;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage<K, V> {

    @Getter
    private Map<K, V> map;

    public MapStorage() {
        map = Maps.newHashMap();
    }

    public void set(K o, V v) {
        if (map.get(o) != null) {
            map.remove(o);
        }
        map.put(o, v);
    }

    public boolean containsKey(K o) {
        return map.containsKey(o);
    }

    public V get(K o) {
        if (!map.containsKey(o)) {
            System.out.println("Does not contain this key");
            return null;
        }
        return map.get(o);
    }

    public Collection<V> getValues() {
        return map.values();
    }

    public Collection<K> getKeys() {
        return map.keySet();
    }
}
