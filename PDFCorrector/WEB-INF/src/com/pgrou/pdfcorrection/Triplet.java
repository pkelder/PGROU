package com.pgrou.pdfcorrection;

/*
 * Cette classe sera utile pour y voir plus clair dans le main.
 * Elle permettra de cr�er (� partir des donn�es diff�rentes obtenues avec les deux corrections)
 * Un objet "commun" r�sumant, pour chaque erreur (orthographique ou grammaticale)
 * sa ligne, le mot concern�, et la liste des correction propos�es.
 * 
 * 
 */

import java.util.*;

public class Triplet {

	/***** Attributes *****/

	protected int line;
	protected String word;
	protected List<String> correction;
	protected String message;

	/***** Constructors *****/

	public Triplet() {
	}

	public Triplet(int one, String two, List<String> three, String mess) {
		line = one;
		word = two;
		correction = three;
		message = mess;
	}

	/***** Getters *****/

	public int getLine() {
		return line;
	}

	public String getWord() {
		return word;
	}

	public String getMessage() {
		return message;
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

	public void setMessage(String w) {
		this.message = w;
	}

	public void setCorrection(List<String> list) {
		this.correction = list;
	}

}
