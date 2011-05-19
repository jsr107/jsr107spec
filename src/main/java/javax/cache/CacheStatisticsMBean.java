package javax.cache;

public interface CacheStatisticsMBean {



    public long getEntryCount();

    public long getCacheHits();

    public long getCacheMisses();

    public void clearStatistics();
}
