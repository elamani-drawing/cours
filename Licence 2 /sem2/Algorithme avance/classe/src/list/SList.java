package list;

import dict.Pair;
import java.util.Objects;

public class SList<T> implements LList<T> {

    protected Node<T> head; //tete de liste
    protected Node<T> last; //dernier element
    protected int size;

    /**
     * Create an empty list
     */
    public SList(){
        head = null;
        last = null;
        size = 0;
    }

    public SList(T e){
        head = new Node<>(e);
        last = head;
        size = 1;
    }
/*
    public SList(T head, SList<T> tail) {
        this .head = new Node<T>(head, tail.head) ;
        this.size = 1 + tail.size;
        this.last = tail.last;
    }
*/
    public SList(T head, SList<T> tail) {
        if (tail == null) {
            this.head = new Node<T>(head);
            last = this.head;
            size = 1;
        } else {
            this.head = new Node<T>(head, tail.head) ;
            Node<T> p = this.head; // sauvegarde debut liste head
            size = 1; // au moins 1 element dans la liste
            while (p.next != null){
                p = p.next;
                size ++;
            }
            // on s'arrete sur le dernier element
            last = p;
        }
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
    public boolean isEmpty() {
        return head == null;
    }



    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("(");
        Node<T> p = head;
        while (p != null){
            sb.append(p.value);
            if (p.next != null) sb.append(",");
            p = p.next;
        }
        sb.append(")");
        return sb.toString();
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
    
    public void addAll(SList<T> L) {
    	if(head == null) {
    		head = L.head;
    		last = L.last;
    		size = L.size;
    	}else {
    		last.next = L.head; 
    		last = L.last;
    		size += L.size;
    	}
    }
    
    //version qui ajou te une copie des valeurs
    public void addAllCopy(SList<T> L) {
    	Node<T> p = L.head;
    	while(p != null) {
    		Node<T> tmp = new Node(p.value);
    		if(last == null) {
        		head = tmp;
        		last = head;
        		size = 1;
        	}else {
        		last.next = tmp; 
        		last = last.next;
        		size ++;
        	}
    		p=p.next;
    	}
    }
    
    public void insert(SList<T> L, int index) {
    	if(index==0) {
    		//insert au debut
    		L.last.next = head;
    		head = L.head;
    	}else if (index >size) {
    		//insert in the end
    		last.next = L.head;
    		last = L.last;
    	}else {
    		//insertion a la position index
    		int i = 0;
    		Node<T> p = head;
    		while (i<index-1) {
    			p = p.next;
    			i = i+1;
    		}
    		Node<T> tmp = p.next; //on enregistre le suivant
    		p.next = L.head;
    		L.last.next = tmp;
    	}
    	size += L.size;
    }
    
    public boolean equals(SList<T> L) {
    	if(this.isEmpty()) return L.isEmpty();
    	else {
    		Node<T> p = head;
    		Node<T> q = L.head;
    		boolean res = true; 
    		while (p !=null && q!= null && res) {
    			res = Objects.equals(p.value, q.value);
    			p = p.next;
    			q = q.next;
    		}
    		return res;
    	}
    }
    
    public void reverse() {
    	if(this.isEmpty()) {
    		Node<T> p = head;
    		Node<T> res = new Node<>(p.value);
    		while(p.next != null) {
    			p = p.next; 
    			res = new Node<>(p.value, res);
    		}
    		head = res;
    	}
    }
    
    private Node<T> reverseRecNode(Node<T> L){
    	if(L==null || L.next == null) return L;
    	else {
    		//ajoue les elemebts dans L
    		Node<T> res = reverseRecNode(L.next);
    		Node<T> p = res;
    		while(p.next != null) {
    			p = p.next;
    		}
    		p.next = new Node<>(L.value);
    		return res;
    	}
    }
    
    
    public void reverseRec() {
    	head = reverseRecNode(head);
    }
    
    public boolean isPalindrome() {
    	//creer une lste inverser et on les comare
    	SList<T> inverse = new SList<>();
    	inverse.head = reverseRecNode(this.head);
    	return equals(inverse);
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
    public boolean contains(T elem) {
        Node<T> p = head;
        while (p != null){
            if (p.value.equals(elem)) return true;
            else p = p.next;
        }
        return false;
    }

    @Override
    public int indexOf(T elem) {
        int index = 0;
        Node<T> p = head;
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

    @Override
    public T get(int index) {
        int cpt = 0;
        Node<T> p = head;
        while (p != null && cpt < index) {
            p = p.next;
            cpt++;
        }
        if (p != null) return p.value;
        else return null;
    }

    @Override
    public void set(T elem, int index){
        Node<T> p = head;
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
    public void append(LList<T> other) {
        if (other != null){
            SList<T> cast = (SList<T>)other;
            if (isEmpty()) {
                this.head = cast.head;
                this.size = cast.size;
                this.last = cast.last;
            } else {
                this.last.next = cast.head;
                this.size += cast.size;
                this.last = cast.last;
            }
        }
    }
    
	public double average() {
		if(head == null) return 0;
		else {
			Class c = head.value.getClass();
			if(Number.class.isAssignableFrom(c)) {
				Pair<Integer, Double> res = sum_length(head);
				return res.getRight()/ res.getLeft();
			}else {
				System.out.println("Peut pas faire la moyenne");
				return 0;
			}
		}
	}
	
	private Pair<Integer, Double> sum_length(Node<T> node){
		if(node ==null) return new Pair(0,0);
		else {
			Pair<Integer, Double> tmp = sum_length(node.next);
			int left = tmp.getLeft()+1;
			double right = ((Number)tmp.getRight()).doubleValue()+ ((Number) node.value).doubleValue();
			return new Pair<Integer, Double>(left, right);	
		}
	}
	
}
