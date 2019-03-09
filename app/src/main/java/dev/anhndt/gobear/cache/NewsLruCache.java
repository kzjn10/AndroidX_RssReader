package dev.anhndt.gobear.cache;


import android.util.Log;

import java.util.ArrayList;

import dev.anhndt.gobear.entities.NewsEntity;
import dev.anhndt.gobear.global.Global;
import dev.anhndt.gobear.lrucache.LRUExpireCache;

/**
 * Created date: 9/6/2016.
 */

public class NewsLruCache extends LRUExpireCache {
    private static final String TAG = NewsLruCache.class.getSimpleName();

    private static NewsLruCache mInstance = null;
    public static final int EXPIRE_TIME = 5 * 60 * 1000;

    private NewsLruCache(int size) {
        super(size);
    }

    public static NewsLruCache getInstance() {
        if (mInstance == null) {
            mInstance = new NewsLruCache(1000);
        }

        return mInstance;
    }

    public boolean putNewsList(ArrayList<NewsEntity> data) {
        boolean result = false;

        try {
            String key = buildCacheKey(Global.LruCacheKey.NEWS_LIST, "News_List");
            put(key, data, EXPIRE_TIME);
            result = true;
        } catch (Exception e) {
            Log.e(TAG, "putNewsList", e);
        }

        return result;
    }

    public ArrayList<NewsEntity> getNewsList() {
        ArrayList<NewsEntity> result = null;

        try {
            String key = buildCacheKey(Global.LruCacheKey.NEWS_LIST, "News_List");
            result = (ArrayList<NewsEntity>) get(key);
        } catch (Exception e) {
            Log.e(TAG, "getNewsList", e);
        }

        return result;
    }

    private String buildCacheKey(String key, String commentId) {
        String ret = null;
        try {
            ret = String.format("%s_%s", key, commentId);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return ret;
    }
}
