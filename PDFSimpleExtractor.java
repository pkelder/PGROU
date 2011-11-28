package com.pgrou.pdfcorrection;

import java.io.IOException;

import org.apache.pdfbox.exceptions.CryptographyException;

public abstract class PDFSimpleExtractor implements PDFExtractor {

	/***** Attributes *****/
	protected String extractedText = new String();
	protected String pathToPDF = new String();

	/***** Constructors *****/
	public PDFSimpleExtractor(String pathToPDF) {
		this.pathToPDF = pathToPDF;
	}

	public PDFSimpleExtractor() {
	}

	/*
	 * Méthode qui extrait le texte du PDF. Prend le path du PDF, et remplit
	 * l'attribut extractedText avec le texte extrait
	 */

	abstract public void textExtraction() throws IOException,
			CryptographyException;

	/***** Getters *****/
	public String getExtractedText() {
		return this.extractedText;
	}

	/***** Setters *****/
	public void setExtractedText(String text) {
		this.extractedText = text;
	}

	// fait le lien entre PDFSimpleExtractor et l'extracteur choisit

}
