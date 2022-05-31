package dict;

/***
 * Node specific for the list of association
 * @param <K>
 * @param <V>
 */

 public class NodeAssoc<K,V> {
    Pair<K,V> value;
    NodeAssoc<K,V> next;

    public NodeAssoc(K key , V value){
        this.value = new Pair<K,V>(key, value);
        this.next = null;
    }

    public NodeAssoc(K key , V value, NodeAssoc<K,V> _next){
        this.value = new Pair<K,V>(key, value);
        this.next = _next;
    }
}