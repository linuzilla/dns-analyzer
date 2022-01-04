package ncu.cc.commons.lookup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectLookupImpl<T> implements ObjectLookup<T> {
	private static final Logger logger = LoggerFactory.getLogger(ObjectLookupImpl.class);
	private List<ObjectStored<T>> list = new ArrayList<>();
	private Boolean sorted = false;
	private PartialMatch<ObjectStored<?>> comparator = new PartialMatch<>();
	
	@Override
	public void add(String key, T obj) {
		synchronized (sorted) {
			logger.trace("add {} to lookup table", key);
			list.add(new ObjectStored<>(key, obj));
			sorted = false;
		}
	}
	
	public void sort() {
		synchronized (sorted) {
			if (! sorted) {
				Collections.sort(list);
				sorted = true;
			}
		}
	}
	
	@Override
	public List<ObjectStored<T>> findMatched(String substr, int maxMatch) {
		ObjectStored<T>	o = new ObjectStored<>(substr, null);
		this.sort();
		int index = Collections.binarySearch(list, o, comparator);
		// Logger.print1(index);
		logger.trace("search for [{}], index={}", substr, index);
		if (index >= 0) {
			int	firstMatch = index;
			for (int i = index - 1; i >= 0; i--) {
				if (comparator.compare(list.get(i), o) != 0) {
					break;
				}
				firstMatch = i;
			}
			List<ObjectStored<T>>	returnList = new ArrayList<ObjectStored<T>>();
			// Logger.print1(firstMatch);
			for (int i = 0, j = firstMatch; i < maxMatch; i++, j++) {
				if (j <= index) {
					returnList.add(list.get(j));
				} else {
					if (comparator.compare(list.get(j), o) == 0) {
						returnList.add(list.get(j));
					} else {
						break;
					}
				}
			}
			// Logger.print1(returnList);
			return returnList;
		}
		return Collections.emptyList();
	}
	
	public void printList() {
		for (int i = 0; i < list.size(); i++) {
			System.out.printf("%d, [%s]%n", i, list.get(i).getKey());
		}
	}
}
