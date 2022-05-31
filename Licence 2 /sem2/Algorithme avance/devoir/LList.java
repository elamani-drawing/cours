package devoir;

public interface LList<T> {
    void add(T elem);
    void add(T elem, int index);
    void clear();
    boolean contains(T elem);
    boolean isEmpty();
    int indexOf(T elem);
    void remove(int index);
    T get(int index);
    void set(T elem, int index);
    int size();
    String toString();
    void append(LList<T> leaves);
}
