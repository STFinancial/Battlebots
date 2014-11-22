package team017.framework;

public class Utility {
	private final static int m = 2147483647;
	private final static int a = 48271;
	private static int randNum;

	/**
	 * Seeds the RNG. Should be called once from BasePlayer.
	 * @param seed1 - The number of bytes executed.
	 * @param seed2 - The robot ID number.
	 */
	public static void seed(int seed1, int seed2) {
		randNum = (((seed1 * seed2) * a) % m) >>> 1;
	}

	/**
	 * Returns the next random number between 0 (inclusive) and n (exclusive).
	 * @param n - Exclusive upper bound on next random
	 * **/
	public static int nextInt(int n) {
		randNum = ((a * randNum) % m) >>> 1;
		return randNum % n;
	}

}
