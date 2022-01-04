package ncu.cc.commons.lookup;

import java.util.*;

public class ExtendCollections<T> {
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		Object[] a = list.toArray();
		Arrays.sort(a);
		ListIterator<T> i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set((T) a[j]);
		}
	}

	
	public static <T> List<T> findMatchedByIteration(List<T> list, ObjectMatcher<T> matcher) {
		List<T>	returnList = new ArrayList<T>();
		for (T obj: list) {
			if (matcher.compareWith(obj) == 0) {
				returnList.add(obj);
			}
		}
		return returnList;
	}
	
	public static <T> List<T> findMatched(List<T> list, ObjectMatcher<T> matcher) {
		int		begin = findFirstMatch(list, matcher);

		// System.out.printf ("Begin=%d%n", begin);
		
		if (begin >= 0) {
			int	end = findLastMatch(list, matcher);
			
			if (begin == 0 && end == list.size() - 1) {
				return list;
			} else {
				List<T>	returnList = new ArrayList<T>();
			
				for (int i = begin; i <= end; i++) {
					returnList.add(list.get(i));
				}
				return returnList;
			}
		}
		return Collections.emptyList();
	}
	
	public static <T> int findFirstMatch(List<T> list, ObjectMatcher<T> matcher) {
		int		f = 0, t = list.size() - 1;
		int		m;
		int		result;
		
		if (matcher.compareWith(list.get(t)) < 0) return -1;	// not in range
		if ((result = matcher.compareWith(list.get(f))) > 0) return -1;	// not in range
		if (result == 0) return f;
			
		do {
			m = (f + t) / 2;
			result = matcher.compareWith(list.get(m));
			// System.out.printf("First Try [%d], result = %d (%d,%d) - ", m, result, f, t);
			
			if (result == 0) { // Consider match
				t = m;
			} else if (result > 0) {
				t = m;
			} else if (result < 0) {
				f = m;
			}
			// System.out.printf("(%d,%d,%d)%n", f, m, t);
		} while (f + 1 < t);
		
		return t;
	}
	
	public static <T> int findLastMatch(List<T> list, ObjectMatcher<T> matcher) {
		int		f = 0, t = list.size() - 1;
		int		m;
		int		result;
		
		if (matcher.compareWith(list.get(f)) > 0) return -1;	// not in range
		if ((result = matcher.compareWith(list.get(t))) < 0) return -1;	// not in range
		if (result == 0) return t;
			
		do {
			m = (f + t) / 2;
			result = matcher.compareWith(list.get(m));
			// System.out.printf("Last Try [%d], result = %d (%d,%d) - ", m, result, f, t);
			
			if (result == 0) { // Consider match
				f = m;
			} else if (result > 0) {
				t = m;
			} else if (result < 0) {
				f = m;
			}
			// System.out.printf("(%d,%d,%d)%n", f, m, t);
		} while (f + 1 < t);
		
		return f;
	}
	
	public static void main (String args[]) {
		List<Integer>	list = new ArrayList<Integer>();
		
		for (int i = 0; i < 100; i++) {
			list.add(i);
		}
		
		List<Integer> l = findMatched(list, new MyObjectMatcher(-45,106));
	
		System.out.println ("Size=" + l.size());
		
		for (int i = 0; i < l.size(); i++) {
			System.out.printf ("[%d]", l.get(i));
		}
		System.out.println();
	}
}

class MyObjectMatcher implements ObjectMatcher<Integer> {
	private int		from;
	private int		to;
	
	MyObjectMatcher(int from, int to) {
		this.from = from;
		this.to = to;
	}

//	public int compareWith(Integer object) {
//		return 0;
//	}

	public int compareWith(Integer object) {
//		System.out.printf("Compare with %d%n", object);
		if (object < from) return -1;
		if (object > to) return +1;
		return 0;
	}
}
