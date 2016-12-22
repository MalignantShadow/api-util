package info.malignantshadow.api.util.random;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeightedRandom<T> {
	
	private Map<T, Integer> _weights;
	private Random _r;
	private int _total;
	
	public WeightedRandom() {
		_r = new Random();
		_weights = new HashMap<T, Integer>();
	}
	
	public void add(T element, int weight) {
		_weights.put(element, weight);
		_total += weight;
	}
	
	public T next() {
		if (_total == 0)
			return null;
		
		int roll = _r.nextInt(_total) + 1;
		int total = 0;
		for (Map.Entry<T, Integer> e : _weights.entrySet()) {
			int nextTotal = total + e.getValue();
			if (roll > total && roll <= nextTotal)
				return e.getKey();
			
			total = nextTotal;
		}
		
		//shouldn't get here, I'm doing this to make the compiler happy
		return null;
	}
	
}
