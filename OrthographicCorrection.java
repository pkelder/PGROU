import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.stibocatalog.hunspell.Hunspell;

public class OrthographicCorrection implements Corrector {
	/* Attributes */
	String textToCorrect;
	HashMap<String, List<String>> mistakesAndSuggestions;
	Set<String> mistakes;
	Iterator<String> iterator;
	String currentMistake;

	/* Constructors */

	/*
	 * Récupère le texte et lance la correction
	 */
	public OrthographicCorrection(String textToCorrect) {
		this.textToCorrect = textToCorrect;
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
		/*
		 * Solution choisie pour garder en m�moire la ligne de l'erreur (pas
		 * forc�ment la meilleure): � chaque fin de ligne, on rajoute dans
		 * result l'�lement <'numero',<'numero'>> contenant le numero de la
		 * ligne Ainsi on ne s'ennuie pas avec un autre objet. Pas de confusion
		 * possible: en temps normal, si la liste des corrections existe, elle
		 * est diff�rente du String de d�part
		 */

		/* Attributes */
		String separators = new String("\\s|\\.|:|,|;"); // Liste des
		// séparateurs qui
		// déterminent
		// comment découper
		// le texte.
		String lineSeparator = new String("\\n");
		String dictionaryPath = new String(
				"/home/tagazok/Documents/Cours/PGROU/Hunspell_Dictionaries/en_GB"); // Path
		// to
		// the
		// dictionary
		Hunspell.Dictionary dico = null; // Hunspell dico
		HashMap<String, List<String>> result = new HashMap<String, List<String>>(); // Résultat
		// final

		// on commence ici par s�parer chaque ligne du texte

		String[] line = this.textToCorrect.split(lineSeparator);
		int numberOfLine = line.length;

		// on parcourt chaque ligne l'une apr�s l'autre
		for (int j = 0; j < numberOfLine; j++) {

			// on initialise result avec le numero de la ligne en cours: j (on
			// note j+1 car ligne1=0 dans le code)
			List<String> newList = new ArrayList<String>();
			newList.add(String.valueOf(j + 1));
			result.put(String.valueOf(j + 1), newList);

			// Extraction des mots pour la ligne j du texte du texte
			String[] words = line[j].split(separators);

			try {
				// Chargement du dictionnaire
				dico = Hunspell.getInstance().getDictionary(dictionaryPath);

				// Vérification de chaque mot et récupération des suggestions de
				// solution
				int length = words.length;
				String currentWord = new String();
				for (int i = 0; i < length; i++) {
					currentWord = words[i];
					if (dico.misspelled(currentWord)) {
						result.put(currentWord, dico.suggest(currentWord));
					}
				}
				System.out.println("Correction orthographique terminée !");
			} catch (Exception e) {
				System.out
						.println("Exception occured when loading dictionary : "
								+ e.getMessage());
			}

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
		return this.iterator.hasNext();
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
		int length = splittedText.length - 1;
		int[] result = new int[length];
		// On compte le nombre d'occurrence de retour à la ligne dans chaque
		// morceau
		int numLine = 0;
		for (int i = 0; i < length; i++) {
			int newLineIndex = 0;
			int startIndex = 0;
			while (newLineIndex != -1) {
				numLine++;
				newLineIndex = splittedText[i].indexOf("\\n", startIndex);
				startIndex = newLineIndex + 2;
			}
			result[i] = numLine;
		}

		return result;
	}

	@Override
	public String nextMessage() {
		return null;
	}

}
