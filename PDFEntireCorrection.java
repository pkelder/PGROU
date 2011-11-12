import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.stibocatalog.hunspell.Hunspell;

import de.danielnaber.languagetool.JLanguageTool;
import de.danielnaber.languagetool.Language;
import de.danielnaber.languagetool.rules.RuleMatch;


public class PDFEntireCorrection {
	/*****  Attributes  *****/
	protected String extractedText = new String();
	protected String pathToPDF = new String();
	
	
	
	/*****  Constructors  *****/
	public PDFEntireCorrection(String pathToPDF) {
		this.pathToPDF = pathToPDF;
	}
	
	public PDFEntireCorrection() {}

	
	
	/*****  Methods  *****/
	
	
	/*
	 * Méthode qui extrait le texte du PDF. Prend le path du PDF, et retourne le texte extrait
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
		if (! file.isFile()) {
			System.err.println("The file " + this.pathToPDF + " doesn't exist !");
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
			System.out.println("An exception occured during parsing PDF Document and text extraction : " + e.getMessage());
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

	
	
	
	/*
	 * Méthode qui applique la correction orthographique au texte donné en argument.
	 * Retourne un HashMap contenant en clés les mots mal écrits, avec pour valeur
	 * correspondante la liste des suggestions.
	 * 
	 * Principe : Commme Hunspell ne peut corriger qu'un mot à la fois, il faut extraire les mots du texte,
	 * 			puis les passer un par un dans le correcteur. Si un mot est faut, on le met dans le HashMap
	 * 			avec sa liste de propositions de corrections.
	 * 
	 * L'extraction des mots du texte se fait en précisant les séparateurs. Au besoin vous pouvez compléter
	 * la liste ci-dessous suivant le schéma : "separ_1 | separ_2 | separ_3"
	 * 
	 * @param textToCorrect
	 */
	public HashMap<String, List<String>> orthographicCorrection() {
		return this.orthographicCorrection(this.extractedText);
	}
	
	
	public HashMap<String, List<String>> orthographicCorrection(String textToCorrect) {
		/*  Attributes  */
		String separators = new String("\\s|\\.|:|,|;");		// Liste des séparateurs qui déterminent comment découper le texte.
		String dictionaryPath = new String("/home/tagazok/Documents/Cours/PGROU/Hunspell_Dictionaries/en_GB");		// Path to the dictionary
		Hunspell.Dictionary dico = null;						// Hunspell dico
		HashMap<String, List<String>> result = new HashMap<String, List<String>>();		// Résultat final
		
		// Extraction des mots du texte
		String[] words = textToCorrect.split(separators);
		
		try {
			// Chargement du dictionnaire
			dico = Hunspell.getInstance().getDictionary(dictionaryPath);
			
			// Vérification de chaque mot et récupération des suggestions de solution
			int length = words.length;
			String currentWord = new String();
			for (int i=0; i<length; i++) {
				currentWord = words[i];
				if (dico.misspelled(currentWord)) {
					result.put(currentWord, dico.suggest(currentWord));
				}
			}
			System.out.println("Correction orthographique terminée !");
		} catch (Exception e) {
			System.out.println("Exception occured when loading dictionary : " + e.getMessage());
		}
		
		// Retourne le résultat
		return result;
	}
	
	
	
	/*
	 * Méthode qui applique la correction grammaticale au texte donné en argument
	 * Retourne une liste de RuleMatch : List<RuleMatch>
	 * Un RuleMatch contient les infos d'une erreur. Il y a un certain nombre de méthode qui nous seront utiles
	 * pour gérer la correction dans notre appli. Toutes les méthodes sont visibles ici :
	 * http://www.languagetool.org/development/api/de/danielnaber/languagetool/rules/RuleMatch.html
	 */
	public List<RuleMatch> grammaticalCorrection() {
		return this.grammaticalCorrection(this.extractedText);
	}
	
	
	public List<RuleMatch> grammaticalCorrection(String textToCorrect) {
		JLanguageTool corrector = null;			// Correcteur grammatical
		List<RuleMatch> mistakes = null;			// Liste des erreurs
		try {
			// Sélection de la langue, création du correcteur
			corrector = new JLanguageTool(Language.ENGLISH);
			
			// Réglage obligatoire (pas chercher à comprendre)
			corrector.activateDefaultPatternRules();
			
			// Vérifie le texte
			mistakes = corrector.check(textToCorrect);
		} catch (Exception e) {
			System.out.println("Exception occured during gramatical corrector creation");
		}
		
		// Retourne la liste des erruers
		return mistakes;
	}
	
	
	
	
	
	/***** Getters *****/
	public String getExtractedText() { return this.extractedText; }
	
	
	
	
	/***** Setters *****/
	public void setExtractedText(String text) {
		this.extractedText = text;
	}
	
}
