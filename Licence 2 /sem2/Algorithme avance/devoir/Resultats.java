package devoir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;


public class Resultats {

    // est une liste de resultat issu d'une simulation
    private Resultat[] resultatsOfSimulation;
    //ATTENTION aucun test de l'existence du dossier est faite
    private File folder = new File(System.getProperty("user.dir")+"/src/devoir/data/");//le dossier de sortie
    private String enteteFile ="";//le delimiter est
    private String delimiter =";";
    private String format = "csv";

    /**
     * Un object contenant touts les résultats d'une simulation, et les methodes permettants d'exporter les données
     * @param size est la taille du tableau resultatsOfSimulation (le nombre d'object resultat qu'il va contenir)
     */
    public Resultats(int size){
        //nous créons un tableau qui va contenir nos resultats
        this.resultatsOfSimulation = new Resultat[size];
        this.generateEnteteFile();//generation des entetes pour le fichier csv qui sera exporter à la fin
    }

    /**
     * Genere automatiquement l'entete du fichier csv
     */
    private void generateEnteteFile(){
        Class classResultat =  Resultat.class;
        Field[] fields = classResultat.getDeclaredFields();

        for(int i = 0; i < fields.length; i++) {
            if (i==0){
                this.enteteFile+= fields[i].getName();
            }else{
                this.enteteFile+= this.delimiter+fields[i].getName();
            }
        }

    }

    /**
     * Ecrit les resultats de resultatsOfSimulation dans le buffer
     * @param buff un buffer de type BufferedWriter
     */
    private BufferedWriter writeData(BufferedWriter buff){
        Resultat resultat;
        for (int i = 0; i < this.resultatsOfSimulation.length; i++) {

            resultat = this.resultatsOfSimulation[i];
            try {
                buff.write("\n"+resultat.getTimeTriInsertion()+ this.delimiter
                        +resultat.getTimeTriBulle()+ this.delimiter
                        +resultat.getTimeTriFusion()+ this.delimiter
                        +resultat.getTimeTriRapide()+ this.delimiter
                        +resultat.getMode()+ this.delimiter
                        +resultat.getMoyenne()+ this.delimiter
                        +resultat.getEffectif()+ this.delimiter
                        +resultat.getEcartType()+ this.delimiter
                        +resultat.getMediane()+ this.delimiter
                        +resultat.getVariance()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buff;
    }

    /**
     * Export le resultat de resultatsOfSimulation au format csv
     * @param name
     */
    public void exportResultat(String name){
        File file  =new File(folder.getPath()+"/"+ name+"."+this.format);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            BufferedWriter bufw = new BufferedWriter(writer);
            bufw.write(this.enteteFile);
            bufw = this.writeData(bufw);//nous ecrivons les resultats
            bufw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }


    public Resultat[] getResultatsOfSimulation() {
        return resultatsOfSimulation;
    }

    public void setResultatsOfSimulation(Resultat[] resultatsOfSimulation) {
        this.resultatsOfSimulation = resultatsOfSimulation;
    }

}
