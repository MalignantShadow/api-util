package info.malignantshadow.api.util.random;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents multiple dice that can be rolled at once.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class DiceRoller {
	
	private List<Dice> _dice;
	private int _roll;
	
	public DiceRoller() {
		_dice = new ArrayList<Dice>();
	}
	
	/**
	 * Get the amount of dice used by this dice roller.
	 * 
	 * @return
	 */
	public int getAmount() {
		return _dice.size();
	}
	
	/**
	 * Get the last roll.
	 * 
	 * @return The last roll.
	 */
	public int getLastRoll() {
		return _roll;
	}
	
	/**
	 * Get the list of dice used by this dice roller.
	 * 
	 * @return
	 */
	public List<Dice> getDice() {
		return _dice;
	}
	
	/**
	 * Roll all dice and store the result.
	 * 
	 * @return
	 */
	public int roll() {
		int counter = 0;
		for (Dice d : _dice)
			counter += d.roll();
		
		return (_roll = counter);
	}
	
}
