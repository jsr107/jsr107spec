/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.Cache.Entry;
import javax.cache.Configuration.Duration;
import java.io.Serializable;

/**
 * Defines functions to determine when cache entries will expire based on 
 * creation, access and modification operations.
 * <p/>
 * Each of the functions return a {@link Duration} that of which specifies the
 * amount of time that must pass before a cache entry is considered expired.
 * This {@link Duration} is often called a "time-to-live", commonly abbreviated 
 * to simply "TTL".
 *
 * todo rename TTL to Expiry
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * 
 * @author Brian Oliver
 * @author Greg Luck
 */
public interface ExpiryPolicy<K, V> {
       
    /**
     * Gets the time-to-live before the newly Cache.Entry is considered expired.
     * <p/>
     * This method is called by the caching implementation after a Cache.Entry is created, but before
     * the said entry is added to a cache, to determine the {@link Duration} before the
     * said entry expires.  If a {@link Duration#ZERO} is returned the Cache.Entry is 
     * considered to be already expired and will not be added to the Cache.
     * <p/>
     * Should an exception occur while determining the Duration, an implementation
     * specific default Duration will be used.
     *
     * @param entry the cache entry that was created
     * @return the duration until the entry expires
     */
    Duration getExpiryForCreatedEntry(Entry<? extends K, ? extends V> entry);

    /**
     * Gets the time-to-live before the accessed Cache.Entry is considered expired.
     * <p/>
     * This method is called by the caching implementation after a Cache.Entry is accessed
     * to determine the {@link Duration} before the said entry expires in the future.  If a
     * {@link Duration#ZERO} is returned the Cache.Entry will be considered 
     * expired for future access.  Returning <code>null</code> will result in
     * no change to the previously understood expiry {@link Duration}.
     * <p/>
     * Should an exception occur while determining the Duration, an implementation
     * specific default Duration will be used.
     *
     * @param entry    the cache entry that was accessed
     *
     * @return the duration until the entry expires
     */
    Duration getExpiryForAccessedEntry(Entry<? extends K, ? extends V> entry);
        
    /**
     * Gets the time-to-live before the modified Cache.Entry is considered expired.
     * <p/>
     * This method is called by the caching implementation after a Cache.Entry is modified to
     * determine the {@link Duration} before the updated entry expires.  If a
     * {@link Duration#ZERO} is returned the Cache.Entry is considered already
     * expired.  Returning <code>null</code> will result in no change to the
     * previously understood expiry {@link Duration}.
     * <p/>
     * Should an exception occur while determining the Duration, an implementation
     * specific default Duration will be used.
     *
     * @param entry    the cache entry that was modified
     * @return the duration until the entry expires
     */
    Duration getExpiryForModifiedEntry(Entry<? extends K, ? extends V> entry);

    /**
     * An {@link ExpiryPolicy} that defines the expiry {@link Duration}
     * of a Cache Entry based on when it was created. An update does not reset
     * the expiry time.
     *
     * @param <K> the type of cache keys
     * @param <V> the type of cache values
     */
    public static final class Created<K, V> implements ExpiryPolicy<K, V>, Serializable {

        /**
         * The serialVersionUID required for {@link Serializable}.
         */
        public static final long serialVersionUID = 201305291023L;

        /**
         * The {@link Duration} a Cache Entry should be available before it expires.
         */
        private Duration expiryDuration;

        /**
         * Constructs an {@link Created} {@link ExpiryPolicy}.
         *
         * @param expiryDuration the {@link Duration} a Cache Entry should exist be
         *                       before it expires after being modified
         */
        public Created(Duration expiryDuration) {
            this.expiryDuration = expiryDuration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForCreatedEntry(Entry<? extends K, ? extends V> entry) {
            //for newly created entries we use the specified expiry duration
            return expiryDuration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForAccessedEntry(Entry<? extends K, ? extends V> entry) {
            //accessing a cache entry has no affect on the current expiry duration
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForModifiedEntry(Entry<? extends K, ? extends V> entry) {
            //accessing a cache entry has no affect on the current expiry duration
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((expiryDuration == null) ? 0 : expiryDuration.hashCode());
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Created)) {
                return false;
            }
            Created<?, ?> other = (Created<?, ?>) obj;
            if (expiryDuration == null) {
                if (other.expiryDuration != null) {
                    return false;
                }
            } else if (!expiryDuration.equals(other.expiryDuration)) {
                return false;
            }
            return true;
        }
    }

    /**
     * An {@link ExpiryPolicy} that defines the expiry {@link Duration}
     * of a Cache Entry based on the last time it was accessed. Accessed
     * does not include a cache update.
     *
     * @param <K> the type of cache keys
     * @param <V> the type of cache values
     */
    public static final class Accessed<K, V> implements ExpiryPolicy<K, V>, Serializable {

        /**
         * The serialVersionUID required for {@link Serializable}.
         */
        public static final long serialVersionUID = 201305101601L;

        /**
         * The {@link Duration} a Cache Entry should be available before it expires.
         */
        private Duration expiryDuration;

