import java.util.List;

public interface GrammaticalCorrector {
	public void correctText();

	public boolean hasNextMistake();

	public String nextMistake();

	public List<String> nextSuggestions();

	public int nextMistakeLine();

	public String nextMessage();
}