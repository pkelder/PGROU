package com.pgrou.pdfcorrection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import de.danielnaber.languagetool.rules.RuleMatch;

public class Main {

	/**
	 * @param args
	 * @throws CryptographyException
	 * @throws IOException
	 */
	public static void main(String[] args) {
		// DÃ©monstrateur 1
		// demonstrateur_1();

		// DÃ©monstrateur 2
		// demonstrateur_2();

		// DÃ©monstrateur 3
		demonstrateur_3();
	}

	/*
	 * DÃ©monstrateur 3 Utilise les interfaces Corrector et Extractor et produit
	 * le HTML avec les suggestions Test fait sur un PDF simple colonne que j'ai
	 * fait, en Anglais, avec des fautes volontaires.
	 */

	public static void demonstrateur_3() {
		// Extraction du texte
		PDFSimpleExtractor extraction = (PDFSimpleExtractor) getPDFExtractor("/Users/loukelder/Desktop/P.pdf");

		try {
			extraction.textExtraction();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'exctraction");
			e.printStackTrace();
		} catch (CryptographyException e) {
			System.out.println("Erreur de cryptographie lors de l'exctraction");
			e.printStackTrace();
		}
		// System.out.println(extraction.getExtractedText());

		// Grammatical correction
		GrammaticalCorrection gramm = (GrammaticalCorrection) getGrammaticalCorrector(extraction
				.getExtractedText());

		gramm.correctText();
		System.out.println("GRAMMATICAL CORRECTION");

		displayErrors(gramm);

		ArrayList<Triplet> result = putErrorsInTriplet(gramm);

		// Orthographic correction
		OrthographicCorrection ortho = (OrthographicCorrection) getOrthographicCorrector(extraction
				.getExtractedText());

		ortho.correctText();
		System.out.println("ORTHOGRAPHIC CORRECTION");

		// displayErrors(ortho);

		// result.addAll(putErrorsInTriplet(ortho));

		Collections.sort(result, new ComparateurTriplet());

		writeErrors(result);

		// HTML (Ne marche pas encore. Il faut que je demande de l'aide aux
		// profs pour une regex un peu compliquÃ©.

		System.out.println("HTML");
		CorrectionResult htmlFactory = new CorrectionResult(
				extraction.getExtractedText(), ortho, gramm);

		htmlFactory.makeHTML();
		System.out.println(htmlFactory.getHTMLWithSuggestions());
	}

	/*
	 * Prend un Corrector, et affiche les erreurs dans la console. TrÃ¨s utile
	 * pour tester !
	 */
	protected static void displayErrors(Corrector corrector) {

		Iterator<String> i;
		String suggestions = null;
		while (corrector.hasNextMistake()) {
			System.out.println("Error : " + corrector.nextMistake());
			for (int j = 0; j < corrector.nextMistakeLine().length; j++) {
				System.out.println("On line : "
						+ corrector.nextMistakeLine()[j]);

			}
			System.out.println("Message : " + corrector.nextMessage());

			i = corrector.nextSuggestions().iterator();
			suggestions = "";
			while (i.hasNext()) {
				suggestions += i.next() + " / ";
			}
			System.out.println("Suggestions : " + suggestions);
			System.out.println("");
		}

	}

	/*
	 * crit dans un fichier txt la liste des erreurs contenant dans le
	 * ArrayList<Triplet>
	 */

