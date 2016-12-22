package info.malignantshadow.api.util;

public class GameUtil {
	
	public static final int pointToIndex(int x, int y, int width) {
		return (y * width) + x;
	}
	
	public static final int[] indexToPoint(int index, int width) {
		return new int[] { index % width, index / width };
	}
	
}
