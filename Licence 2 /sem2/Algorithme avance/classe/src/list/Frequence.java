package list;


import java.util.Random;

public class Frequence extends SAssoc<Character,Integer> {

    public Frequence(){
        super();
    }

    public Frequence( SList <Character> L){
        this();
        for (int i =0; i < L.size () ; i ++) {
            char lettre = L.get(i);
            if (this.containsKey(L.get(i))) {
                this.put(L.get(i), this.get(lettre)+1);
            } else this.put(lettre,1);
        }
    }

    /**
     * genere une liste de lettre
     * @param n
     * @return
     */
    public static SList < Character > factory ( int n ){
        SList<Character> liste = new SList<Character>();
        Random rand = new Random();
        for (int i =0; i<n ; i++) {
            int alea = rand.nextInt(26);
            char c = (char)(alea + (int) 'A');
            liste.add(c);
        }
        return liste;
    }

    public static void main(String[] args) {
        SList<Character> tmp = factory(10);
        System.out.println(tmp);
        Frequence f = new Frequence(tmp);
        System.out.println(f);
    }
}