/*
 * Cette classe sera utile pour y voir plus clair dans le main.
 * Elle permettra de créer (à partir des données différentes obtenues avec les deux corrections)
 * Un objet "commun" résumant, pour chaque erreur (orthographique ou grammaticale)
 * sa ligne, le mot concerné, et la liste des correction proposées.
 * 
 * 
 */

import java.util.*;

public class Triplet {

	/***** Attributes *****/

	protected int line;
	protected String word;
	protected List<String> correction;

	/***** Constructors *****/

	public Triplet() {
	}

	public Triplet(int one, String two, List<String> three) {
		line = one;
		word = two;
		correction = three;
	}

	/***** Getters *****/

	public int getLine() {
		return line;
	}

	public String getWord() {
		return word;
	}

	public List<String> getCorrection() {
		return correction;
	}

	/***** Setters *****/

	public void setLine(int l) {
		this.line = l;
	}

	public void setWord(String w) {
		this.word = w;
	}

	public void setCorrection(List<String> list) {
		this.correction = list;
	}

}
