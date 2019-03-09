package dev.anhndt.gobear.lrucache;

public class LRUCacheObject<V> {
    private V value = null;
    private long expire = 0;

    public LRUCacheObject(V value) {
        this.value = value;
        this.expire = 0;
    }

    public LRUCacheObject(V value, long expire) {
        this.value = value;
        this.expire = expire;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
