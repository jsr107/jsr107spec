package net.sf.jsr107cache;

public interface CacheStatistics {
    public static final int STATISTICS_ACCURACY_NONE = 0;
    public static final int STATISTICS_ACCURACY_BEST_EFFORT = 1;
    public static final int STATISTICS_ACCURACY_GUARANTEED = 2;

    public int getStatisticsAccuracy();

    // REVIEW adam@bea.com 21-Jun-04 - How does this differ from Cache.size()?
    // REVIEW brian@quiotix.com - Implementation may well delegate to Cache.size, 
    //        but it seemed like it would be a glaring omission to leave objectCount
    //        out of CacheStatistics
    public int getObjectCount();

    public int getCacheHits();

    public int getCacheMisses();

    public void clearStatistics();
}
