import java.util.List;

public interface OrthographicCorrector {
	public void correctText();

	public boolean hasNextMistake();

	public String nextMistake();

	public List<String> nextSuggestions();

	public int[] nextMistakeLine();
}
