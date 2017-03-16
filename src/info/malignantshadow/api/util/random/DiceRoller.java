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
	 * Get the last roll for the die at the specified index.
	 * 
	 * @param index
	 *            The index of the die.
	 * @return The last roll.
	 */
	public int getRoll(int index) {
		return _dice.get(index).getLastRoll();
	}
	
	/**
	 * Get the list of dice used by this dice roller.
	 * 
	 * @return All dice.
	 */
	public List<Dice> getDice() {
		return _dice;
	}
	
	/**
	 * Add a die to this DiceRoller.
	 * 
	 * @param faces
	 *            The amount of faces on the die.
	 * @return The Dice that was added.
	 */
	public Dice addDice(int faces) {
		Dice d = new Dice(faces);
		_dice.add(d);
		return d;
	}
	
	/**
	 * Add multiple dice to this DiceRoller.
	 * 
	 * @param faces
	 *            The amount of faces on the dice.
	 */
	public void addDice(int faces, int amount) {
		for (int i = 0; i < amount; i++)
			_dice.add(new Dice(faces));
	}
	
	/**
	 * Get the die at the specified index.
	 * 
	 * @param index
	 *            The index of the die.
	 * @return The die.
	 */
	public Dice getDice(int index) {
		return _dice.get(index);
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
	
	/**
	 * Set which face is up on a particular die.
	 * 
	 * @param index
	 *            The index of the Dice
	 * @param roll
	 *            The face that is up
	 * @return The previous face that was up.
	 */
	public int setRoll(int index, int roll) {
		return _dice.get(index).setRoll(roll);
	}
	
}
