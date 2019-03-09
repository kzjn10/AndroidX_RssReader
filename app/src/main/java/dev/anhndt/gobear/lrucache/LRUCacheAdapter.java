package dev.anhndt.gobear.lrucache;


import android.util.Log;

import java.util.HashMap;

public class LRUCacheAdapter {
    private static final String TAG = LRUCacheAdapter.class.getSimpleName();

    private static LRUCacheAdapter mInstance = null;
    @SuppressWarnings("rawtypes")
    private static HashMap<String, LRUExpireCache> mCacheMap = null;

    @SuppressWarnings("rawtypes")
    private LRUCacheAdapter() {
        mCacheMap = new HashMap<>();
    }

    public static LRUCacheAdapter getInstance() {
        if (mInstance == null) {
            mInstance = new LRUCacheAdapter();
        }

        return mInstance;
    }

    @SuppressWarnings("rawtypes")
    public LRUExpireCache getCache(String name, int size) {
        LRUExpireCache result = null;

        try {
            if (mCacheMap == null) {
                mCacheMap = new HashMap<>();
            }

            result = mCacheMap.get(name);
            if (result == null) {
                result = LRUExpireCache.getInstance(size);
                mCacheMap.put(name, result);

            }
        } catch (Exception e) {
            Log.e(TAG, "getCache", e);
        }

        return result;
    }

}
