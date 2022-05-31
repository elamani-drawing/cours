package devoir;

public interface Sort<T extends Comparable<T>> {
	public SList<T> triInsertion();
	public SList<T> triBulles();
	public SList<T> triFusion();
	public SList<T> triRapide();
}