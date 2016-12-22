package info.malignantshadow.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ListUtil {
	
	public static interface SizeRestricter {
		public <T> List<T> restrictSize(List<T> list, int size);
	}
	
	public static class DefaultSizeRestricter implements SizeRestricter {
		
		@Override
		public <T> List<T> restrictSize(List<T> list, int size) {
			if (list == null || list.isEmpty() || size == 0)
				return new ArrayList<T>();
			
			int amount = Math.abs(size);
			List<T> restricted = new ArrayList<T>();
			for (int i = 0; i < amount; i++) {
				int index = size > 0 ? i : list.size() - 1 - i;
				if (index < 0 || index >= list.size())
					break;
				
				restricted.add(list.get(index));
			}
			return restricted;
		}
	}
	
	public static class RandomSizeRestricter implements SizeRestricter {
		
		@Override
		public <T> List<T> restrictSize(List<T> list, int size) {
			List<T> restricted = new ArrayList<T>();
			if (list == null || list.isEmpty() || size <= size)
				return restricted;
			
			List<T> copy = new ArrayList<T>(list);
			Random r = new Random();
			for (int i = 0; i < size; i++) {
				if (copy.isEmpty())
					break;
				
				restricted.add(copy.remove(r.nextInt(copy.size())));
			}
			return restricted;
		}
		
	}
	
	public static <T> boolean contains(List<T> list, Function<T, Boolean> f) {
		return indexOf(list, f) > -1;
	}
	
	public static <T> T find(List<T> list, Function<T, Boolean> f) {
		if (list == null || list.isEmpty() || f == null)
			return null;
		
		for (T t : list)
			if (f.apply(t))
				return t;
			
		return null;
	}
	
	public static <T> int indexOf(List<T> list, Function<T, Boolean> f) {
		if (list == null || list.isEmpty() || f == null)
			return -1;
		
		for (int i = 0; i < list.size(); i++)
			if (f.apply(list.get(i)))
				return i;
			
		return -1;
	}
	
	public static <T> T remove(List<T> list, Function<T, Boolean> f) {
		int index = indexOf(list, f);
		if (index == -1)
			return null;
		
		return list.remove(index);
	}
	
	public static <T> List<T> slice(List<T> list, Function<T, Boolean> f) {
		List<T> slice = new ArrayList<T>();
		if (list == null || list.isEmpty() || f == null)
			return slice;
		
		list.forEach((item) -> {
			if (f.apply(item))
				slice.add(item);
		});
		return slice;
	}
	
	public static <T> int count(List<T> list, Function<T, Boolean> f) {
		return slice(list, f).size();
	}
	
	public static <T> boolean replace(List<T> list, T item, Function<T, Boolean> f) {
		return replace(list, item, 1, f);
	}
	
	public static <T> boolean replace(List<T> list, T item, int amount, Function<T, Boolean> f) {
		if (list == null || list.isEmpty() || amount <= 0 || f == null)
			return false;
		
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (count > amount)
				return true; //all were replaced
				
			T loopItem = list.get(i);
			if (f.apply(loopItem)) {
				list.set(i, item);
				count++;
			}
		}
		//could not replace all
		return false;
	}
	
	public static <T> String join(List<T> list) {
		return join(list, (item) -> "" + item);
	}
	
	public static <T> String join(List<T> list, Function<T, String> describer) {
		return join(list, describer, " ");
	}
	
	public static <T> String join(List<T> list, String delimiter) {
		return join(list, (item) -> "" + item, delimiter);
	}
	
	public static <T> String join(List<T> list, Function<T, String> describer, String delimiter) {
		if (list == null || list.isEmpty())
			return "";
		
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += describer.apply(list.get(i));
			if (i < list.size() - 1)
				result += delimiter;
		}
		
		return result;
	}
	
}
