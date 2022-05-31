package devoir;
import java.util.Random;


public class Tri implements Sort<Integer> {
	public Node<Integer> head= null;//la liste initial
	private Node<Integer> last= null;//le dernier element de la liste non ranger, utile pour le tri rapide pour avoir le premier end sans avoir a parcourir une fois la liste
	public Node<Integer> headSorted= null;//la liste ranger, elle nous ait utile dans le tri par insertion comme liste resultat (le node de tete de la liste resultat)
	public int size = 0;//la taille de la liste 
	
	public Tri(int n, int m) {
		this.randomIntegerListGeneration(n, m);//on genere les elements de la liste
	}

	public static void main(String[] args) {
		Tri tri = new Tri(99999, 100);
		System.out.println(tri);
		//SList<Integer> insertTri = tri.triInsertion();
		//SList<Integer> bulleTri = tri.triBulles();
		//SList<Integer> bulleTri = tri.triFusion();
		SList<Integer> bulleTri = tri.triRapide();
		long timeBefore = System.currentTimeMillis();
		timeBefore = System.nanoTime();
		System.out.println(bulleTri);
		long timeNext = System.currentTimeMillis();
		timeNext = System.nanoTime();
		System.out.println("--le temps"+ ( timeNext- timeBefore));
	}

	/**
	 * La fonction genere une liste de nombre al�atoire dans les valeurs sont dans l'intervalle [1, max]
	 * @param n doit etre un entier strictement plus grand que 0
	 * @param max doit etre un entier strictement plus grand que 1
	 */
	private void randomIntegerListGeneration(int n, int max){
		Random r = new Random();
		int min = 1;
		max +=1;//pour que max soit compris dans linterval
		for (int i = 0; i<n; i++) {
			this.push(min+ r.nextInt(max - min));//on a ajoute a la liste de travail qui est accessible depuis head
		}
	}
	
	/**
	 * R�alise un tri par insertion sur liste
	 */
	@Override
	public SList<Integer> triInsertion() {
        Node p = this.head;
        if (p == null) {
            return new SList<Integer>(); //si cest vide on retourne la liste vide
        }
        this.headSorted=null;//on r�nitialise la liste ranger au cas ou un autre tri a ete realiser
        // On parcourt la liste et on insert les elements a leur place 
        while (p != null) {
            //On sauvegarde la reference du suivant
            Node next = p.next;
            // on insert lelement p a sa place dans la liste
            sortedInsert(p);
            // On passe a lelement dapres
            p = next;
        }
        // on retourne la liste ranger
        return new SList<Integer>(this.headSorted);
    }
	
	/**
	 * La fonction insert elem a la bonne position dans la nouvelle liste ranger 
	 * @param elem est l'element � inseret
	 */
	private void sortedInsert(Node<Integer> elem) {
        //si on insert en debut de liste (car la liste est vide ou le premier element est plus grand que elem)
        if (headSorted == null || headSorted.value >= elem.value) {
        	elem.next = headSorted;
        	headSorted = elem;
            this.headSorted = headSorted;
        } else {
            Node<Integer> p = this.headSorted; //la liste ranger
            //on cherche la position i ou on doit inseret elem (on sarrete a i-1)
            while (p.next != null && p.next.value < elem.value) {
            	p = p.next;
            }
            //on recolle les  nodes
            elem.next = p.next;
            p.next = elem;
        }
    }
	
	/**
	 * Realise le tri a bulles sur this.head
	 */
	@Override
	public SList<Integer> triBulles() {
        // current est le node qui pointe vers la tete 
        Node<Integer> current = this.head;
        //si la liste est null en retourne une liste vide
        if (current == null) {
            return new SList<Integer>(); //si cest vide on retourne la liste vide
        }        
        Node<Integer> index = null; //un deuxieme pointeur
        int temp; //une variable qui va servir a sauvegarder temporairement la valeur du node
        //si on est ici cest que current (head) nest pas null
        while (current != null) {
            // index pointe sur lelement dapres
            index = current.next;
            //on parcourt le reste de la liste depuis lindex jusqua la fin
            while (index != null) {
                // si lelement dapres est plus grand que notre element index, on echange leur valeur de maniere a avoir
                if (current.value > index.value) {
                	//echange des valeurs
                    temp = current.value; //sauvegarde de la valeur de i
                    current.value = index.value; //on met la valeur de i+1 en i
                    index.value = temp; //on place lancienne valeur de i en i+1
                }
                //on passe a lelement i+2
                index = index.next;
            }
            //on passe a lelement i+1
            current = current.next;
        }
        //on retourne la liste ranger
        return new SList<Integer>(this.head);
    }



