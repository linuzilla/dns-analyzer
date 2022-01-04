package ncu.cc.commons.lookup;

public class PartialMatchedStringObjectStored<T> implements Comparable<PartialMatchedStringObjectStored<T>>{
	private String				substr;
	private ObjectStored<T>		objectStored;
	
	public PartialMatchedStringObjectStored() {
		super();
	}

	public PartialMatchedStringObjectStored(String substr,
			ObjectStored<T> objectStored) {
		super();
		this.substr = substr;
		this.objectStored = objectStored;
	}

	public String getSubstr() {
		return substr;
	}
	
	public void setSubstr(String substr) {
		this.substr = substr;
	}

	public ObjectStored<T> getObjectStored() {
		return objectStored;
	}

	public void setObjectStored(ObjectStored<T> objectStored) {
		this.objectStored = objectStored;
	}

	public int compareTo(PartialMatchedStringObjectStored<T> o) {
		int rc = substr.compareToIgnoreCase(o.substr);
		
		if (rc == 0) {
			return objectStored.compareTo(o.objectStored);
		}
		return rc;
	}
}
