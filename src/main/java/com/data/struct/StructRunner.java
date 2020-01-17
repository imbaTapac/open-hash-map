package com.data.struct;

import com.data.struct.map.OpenAddressHashMap;
import com.data.struct.map.OpenAddressMap;

public class StructRunner {
	public static void main(String[] args) {
		OpenAddressMap map = new OpenAddressHashMap(20);
		System.out.println(map.containsValue(1L));
		System.out.println(map.containsKey(1));
		for(int i = 2; i < 100000; i = i * 2) {
			System.out.println("Pushing key " + i);
			long value = i * 5;
			System.out.println("Pushing value " + value);
			map.put(i, value);
			System.out.println("Pair is " + map.toString());
		}
		map.remove(8);
		System.out.println(map.size());
		System.out.println(map.toString());
		map.put(8, 770L);
		map.put(8, 22L);
		System.out.println("Current map size " + map.size());
		System.out.println(map.get(32));


		System.out.println(map.toString());
		System.out.println(map.size());
		System.out.println(map.containsValue(770));
	}
}
