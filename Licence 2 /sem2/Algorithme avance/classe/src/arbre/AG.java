package arbre;

import list.ISList;


import java.util.Iterator;

public class AG<T> {

    T label;
    IList<AG<T>> forest;

    public AG(T elem){
        label = elem;
        forest = new ISList();
    }

    public AG(T elem, IList<AG<T>> f) {
        label = elem;
        forest = f;
    }

    public T getLabel() {
        return label;
    }

    public void setLabel(T label) {
        this.label = label;
    }

    public IList<AG<T>> getForest() {
        return forest;
    }

    public void setForest(IList<AG<T>> f) {
        this.forest = f;
    }

    public boolean isLeaf() {
        return forest.isEmpty();
    }

    public String toString(){
        return aff(0);
    }

    private String shift(int shift){
        if (shift == 0) return "";
        StringBuilder buffer = new StringBuilder("");
        for (int i = 0; i < shift - 1; i++) buffer.append("   ");
        buffer.append("|-->");
        return buffer.toString();
    }

    private String aff_Forest(int level) {
        StringBuilder sb = new StringBuilder("");
        Iterator<AG<T>> it = getForest().iterator();
        while (it.hasNext()) {
            sb.append(it.next().aff(level));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String aff(int level){
        if (this.isLeaf()) return shift(level) + getLabel();
        else {
            return shift(level) + getLabel() + "\n" + aff_Forest(level + 1);
        }
    }

    private int nbNodesForest() { int sum = 0;
        Iterator<AG<T>> it = this.getForest ( ).iterator () ;
        while (it.hasNext() ) {
            sum += it.next().nbNodes() ;
        }
        return sum;
    }

    public int nbNodes() {
        if (isLeaf() ) return 1;
        else return 1 + this.nbNodesForest() ;
    }

    private int nbdesFeuilles() { int sum = 0;
        Iterator<AG<T>> it = this.getForest ( ).iterator () ;
        while (it.hasNext() ) {
            sum += it.next().nbNodes() ;
            ;}
        return sum;
    }
    public int nbFeuilles() {
        if (isLeaf() ) return 1;
        else return 1 + this.nbdesFeuilles() ;
    }

    public int profondeur(){
        int prof = 0; //profondeur départ
        if(getForest() == null) //cas de base : arbre = feuille
            return 1;
        else{
            Iterator<AG<T>> it = getForest().iterator();
            while(it.hasNext()){
                int p = it.next().profondeur();
                if(p>prof)
                    prof = p;
            }
            return 1 + prof;
        }
    }

    public int nbrNodeNiv(int niv){
        int count = 0;
        if(niv == 1) return 1;
        else{
            Iterator<AG<T>> it = getForest().iterator();
            while(it.hasNext()){
                count += it.next().nbrNodeNiv(niv - 1);
            }
            return count;
        }
    }

    public int strahler(){
        if(getForest() == null) return 0;
        else{
            Iterator<AG<T>> it = getForest().iterator();
            int max = 0;
            int first = it.next().strahler();
            boolean tousEgaux = true;
            while(it.hasNext()){
                int s = it.next().strahler();
                if(s > max) max = s;
                tousEgaux = (s == first) && tousEgaux;
            }
            if(tousEgaux) return first + 1;
            else return max;
        }
    }

}