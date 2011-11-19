import java.util.*;

import de.danielnaber.languagetool.rules.RuleMatch;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Démonstrateur 1
		demonstrateur_1();
		
		// Démonstrateur 2
		//demonstrateur_2();
	}
	
	


	/*
	 * Démonstration de PDFEntireCorrector
	 * Je vous conseille de commenter quelques truc si vous voulez pas avoir la console
	 * qui explose :)
	 * 
	 * Version 1 - Corrections faites sur le texte extrait du PDF.
	 */
	public static void demonstrateur_1() {
		// Mettre le path vers le PDF en argument
		PDFExtraction correctorPDF = new PDFExtraction("/Users/loukelder/Desktop/P.pdf");


		// Extraction du texte
		correctorPDF.textExtraction();
		String textToCorrect=correctorPDF.getExtractedText();
		System.out.println(textToCorrect);


		//finalResult est une liste d'objets Triplet, r�sumant l'ensemble des erreurs trouv�es
		//c'est � partir de �a qu'on cr�era tous les fichiers de sortie
		
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
			
			//test �ventuellement � revoir: on regarde juste ici si currentBadWord est l'entier de la ligne suivante
			
			if (Integer.parseInt(currentBadWord)==currentLine+1){
				//on a atteint un s�parateur de lignes: on passe � la ligne suivante
				currentLine++;
			}
			
			else{
				
			//cr�ation de la liste des suggestions du mot en cours
			List<String> currentSuggestion=new ArrayList<String>();
			j = orthoMistakesAndSuggestions.get(currentBadWord).iterator();
			
			while (j.hasNext()) {
				
				//ajout de la nouvelle suggestion dans la liste
				currentSuggestion.add(j.next());
			}
			
			//ajout de la nouvelle erreur dans la liste de Triplet
			finalResult.add(new Triplet(currentLine,currentBadWord,currentSuggestion));
			
			}
			
			//� quoi sert ce compteur? on limite le nombre de suggestions � 20?
			counter++;
		}
		


		// Correction grammaticale
		GrammaticalCorrection gramma=new GrammaticalCorrection();
		List<RuleMatch> grammMistakesAndInfos = gramma.grammaticalCorrection(textToCorrect);

		// Pour visualiser le contenu de grammMistakesAndInfos
		for (RuleMatch currentMistake : grammMistakesAndInfos) {
			
			//cr�ation du Triplet de l'erreur
			//� v�rifier: currentMistake.getMessage() renvoie bien le mot concern� par l'erreur?
			Triplet currentTriplet=new Triplet(currentMistake.getEndLine(),currentMistake.getMessage(),currentMistake.getSuggestedReplacements());
			
			//ajout du triplet � la finalResult
			finalResult.add(currentTriplet);
			
		/*	System.out.println("Potential error at line " +
					currentMistake.getEndLine() + ", column " +
					currentMistake.getColumn() + ": " + currentMistake.getMessage());
			System.out.println("Suggested correction: " + currentMistake.getSuggestedReplacements());
			*/
			
		}
		
	}

	
	
	/*
	 * Démonstration de PDFEntireCorrector
	 * 
	 * Version 2 - Si on veut utiliser un tyexte qu'on a déjà sans avoir a l'extraire du PDF.
	 */
	/*public static void demonstrateur_2() {
		// Texte à corriger
		String textToCorrect = new String("Moi je veu etre corige");
		
		// Création du correcteur avec le constructeur vide
		PDFEntireCorrection correctorPDF = new PDFEntireCorrection();
		
		// Correction ortho
		HashMap<String, List<String>> orthoMistakesAndSuggestions = correctorPDF.orthographicCorrection(textToCorrect);
		
		// Correction grammatical
		List<RuleMatch> grammMistakesAndInfos = correctorPDF.grammaticalCorrection(textToCorrect);
	}*/
}
