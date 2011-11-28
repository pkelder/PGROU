import java.io.*;
import java.util.*;

/**
 * @opt all
 */
class ComparateurTriplet implements Comparator<Triplet> {
	public int compare(Triplet t1,Triplet t2) {}
}

/**
 * @opt all
 * @has "" - "" OrthographicCorrection
 * @has "" - "" GrammaticalCorrection
 */
class CorrectionResult {
	OrthographicCorrection orthographicCorrection;
	GrammaticalCorrection grammaticalCorrection;
	String htmlWithSuggestions;
	String listErrors;
	
	public CorrectionResult(String textToCorrect,
			OrthographicCorrection orthographicCorrection,
			GrammaticalCorrection grammaticalCorrection) {}

	public void makeHTML() {}

	private void insertSuggestionsFromCorrector(Corrector corrector,
			String markupName) {}

	public String getHTMLWithSuggestions() { return null;}
}

/**
 * @opt all
 */
interface Corrector {
	public void correctText();

	public boolean hasNextMistake();

	public String nextMistake();

	public List<String> nextSuggestions();

	public int[] nextMistakeLine();

	public String nextMessage();
}

/**
 * @opt all
 */
abstract class GrammaticalCorrection implements Corrector {
	protected String textToCorrect;
	
	public GrammaticalCorrection(String textToCorrect) {}
	abstract public void correctText();
}

/**
 * @opt all
 */
class HunspellOrthographicCorrection extends OrthographicCorrection{
	protected HashMap<String, List<String>> mistakesAndSuggestions;
	protected Set<String> mistakes;
	protected Iterator<String> iterator;
	protected String currentMistake;
	
	public HunspellOrthographicCorrection(String textToCorrect) {}
	public void correctText() {}
	public boolean hasNextMistake() {return true;}
	public String nextMistake() {return null;}
	public List<String> nextSuggestions() {return null;}
	public int[] nextMistakeLine() {return new int[1];}
	public String nextMessage() {return null;}
}

/**
 * @opt all
 * @has "" - "" RuleMatch
 */
class LanguageToolGrammaticalCorrection extends GrammaticalCorrection{
	
	protected List<RuleMatch> mistakesInfos;
	protected Iterator<RuleMatch> iterator;
	protected RuleMatch currentMistakeInfo;
	
	public LanguageToolGrammaticalCorrection(String textToCorrect) {}

	public void correctText() {}
	public boolean hasNextMistake() {return true;}
	public String nextMistake() {return null;}
	public List<String> nextSuggestions() {return null;}
	public int[] nextMistakeLine() {return new int[1];}
	public String nextMessage() {return null;}
}

/**
 * @opt all
 */
class Main {

	/**
	 * @param args
	 * @throws CryptographyException
	 * @throws IOException
	 */
	public static void main(String[] args) {}
	public static void demonstrateur_3() {}
	protected static void displayErrors(Corrector corrector) {}
	protected static void writeErrors(ArrayList<Triplet> list) {}
	protected static ArrayList<Triplet> putErrorsInTriplet(Corrector corrector) {return null;}
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

/**
 * @opt all
 */
abstract class OrthographicCorrection implements Corrector {
	protected String textToCorrect;
	public OrthographicCorrection(String textToCorrect) {}
	abstract public void correctText();
}

/**
 * @opt all
 */
class PDFBoxExtractor extends PDFSimpleExtractor{
	
	protected int nbCol;
	
	public PDFBoxExtractor(String pathToPDF) {}
	public PDFBoxExtractor() {}
	public PDFBoxExtractor(String pathToPDF, int nbCol) {}
	public PDFBoxExtractor(int nbCol) {}
	
	public void textExtraction() throws IOException, CryptographyException {}
	public int getNbCol() {return 0;}
	public void setNbCol(int nbCol) {}
}

/**
 * @opt all
 */
class PDFExtraction {

	public String extractedText = new String();
	protected String pathToPDF = new String();
	protected int nbCol;

	public PDFExtraction(String pathToPDF) {}
	public PDFExtraction() {}
	public PDFExtraction(int nbCol) {}
	public PDFExtraction(String pathToPDF, int nbCol) {}
	
	public void textExtraction() throws IOException, CryptographyException {}
	public String getExtractedText() {return null;}
	public void setExtractedText(String text) {}
	public int getNbCol() {return 0;}
	public void setNbCol(int nbCol) {}
}

/**
 * @opt all
 */
interface PDFExtractor {
	public void textExtraction() throws IOException, CryptographyException;

	public String getExtractedText();
}

/**
 * @opt all
 */
abstract class PDFSimpleExtractor implements PDFExtractor {
	protected String extractedText = new String();
	protected String pathToPDF = new String();
	
	public PDFSimpleExtractor(String pathToPDF) {}
	public PDFSimpleExtractor() {}
	
	abstract public void textExtraction() throws IOException, CryptographyException;
	public String getExtractedText() {return null;}
	public void setExtractedText(String text) {}
}

/**
 * @opt all
 */
class Triplet {

	protected int line;
	protected String word;
	protected List<String> correction;
	protected String message;

	public Triplet() {}
	public Triplet(int one, String two, List<String> three,String mess) {}
	
	public int getLine() {return 0;}
	public String getWord() {return null;}
	public String getMessage() {return null;}
	public List<String> getCorrection() {return null;}
	public void setLine(int l) {}
	public void setWord(String w) {}
	public void setMessage(String w) {}
	public void setCorrection(List<String> list) {}
}

/**
 * @opt all
 */
 class RuleMatch {}

/**
 * @opt all
 */ 
 class CryptographyException {}

