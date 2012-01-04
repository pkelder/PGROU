package com.pgrou.pdfcorrection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import org.apache.pdfbox.exceptions.CryptographyException;



public class Main {

	/**
	 * @param args
	 * @throws CryptographyException
	 * @throws IOException
	 */
	
	public static void main(String[] args) {
		
		
	}

	/*
	 * correction_final Utilise les interfaces Corrector et Extractor et produit
	 * le HTML avec les suggestions Test fait sur un PDF simple colonne que j'ai
	 * fait, en Anglais, avec des fautes volontaires.
	 */

	
	public static String correction_final(String path,String nbCol,String margeHaut,String margeBas) {
		
		
		//System.out.println("Entree dans la correction finale, avec pour valeurs: \n");
		//System.out.println("nb de colonnes: "+nbCol+"\n");
		//System.out.println("margeHaute: "+margeHaut+"\n");
		//System.out.println("margeBas: "+margeBas+"\n");
		
		// Extraction du texte
		PDFSimpleExtractor extraction = (PDFSimpleExtractor) getPDFExtractor(path,nbCol,margeHaut,margeBas);

		try {
			extraction.textExtraction();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'exctraction");
			e.printStackTrace();
		} catch (CryptographyException e) {
			System.out.println("Erreur de cryptographie lors de l'exctraction");
			e.printStackTrace();
		}
		
		//System.out.println("voici le texte"+extraction.getExtractedText());
		
		
		// Grammatical correction
		GrammaticalCorrection gramm = (GrammaticalCorrection) getGrammaticalCorrector(extraction
				.getExtractedText());

		gramm.correctText();
		System.out.println("GRAMMATICAL CORRECTION");

		//displayErrors(gramm);

		ArrayList<Triplet> result = putErrorsInTriplet(gramm);

		// Orthographic correction
		OrthographicCorrection ortho = (OrthographicCorrection) getOrthographicCorrector(extraction
				.getExtractedText());

	/*	ortho.correctText();
		System.out.println("ORTHOGRAPHIC CORRECTION");

		// displayErrors(ortho);

		 result.addAll(putErrorsInTriplet(ortho));

		Collections.sort(result, new ComparateurTriplet()); */

		writeErrors(result,path);


		System.out.println("HTML");
		
		
		CorrectionResult htmlFactory = new CorrectionResult(
				extraction.getExtractedText(), ortho, gramm);

		htmlFactory.makeHTML();
			
		writeHTML(htmlFactory.getHTMLWithSuggestions(),path);
		
		return htmlFactory.getHTMLWithSuggestions();
	}

	/*
	 * Prend un Corrector, et affiche les erreurs dans la console. TrÃƒÂ¨s utile
	 * pour tester !
	 */
	protected static void displayErrors(Corrector corrector) {

		Iterator<String> i;
		String suggestions = null;
		while (corrector.hasNextMistake()) {
			//System.out.println("Error : " + corrector.nextMistake());
			for (int j = 0; j < corrector.nextMistakeLine().length; j++) {
				System.out.println("On line : "
						+ corrector.nextMistakeLine()[j]);

			}
			//System.out.println("Message : " + corrector.nextMessage());

			i = corrector.nextSuggestions().iterator();
			suggestions = "";
			while (i.hasNext()) {
				suggestions += i.next() + " / ";
			}
			//System.out.println("Suggestions : " + suggestions);
			//System.out.println("");
		}

	}

	
	
	protected static void writeHTML(String html,String path) {
		

		//System.out.println("entree dans writeHTML");
		
		
		//rŽcupŽration du bon chemin
				int i=path.lastIndexOf("/");
				path=path.substring(0,i);
				
		//on ajoute l'en-tte
		String header="<!DOCTYPE html><br/><html><br/><head><br/><meta charset='utf-8'/><title>PDFCorrector</title><style type='text/css'><%@ include file='css/PDFCorrectorInterface.css' %></style><script type='text/JavaScript'><%@ include file='script/jQuery.js' %></script><script type='text/JavaScript'><%@ include file='script/PDFCorrectionInterface.js' %></script></head><body>";
		//on ajoute le footer
		String avantBouton="<div id='suggestions'><div class='suggestion'>Item 1</div><div class='suggestion'>Item 2</div><div class='suggestion'>Item 3</div><div class='suggestion'>Item 4</div></div>";
		//on ajoute le bouton
		String bouton="<form id='sendCorrectedText' name='sendCorrected' method='post' action='<%=request.getContextPath()%>/WriteFileCorrection?action=writecorrection' enctype='multipart/form-data' onsubmit='return sendCorrectedText()'><input type='hidden' name='correctedText' value=''/><input type='submit' value='Valider le texte' name='writecorrection'/></form>";
		String footer="</body></html>";
				
		html=header+html+avantBouton+bouton+footer;
	
		
		// crÂŽation du nouveau fichier
		
		//File fichier = new File(path+"/online_correction.jsp");
		int j=path.lastIndexOf("/");
		path=path.substring(0,j);
		File fichier = new File(path+"/online_correction.jsp");
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(fichier));
		} catch (IOException e) {
			//System.out.println("creation du fichier impossible");
			e.printStackTrace();
		}
			out.write(html);
			out.println();
			out.close(); // Ferme le flux du fichier, sauvegardant ainsi les donnŽes
			//System.out.println("sortie de writeHTML");
	}	

	
	/*
	 * ÂŽcrit dans un fichier txt la liste des erreurs contenant dans le
	 * ArrayList<Triplet>
	 */

	protected static void writeErrors(ArrayList<Triplet> list,String path) {

		// crÂŽation du nouveau fichier
		//System.out.println("entree dans write error");
		
		
		//rŽcupŽration du bon chemin
		int i=path.lastIndexOf("/");
		path=path.substring(0,i);
		
		File fichier = new File(path+"/error-liste.txt");
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(fichier));
		} catch (IOException e) {
			//System.out.println("creation du fichier impossible");
			e.printStackTrace();
		}

		// parcourt de la liste et ÂŽcriture

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
			out.println("\n");

		}

		out.close(); // Ferme le flux du fichier, sauvegardant ainsi les donnÂŽes

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

			//System.out.println("Suggestions : " + suggestions);

			array.add(currentTriplet);
		}
		return array;

	}

	
	public static String readFile(String path){
		//System.out.println("entree dans readFile");
		String chaine="";
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(path); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				//System.out.println(ligne);
				chaine+=ligne+"\n";
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		//System.out.println(chaine);
		return chaine;
	}

	
	public static PDFExtractor getPDFExtractor(String path,String nbCol,String mH,String mB) {
		return new PDFBoxExtractor(path,Integer.parseInt(nbCol),Integer.parseInt(mH),Integer.parseInt(mB));
	}

	public static GrammaticalCorrection getGrammaticalCorrector(String text) {
		return new LanguageToolGrammaticalCorrection(text);
	}

	public static OrthographicCorrection getOrthographicCorrector(String text) {
		return new HunspellOrthographicCorrection(text);
	}
}
