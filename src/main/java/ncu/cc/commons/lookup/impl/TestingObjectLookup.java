package ncu.cc.commons.lookup.impl;

import ncu.cc.commons.lookup.ObjectLookup;
import ncu.cc.commons.lookup.ObjectLookupImpl;

public class TestingObjectLookup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectLookupImpl<String> lookup = new ObjectLookupImpl();
		
		add(lookup, "abc");
		add(lookup, "clear");
		add(lookup, "about");
		add(lookup, "bread");
		add(lookup, "beast");
		add(lookup, "brown");
		add(lookup, "against");
		add(lookup, "break");
		add(lookup, "deep");
		add(lookup, "american");
		add(lookup, "hjk");
		add(lookup, "v78r");
		add(lookup, "english");
		add(lookup, "enable");
		add(lookup, "england");
		add(lookup, "v8ygj");
		add(lookup, "buyfguh");
		add(lookup, "76fjh");
		add(lookup, "jasdfklj");
		add(lookup, "enlarge");
		
		//lookup.printList();
		lookup.sort();
		lookup.printList();
		lookup.findMatched("7", 5);
	}

	private static void add(ObjectLookup<String> lookup, String string) {
		lookup.add(string, string);
	}

}
