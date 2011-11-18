import de.danielnaber.languagetool.JLanguageTool;
import de.danielnaber.languagetool.Language;
import de.danielnaber.languagetool.rules.RuleMatch;

import java.util.*;

public class GrammaticalCorrection {
	
	/*
	 * Méthode qui applique la correction grammaticale au texte donné en argument
	 * Retourne une liste de RuleMatch : List<RuleMatch>
	 * Un RuleMatch contient les infos d'une erreur. Il y a un certain nombre de méthode qui nous seront utiles
	 * pour gérer la correction dans notre appli. Toutes les méthodes sont visibles ici :
	 * http://www.languagetool.org/development/api/de/danielnaber/languagetool/rules/RuleMatch.html
	 */
	/*public List<RuleMatch> grammaticalCorrection() {
		return this.grammaticalCorrection(this.extractedText);
	}*/
	
	
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
	

}
