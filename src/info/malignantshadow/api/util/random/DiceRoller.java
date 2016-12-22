package info.malignantshadow.api.util.random;

import java.util.ArrayList;
import java.util.List;

public class DiceRoller {
	
	private List<Dice> _dice;
	private int _roll;
	
	public DiceRoller() {
		_dice = new ArrayList<Dice>();
	}
	
	public void addDice(Dice dice) {
		_dice.add(dice);
	}
	
	public int getAmount() {
		return _dice.size();
	}
	
	public int getLastRoll() {
		return _roll;
	}
	
	public Dice getDice(int index) {
		return _dice.get(index);
	}
	
	public int roll() {
		int counter = 0;
		for (Dice d : _dice)
			counter += d.roll();
		
		return (_roll = counter);
	}
	
}