	/**
	 * Range dans l'ordre et fusionne les elements de nLeft et nRight
	 * @param nLeft un Node<Integer>
	 * @param nRight un Node<Integer>
	 * @return Node<Integer> qui est la fusion ranger des elements contenu dans nLeft et nRight
	 */
	private Node<Integer> sortedFusion(Node<Integer> nLeft, Node<Integer> nRight) {
        Node<Integer> resultat = null;
        //si l'une des listes est vide on renvoie lautre
        if (nLeft == null)
            return nRight;
        if ( nRight == null)
            return nLeft;
        /* si nLeft est le plus oetit */
        if (nLeft.value <= nRight.value) {
            resultat = nLeft; //le plus petit sera nLeft
            //on cherche le prochain plus petit entre nLeft.next est le pointeur vers nRight
            resultat.next = sortedFusion(nLeft.next, nRight);
        } else {
            resultat = nRight; //si cest NrIght le plus petit
            //on cherche parmis nLeft et le suivant de nRight
            resultat.next = sortedFusion(nLeft, nRight.next);
        }
        return resultat; //on peut retourner la liste (fusionner) ranger de nLeft et nRight
    }
	/**
	 * Renvoie le milieu de la liste chainee p 
	 * @param p est un Node<Integer>
	 * @return
	 */
    private Node<Integer> getMiddle(Node<Integer> p) {
        if (p == null) return p;
        //currentDouble avance 2 fois plus ite que current, donc quand currentDouble arrive a la fin de la liste, le milieu est current
        Node<Integer> current = p; //current est la position i de la liste
        Node<Integer> currentDouble = p; //currentDouble est la position i*2 de la liste
        //parcours de la liste, tant que currentDouble na pas fini son parcours
        while (currentDouble.next != null && currentDouble.next.next != null) {
        	current = current.next;//current passe a lelement i+1 avec i ca position actuel
        	currentDouble = currentDouble.next.next; //passe a la position i+2 avec i sa position actuel
        }
        return current;//on retourne le millieu
    }
    
    /**
     * La classe r�alise une reccursion sur p, pour r�aliser le tri par fusion 
     * @param p un Node<Integer>
     * @return un Node<Integer>
     */
    private Node<Integer> fusionSort(Node<Integer> p) {
        // si p ou le suivant est null, on sarrete
        if (p == null || p.next == null) {
            return p;
        }
        // On reccupere le milieu de p
        Node<Integer> middle = getMiddle(p);
        //on reccupere le suivant du milieu
        Node<Integer> middleNext = middle.next;
        // on enleve le lien a la parti gauche de la liste
        middle.next = null; //ce qui fait quon a une liste qui va de p � midle, puis de midleNext � fin
        // on continue la reccursion sur la partie gauche de la liste
        Node<Integer> left = fusionSort(p);
        // on fait de meme pour la partie droite
        Node<Integer> right = fusionSort(middleNext);
        // On range et fusionne la partie gauche et droite
        Node<Integer> resultat = sortedFusion(left, right);
        return resultat;
    }
	

    /**
     * La fonction appelle la fonction qui soccupe du tri par fusion, puis retourne le resultat dans une liste SList<Integer>
     */
	@Override
	public SList<Integer> triFusion() {
		Node<Integer> resultat = this.fusionSort(this.head);
		return  new SList<Integer>(resultat);
	}
    
