package com.data.struct.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OpenAddressMapTest {
	private OpenAddressMap map;
	private static final int NEGATIVE_CAPACITY = -1;
	private static final int WRONG_KEY_TO_REMOVE = 23;
	private static final int DEFAULT_TEST_KEY = 22;
	private static final long DEFAULT_TEST_VALUE = 66;
	private static final int EXPECTED_MAP_SIZE = 10;

	@Before
	public void setUp() {
		map = new OpenAddressHashMap();
		for(int i = 0; i < 10; i++) {
			long value = (long) i * 5;
			map.put(i, value);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void initMapWithZeroOrNegativeCapacityTest() {
		map = new OpenAddressHashMap(NEGATIVE_CAPACITY);
	}

	@Test
	public void successPutPairTest() {
		long value = map.put(DEFAULT_TEST_KEY, DEFAULT_TEST_VALUE);
		assertEquals("Value not equal to default test value", value, DEFAULT_TEST_VALUE);
	}

	@Test
	public void successGetPairTest() {
		long value = map.put(DEFAULT_TEST_KEY, DEFAULT_TEST_VALUE);
		map.remove(DEFAULT_TEST_KEY);
	}

	@Test(expected = NoSuchElementException.class)
	public void errorGetPairTest() {
		long value = map.get(56);
	}

	@Test
	public void successRemovePairTest() {
		long value = map.put(DEFAULT_TEST_KEY, DEFAULT_TEST_VALUE);
		boolean result = map.remove(DEFAULT_TEST_KEY);
		assertTrue("Pair must be removed by default test key", result);
	}

	@Test(expected = NoSuchElementException.class)
	public void errorRemovePairTest() {
		long value = map.put(DEFAULT_TEST_KEY, DEFAULT_TEST_VALUE);
		boolean result = map.remove(WRONG_KEY_TO_REMOVE);
	}

	@Test
	public void containsKeyInMapTest() {
		long value = map.put(DEFAULT_TEST_KEY, DEFAULT_TEST_VALUE);
		assertTrue("Map must contain default test key", map.containsKey(DEFAULT_TEST_KEY));
	}

	@Test
	public void containsValueInMapTest() {
		long value = map.put(DEFAULT_TEST_KEY, DEFAULT_TEST_VALUE);
		assertTrue("Default test map must contain default test value", map.containsValue(DEFAULT_TEST_VALUE));
	}

	@Test
	public void isMapEmptyTest() {
		assertFalse("Default test map cannot be empty", map.isEmpty());
	}

	@Test
	public void sizeMapTest() {
		int currentMapSize = map.size();
		assertEquals("Map size must be equal to expected size", currentMapSize, EXPECTED_MAP_SIZE);
	}
}
