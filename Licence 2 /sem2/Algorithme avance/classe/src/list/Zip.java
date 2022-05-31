package list;

import dict.Pair;

import java.util.Random;

public class Zip<K,V> extends SList <Pair<K,V>> {


    public Zip(SList<K> li, SList<V> lc) {
        super();
        for(int i=0; i< li.size; i++) {
            Pair<K,V> p = new Pair<K,V>(li.get(i), lc.get(i));
            this.add(p);
        }
    }
	/* ou prof

	public Zip (SList<K > L , SList<V > M) {
		super () ;
		Node < Pair <K ,V > > p = head ;
		for ( int i = 0; i < L . size () ; i ++) {
			Pair <K , V > tmp = new Pair ( L. get ( i ) , M. get ( i )) ;
			if ( head == null ) {
				head = new Node ( tmp ) ;
				p = head ;
			} else {
				p . next = new Node ( tmp ) ;
				p = p . next ;
			}
		}
	}
	 */


    public static void main(String[] args) {
        SList zi = genereListeAleaInt(10);
        SList zc = genereListeAleaChar(10);
        Zip z = new Zip<Integer, Character>(zi, zc);
        System.out.println(zi);
        System.out.println(zc);
        System.out.println(z);
    }

    public static SList<Character> genereListeAleaChar(int n) {
        SList<Character> liste = new SList<Character>();
        Random rand = new Random();
        for (int i =0; i<n ; i++) {
            int alea = rand.nextInt(26);
            char c = (char)(alea + (int) 'A');
            liste.add(c);
        }
        return liste;
    }
    public static SList<Integer> genereListeAleaInt(int n) {
        SList<Integer> liste = new SList<Integer>();
        Random rand = new Random();
        for (int i =0; i<n ; i++) {
            int alea = rand.nextInt(26);
            liste.add(alea);
        }
        return liste;
    }

}
