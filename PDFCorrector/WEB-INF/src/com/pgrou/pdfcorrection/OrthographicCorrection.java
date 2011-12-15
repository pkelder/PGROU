package com.pgrou.pdfcorrection;

public abstract class OrthographicCorrection implements Corrector {

	/* Attributes */
	protected String textToCorrect;

	/* Constructors */

	public OrthographicCorrection(String textToCorrect) {
		this.textToCorrect = textToCorrect;
	}

	abstract public void correctText();

}
