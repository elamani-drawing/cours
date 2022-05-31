package arbre;

import java.util.Random;

public class ABInt extends AB<Integer>{


    public ABInt(Integer _label) {
        super(_label);
    }

    //Hypothèse l'arbre à au moins une feuille
    public void addRand(int val){
        double r = Math.random();
        if(r < 0.5){
            if(hasLeft())
                ((ABInt) getLeft()).addRand(val);
            else
                setLeft(new ABInt(val));
        }
        else{
            if(hasRight())
                ((ABInt)getRight()).addRand(val);
            else
                setRight(new ABInt(val));
        }
    }

    public void addAlea(int size, int valMax){
        int place = 1;
        Random r = new Random();
        while(place < size){
            int val = r.nextInt(valMax);
            addRand(val);
            place++;
        }
    }

    public void addFullRight(ABInt A){
        ABInt p = this;
        while (p.hasRight()){
            p = (ABInt) p.getRight();
        }
        p.setRight(A);
    }

    public void addOrder(int val){
        if(getLabel() > val){
            if(hasLeft())
                ((ABInt)getLeft()).addOrder(val);
            else setLeft(new ABInt(val));
        }
        else if(getLabel() < val){
            if(hasRight())
                ((ABInt)getRight()).addOrder(val);
            else setRight(new ABInt(val));
        }
        //pas de test lorsque val == getLabel() car pas d'action à faire dans ce cas là
    }

    public int shortestPath(){
        if(isLeaf()) return 1;
        else{
            int pathLeft = (hasLeft())? ((ABInt)getLeft()).shortestPath():0;
            int pathRight = (hasRight())? ((ABInt)getRight()).shortestPath():0;
            return getLabel() + (int) Math.min(pathLeft, pathRight);
        }
    }



}
