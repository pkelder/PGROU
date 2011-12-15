package com.pgrou.pdfcorrection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ArrayList;

import com.stibocatalog.hunspell.Hunspell;

public class HunspellOrthographicCorrection extends OrthographicCorrection {

	/* Attributes */
	protected HashMap<String, List<String>> mistakesAndSuggestions;
	protected Set<String> mistakes;
	protected Iterator<String> iterator;
	protected String currentMistake;

	public HunspellOrthographicCorrection(String textToCorrect) {
		super(textToCorrect);
	}

	/*
	 * Méthode qui applique la correction orthographique au texte donné en
	 * argument. Retourne un HashMap contenant en clés les mots mal écrits, avec
	 * pour valeur correspondante la liste des suggestions.
	 * 
	 * Principe : Commme Hunspell ne peut corriger qu'un mot à la fois, il faut
	 * extraire les mots du texte, puis les passer un par un dans le correcteur.
	 * Si un mot est faut, on le met dans le HashMap avec sa liste de
	 * propositions de corrections.
	 * 
	 * L'extraction des mots du texte se fait en précisant les séparateurs. Au
	 * besoin vous pouvez compléter la liste ci-dessous suivant le schéma :
	 * "separ_1 | separ_2 | separ_3"
	 * 
	 * @param textToCorrect
	 */
	public void correctText() {
		/* Attributes */
		String separators = new String("\\s|\\.|:|,|;"); // Liste des
		// séparateurs qui déterminent comment découper le texte.
		String dictionaryPath = new String(
				"/home/tagazok/Documents/Cours/PGROU/Hunspell_Dictionaries/en_GB"); // Path
		// to the dictionary
		Hunspell.Dictionary dico = null; // Hunspell dico
		HashMap<String, List<String>> result = new HashMap<String, List<String>>(); // Résultat
		// final

		// Extraction des mots pour la ligne j du texte du texte
		String[] words = this.textToCorrect.split(separators);

		try {
			// Chargement du dictionnaire
			dico = Hunspell.getInstance().getDictionary(dictionaryPath);

			// Vérification de chaque mot et récupération des suggestions de
			// solution
			int length = words.length;
			String currentWord = new String();
			for (int i = 0; i < length; i++) {
				currentWord = words[i];
				// On exclut certains caractères
				if (dico.misspelled(currentWord)
						&& !"#!^$()[]{}?+*.\\|".contains(currentWord)) {
					result.put(currentWord, dico.suggest(currentWord));
				}
			}
			//System.out.println("Correction orthographique terminee !");
		} catch (Exception e) {
			System.out.println("Exception occured when loading dictionary : "
					+ e.getMessage());
		}

		// Retourne le résultat
		this.mistakesAndSuggestions = result;
		this.mistakes = result.keySet();
		this.iterator = this.mistakes.iterator();
	}

	/*
	 * Indique par vrai ou faux si il y a une autre erreur
	 */
	public boolean hasNextMistake() {
		boolean result = this.iterator.hasNext();
		// Si on arrive au bout de la liste, on ramène le curseur au début
		if (result == false) {
			this.iterator = this.mistakes.iterator();
		}
		return result;
	}

	/*
	 * Retourne l'erreur suivante
	 */
	public String nextMistake() {
		try {
			this.currentMistake = this.iterator.next();
		} catch (NoSuchElementException e) {
			System.out.println("No more mistake");
		}
		return this.currentMistake;
	}

	/*
	 * Retourne la liste de suggestion de l'erreur actuelle
	 */
	public List<String> nextSuggestions() {
		return this.mistakesAndSuggestions.get(this.currentMistake);
	}

	/*
	 * Retourne une tableau contenant les lignes de l'erreur (commence à 1)
	 * Tableau pour si l'erreur apparaît plusieurs fois
	 */
	public int[] nextMistakeLine() {
		// On découpe le texte au niveau de l'erreur
		String[] splittedText = this.textToCorrect.split(this.currentMistake);
		String[] separateLines;
		int length = splittedText.length - 1;
		int[] result = new int[length];
		int nbLines = 0;

		for (int i = 0; i < length; i++) {
			// On sépare les lignes suivant le caractère de retour à la ligne
			separateLines = splittedText[i].split("\n");
			nbLines += separateLines.length;
			result[i] = nbLines;
		}

		return result;
	}

	@Override
	public String nextMessage() {
		return null;
	}

}
