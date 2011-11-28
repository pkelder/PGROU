package com.pgrou.pdfcorrection;

public abstract class GrammaticalCorrection implements Corrector {
	/* Attributes */
	protected String textToCorrect;

	/* Constructors */
	public GrammaticalCorrection(String textToCorrect) {
		this.textToCorrect = textToCorrect;
	}

	/* Methods */

	@Override
	abstract public void correctText();

}
