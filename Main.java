import java.util.*;

import de.danielnaber.languagetool.rules.RuleMatch;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// D√©monstrateur 1
		demonstrateur_1();
		
		// D√©monstrateur 2
		//demonstrateur_2();
	}
	
	


	/*
	 * D√©monstration de PDFEntireCorrector
	 * Je vous conseille de commenter quelques truc si vous voulez pas avoir la console
	 * qui explose :)
	 * 
	 * Version 1 - Corrections faites sur le texte extrait du PDF.
	 */
	public static void demonstrateur_1() {
		// Mettre le path vers le PDF en argument
		PDFExtractor correctorPDF = new PDFExtractor("/Users/loukelder/Desktop/P.pdf");


		// Extraction du texte
		correctorPDF.textExtraction();
		String textToCorrect=correctorPDF.getExtractedText();
		System.out.println(textToCorrect);


		//finalResult est une liste d'objets Triplet, résumant l'ensemble des erreurs trouvées
		//c'est à partir de ça qu'on créera tous les fichiers de sortie
		
		List<Triplet> finalResult=new ArrayList<Triplet>();
		
		// Correction orthographique
		OrthographicCorrection ortho=new OrthographicCorrection();
		HashMap<String, List<String>> orthoMistakesAndSuggestions = ortho.orthographicCorrection(textToCorrect);		// Analyse le texte extrait

		// Pour visualiser le contenu de orthoMistakesAndSuggestions 
		
		Set<String> badWords = orthoMistakesAndSuggestions.keySet();
		String currentBadWord = new String();
		Iterator<String> i = badWords.iterator();
		Iterator<String> j;
		int currentLine=1;
		int counter = 0;
		
		while (i.hasNext() && counter < 20) {
			currentBadWord = i.next();
			
			//test éventuellement à revoir: on regarde juste ici si currentBadWord est l'entier de la ligne suivante
			
			if (Integer.parseInt(currentBadWord)==currentLine+1){
				//on a atteint un séparateur de lignes: on passe à la ligne suivante
				currentLine++;
			}
			
			else{
				
			//création de la liste des suggestions du mot en cours
			List<String> currentSuggestion=new ArrayList<String>();
			j = orthoMistakesAndSuggestions.get(currentBadWord).iterator();
			
			while (j.hasNext()) {
				
				//ajout de la nouvelle suggestion dans la liste
				currentSuggestion.add(j.next());
			}
			
			//ajout de la nouvelle erreur dans la liste de Triplet
			finalResult.add(new Triplet(currentLine,currentBadWord,currentSuggestion));
			
			}
			
			//à quoi sert ce compteur? on limite le nombre de suggestions à 20?
			counter++;
		}
		


		// Correction grammaticale
		GrammaticalCorrection gramma=new GrammaticalCorrection();
		List<RuleMatch> grammMistakesAndInfos = gramma.grammaticalCorrection(textToCorrect);

		// Pour visualiser le contenu de grammMistakesAndInfos
		for (RuleMatch currentMistake : grammMistakesAndInfos) {
			
			//création du Triplet de l'erreur
			//à vérifier: currentMistake.getMessage() renvoie bien le mot concerné par l'erreur?
			Triplet currentTriplet=new Triplet(currentMistake.getEndLine(),currentMistake.getMessage(),currentMistake.getSuggestedReplacements());
			
			//ajout du triplet à la finalResult
			finalResult.add(currentTriplet);
			
		/*	System.out.println("Potential error at line " +
					currentMistake.getEndLine() + ", column " +
					currentMistake.getColumn() + ": " + currentMistake.getMessage());
			System.out.println("Suggested correction: " + currentMistake.getSuggestedReplacements());
			*/
			
		}
		
	}

	
	
	/*
	 * D√©monstration de PDFEntireCorrector
	 * 
	 * Version 2 - Si on veut utiliser un tyexte qu'on a d√©j√† sans avoir a l'extraire du PDF.
	 */
	/*public static void demonstrateur_2() {
		// Texte √† corriger
		String textToCorrect = new String("Moi je veu etre corige");
		
		// Cr√©ation du correcteur avec le constructeur vide
		PDFEntireCorrection correctorPDF = new PDFEntireCorrection();
		
		// Correction ortho
		HashMap<String, List<String>> orthoMistakesAndSuggestions = correctorPDF.orthographicCorrection(textToCorrect);
		
		// Correction grammatical
		List<RuleMatch> grammMistakesAndInfos = correctorPDF.grammaticalCorrection(textToCorrect);
	}*/
}
