package com.pgrou.pdfcorrection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.danielnaber.languagetool.rules.RuleMatch;


public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Démonstrateur 1
		demonstrateur_1();
		
		// Démonstrateur 2
		demonstrateur_2();
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
		PDFEntireCorrection correctorPDF = new PDFEntireCorrection("/Users/loukelder/Desktop/P2.pdf");


		// Extraction du texte
		correctorPDF.textExtraction();
		System.out.println(correctorPDF.getExtractedText());


		// Correction orthographique
		HashMap<String, List<String>> orthoMistakesAndSuggestions = correctorPDF.orthographicCorrection();		// Analyse le texte extrait

		// Pour visualiser le contenu de orthoMistakesAndSuggestions (pour l'example)
		Set<String> badWords = orthoMistakesAndSuggestions.keySet();
		String currentBadWord = new String();
		String example = new String();
		Iterator<String> i = badWords.iterator();
		Iterator<String> j;
		int counter = 0;
		while (i.hasNext() && counter < 20) {
			currentBadWord = i.next();
			example = currentBadWord + " : ";
			j = orthoMistakesAndSuggestions.get(currentBadWord).iterator();
			while (j.hasNext()) {
				example += j.next() + "/";
			}
			example += "\n";
			counter++;
		}
		System.out.println(example);


		// Correction grammaticale
		List<RuleMatch> grammMistakesAndInfos = correctorPDF.grammaticalCorrection();

		// Pour visualiser le contenu de grammMistakesAndInfos (pour l'example)
		for (RuleMatch currentMistake : grammMistakesAndInfos) {
			System.out.println("Potential error at line " +
					currentMistake.getEndLine() + ", column " +
					currentMistake.getColumn() + ": " + currentMistake.getMessage());
			System.out.println("Suggested correction: " +
					currentMistake.getSuggestedReplacements());
		}
	}

	
	
	/*
	 * Démonstration de PDFEntireCorrector
	 * 
	 * Version 2 - Si on veut utiliser un tyexte qu'on a déjà sans avoir a l'extraire du PDF.
	 */
	public static void demonstrateur_2() {
		// Texte à corriger
		String textToCorrect = new String("Moi je veu etre corige");
		
		// Création du correcteur avec le constructeur vide
		PDFEntireCorrection correctorPDF = new PDFEntireCorrection();
		
		// Correction ortho
		HashMap<String, List<String>> orthoMistakesAndSuggestions = correctorPDF.orthographicCorrection(textToCorrect);
		
		// Correction grammatical
		List<RuleMatch> grammMistakesAndInfos = correctorPDF.grammaticalCorrection(textToCorrect);
	}
}
