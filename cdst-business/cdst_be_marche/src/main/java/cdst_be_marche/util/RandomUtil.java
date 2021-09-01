package cdst_be_marche.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtil {

	private static int COUNT_RANDOM_TOKEN= 16;
	
	public static String getRandomToken() {
		return RandomStringUtils.randomAlphanumeric(COUNT_RANDOM_TOKEN);
		
	} 
	
	
}
