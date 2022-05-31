package dict;

import java.util.Objects;

public class Tuple {
	final Object[] values;
	
	// "empty" tuple
	public Tuple(Object... elements) {
		values = elements;
	}

	public <E> E get(int index){
		E tmp = (E) values[index];
		return tmp;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tuple pair = (Tuple) o;
		boolean res = true;
		for (int i = 0; i < values.length; i++){
			res = Objects.equals(get(i), pair.get(i));
			if (!res) return false;
		}
		return true;
	}

	public String toString(){
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < values.length; i++){
			res.append(values[i]);
			if (i < values.length-1) { res.append(", "); }
		}
		return "{" + res + "}";
	}

	public static void main(String[] args) {
		Tuple test = new Tuple("Gandalf", "Magicien", 50, true, 10.3);
		Tuple test2 = new Tuple("Gandalf", "Magicien", 50, true, 10.3);
		Tuple test3 = new Tuple("Sauron", "Sorcier", 150, false, 999.99);
		System.out.println(test);
		System.out.println(test.equals(test2));
		System.out.println(test.equals(test3));
		// System.out.println(test.<Boolean>get(3));
	}
}