package com.data.struct.map;

import static java.util.Objects.nonNull;

import java.util.NoSuchElementException;
import java.util.Objects;

public class OpenAddressHashMap implements OpenAddressMap {
	private static final int DEFAULT_CAPACITY = 16;
	private static final int MAXIMUM_CAPACITY = 1 << 30;
	private int initialCapacity;
	private static final float DEFAULT_LOAD_FACTOR = 0.75F;
	private int size = 0;
	private Pair[] table;

	public OpenAddressHashMap() {
		this.table = new Pair[DEFAULT_CAPACITY];
		initialCapacity = DEFAULT_CAPACITY;
	}

	public OpenAddressHashMap(int capacity) {
		if(capacity < 0) {
			throw new IllegalArgumentException("Initial capacity cannot be zero or negative");
		}
		if(capacity > MAXIMUM_CAPACITY) {
			capacity = MAXIMUM_CAPACITY;
		}
		initialCapacity = capacity;
		this.table = new Pair[capacity];
	}

	private int hashCode(int key) {
		return Objects.hashCode(key) ^ 31;
	}

	@Override
	public long put(int key, long value) {
		int hashCode = hashCode(key);
		if(checkSize() || hashCode >= initialCapacity) {
			table = resize();
		}
		if(table[hashCode] == null) {
			table[hashCode] = new Pair(key, value);
			size++;
			return value;
		} else if(table[hashCode].getKey() == hashCode) {
			table[hashCode].setValue(value);
			return value;
		}
		for(int i = hashCode + 1; i != hashCode; i = (i + 1) % table.length) {
			if(table[i] != null && table[i].getKey() == key) {
				table[i] = new Pair(key, value);
				return value;
			}
		}
		return 0;
	}

	@Override
	public long get(int key) {
		int hashCode = hashCode(key);
		if(table.length >= hashCode) {
			if(table[hashCode] == null) {
				throw new NoSuchElementException("No element with such key " + key);
			}
			if(table[hashCode].getKey() == key) {
				return table[hashCode].getValue();
			}
			for(int i = hashCode + 1; i != hashCode; i = (i + 1) % table.length) {
				if(table[i].getValue() == key) {
					return table[hashCode].getValue();
				}
			}
		} else {
			throw new NoSuchElementException("No element with such key " + key);
		}
		return 0;
	}

	@Override
	public boolean remove(int key) {
		int hashCode = hashCode(key);
		if(table.length >= hashCode) {
			if(table[hashCode] == null) {
				throw new NoSuchElementException("No element with such key " + key);
			}
			if(table[hashCode].getKey() == key) {
				table[hashCode] = null;
				size--;
				return true;
			}
			for(int i = hashCode + 1; i != hashCode; i = (i + 1) % table.length) {
				if(table[i].getValue() == key) {
					table[i] = null;
					size--;
					return true;
				}
			}
		} else {
			throw new NoSuchElementException("No element with such key " + key);
		}
		return false;
	}

	@Override
	public boolean containsKey(int key) {
		return get(key) != 0;
	}

	@Override
	public boolean containsValue(long value) {
		if(size > 0) {
			for(Pair pair : table) {
				if(pair != null && pair.getValue() == value) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	private boolean checkSize() {
		return size + 1 >= initialCapacity * DEFAULT_LOAD_FACTOR;
	}

	private Pair[] resize() {
		initialCapacity = initialCapacity * initialCapacity;
		if(initialCapacity < 0 || initialCapacity > MAXIMUM_CAPACITY) {
			initialCapacity = MAXIMUM_CAPACITY;
		}
		Pair[] entries = getAllPairs();
		table = new Pair[initialCapacity];
		size = 0;
		for(Pair entry : entries) {
			int key = entry.getKey();
			long value = entry.getValue();
			put(key, value);
		}
		return table;
	}

	private Pair[] getAllPairs() {
		Pair[] entries = new Pair[size];
		int j = 0;
		for(Pair pair : table) {
			if(pair != null) {
				entries[j] = pair;
				j++;
			}
		}
		return entries;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Pair pair : this.table) {
			if(nonNull(pair)) {
				sb
						.append(pair)
						.append("\n");
			}
		}
		return "OpenHashMap{" +
				"\n" + sb.toString() +
				'}';
	}
}

class Pair {
	private final int key;
	private long value;

	Pair(int key, long value) {
		this.key = key;
		this.value = value;
	}

	int getKey() {
		return key;
	}

	long getValue() {
		return value;
	}

	void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Pair{" +
				"key=" + key +
				", value=" + value +
				'}';
	}
}