	/**
	 * Selectionne un pivot, et replace les elements par apport a lui
	 * @param start est un Node<Integer> pointant vers lelement apes le pivot
	 * @param end est un Node<Integer> pointant vers la fin de la partie de la liste
	 * @return 
	 */
    private Node<Integer> partitionLast(Node<Integer>  start, Node<Integer>  end) {
    	//si on a fini (linterval est null start == end) ou lun deux est null, on retourne null ou start
    	if (start == end ||  start == null || end == null)
            return start;
    	//par default
    	//lelement avant le pivot est le depart
        Node<Integer> pivot_prev = start;
        //une autre reference a la liste start
        Node<Integer> current = start;
        //int temp = start.value;
        int temp;
        //lelement le plus grand sera lelement de fin de liste
        int pivot = end.value;
        // on parcourt l'intervale start - end et on sarrete aant le end
        while (start != end) {
        	//si la valeur du node courant est plus petit que le pivot ils passent a gauche
            if (start.value < pivot) {
                // on met a jour lelement avant le pivot 
                pivot_prev = current;
                //on echange les valeurs
                temp = current.value; //sauvegarde de la valeur de current
                current.value = start.value;//on donne a current, la valeur du node actuel
                start.value = temp; //on donne a start la valeur de la
                current = current.next;
            }
            start = start.next;//on passe au node suivant
        }
        // on echange les valeurs
        temp = current.value;
        current.value = pivot;
        end.value = temp;
        // renvoie le pr�c�dent current parce que le current pointe maintenant vers le pivot
        return pivot_prev;
    }
	
    /**
     * S'occupe du rangement et de la partition de l'intervalle donner en parametre
     * @param start Node<Integer> de debut de l'intervalle
     * @param end Node<Integer> de fin de l'intervalle
     * @return Node<Integer> etant le premier element de la liste de node ranger 
     */
    private Node<Integer> rapidSort(Node<Integer> start, Node<Integer> end) {
    	//si lintervalle est null on ne peux pas choisir de pivot ni la ranger 
        if (start == end || start == null || start == end.next) return null;
        // choisit le pivot et deplace place les elements a sa gauche ou a sa droite par apport a leur valeur au pivot
        Node<Integer> pivot_prev = partitionLast(start, end);
        //gauche correspond au element avant le pivot, droite correspond au element apres le pivot
        rapidSort(start, pivot_prev);//on choisit le pivot et on fait le rangement de la partie gauche de la liste
        
        //on choisit le pivot droit et on fait le rangement de la partie droite de la liste
        //si lelement avant le pivot nest pas null et quil est le premier element de la liste 
        if (pivot_prev != null && pivot_prev == start)
        	//on rappelle la fonction sur le nouvel interalle pivot et la fin de la liste de node
        	rapidSort(pivot_prev.next, end);
        //sinon si lelement avant le pivot nest pas null et que le pivot existe (le pivot est un autre element que start)
        else if (pivot_prev != null &&  pivot_prev.next != null)
        	rapidSort(pivot_prev.next.next, end);//on rappelle la fonction dans cet intervalle
        return start;
    }
    
    /**
     * R�alise le tri rapide
     */
	@Override
	public SList<Integer> triRapide() {
		Node<Integer> resultat = this.rapidSort(this.head, this.last);
		return  new SList<Integer>(resultat);
	}

	/**
	 * permet d'ajouter un element a la liste pointer par head
	 * @param val est la valeur ajouter a la liste
	 */
	private void push(int val) {
		size+=1;//on augmente la taille
        if (this.head == null) {
            Node p = new Node(val);
            //p sera le premier et le dernier element de la liste
            this.head = p;
            this.last = p;
            return;
        }
        //on ne change pas le last
        Node p = new Node(val);
        //on insert lelement en tete de liste
        p.next=this.head;
        //on change la tete
        this.head = p;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("(");
        Node<Integer> p = head;
        while (p != null){
            sb.append(p.value);
            if (p.next != null) sb.append(",");
            p = p.next;
        }
        sb.append(")");
        return sb.toString();
    }
    
}