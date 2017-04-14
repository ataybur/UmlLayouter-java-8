package com.ataybur.umlLayouter.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleCache {
    public static SimpleCache INSTANCE = new SimpleCache();
    final int MAX_ENTRIES = 100;
    Map<String, Object> cache;

    private SimpleCache() {
	cache = new LinkedHashMap<String, Object>(MAX_ENTRIES + 1, .75F, true) {
	    private static final long serialVersionUID = 1L;

	    // This method is called just after a new entry has been added
	    public boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
		return size() > MAX_ENTRIES;
	    }
	};
	cache = (Map<String, Object>) Collections.synchronizedMap(cache);
    }

    public void put(String key, Object value) {
	cache.put(key, value);
    }

    public Object get(String key) {
	return cache.get(key);
    }

    public String getString(String key) {
	Object value = cache.get(key);
	String result;
	if (value == null) {
	    result = null;
	} else {
	    result = value.toString();
	}
	return result;
    }
}
