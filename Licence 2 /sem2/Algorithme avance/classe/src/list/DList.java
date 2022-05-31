package list;

/**
 * Double Linked List
 * @param <T>
 */
public class DList<T> implements LList<T> {

    protected DNode<T> head;
    protected DNode<T> last;
    protected int size;

    /**
     * Create an empty list
     */
    public DList(){
        head = null;
        last = null;
        size = 0;
    }

    /**
     *
     * @param val : val is the first value of the list
     * @param tail : tail is a double linked list
     */
    public DList(T val, DList<T> tail) {
        head = new DNode<T>(val, null, tail.head);
        size = 1 + tail.size;
        last = tail.last;
    }

    /**
     * Add an element to the end of the list
     * @param elem
     */
    @Override
    public void add(T elem) {
        if (isEmpty()){
            this.head = new DNode<T>(elem);
            this.last = head;
            size = 1;
        } else {
            DNode<T> q = new DNode<T>(elem);
            last.next = q;
            q.prev = last;
            last = last.next;
            size +=1;
        }
    }

    @Override
    public void add(T elem, int index) {
        DNode<T> p = this.head;

        if (index == 0) {
            DNode<T> tmp = new DNode<T>(elem, null, head);
            head = tmp;
        } else {
            int i = 1;
            while (p.next != null && i < index){
                p = p.next;
                i++;
            }
            // insert tmp with double linkage
            DNode<T> tmp = new DNode<T>(elem, p, p.next);
        }
    }

    /**
     * Remove all elements of the list
     */
    @Override
    public void clear() {
        this.head = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(T elem) {
        DNode<T> p = head;
        while (p != null){
            if (p.value.equals(elem)) return true;
            else p = p.next;
        }
        return false;
    }


    @Override
    public int indexOf(T elem) {
        int index = 0;
        DNode<T> p = head;
        while (p != null){
            if (p.value.equals(elem)) return index;
            else {
                p = p.next;
                index ++;
            }
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        if (index < size){
            if (index == 0) {
                this.head = this.head.next;
                this.head.prev = null; // double linked list
            }
            else {
                int cpt = 0;
                DNode<T> p = head;
                while (cpt < index - 1) {
                    p = p.next;
                    cpt++;
                }
                DNode<T> q = p.next; // q = the element to remove
                if (q.next != null){ // q is not the last element
                    p.next = q.next;
                    q.next.prev = p;
                } else { // in case q is the last element
                    p.next = null;
                    q.prev = null;
                }
            }
            size --;
        }
    }

    @Override
    public T get(int index) {
        int cpt = 0;
        DNode<T> p = head;
        while (p != null && cpt < index) {
            p = p.next;
            cpt++;
        }
        if (p != null) return p.value;
        else return null;
    }

    @Override
    public void set(T elem, int index){
        DNode<T> p = head;
        int i = 0;
        while (p != null && i < index){
            p = p.next;
            i++;
        }
        if (p != null) p.value = elem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("(");
        DNode<T> p = head;
        while (p != null){
            sb.append(p.value);
            if (p.next != null) sb.append(",");
            p = p.next;
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public void append(LList<T> other) {
        if (other != null){
            DList<T> cast = (DList<T>)other;
            if (isEmpty()) {
                this.head = cast.head;
                this.size = cast.size;
                this.last = cast.last;
            } else {
                this.last.next = cast.head;
                cast.head.prev = this.last;

                this.size += cast.size;
            }
        }
    }

    public DNode<T> getHead(){return head;}
    public DNode<T> getLast(){return last;}
    public int getSize(){return size;}

    public void setHead(DNode<T> h){head = h;}
    public void setLast(DNode<T> l){last = l;}
    public void setSize(int s){size = s;}

    public static void main(String[] args) {
        DList<Integer> L = new DList<>();
        L.add(1); L.add(2); L.add(3);
        L.remove(2);
        L.set(4,1);
        System.out.println(L.get(0));
        System.out.println(L);
    }
}