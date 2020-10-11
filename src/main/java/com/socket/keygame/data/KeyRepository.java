package com.socket.keygame.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KeyRepository {

	private final static Map<String, String> KEYS = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("1", "1");
			put("2", "2");
			put("3", "3");
			put("4", "4");
			put("5", "5");
			put("6", "6");
			put("7", "7");
			put("8", "8");
			put("9", "9");
		}
	};

	private static final KeyRepository INSTANCE = new KeyRepository();

	private List<String> keys = new ArrayList<String>(KEYS.keySet());

	private Random random = new Random();

	private KeyRepository() {

	}

	public static KeyRepository getInstance() {
		return INSTANCE;
	}

	public String getRandomKey() {
		return KEYS.get(keys.get(random.nextInt(keys.size())));
	}

	public String getKey(String key) {
		return KEYS.get(key);
	}

}