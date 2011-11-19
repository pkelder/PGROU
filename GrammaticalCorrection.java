import de.danielnaber.languagetool.JLanguageTool;
import de.danielnaber.languagetool.Language;
import de.danielnaber.languagetool.rules.RuleMatch;

import java.util.*;

public class GrammaticalCorrection implements GrammaticalCorrectionInterface {
	/* Attributes */
	String textToCorrect;
	List<RuleMatch> mistakesInfos;
	Iterator<RuleMatch> iterator;
	RuleMatch currentMistakeInfo;

	/* Constructors */
	public GrammaticalCorrection(String textToCorrect) {
		this.textToCorrect = textToCorrect;
	}

	/* Methods */

	/*
	 * Méthode qui applique la correction grammaticale au texte donné en
	 * argument Retourne une liste de RuleMatch : List<RuleMatch> Un RuleMatch
	 * contient les infos d'une erreur. Il y a un certain nombre de méthode qui
	 * nous seront utiles pour gérer la correction dans notre appli. Toutes les
	 * méthodes sont visibles ici :
	 * http://www.languagetool.org/development/api/de
	 * /danielnaber/languagetool/rules/RuleMatch.html
	 */
	@Override
	public void correctText() {
		JLanguageTool corrector = null; // Correcteur grammatical
		List<RuleMatch> mistakes = null; // Liste des erreurs
		try {
			// Sélection de la langue, création du correcteur
			corrector = new JLanguageTool(Language.ENGLISH);

			// Réglage obligatoire (pas chercher à comprendre)
			corrector.activateDefaultPatternRules();

			// Vérifie le texte
			mistakes = corrector.check(textToCorrect);
		} catch (Exception e) {
			System.out
					.println("Exception occured during gramatical corrector creation");
		}

		// Retourne la liste des erruers
		this.mistakesInfos = mistakes;
		iterator = mistakes.iterator();
	}

	/*
	 * Indique par vrai ou faux si il y a une autre erreur
	 * 
	 * @see GrammaticalCorrectionInterface#hasNextMistake()
	 */
	@Override
	public boolean hasNextMistake() {
		return this.iterator.hasNext();
	}
	
	/*
	 * Retourne l'erreur suivante
	 * @see GrammaticalCorrectionInterface#nextMistake()
	 */
	@Override
	public String nextMistake() {
		String mistake = null;
		try {
			this.currentMistakeInfo = this.iterator.next();
			int mistakeStartIndex = this.currentMistakeInfo.getFromPos();
			int mistakeEndIndex = this.currentMistakeInfo.getToPos();
			mistake = this.textToCorrect.substring(mistakeStartIndex,
					mistakeEndIndex);
		} catch (NoSuchElementException e) {
			System.out.println("No more mistake");
		}
		return mistake;
	}

	/*
	 * Retourne la liste de suggestion de l'erreur actuelle
	 * 
	 * @see GrammaticalCorrectionInterface#nextSuggestions()
	 */
	@Override
	public List<String> nextSuggestions() {
		return this.currentMistakeInfo.getSuggestedReplacements();
	}

	/*
	 * Retourne une tableau contenant les lignes de l'erreur
	 * 
	 * @see GrammaticalCorrectionInterface#nextMistakeLine()
	 */
	@Override
	public int nextMistakeLine() {
		return this.currentMistakeInfo.getLine();
	}

	/*
	 * Retourne un message d'information sur l'erreur
	 * 
	 * @see GrammaticalCorrectionInterface#nextMessage()
	 */
	@Override
	public String nextMessage() {
		return this.currentMistakeInfo.getMessage();
	}

}
