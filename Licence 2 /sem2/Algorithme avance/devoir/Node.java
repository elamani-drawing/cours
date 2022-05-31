package devoir;

public class Node<T> {
    public T value;
    public Node<T> next;
    
    public Node(T elem){
        this.value = elem; //valeur de lelement de liste
        this.next = null; //suite ou null
    }

    public Node(T elem, Node<T> _next){
        this.value = elem;
        this.next = _next;
    }
}