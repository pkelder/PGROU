package com.pgrou.pdfcorrection;

import java.util.Comparator;

class ComparateurTriplet implements Comparator<Triplet> {

	public int compare(Triplet t1, Triplet t2) {
		int resultat = 0;
		if (t1.line > t2.line)
			resultat = 1;
		if (t1.line < t2.line)
			resultat = -1;
		if (t1.line == t2.line)
			resultat = 0;
		return resultat;
	}

}