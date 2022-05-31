package list;

import fonc.Filter;
import fonc.Mapper;
import fonc.Reducer;

//import java.nio.file.DirectoryStream;
import java.util.Iterator;
import java.util.Objects;

public class ISList<T> extends SList<T> implements IList<T> {

    /**
     * Create an empty list
     */
    public ISList(){
        super();
    }

    public ISList(T value) {
        super(value, null);
    }

    public ISList(T value, ISList<T> tail) {
        super(value, tail);
    }


    /**
     * Add an element to the end of the list
     * @param elem
     */
    @Override
    public void add(T elem) {
        if (isEmpty()){
            this.head = new Node<T>(elem);
            this.last = head;
            size = 1;
        } else {
            last.next = new Node<T>(elem);
            last = last.next;
            size +=1;
        }
    }

    @Override
    public void add(T elem, int index) {
        Node<T> p = this.head;
        Node<T> tmp = new Node<T>(elem);
        if (index == 0) {
            tmp.next = head;
            head = tmp;
        } else {
            int i = 1;
            while (p.next != null && i < index){
                p = p.next;
                i++;
            }
            tmp.next = p.next;
            p.next = tmp;
        }
    }

    /**
     * Remove all elements of the list
     */
    @Override
    public void clear() {
        head = null;
        last = null;
        size = 0;
    }

    /**
     * Note: rewriting contains with iterator
     * @param elem
     * @return
     */
    @Override
    public boolean contains(T elem) {
        Iterator<T> it = iterator();            // reset iterator
        while(it.hasNext()){
            if (Objects.equals(elem, it.next())) return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    // Note: rewriting indexOf with iterator
    @Override
    public int indexOf(T elem) {
        Iterator<T> it = iterator();            // reset iterator
        int index = -1;
        while (it.hasNext()){
            index ++;
            if (Objects.equals(it.next(), elem)) return index;
        }
        return index;
    }


    @Override
    public void remove(int index) {
        if (index < size){
            if (index == 0) this.head = this.head.next;
            else {
                int cpt = 0;
                Node<T> p = head;
                while (cpt < index - 1) {
                    p = p.next;
                    cpt++;
                }
                p.next = p.next.next;
            }
            size --;
        }
    }

    // Note: rewriting get with iterator
    @Override
    public T get(int index) {
        Iterator<T> it = iterator();            // reset iterator
        int cpt = 0;
        T res = null;
        while (it.hasNext() && cpt <= index){
            res = it.next();
            cpt ++;
        }
        if (cpt < index) return null;
        else return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("(");
        Iterator<T> it = iterator();
        while (it.hasNext()){
            sb.append(it.next());
            if (it.hasNext()) sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    public <O> ISList<O> map(Mapper<T,O> mapper){
        Iterator<T> it = iterator();
        ISList<O> res = new ISList<O>();
        while(it.hasNext()){
            T val = it.next();
            res.add(mapper.mapTo(val));
        }
        return res;
    }

    public ISList<T> filter(Filter<T> filter){
        Iterator<T> it = iterator();
        ISList<T> res = new ISList<T>();
        while(it.hasNext()){
            T val = it.next();
            if (filter.filter(val)) res.add(val);
        }
        return res;
    }

    // Iterative version of reducer
    public <K> K reduce(Reducer<T,K> r){
        K res = r.getBase();
        Iterator<T> it = iterator();
        while (it.hasNext()){
            res = r.reduce(it.next(), res);
        }
        return res;
    }

    public <K> K reduceRec(Reducer<T, K> r) {
        return reduceNode(r, head) ;
    }
    private <K> K reduceNode(Reducer<T, K> r, Node<T> L) {
        if (L == null) return r.getBase();
        else return r.reduce(L.value , reduceNode(r, L.next));
    }

    @Override
    public Iterator<T> iterator() {
        return new DummyIteratorExt<>(this);
    }
}
