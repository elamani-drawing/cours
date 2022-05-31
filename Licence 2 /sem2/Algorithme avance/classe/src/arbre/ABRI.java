package arbre;
public class ABRI extends ABR<Integer>{

    public ABRI(Integer _label) {
        super(_label);
    }

    public ABRI(Integer _label, AB<Integer> _left, AB<Integer> _right) {
        super(_label, _left, _right);
    }

    public void addLeaf(Integer value){
        if(!this.isLeaf()) new ABR(value);
        else if(getLabel().compareTo(value) < 0){
            //value > label
            //"getLabel() - value" < 0
            if(getRight() != null) ((ABRI)getRight()).addLeaf(value);
            else setRight(new AB(value));
        }else if(getLabel().compareTo(value) > 0){
            //label > value => gauche
            if(getLeft() != null) ((ABRI)getLeft()).addLeaf(value);
            else setLeft(new AB(value));
        }
    }


}
