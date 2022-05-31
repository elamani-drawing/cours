package arbre;

public class ABR<T extends Comparable<T>> extends AB<T> {

    public ABR(T _label) {
        super(_label);
    }

    public ABR(T _label, AB<T> _left, AB<T> _right) {
        super(_label, _left, _right);
    }

    //contains prof
    public boolean contains(T elem){
        if(isLeaf()) return this.getLabel().compareTo(elem) == 0;
        else{
            boolean found = this.getLabel().compareTo(elem) == 0;
            if(!found){
                if(this.getLabel().compareTo(elem) > 0 && this.hasLeft()){
                    found = ((ABR) this.getLeft()).contains(elem);
                }else if(this.getLabel().compareTo(elem) < 0 && this.hasRight()){
                    found = ((ABR) this.getRight()).contains(elem);
                }else found = false;
            }
            return found;
        }
    }

    //contains moi
    public boolean contains2(T elem){
        boolean found = false;
        if(getLabel().compareTo(elem) == 0)
            found = true;
        else{
            if(getLabel().compareTo(elem) < 0) {
                if (hasRight()){
                    found = ((ABR) getRight()).contains2(elem);
                }
            }
            else if(hasLeft()){
                    found = ((ABR) getLeft()).contains2(elem);
                }
        }
        return found;
    }

    public boolean isABR(){
       return true;
    }

}
