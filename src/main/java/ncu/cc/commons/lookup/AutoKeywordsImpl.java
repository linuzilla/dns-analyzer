package ncu.cc.commons.lookup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoKeywordsImpl<T> implements AutoKeywords<T> {
    private static final Logger logger = LoggerFactory.getLogger(AutoKeywordsImpl.class);
    private static Pattern ENGLISH_PATTERN = Pattern.compile("^([a-zA-Z]+)[^a-zA-Z].*$");

    // private List<ObjectStored<T>>	list = new ArrayList<ObjectStored<T>>();
    private List<PartialMatchedStringObjectStored<T>> list = new ArrayList<PartialMatchedStringObjectStored<T>>();
    private int counter = 0;

    public void addObject(String key, T obj) {
        if (key != null && !"".equals(key)) {
            this.addString(new ObjectStored<T>(key, obj));
            counter++;
        }
    }

    public void addObjectWithoutSubstringAnalyze(String key, T obj) {
        if (key != null && !"".equals(key)) {
            list.add(new PartialMatchedStringObjectStored<T>(key.toLowerCase(), new ObjectStored<T>(key, obj)));
            counter++;
        }
    }

    public void clear() {
        list.clear();
        counter = 0;
    }

    public List<PartialMatchedStringObjectStored<T>> getList() {
        return list;
    }

    public void setList(List<PartialMatchedStringObjectStored<T>> list) {
        this.list = list;
    }

    private void addString(ObjectStored<T> obj) {
        String str = obj.getKey();
        int len = str.length();

        // stringList.add(str);

        for (int i = 0; i < len - 1; i++) {
            String substr = str.substring(i); // , len);

            Matcher m = ENGLISH_PATTERN.matcher(substr);

            if (m.matches()) {
                list.add(new PartialMatchedStringObjectStored<T>(substr.toLowerCase(), obj));
                i += m.group(1).length() - 1;
                // System.out.printf("[%s](%s)[%s]%n", substr, m.group(1), str.substring(i + 1));
            } else if (substr.matches("\\s+.*")) {    // skip leading space
            } else if (substr.matches("\\S+\\s*")) {
                list.add(new PartialMatchedStringObjectStored<T>(
                        substr.toLowerCase(), obj));
                break;
            } else if (substr.matches("：.*")) {
            } else if (substr.matches("）.*")) {
            } else if (substr.matches("[-\\)\\.,:'\"\\[\\]{}].*")) {
            } else {
                list.add(new PartialMatchedStringObjectStored<T>(substr, obj));
            }
        }
    }

    public void processing() {
        Collections.sort(list);

        String prev = "";

        for (int i = 0; i < list.size(); i++) {
            PartialMatchedStringObjectStored<T> pmso = list.get(i);

            if (prev.equals(pmso.getSubstr())) {
                pmso.setSubstr(prev);        // space saving
            }
            prev = pmso.getSubstr();

//			System.out.printf("<[%s][%s]>",
//					partialStringList.get(i).getSubstr(),
//					partialStringList.get(i).getObjectStored().getKey());
//			System.out.printf("[%s]",
//			partialStringList.get(i).getSubstr());
//			if (i % 10 == 0) System.out.println();
        }

        logger.info("{} record(s), {} indexed record(s)", counter, list.size());

    }

    private ObjectMatcher<PartialMatchedStringObjectStored<T>> requestMatcher(final String str) {
        return object -> {
            if (object.getSubstr().startsWith(str.toLowerCase())) {
                return 0;
            }
            return object.getSubstr().compareToIgnoreCase(str);
        };
    }

    public List<T> findMatchObjects(String str, int maxMatch) {
        ObjectMatcher<PartialMatchedStringObjectStored<T>> matcher = requestMatcher(str);

        if (maxMatch <= 0) maxMatch = 99999999;

        int from = ExtendCollections.findFirstMatch(list, matcher);

        if (from >= 0 && from < list.size()) {
            int to = ExtendCollections.findLastMatch(list, matcher);

            List<T> rc = new ArrayList<T>();
            for (int i = 0; i < maxMatch && from <= to; i++, from++) {
                rc.add(list.get(from).getObjectStored().getObj());
            }
            return rc;
        }
        return Collections.emptyList();
    }

    public List<PartialMatchedStringObjectStored<T>> findMatch(String str, int maxMatch) {
        ObjectMatcher<PartialMatchedStringObjectStored<T>> matcher = requestMatcher(str);

        if (maxMatch <= 0) maxMatch = 99999999;

        int from = ExtendCollections.findFirstMatch(list, matcher);

        if (from >= 0 && from < list.size()) {
            int to = ExtendCollections.findLastMatch(list, matcher);

            List<PartialMatchedStringObjectStored<T>> rc = new ArrayList<PartialMatchedStringObjectStored<T>>();
            for (int i = 0; i < maxMatch && from <= to; i++, from++) {
                rc.add(list.get(from));
            }
            return rc;
        }
        return Collections.emptyList();
    }

}
