import java.util.*;
import com.stibocatalog.hunspell.Hunspell;

public class OrthographicCorrection {

	/*
	 * Méthode qui applique la correction orthographique au texte donné en
	 * argument. Retourne un HashMap contenant en clés les mots mal écrits,
	 * avec pour valeur correspondante la liste des suggestions.
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

	/*
	 * public HashMap<String, List<String>> orthographicCorrection() { return
	 * this.orthographicCorrection(this.extractedText); }
	 */

	public HashMap<String, List<String>> orthographicCorrection(String textToCorrect) {
		/*
		 * Solution choisie pour garder en m�moire la ligne de l'erreur (pas forc�ment la meilleure):
		 * � chaque fin de ligne, on rajoute dans result l'�lement <'numero',<'numero'>> contenant le numero de la ligne
		 * Ainsi on ne s'ennuie pas avec un autre objet. Pas de confusion possible: en temps normal,
		 * si la liste des corrections existe, elle est diff�rente du String de d�part 
		 */
		
		
		
		
		/* Attributes */
		String separators = new String("\\s|\\.|:|,|;"); // Liste des
															// séparateurs qui
															// déterminent
															// comment découper
															// le texte.
		String lineSeparator=new String("\\n");
		String dictionaryPath = new String("/home/tagazok/Documents/Cours/PGROU/Hunspell_Dictionaries/en_GB"); // Path
																					// to
																					// the
																					// dictionary
		Hunspell.Dictionary dico = null; // Hunspell dico
		HashMap<String, List<String>> result = new HashMap<String, List<String>>(); // Résultat
																					// final
		
		//on commence ici par s�parer chaque ligne du texte
		
		String[] line=textToCorrect.split(lineSeparator);
		int numberOfLine=line.length;
		
		
		//on parcourt chaque ligne l'une apr�s l'autre
		
		for (int j=0;j<numberOfLine;j++){
			
			//on initialise result avec le numero de la ligne en cours: j (on note j+1 car ligne1=0 dans le code)
			List<String> newList = new ArrayList<String>(); 
			newList.add(String.valueOf(j+1)); 
			result.put(String.valueOf(j+1),newList);
			
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
			System.out.println("Exception occured when loading dictionary : "
					+ e.getMessage());
		}

		}
		// Retourne le résultat
		return result;
	}

}
