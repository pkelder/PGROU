package com.pgrou.pdfcorrection;

import com.google.gson.Gson;

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
		this.htmlWithSuggestions = "<correction>" + this.htmlWithSuggestions
				+ "</correction>";
	}

	/*
	 * Prend en argument le correcteur et le nom de la balise HTML correspondant
	 * au type d'erreur, et insère dans le texte les suggestions.
	 */
	private void insertSuggestionsFromCorrector(Corrector corrector,
			String markupName) {
		String result;
		String mistake;
		String regex;
		Gson gson = new Gson();

		while (corrector.hasNextMistake()) {
			mistake = corrector.nextMistake();
			regex = "[(" + mistake + ")&&[^(<" + markupName + ".+</"
					+ markupName + ">)]]";
			System.out.println("Mistake replace all tiret : "
					+ mistake.replaceAll("-", "\\-"));
			System.out.println("Regex = " + regex);
			result = "<" + markupName + " suggestions=\""
					+ gson.toJson(corrector.nextSuggestions()) + "\">"
					+ mistake + "</" + markupName + ">";
			this.htmlWithSuggestions = this.htmlWithSuggestions.replaceAll(
					regex, result);

			/*
			 * // TEST //
			 * System.out.println("*********   HTML GENERATION   ************");
			 * // System.out.println("Mistake : " + mistake); //
			 * System.out.println("HTML markup : " + result); for (int k = 0; k
			 * < 2; k++) { System.out.println("ReplaceAll : " +
			 * this.htmlWithSuggestions.replaceAll(mistake, result)); }
			 */
		}
	}

	/* Getters */
	public String getHTMLWithSuggestions() {
		return this.htmlWithSuggestions;
	}
}
