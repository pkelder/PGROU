package com.pgrou.pdfcorrection;

import java.io.IOException;

import org.apache.pdfbox.exceptions.CryptographyException;

public interface PDFExtractor {
	public void textExtraction() throws IOException, CryptographyException;

	public String getExtractedText();
}
