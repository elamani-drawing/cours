package list;

public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T elem){
        this.value = elem;
        this.next = null;
    }

    public Node(T elem, Node<T> _next){
        this.value = elem;
        this.next = _next;
    }
}