        /**
         * Constructs an {@link Accessed} {@link ExpiryPolicy}.
         * 
         * @param expiryDuration the {@link Duration} a Cache Entry should exist be
         *                       before it expires after being accessed
         */
        public Accessed(Duration expiryDuration) {
            this.expiryDuration = expiryDuration;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForCreatedEntry(Entry<? extends K, ? extends V> entry) {
            //for newly created entries we use the specified expiry duration
            return expiryDuration;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForAccessedEntry(Entry<? extends K, ? extends V> entry) {
            //when a cache entry is accessed, we return the specified expiry duration, 
            //ignoring the current expiry duration
            return expiryDuration;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForModifiedEntry(Entry<? extends K, ? extends V> entry) {
            //modifying a cache entry has no affect on the current expiry duration
            return null;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((expiryDuration == null) ? 0 : expiryDuration.hashCode());
            return result;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Accessed)) {
                return false;
            }
            Accessed<?, ?> other = (Accessed<?, ?>) obj;
            if (expiryDuration == null) {
                if (other.expiryDuration != null) {
                    return false;
                }
            } else if (!expiryDuration.equals(other.expiryDuration)) {
                return false;
            }
            return true;
        }
    }

    /**
     * An {@link ExpiryPolicy} that defines the expiry {@link Duration}
     * of a Cache Entry based on the last time it was modified. Modified
     * includes created and updated.
     * 
     * @param <K> the type of cache keys
     * @param <V> the type of cache values
     */
    public static final class Modified<K, V> implements ExpiryPolicy<K, V>, Serializable {

        /**
         * The serialVersionUID required for {@link Serializable}.
         */
        public static final long serialVersionUID = 201305101602L;

        /**
         * The {@link Duration} a Cache Entry should be available before it expires.
         */
        private Duration expiryDuration;

        /**
         * Constructs an {@link Modified} {@link ExpiryPolicy}.
         * 
         * @param expiryDuration the {@link Duration} a Cache Entry should exist be
         *                       before it expires after being modified
         */
        public Modified(Duration expiryDuration) {
            this.expiryDuration = expiryDuration;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForCreatedEntry(Entry<? extends K, ? extends V> entry) {
            //for newly created entries we use the specified expiry duration
            return expiryDuration;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForAccessedEntry(Entry<? extends K, ? extends V> entry) {
            //accessing a cache entry has no affect on the current expiry duration
            return null;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForModifiedEntry(Entry<? extends K, ? extends V> entry) {
            //when a cache entry is modified, we return the specified expiry duration, 
            //ignoring the current expiry duration
            return expiryDuration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((expiryDuration == null) ? 0 : expiryDuration.hashCode());
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Modified)) {
                return false;
            }
            Modified<?, ?> other = (Modified<?, ?>) obj;
            if (expiryDuration == null) {
                if (other.expiryDuration != null) {
                    return false;
                }
            } else if (!expiryDuration.equals(other.expiryDuration)) {
                return false;
            }
            return true;
        }
    }

    /**
     * An {@link ExpiryPolicy} that defines the expiry {@link Duration}
     * of a Cache Entry based on when it was last touched. A touch includes
     * creation, update or access.
     *
     * @param <K> the type of cache keys
     * @param <V> the type of cache values
     */
    public static final class Touched<K, V> implements ExpiryPolicy<K, V>, Serializable {

        /**
         * The serialVersionUID required for {@link Serializable}.
         */
        public static final long serialVersionUID = 201305291023L;

        /**
         * The {@link Duration} a Cache Entry should be available before it expires.
         */
        private Duration expiryDuration;

        /**
         * Constructs an {@link Touched} {@link ExpiryPolicy}.
         *
         * @param expiryDuration the {@link Duration} a Cache Entry should exist be
         *                       before it expires after being modified
         */
        public Touched(Duration expiryDuration) {
            this.expiryDuration = expiryDuration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForCreatedEntry(Entry<? extends K, ? extends V> entry) {
            //for newly created entries we use the specified expiry duration.
            return expiryDuration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForAccessedEntry(Entry<? extends K, ? extends V> entry) {
            //accessing a cache entry resets the duration.
            return expiryDuration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForModifiedEntry(Entry<? extends K, ? extends V> entry) {
            //accessing a cache entry resets the duration.
            return expiryDuration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((expiryDuration == null) ? 0 : expiryDuration.hashCode());
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Touched)) {
                return false;
            }
            Touched<?, ?> other = (Touched<?, ?>) obj;
            if (expiryDuration == null) {
                if (other.expiryDuration != null) {
                    return false;
                }
            } else if (!expiryDuration.equals(other.expiryDuration)) {
                return false;
            }
            return true;
        }
    }

    /**
     * The eternal {@link ExpiryPolicy} specifies that Cache Entries
     * won't expire.  This however doesn't mean they won't be expired if an
     * underlying implementation needs to free-up resources where by it may 
     * choose to expire entries that are not due to expire.
     */
    public static final class Eternal<K, V> implements ExpiryPolicy<K, V>, Serializable {

        /**
         * The serialVersionUID required for {@link Serializable}.
         */
        public static final long serialVersionUID = 201305101603L;

        /**
         * Obtains a {@link Factory} for a Eternal {@link ExpiryPolicy}.
         *
         * @return a {@link Factory} for a Eternal {@link ExpiryPolicy}.
         */
        public static <K, V> Factory<ExpiryPolicy<? super K, ? super V>> getFactory() {
            return new Factories.SingletonFactory<ExpiryPolicy<? super K, ? super V>>(new Eternal<K, V>());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForCreatedEntry(Entry<? extends K, ? extends V> entry) {
            return Duration.ETERNAL;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForAccessedEntry(Entry<? extends K, ? extends V> entry) {
            return null;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration getExpiryForModifiedEntry(Entry<? extends K, ? extends V> entry) {
            return null;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Eternal.class.hashCode();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object other) {
            return other instanceof Eternal;
        }
    }
}
