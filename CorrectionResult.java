package com.pgrou.pdfcorrection;

import java.util.Iterator;
import java.util.List;

/*
 * Classe générant le résultat de la correction.
 * Pour nous : le HTML et le texte qui liste les erreurs
 */
public class CorrectionResult {
	/* Attributes */
	OrthographicCorrection orthographicCorrection;
	GrammaticalCorrection grammaticalCorrection;
	String htmlWithSuggestions;
	String listErrors;

	/* Constructors */
	public CorrectionResult(String textToCorrect,
			OrthographicCorrection orthographicCorrection,
			GrammaticalCorrection grammaticalCorrection) {
		this.htmlWithSuggestions = textToCorrect;
		this.orthographicCorrection = orthographicCorrection;
		this.grammaticalCorrection = grammaticalCorrection;
	}

	/* Methods */

	/*
	 * Génère le HTML avec les suggestions.
	 */
	public void makeHTML() {
		this.insertSuggestionsFromCorrector(this.grammaticalCorrection,
				"grammerror");
		this.insertSuggestionsFromCorrector(this.orthographicCorrection,
				"orthoerror");
		this.htmlWithSuggestions = "<div id=\"correction\">" + this.htmlWithSuggestions
				+ "</div>";
		
		// On remplace les retours à la ligne par <br/>
		this.htmlWithSuggestions = this.htmlWithSuggestions.replaceAll("\n", "<br/>");
	}

	/*
	 * Prend en argument le correcteur et le nom de la balise HTML correspondant
	 * au type d'erreur, et insère dans le texte les suggestions.
	 */
	private void insertSuggestionsFromCorrector(Corrector corrector,
			String markupName) {
		String result;
		String mistake;
		String suggestions = new String();
		List<String> listeSuggestion;
		Iterator<String> iterator;
		String regex;

		while (corrector.hasNextMistake()) {
			mistake = corrector.nextMistake();
			suggestions = "";
			
			// On enlève le caractère " des erreurs
			if (!mistake.equals("\"")) {
				// Sérialisation de la liste de suggestions
				listeSuggestion = corrector.nextSuggestions();
				iterator = listeSuggestion.iterator();
				while (iterator.hasNext()) {
					suggestions += iterator.next() + ',';
				}
				if (suggestions.length() > 2)
					suggestions = suggestions.substring(0, suggestions.length() - 1);
				
				regex = "(?<!<span class=\"(grammerror|orthoerror)\")" + mistake
						+ "(?!" + "</span>)";
				System.out.println("Regex = " + regex);
				result = "<span class=\"" + markupName + "\" suggestions=\""
						+ suggestions + "\">"
						+ mistake + "</span>";
				this.htmlWithSuggestions = this.htmlWithSuggestions.replaceAll(
						regex, result);
			}
		}
	}

	/* Getters */
	public String getHTMLWithSuggestions() {
		return this.htmlWithSuggestions;
	}
}
