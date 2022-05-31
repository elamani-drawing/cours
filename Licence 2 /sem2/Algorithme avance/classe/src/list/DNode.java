package list;

public class DNode<T>{

    T value;
    DNode<T> prev;
    DNode<T> next;

    public DNode(T elem) {
        this.value = elem;
        this.prev = null;
        this.next = null;
    }

    /**
     * This function performs the double linkage
     * @param elem : value of the node
     * @param _prev : reference to previous node
     * @param _next : reference to next node
     * @implNote notice that the number of operations is double compared to
     * a single linked list as SList<T>
     */

    public DNode(T elem, DNode<T> _prev, DNode<T> _next) {
        this.value = elem;
        this.prev = _prev;
        if (_prev != null) _prev.next = this;
        this.next = _next;
        if (_next != null) _next.prev = this;
    }

    public T getValue(){ return value;}
    public DNode<T> getPrev(){ return prev;}
    public DNode<T> getNext(){ return next;}

    /* Replace the actual previous element if any */
    public void setPrev(DNode<T> prv){
        if (prv != null) prv.next = this;
        this.prev = prv;
    }

    /* Replace the actual previous element if any */
    public void setNext(DNode<T> nxt){
        if (nxt != null) nxt.prev = this;
        this.next = nxt;
    }

    /* Insert before this and after the actual 'previous' element */
    public void insertPrev(DNode<T> prv){
        if (prv != null){
            DNode<T> mem = getPrev(); // memorize the ancient prev
            prv.prev = mem;
            if (mem != null) mem.next = prv;
            prv.next = this;
            this.prev = prv;
        }
    }

    /* Insert after this and before the actual 'next' element */
    public void insertNext(DNode<T> nxt){
        if (nxt != null){
            DNode<T> mem = getNext(); // memorize the ancient next
            nxt.next = mem;
            if (mem != null) mem.prev = nxt;
            nxt.prev = this;
            this.next = nxt;
        }
    }

}