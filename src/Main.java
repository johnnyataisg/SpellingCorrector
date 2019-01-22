import spell.*;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException
	{
		SpellCorrector spellCorrector = new SpellCorrector();
		spellCorrector.useDictionary(args[0]);
		String suggestion = spellCorrector.suggestSimilarWord(args[1]);
		if (suggestion == null)
		{
			suggestion = "No similar word found";
		}
		System.out.println("Suggestion is: " + suggestion);
		Trie trie = spellCorrector.getTrie();
		System.out.println(trie.hashCode(trie.find(args[1])));
	}

}