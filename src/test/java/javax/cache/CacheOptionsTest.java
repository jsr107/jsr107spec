package javax.cache;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class CacheOptionsTest {
  private static final String DEFAULT_VALUE = "v1";

  @Test
  public void testCacheV1() {
    String value = DEFAULT_VALUE;
    List<String> listKey = getListKey(value);
    Collection<String> collectionKey = listKey;

    CacheOptions.CacheV1<List, String> cacheV1 =
        new CacheOptions.CacheV1Impl<List, String>();
    cacheV1.put(listKey, value);
    assertEquals(value, cacheV1.get(listKey));
  }

  private List<String> getListKey(String key) {
    List<String> listKey = new ArrayList<String>();
    listKey.add(key);
    return listKey;
  }
}
