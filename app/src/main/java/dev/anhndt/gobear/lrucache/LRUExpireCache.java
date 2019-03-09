package dev.anhndt.gobear.lrucache;


import android.util.Log;

public class LRUExpireCache<K, V> {
    public static final int NEVER_EXPIRE = 0;
    private static final String TAG = LRUExpireCache.class.getSimpleName();
    @SuppressWarnings("rawtypes")
    private static LRUExpireCache mInstance = null;
    @SuppressWarnings("rawtypes")
    private static LruCache mCache = null;

    @SuppressWarnings("rawtypes")
    public LRUExpireCache(int size) {
        mCache = new LruCache(size);
    }

    @SuppressWarnings("rawtypes")
    public static LRUExpireCache getInstance(int size) {
        if (mInstance == null) {
            mInstance = new LRUExpireCache(size);
        }

        return mInstance;
    }

    @SuppressWarnings("unchecked")
    public final void put(K key, V value) {
        try {
            LRUCacheObject<Object> cacheValue = new LRUCacheObject<Object>(value, NEVER_EXPIRE);
            mCache.put(key, cacheValue);
        } catch (Exception e) {
            Log.e(TAG, "put", e);
        }
    }

    @SuppressWarnings("unchecked")
    public final void put(K key, V value, long timeInMillis) {
        try {
            long expire = 0;
            if (timeInMillis != 0) {
                expire = System.currentTimeMillis() + timeInMillis;
            }

            LRUCacheObject<Object> cacheValue = new LRUCacheObject<Object>(value, expire);
            mCache.put(key, cacheValue);
        } catch (Exception e) {
            Log.e(TAG, "put", e);
        }
    }

    @SuppressWarnings("unchecked")
    public final V get(K key) {
        V result = null;

        try {
            LRUCacheObject<V> cacheValue = (LRUCacheObject<V>) mCache.get(key);
            if (cacheValue != null) {
                long expire = cacheValue.getExpire();
                if (expire != 0 && expire < System.currentTimeMillis()) {
                    mCache.remove(key);
                } else {
                    result = cacheValue.getValue();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "get", e);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public final void update(K key, V newValue) {
        try {
            LRUCacheObject<V> oldValue = (LRUCacheObject<V>) mCache.get(key);
            if (oldValue != null) {
                oldValue.setValue(newValue);
            }

            mCache.put(key, oldValue);
        } catch (Exception e) {
            Log.e(TAG, "put", e);
        }
    }

    public final void clear() {
        try {
            mCache.evictAll();
        } catch (Exception e) {
            Log.e(TAG, "put", e);
        }
    }
}
