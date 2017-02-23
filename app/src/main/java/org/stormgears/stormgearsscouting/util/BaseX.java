package org.stormgears.stormgearsscouting.util;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andrew on 1/24/17.
 */
public class BaseX {
	private String alphabet;
	private HashMap<Character, Integer> alphabetMap;
	private int base;
	private char leader;

	public BaseX() {
		this("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^&*()_+`-=[]\\{}|;':\",./<>?");
	}

	public BaseX(String alphabet) {
		this.alphabet = alphabet;
		alphabetMap = new HashMap<>();
		base = alphabet.length();
		leader = alphabet.charAt(0);
		for (int i = 0; i < alphabet.length(); i++) {
			char x = alphabet.charAt(i);

			if (alphabetMap.containsKey(x)) {
				throw new InvalidParameterException("Ambiguous alphabet " + alphabet);
			}
			alphabetMap.put(x, i);
		}
	}

	public String encode(byte[] byteSource) {
		// convert to unsigned bytes
		int[] source = new int[byteSource.length];
		for (int i = 0; i < byteSource.length; i++) {
			source[i] = byteSource[i] & 0xFF;
		}

		if (source.length == 0) {
			return "";
		}

		ArrayList<Integer> digits = new ArrayList<>();
		digits.add(0);
		for (int i = 0; i < source.length; i++) {
			int carry = source[i];
			for (int j = 0; j < digits.size(); j++) {
				carry += digits.get(j) << 8;
				digits.set(j, carry % base);
				carry = (carry / base);
			}

			while (carry > 0) {
				digits.add(carry % base);
				carry = carry / base;
			}
		}

		StringBuilder string = new StringBuilder();
		for (int k = 0; source[k] == 0 && k < source.length - 1; k++) {
			string.append(alphabet.charAt(0));
		}
		for (int q = digits.size() - 1; q >= 0; q--) {
			string.append(alphabet.charAt(digits.get(q)));
		}

		return string.toString();
	}
}
