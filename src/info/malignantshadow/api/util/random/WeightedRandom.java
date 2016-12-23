package info.malignantshadow.api.util.random;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A class to aid in the process of choosing object/actions by assigning weights to them. This is useful for loot tables or boss encounters.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 * @param <T>
 *            The type of Object this WeightedRandom will contain.
 */
public class WeightedRandom<T> {
	
	private Map<T, Integer> _weights;
	private Random _r;
	private int _total;
	
	/**
	 * Create a new empty WeightedRandom
	 */
	public WeightedRandom() {
		_r = new Random();
		_weights = new HashMap<T, Integer>();
	}
	
	/**
	 * Add an object to the pool.
	 * 
	 * @param element
	 *            The object
	 * @param weight
	 *            Its weight
	 */
	public void add(T element, int weight) {
		_weights.put(element, weight);
		_total += weight;
	}
	
	/**
	 * Get a random element from the pool
	 * 
	 * @return A random object from this pool.
	 */
	public T next() {
		if (_total == 0)
			return null;
		
		int roll = _r.nextInt(_total) + 1;
		int total = 0;
		T last = null;
		for (Map.Entry<T, Integer> e : _weights.entrySet()) {
			last = e.getKey();
			int nextTotal = total + e.getValue();
			if (roll > total && roll <= nextTotal)
				break;
			
			total = nextTotal;
		}
		
		return last;
	}
	
}
