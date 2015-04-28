package com.ericsson.pc.migrationtool.util;

public class StringUtil {
	
	public static String normalizePrice(String originalPrice) {
		if (originalPrice != null && originalPrice.contains(".") && originalPrice.endsWith("00")) {
			return originalPrice.subSequence(originalPrice.indexOf(".") + 1, originalPrice.length()).subSequence(0, 2).toString();
		} else {
			return originalPrice;
		}
	}

}
