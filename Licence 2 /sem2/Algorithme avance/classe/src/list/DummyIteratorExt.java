package list;

import java.util.Iterator;

public class DummyIteratorExt<T> implements Iterator<T> {

    private Node<T> current;

    public DummyIteratorExt(ISList<T> internal) {
        this.current = internal.head;
    }

    @Override
    public boolean hasNext(){
        return current != null;
    }

    @Override
    public T next() {
      if (current != null){
            T tmp = current.value;
            current = current.next;
            return tmp;
      } else return null;
    }
}
