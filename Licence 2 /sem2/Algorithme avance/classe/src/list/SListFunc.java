package list;

import fonc.Reducer;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Class that extends SList<T> to provide functional operation
 * filter, map, reduce
 * @param <T>
 */
public class SListFunc<T> extends ISList<T>{

    protected Node<T> current;

    public SListFunc(){super();}

    public SListFunc(T e){super(e);}

    public SListFunc(T head, SListFunc<T> tail) {super(head, tail);}

    public SListFunc<T> filter(Function<T, Boolean> filter){
        Iterator<T> it = this.iterator();
        SListFunc<T> res = new SListFunc<>();
        while (it.hasNext()){
            T val = it.next();
            if (filter.apply(val)){
                res.add(val);
            }
        }
        return res;
    }

    public <R> SListFunc<R> map(Function<T,R> mapper){
        Iterator<T> it = this.iterator();
        SListFunc<R> res = new SListFunc<>();
        while (it.hasNext()){
            T val = it.next();
            res.add(mapper.apply(val));
        }
        return res;
    }

    public <R> R reduce(BiFunction<T, R, R> reducer, R base){
        R res = base;
        Iterator<T> it = this.iterator();
        while (it.hasNext()){
            T val = it.next();
            res = reducer.apply(val, res);
        }
        return res;
    }

    public static void main(String[] args) {
        SListFunc<Integer> L = new SListFunc<Integer>(4);
        L.add(5);L.add(6);L.add(7);L.add(8);L.add(9);
        System.out.println(L);

        /* Filter original list to retain only even values */
        Function<Integer, Boolean> filter = (val)-> (val % 2) == 0;
        System.out.println(L.filter(filter));

        /* Map original int list to character list */
        Function<Integer, Character> mapper = (num) -> (char)(65+num%26);
        System.out.println(L.map(mapper));

        /* Reduce a list to its size. Base is the value when list is empty */
        BiFunction<Integer, Integer, Integer> reducer = (val, sum) -> sum + 1;
        System.out.println(L.reduce(reducer, 0));
    }
}

