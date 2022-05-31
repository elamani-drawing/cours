package arbre;

import java.util.Iterator;
import java.util.Objects;

public class AB<T> {

    private T label;
    private AB<T> left;
    private AB<T> right;

    public AB(T _label){
        label = _label;
        left = null;
        right = null;
    }

    public AB(T _label, AB<T> _left, AB<T> _right){
        label = _label;
        left = _left;
        right = _right;
    }

    public boolean isLeaf(){
        return (getLeft() == null && getRight() == null);
    }

    public boolean hasLeft(){
        return getLeft() != null;
    }

    public boolean hasRight(){
        return getRight() != null;
    }

    public int nbNodes(){
        if(isLeaf()) return 1;
        else{
            int left = (hasLeft())? getLeft().nbNodes():0;
            int right = (hasRight())? getRight().nbNodes():0;
            return 1 + left + right;
        }
    }

    public int height(){
        if(isLeaf()) return 1;
        else{
            int left = (hasLeft())? getLeft().height():0;
            int right = (hasRight())? getRight().height():0;
            return 1 + Math.max(left, right);
        }
    }

    private String aff(int level){
        if(isLeaf()) return shift(level) + getLabel();
        else{
            String left = (hasLeft())? getLeft().aff(level + 1): shift(level + 1) + "[X]";
            String right = (hasRight())? getRight().aff(level + 1): shift(level + 1) + "[X]";
            return shift(level) + getLabel() + "\n" + left + "\n" + right;
        }
    }

    private String shift(int shift){
        if(shift == 0) return "";
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < shift - 1; i++){
            buffer.append("   ");
        }
        buffer.append("|---->");
        return buffer.toString();
    }

    public String toString(){
        return aff(0);
    }


    public int nbLeafs(){
        if(isLeaf()) return 1;
        else{
            int left = (hasLeft()) ? getLeft().nbLeafs() : 0;
            int right = (hasRight()) ? getRight().nbLeafs() :0;
            return left + right;
        }
    }

    public boolean isVowel(char letter){
        boolean verif = false;
        if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u' || letter == 'y')
            verif = true;
        return verif;
    }


    public int nbLeafVowel(){
        if(isLeaf() && isVowel((Character) getLabel())) return 1;
        else{
            int left = (hasLeft()) ? getLeft().nbLeafVowel() : 0;
            int right = (hasRight()) ? getRight().nbLeafVowel() : 0;
            return left + right;
        }
    }

    public boolean egalite(AB<T> A){
        return (A != null) && Objects.equals(this.getLabel(), A.getLabel()) && (hasLeft() && getLeft().egalite(A.getLeft()) || (!hasLeft() && !A.hasLeft()) && (hasRight() && getRight().egalite(A.getRight()) || (!hasRight() && A.hasRight())));
    }

    public AB<T> leftBranch(){
        AB<T> leftB = new AB(getLabel());
        if(hasLeft()) {
            leftB = new AB(getLabel(),
                    getLeft().leftBranch(),
                    null
            );
        }
        return leftB;
    }

    public AB<T> rightBranch(){
        AB<T> rightB = new AB(getLabel());
        if(hasRight()) {
            rightB = new AB(getLabel(),
                    null,
                    getRight().rightBranch()
            );
        }
        return rightB;
    }

    public AB<T> miroir(){
        if(isLeaf()) return new AB(getLabel());
        else {
            AB<T> gauche = (getLeft() != null) ? getLeft().miroir() : null;
            AB<T> droite = (getRight() != null) ? getRight().miroir() : null;
            return new AB(getLabel(), droite, gauche);
        }
    }

    public boolean estMiroir(AB<T> a){
        return egalite(a.miroir());
    }

    // nb noeuds dans un niveau
    public int dnb(int niv) {
        if(niv==1)return 1;
        else {
            int left = (hasLeft()) ? getLeft().dnb(niv-1) : 0;
            int right = (hasRight()) ? getRight().dnb(niv-1) : 0;
            return left + right;
        }
    }
    // nb feuilles dans un niveau
    public int nbf(int niv) {
        if(niv==1) {
            return (isLeaf())?1:0;
        }
        else {
            int gauche=(hasLeft())? getLeft().nbf(niv-1) : 0;   //nb des noeuds
            int droite=(hasRight())? getRight().nbf(niv-1) : 0;
            return gauche+droite;
        }
    }


    public void setLabel(T label) {
        this.label = label;
    }

    public void setLeft(AB<T> left) {
        this.left = left;
    }

    public void setRight(AB<T> right) {
        this.right = right;
    }

    public T getLabel() {
        return label;
    }

    public AB<T> getLeft() {
        return left;
    }

    public AB<T> getRight() {
        return right;
    }

}
