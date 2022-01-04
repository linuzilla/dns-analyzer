package ncu.cc.commons.lookup;

import java.util.List;

public interface ObjectLookup<T> {
	void add(String key, T obj);
    List<ObjectStored<T>> findMatched(String substr, int maxMatch);
}