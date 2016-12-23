package info.malignantshadow.api.util.random;

import java.util.Random;

/**
 * Represents a singular die/dice with any positive/non-zero number of faces. Dice can be rolled to produce an integer
 * within the range of 1 to {faces}, where {faces} is the number of faces on the dice.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class Dice {
	
	private Random _r;
	private int _faces, _roll;
	
	/**
	 * Create a new Dice with 6 faces.
	 */
	public Dice() {
		this(6);
	}
	
	/**
	 * Create a new Dice with the given number of faces.
	 * 
	 * @param faces
	 *            The number of faces for this Dice.
	 */
	public Dice(int faces) {
		if (faces <= 0)
			throw new IllegalArgumentException("Cannot create a dice with less than one face");
		
		_r = new Random();
		_faces = faces;
	}
	
	/**
	 * Get the faces on this Dice.
	 * 
	 * @return The faces.
	 */
	public int getFaces() {
		return _faces;
	}
	
	/**
	 * Roll the dice and save its value in this Dice.
	 * 
	 * @return The roll.
	 */
	public int roll() {
		return (_roll = _r.nextInt(_faces) + 1);
	}
	
	/**
	 * Get the last roll of this Dice.
	 * 
	 * @return The last roll.
	 */
	public int getLastRoll() {
		return _roll;
	}
	
}
