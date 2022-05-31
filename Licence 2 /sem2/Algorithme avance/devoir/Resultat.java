package devoir;

public class Resultat {

    private long timeTriInsertion;
    private long timeTriBulle;
    private long timeTriFusion;
    private long timeTriRapide;
    private int mode;
    private double moyenne;
    private int effectif;
    private double ecartType;
    private double mediane;
    private double variance;


    public Resultat(long timeTriInsertion, long timeTriBulle, long timeTriFusion, long timeTriRapide, int mode, double moyenne, int effectif, double ecartType, double mediane, double variance) {
        this.timeTriInsertion = timeTriInsertion;
        this.timeTriBulle = timeTriBulle;
        this.timeTriFusion = timeTriFusion;
        this.timeTriRapide = timeTriRapide;
        this.mode = mode;
        this.moyenne = moyenne;
        this.effectif = effectif;
        this.ecartType = ecartType;
        this.mediane = mediane;
        this.variance = variance;
    }


    //getter setter

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getMediane() {
        return mediane;
    }

    public void setMediane(double mediane) {
        this.mediane = mediane;
    }

    public long getTimeTriInsertion() {
        return timeTriInsertion;
    }

    public void setTimeTriInsertion(long timeTriInsertion) {
        this.timeTriInsertion = timeTriInsertion;
    }

    public long getTimeTriBulle() {
        return timeTriBulle;
    }

    public void setTimeTriBulle(long timeTriBulle) {
        this.timeTriBulle = timeTriBulle;
    }

    public long getTimeTriFusion() {
        return timeTriFusion;
    }

    public void setTimeTriFusion(long timeTriFusion) {
        this.timeTriFusion = timeTriFusion;
    }

    public long getTimeTriRapide() {
        return timeTriRapide;
    }

    public void setTimeTriRapide(long timeTriRapide) {
        this.timeTriRapide = timeTriRapide;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public int getEffectif() {
        return effectif;
    }

    public void setEffectif(int effectif) {
        this.effectif = effectif;
    }

    public double getEcartType() {
        return ecartType;
    }

    public void setEcartType(double ecartType) {
        this.ecartType = ecartType;
    }


}