	protected static void writeErrors(ArrayList<Triplet> list) {

		// cration du nouveau fichier

		File fichier = new File("/Users/loukelder/Desktop/error-liste.txt");
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(fichier));
		} catch (IOException e) {
			System.out.println("cration du fichier impossible");
			e.printStackTrace();
		}

		// parcourt de la liste et criture

		for (Triplet t : list) {

			out.write("On line : " + t.getLine());
			out.println();

			out.write("Word : " + t.getWord());
			out.println();

			out.write("Message : " + t.getMessage());
			out.println();

			List<String> suggestions = t.getCorrection();
			String currentSuggestion = null;
			for (String s : suggestions) {
				currentSuggestion += "/" + s;
			}
			out.write("Suggestions : " + currentSuggestion);
			out.println();

		}

		out.close(); // Ferme le flux du fichier, sauvegardant ainsi les donnes

	}

	protected static ArrayList<Triplet> putErrorsInTriplet(Corrector corrector) {

		Iterator<String> i;
		String suggestions = null;
		ArrayList<Triplet> array = new ArrayList<Triplet>();

		while (corrector.hasNextMistake()) {
			Triplet currentTriplet = new Triplet();
			currentTriplet.word = corrector.nextMistake();
			currentTriplet.line = corrector.nextMistakeLine()[0];
			currentTriplet.message = corrector.nextMessage();
			currentTriplet.correction = new ArrayList<String>();

			i = corrector.nextSuggestions().iterator();
			suggestions = "";
			while (i.hasNext()) {
				// suggestions += i.next() + " / ";
				currentTriplet.correction.add(i.next());
			}

			System.out.println("Suggestions : " + suggestions);

			array.add(currentTriplet);
		}
		return array;

	}

	/*
	 * DÃ©monstration de PDFEntireCorrector Je vous conseille de commenter
	 * quelques truc si vous voulez pas avoir la console qui explose :)
	 * 
	 * Version 1 - Corrections faites sur le texte extrait du PDF.
	 */
	/*
	 * public static void demonstrateur_1() throws IOException,
	 * CryptographyException { // Mettre le path vers le PDF en argument int
	 * nbCol = 2; PDFSimpleExtractor correctorPDF = (PDFSimpleExtractor)
	 * getPDFExtractor("/Users/loukelder/Desktop/P2.pdf");
	 * 
	 * // Extraction du texte correctorPDF.textExtraction(); String
	 * textToCorrect=correctorPDF.getExtractedText();
	 * System.out.println(textToCorrect);
	 * 
	 * 
	 * //finalResult est une liste d'objets Triplet, rï¿½sumant l'ensemble des
	 * erreurs trouvï¿½es //c'est ï¿½ partir de ï¿½a qu'on crï¿½era tous les
	 * fichiers de sortie
	 * 
	 * List<Triplet> finalResult=new ArrayList<Triplet>();
	 * 
	 * // Correction orthographique OrthographicCorrection ortho=new
	 * OrthographicCorrection(); HashMap<String, List<String>>
	 * orthoMistakesAndSuggestions =
	 * ortho.orthographicCorrection(textToCorrect); // Analyse le texte extrait
	 * 
	 * // Pour visualiser le contenu de orthoMistakesAndSuggestions
	 * 
	 * Set<String> badWords = orthoMistakesAndSuggestions.keySet(); String
	 * currentBadWord = new String(); Iterator<String> i = badWords.iterator();
	 * Iterator<String> j; int currentLine=1; int counter = 0;
	 * 
	 * while (i.hasNext() && counter < 20) { currentBadWord = i.next();
	 * 
	 * //test ï¿½ventuellement ï¿½ revoir: on regarde juste ici si
	 * currentBadWord est l'entier de la ligne suivante
	 * 
	 * if (Integer.parseInt(currentBadWord)==currentLine+1){ //on a atteint un
	 * sï¿½parateur de lignes: on passe ï¿½ la ligne suivante currentLine++; }
	 * 
	 * else{
	 * 
	 * //crï¿½ation de la liste des suggestions du mot en cours List<String>
	 * currentSuggestion=new ArrayList<String>(); j =
	 * orthoMistakesAndSuggestions.get(currentBadWord).iterator();
	 * 
	 * while (j.hasNext()) {
	 * 
	 * //ajout de la nouvelle suggestion dans la liste
	 * currentSuggestion.add(j.next()); }
	 * 
	 * //ajout de la nouvelle erreur dans la liste de Triplet
	 * finalResult.add(new
	 * Triplet(currentLine,currentBadWord,currentSuggestion));
	 * 
	 * }
	 * 
	 * //ï¿½ quoi sert ce compteur? on limite le nombre de suggestions ï¿½ 20?
	 * counter++; }
	 * 
	 * 
	 * 
	 * // Correction grammaticale GrammaticalCorrection gramma=new
	 * GrammaticalCorrection(); List<RuleMatch> grammMistakesAndInfos =
	 * gramma.grammaticalCorrection(textToCorrect);
	 * 
	 * // Pour visualiser le contenu de grammMistakesAndInfos for (RuleMatch
	 * currentMistake : grammMistakesAndInfos) {
	 * 
	 * //crï¿½ation du Triplet de l'erreur //ï¿½ vï¿½rifier:
	 * currentMistake.getMessage() renvoie bien le mot concernï¿½ par l'erreur?
	 * Triplet currentTriplet=new
	 * Triplet(currentMistake.getEndLine(),currentMistake
	 * .getMessage(),currentMistake.getSuggestedReplacements());
	 * 
	 * //ajout du triplet ï¿½ la finalResult finalResult.add(currentTriplet);
	 * 
	 * System.out.println("Potential error at line " +
	 * currentMistake.getEndLine() + ", column " + currentMistake.getColumn() +
	 * ": " + currentMistake.getMessage());
	 * System.out.println("Suggested correction: " +
	 * currentMistake.getSuggestedReplacements());
	 * 
	 * 
	 * }
	 */

	public static PDFExtractor getPDFExtractor(String path) {
		return new PDFBoxExtractor(path);
	}

	public static GrammaticalCorrection getGrammaticalCorrector(String text) {
		return new LanguageToolGrammaticalCorrection(text);
	}

	public static OrthographicCorrection getOrthographicCorrector(String text) {
		return new HunspellOrthographicCorrection(text);
	}
}
