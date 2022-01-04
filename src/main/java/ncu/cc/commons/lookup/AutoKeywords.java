package ncu.cc.commons.lookup;

import java.util.List;

public interface AutoKeywords<T> {
    void clear();

    void addObject(String key, T obj);

    void addObjectWithoutSubstringAnalyze(String key, T obj);

    void processing();

    List<PartialMatchedStringObjectStored<T>> getList();

    List<T> findMatchObjects(String str, int maxMatch);

    List<PartialMatchedStringObjectStored<T>> findMatch(String str, int maxMatch);
}
