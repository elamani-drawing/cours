package devoir;

import java.util.Objects;
import java.lang.Math;

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

    public SList(Node<T> p){
        //on calcul le la taille et le dernier element de la liste
    	Node<T> last = p;
    	this.head = p;
    	int taille = 0;
    	while (p != null) {
        	taille += 1;
        	last = p;
        	p = p.next;
        }
    	//on met a jour les donnees
    	this.last = last;
    	this.size = taille;
    }

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
    
    //version qui ajoute une copie des valeurs
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

    /**
     * Calcul l'ecartype de la liste
     * @param moyenne La valeur moyenne de la liste
     * @return
     */
    public double ecartype(double moyenne){
        return Math.sqrt(variance(moyenne));// racine(variance);
    }

    /**
     * Calcul la variance de la liste
     * @param moyenne La valeur moyenne de la liste
     * @return
     */
    public double variance(double moyenne){
        double total=0;
        double carreValue = 0;
        Node<T> p = this.head;
        while(p!= null){
            carreValue = ((int) p.value - moyenne);//valeur - moyenne
            total+= carreValue * carreValue; //nous fesons le carree : (valeur - moyenne)^2
            p = p.next;
        }

        return ((double)1/size) * total; // 1/n * (n1 - moy)^2 + (n2 -moy) ^2 ...
    }

    /**
     * Calcul la mdianne de la liste
     * @return
     */
    public double mediane(){
        boolean paire = false;
        int indice= (size/2)+1;
        if (size%2==0){
            paire = true;
            indice= size/2;
        }
        Node<T> p = this.head;
        while(indice>1){
            indice-=1;
            p=p.next;
        }
        //on est à l'indice voulut
        if(paire==true){
            return  (double)((int)p.value+ (int)p.next.value)/2;//on retourne la moyenne des 2 nombres au milieu de la liste
        }
        return (double) (int)p.value; //la mediane est le nombre au milieu de la liste
    }

    /**
     * Réalise la moyenne de la liste
     * Précondition : T est de type Integer
     * @return la moyenne
     */
    public double moyenne(){
        Node<T> p = this.head;
        double total = 0;
        while(p != null){
            total += ((int) p.value);
            p=p.next;
        }
        return total/this.size();
    }

    /**
     * Trouve le mode de la liste
     * @return
     */
    public int mode(){
        Node<T> p = this.head;
        Node<T> p2;
        int mode = -1;
        int counter = -1;
        int nbrMode = -1;
        while(p != null){
            counter = 0;
            p2 = this.head;
            while(p2 != null){
                if(p.value == p2.value){
                    counter+=1;
                }
                p2 = p2.next;
            }
            if(nbrMode<counter){
                mode = (int) p.value;
                nbrMode= counter;
            }
            p=p.next;
        }
        return mode;
    }


    /**
     * Réalise une copie d'une liste à partir d'un Node. Attention, la liste courante est la parfaite copy de la liste L. (Les anciens elements sont perdue)
     * @param L est un Node
     */
    public void copyListFromNode(Node<T> L) {
        Node<T> p = L;
        last = null;
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
    

}
