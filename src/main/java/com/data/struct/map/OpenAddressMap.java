package com.data.struct.map;

public interface OpenAddressMap {
	long put(int key, long value);

	long get(int key);

	long remove(int key);

	int size();

	boolean isEmpty();

	boolean containsKey(int key);

	boolean containsValue(long value);
}
