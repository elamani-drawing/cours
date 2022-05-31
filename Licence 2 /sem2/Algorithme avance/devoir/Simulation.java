package devoir;
import java.lang.Math;

public class Simulation {

    //liste qui sert a sauvegarder la liste non ranger qui est generer, de sorte a ce que toutes les fonctions de tri travaille sur exactement la meme liste (pour eviter de biaiser  les resultats)
    public static SList save_list = new SList();
    //les tailles des listes qui vont etre generer

    //jvm default
    public static int[] sizeListGenerate = {100, 1000,1500,2000,2500, 3000, 3500,4000,5000,7000,9000,9000,1000};
    public static String[] namesFiles = {"data_simulation_liste_croissante", "data_simulation_liste_decroissante", "data_simulation_liste_quelconque"};
    public static int[] etendueValue = {10, 1000, 10000}; //la liste est tester sur differentes etendue de valeur
    //jusqua 30000 : -Xmx2G -Xms1G -Xss1G
    //jusqua 100000 : -Xmx6G -Xms1G -Xss1G : 20 min de traitement
    //public static int[] sizeListGenerate = {100, 1000,1500,2000,2500, 3000, 3500,4000,5000,7000,9000,9000,11000, 15000,30000, 50000,75000,100000};


    public static void main(String[] args) {
        /*
        //Pour verifier les resultats des fonctions de tri (lancer un rangement un à un sinon le reste des fonctions va trier des listes deja trier)
        Tri tri = new Tri(100, 100);
        SList<Integer> insertTri = tri.triInsertion();
        //SList<Integer> bulleTri = tri.triBulles();
        //SList<Integer> fusionTri = tri.triFusion();
        //SList<Integer> rapideTri = tri.triRapide();
        //SList<Integer> insertTriDecroissant = tri.triDecroissantInsertion();
        System.out.println(insertTri);
        */
        Resultats res;
        for (int i = 0; i < etendueValue.length; i++) {
            for (int j = 0; j < namesFiles.length; j++) {
                res = startSimulation(etendueValue[i],j );
                res.exportResultat(namesFiles[j]+"_etendue_"+etendueValue[i]);
            }
        }

    }

    /**
     * Lance une simulation.
     * Une simulation conciste à generer des listes de differentes tailles, de mesurer les performances en temps de chaque tri
     * et d'exporter les resultats (avec les carracteristiques de chaque liste ex: moyenne, etendue, mode etc.)
     * @param etendu La valeur maximal que peux prendre une valeur de la liste. ATTENTION, c'ette valeur est differentes de l'etendue réel de la liste
     * @param typeList Un Integer indiquant le type de liste sur laquelle nous allant travailler. different de 0 et 1 : quelconque; 0 : trier de maniere croissante; 1 : trier de maniere decroissante
     * @return Un object Resultat contenant les resultats de la simulation
     */
    public static Resultats startSimulation(int etendu, int typeList){
        Tri tri;//l'object tri
        Resultats resultats = new Resultats(sizeListGenerate.length); //va sauvegarder tout les resultats
        Resultat resultat; //va sauvegarder un résultat
        double moyenne;
        double variance;

        long timeTriInsertion;
        long timeTriBulle;
        long timeTriFusion;
        long timeTriRapide;

        for (int i =0; i< sizeListGenerate.length; i++){

            //nous generons la liste
            tri = new Tri(sizeListGenerate[i], etendu);
            if(typeList == 0){
                //la liste etudier sera deja ranger en croissant
                tri.head = tri.triInsertion().head;
            }else if(typeList==1){
                //la liste etudier sera deja ranger en decroissant
                tri.head = tri.triDecroissantInsertion().head;
            }
            //sinon
            // la liste nest pas ranger
            //nous réalisons une sauvegarde de la liste, pour que tout les tri se fasse sur une meme liste
            save_list.copyListFromNode(tri.head);
            //nous réalisons le tri

            timeTriRapide = System.nanoTime();
            tri.triRapide();
            timeTriRapide = System.nanoTime() - timeTriRapide;

            //avant le prochain tri, nous échangeant la liste de tri (qui est ranger) par la liste initial, elle va etre utiliser pour le prochain tri
            tri.head = save_list.head;
            //nous refesons une copie de la liste initial
            save_list.copyListFromNode(save_list.head);

            timeTriInsertion = System.nanoTime();
            tri.triInsertion();
            timeTriInsertion = System.nanoTime() - timeTriInsertion;

            //avant le prochain tri, nous échangeant la liste de tri (qui est ranger) par la liste initial, elle va etre utiliser pour le prochain tri
            tri.head = save_list.head;
            //nous refesons une copie de la liste initial
            save_list.copyListFromNode(save_list.head);

            timeTriFusion = System.nanoTime();
            tri.triFusion();
            timeTriFusion = System.nanoTime() - timeTriFusion;

            //avant le prochain tri, nous échangeant la liste de tri (qui est ranger) par la liste initial, elle va etre utiliser pour le prochain tri
            tri.head = save_list.head;
            //nous fesons refesons une copie de la liste initial
            save_list.copyListFromNode(save_list.head);

            timeTriBulle = System.nanoTime();
            tri.triBulles();
            timeTriBulle = System.nanoTime() - timeTriBulle;

            save_list.copyListFromNode(tri.head);//on reccupere la liste ranger pour réaliser la mediane

            moyenne =save_list.moyenne();
            variance = save_list.variance(moyenne);

            resultat = new Resultat(timeTriInsertion,timeTriBulle,timeTriFusion,timeTriRapide,save_list.mode(),moyenne, sizeListGenerate[i],  Math.sqrt(variance), save_list.mediane(), variance);
            resultats.getResultatsOfSimulation()[i] = resultat; //on sauvegarde le resultat

        }
        return resultats;
    }
}
