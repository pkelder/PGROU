import java.io.*;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFExtraction implements PDFExtractor {

	/***** Attributes *****/
	protected String extractedText = new String();
	protected String pathToPDF = new String();

	/***** Constructors *****/
	public PDFExtraction(String pathToPDF) {
		this.pathToPDF = pathToPDF;
	}

	public PDFExtraction() {
	}

	/*
	 * Méthode qui extrait le texte du PDF. Prend le path du PDF, et retourne le
	 * texte extrait
	 * 
	 * @param pathToPDF
	 */
	public void textExtraction() {
		PDFParser parser = null;
		String parsedText = null;
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;

		// Ouverture du fichier
		File file = new File(this.pathToPDF);

		// Vérifie que le fichier existe, sinon affiche un message d'erreur
		if (!file.isFile()) {
			System.err.println("The file " + this.pathToPDF
					+ " doesn't exist !");
		}
		// Crée un PDFParser
		try {
			parser = new PDFParser(new FileInputStream(file));
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser : " + e.getMessage());
		}
		// Parse le fichier et extrait le texte
		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
			System.out.println("Extraction du texte réussie !");
		} catch (Exception e) {
			System.out
					.println("An exception occured during parsing PDF Document and text extraction : "
							+ e.getMessage());
		}
		// Ferme ce qui doit être fermé
		finally {
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Retourne le texte extrait du PDF
		this.extractedText = parsedText;
	}

	/***** Getters *****/
	public String getExtractedText() {
		return this.extractedText;
	}

	/***** Setters *****/
	public void setExtractedText(String text) {
		this.extractedText = text;
	}

}
