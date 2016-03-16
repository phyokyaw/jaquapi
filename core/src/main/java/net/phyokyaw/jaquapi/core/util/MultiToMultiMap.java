package net.phyokyaw.jaquapi.core.util;

import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class MultiToMultiMap<K, V> {
	private final SetMultimap<K, V> keysToValues = HashMultimap.create();
	private final SetMultimap<V, K> valuesToKeys = HashMultimap.create();

	public Set<V> getValues(K key) {
		return keysToValues.get(key);
	}

	public Set<K> getKeys(V value) {
		return valuesToKeys.get(value);
	}

	public Set<K> getAllKeys() {
		return keysToValues.keySet();
	}

	public Set<V> getAllValues() {
		return valuesToKeys.keySet();
	}

	public boolean put(K key, V value) {
		return keysToValues.put(key, value) && valuesToKeys.put(value, key);
	}

	public boolean putAll(K key, Iterable<? extends V> values) {
		boolean changed = false;
		for (V value : values) {
			changed = put(key, value) || changed;
		}
		return changed;
	}
}
