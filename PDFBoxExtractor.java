package com.pgrou.pdfcorrection;

import java.io.File;
import java.io.IOException;
import java.awt.Rectangle;
import java.io.*;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;

public class PDFBoxExtractor extends PDFSimpleExtractor{
	
	
	protected int nbCol;
	
	/***** Constructors *****/
	public PDFBoxExtractor(String pathToPDF) {
		super(pathToPDF);
	}

	public PDFBoxExtractor() {
	}
	
	public PDFBoxExtractor(String pathToPDF, int nbCol) {
		this.pathToPDF = pathToPDF;
		this.nbCol=nbCol;		
	}
	
	public PDFBoxExtractor(int nbCol) {
		this.nbCol=nbCol;		
	}
	
	/**** Methods *****/
	
	/*
	 * Méthode qui extrait le texte du PDF. Prend le path du PDF, et retourne le
	 * texte extrait
	 * 
	 * @param pathToPDF
	 */
	public void textExtraction() throws IOException, CryptographyException {
		String parsedText = null;
		PDDocument document = null;

		// Ouverture du fichier
		File file = new File(this.pathToPDF);

		// Vérifie que le fichier existe, sinon affiche un message d'erreur
		if (!file.isFile()) {
			System.err.println("The file " + this.pathToPDF
					+ " doesn't exist !");
		}
		else{
		try {
			 document = PDDocument.load(file);
                if( document.isEncrypted() )
                {
                    try
                    {
                        document.decrypt( "" );
                    }
                    catch( InvalidPasswordException e )
                    {
                        System.err.println( "Error: Document is encrypted with a password." );
                        System.exit( 1 );
                    }
                }
			List allPages = document.getDocumentCatalog().getAllPages();
			int x = (int) document.getPageFormat(0).getWidth();
			int y = (int) document.getPageFormat(0).getHeight();
			int margeHaut = 70;
			int margeBas = 30;
			/*int margeGauche = 10;
			 *int margeDroite = 10;
			*/
			
			System.out.println(x);
			System.out.println(y);
			
            int i=allPages.size();
            for (int j=0; j<i; j++){
				PDPage currentPage = (PDPage)allPages.get( j );
				parsedText=parsedText+(" page " + (j+1))+"\n";
				
                for (int k=0; k<this.nbCol; k++){
				
					PDFTextStripperByArea stripper = new PDFTextStripperByArea();
					stripper.setSortByPosition( true );
					  // voir pour passer en paramËtres la taille du texte (rectangle) et le nb de colonnes
					Rectangle rect = new Rectangle( 0+k*x/nbCol, 70, x/this.nbCol, y-margeHaut -margeBas );
					stripper.addRegion("colonne", rect);
					stripper.extractRegions( currentPage );
					parsedText=parsedText+ "colonne " + (k+1)+"\n";
					parsedText=parsedText+stripper.getTextForRegion("colonne");
                }
            }
		}

	// Ferme ce qui doit être fermé
            finally
            {
                if( document != null )
                {
                    document.close();
                }
            }}
  
		// Retourne le texte extrait du PDF
		this.extractedText = parsedText;

           }
	
	/**** Getters et Setters ****/
	
	public int getNbCol() {
		return nbCol;
	}

	public void setNbCol(int nbCol) {
		this.nbCol = nbCol;
	}
	

}
