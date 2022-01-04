package ncu.cc.commons.lookup;

public class ObjectStored<T> implements Comparable<ObjectStored<T>> {
	private		String	key;
	private		T		obj;
	
	public ObjectStored(String key, T obj) {
		this.key = key;
		this.obj = obj;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public int compareTo(ObjectStored<T> o) {
		return key.compareToIgnoreCase(o.key);
	}
}
