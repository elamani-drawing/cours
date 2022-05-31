package list;


import dict.Dict;

import java.util.Objects;

public class SAssoc<K,V> implements Dict<K,V> {

    protected NodeAssoc<K,V> head;
    protected int size;

    public SAssoc(){
        head = null;
        size = 0;
    }

    @Override
    public V get(K key) {
        NodeAssoc<K,V> p = head;
        while (p != null){
            if (Objects.equals(p.value.getLeft(), key)) return p.value.getRight();
            else p = p.next;
        }
        return null;
    }


    @Override
    public void put(K key, V value) {
        if (head == null) {
            head = new NodeAssoc<>(key, value);
			size = 1;
        } else {
            // verification cle unique
            if (containsKey(key)) {
                remove(key); // pair immutable
            }
            // insertion en tete de liste
            head = new NodeAssoc<>(key, value, head);
            size ++;
        }
    }

    @Override
    public boolean containsKey(K key) {
        NodeAssoc<K,V> p = head;
        while (p != null){
            if (Objects.equals(p.value.getLeft(), key)) return true;
            p = p.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        NodeAssoc<K,V> p = head;
        while (p != null){
            if (Objects.equals(p.value.getRight(), value)) return true;
            p = p.next;
        }
        return false;
    }

    @Override
    public V remove(K key) {
        NodeAssoc<K,V> p = head;
        if (p == null) return null;
        else {
            // si le premier est celui qu'on enleve
            if (Objects.equals(p.value.getLeft(), key)){
                head = p.next;
                size --;
                return p.value.getRight();
            } else {
                // on teste sur l'element d'apres
                while (p.next != null){
                    if (Objects.equals(p.next.value.getLeft(), key)){
                        // sauvegarde de la valeur que l'on veut supprimer
                        V res = p.next.value.getRight();
                        p.next = p.next.next;
                        size --;
                        return res;
                    }
                    p = p.next;
                }
                return null; // si non trouve
            }
        }
    }

    @Override
    public LList<K> keySet() {
        SList<K> tmp = new SList<>();
        NodeAssoc<K,V> p = head;
        while (p != null){
            tmp.add(p.value.getLeft());
            p = p.next;
        }
        return tmp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        NodeAssoc<K,V> p = head;
        while (p != null){
            sb.append(p.value);
            if (p.next != null) sb.append(",");
            p = p.next;
        }
        sb.append("]");
        return sb.toString();
    }

	@Override
	public void replace(K key, V value) {
		// TODO Auto-generated method stub
		
	}
}