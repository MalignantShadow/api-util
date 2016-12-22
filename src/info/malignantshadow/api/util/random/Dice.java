package info.malignantshadow.api.util.random;

import java.util.Random;

public class Dice {
	
	private Random _r;
	private int _number, _roll;
	
	public Dice() {
		this(6);
	}
	
	public Dice(int number) {
		_r = new Random();
		_number = number;
	}
	
	public int getMaxNumber() {
		return _number;
	}
	
	public int roll() {
		return (_roll = _r.nextInt(_number) + 1);
	}
	
	public int getLastRoll() {
		return _roll;
	}
	
}
