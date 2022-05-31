package list;

public class CList<T> implements LList<T>{

    protected Node<T> head; //tete de liste
    protected Node<T> last; //dernier element
    protected int size;

    public CList() {
        // TODO Auto-generated constructor stub
        head = null;
        last = null;
        size = 0;
    }

    public CList(T val, CList<T> tail) {
        // TODO Auto-generated constructor stub
        head = new Node<T>(val, tail.head);
        last = tail.last;
        size = 1+tail.size;
        last.next = head;//on colle la fin au debut
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(T elem) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(T elem, int index) {
        // TODO Auto-generated method stub
        Node<T> p =this.head;
        Node<T> tmp = new Node<T>(elem);
        if(index==0) {
            //insertion en position 0
            tmp.next = head;
            head = tmp;
            last.next = head; //on attache la fin au debut
        }else{
            int i = 1;
            while(p.next != head && i<index) {
                p=p.next;
                i++;
            }
            //soit on est au dernier element, soit a la position i
            tmp.next = p.next;
            p.next = tmp;
        }
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(T elem) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int indexOf(T elem) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void pop() {
        //sil ya quun seul element ou que cest vide
        if(size<=1) {
            head = null;
            last=null;
            size = 0;
        }else {
            head = head.next;
            last.next = head;
            size = size-1;
        }
    }

    @Override
    public void remove(int index) {
        // TODO Auto-generated method stub
        if (index <size) {
            if (index==0) pop();
            else {
                int cpt = 0;
                Node<T> p =head;
                while(cpt<index-1) {
                    p=p.next;
                    cpt++;
                }
                //on attache lelement i-1 � i+1
                p.next = p.next.next;
            }
            size --;
        }
    }

    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void set(T elem, int index) {
        // TODO Auto-generated method stub

    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return this.size;
    }

    @Override
    public void append(LList<T> leaves) {
        // TODO Auto-generated method stub

    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        if(!isEmpty()) {
            Node<T> p = head;
            do { //do while parcequavec un while on peut pas parcourir la liste sil ya quun element
                sb.append(p.value);
                if(p.next != head) sb.append(",");
                p = p.next;
            }while(p != head);//tant que la tete est different de head
        }
        sb.append(")");
        return sb.toString();

    }

}
