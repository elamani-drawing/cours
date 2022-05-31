package dict;

import list.LList;

public interface Dict<K,V> {
    public V get(K key);
    public void replace(K key, V value);
    public void put(K key, V value);
    public boolean containsKey(K key);
    public boolean containsValue(V value);
    public V remove(K key);
    public LList<K> keySet();
    public int size();
    public String toString();
}