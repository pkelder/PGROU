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

public class PDFExtraction {

	/***** Attributes *****/
	public String extractedText = new String();
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
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition( true );
                Rectangle rect = new Rectangle( 0, 70, 550, 783 );
                
                // voir pour passer en paramËtres la taille du texte (rectangle)
                
                stripper.addRegion( "class1", rect );
                List allPages = document.getDocumentCatalog().getAllPages();
                int i=allPages.size();
                for (int j=0; j<i; j++){
                
                	PDPage currentPage = (PDPage)allPages.get( j );
                	stripper.extractRegions( currentPage );
                
                	//System.out.println( "Text in the area:" + rect + " page " + (j+1));
                	//System.out.println( stripper.getTextForRegion( "class1" ) );
                	parsedText=parsedText+stripper.getTextForRegion("class1");

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

	/***** Getters *****/
	public String getExtractedText() {
		return this.extractedText;
	}

	/***** Setters *****/
	public void setExtractedText(String text) {
		this.extractedText = text;
	}

}
