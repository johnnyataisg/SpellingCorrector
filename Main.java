import spell.*;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

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
		/*SpellCorrector spellCorrector = new SpellCorrector();
		spellCorrector.useDictionary(args[0]);
		String suggestion = spellCorrector.suggestSimilarWord(args[1]);
		if (suggestion == null)
		{
			suggestion = "No similar word found";
		}
		System.out.println("Suggestion is: " + suggestion);*/
        Scanner scanner = new Scanner(new File(args[0]));
        Scanner scanner2 = new Scanner(new File(args[1]));
        Trie trie = new Trie();
        Trie trie2 = new Trie();
        while (scanner.hasNext())
        {
            trie.add(scanner.next());
        }
        while (scanner2.hasNext())
        {
            trie2.add(scanner2.next());
        }
        System.out.println(trie.equals(trie2));
        System.out.println(trie2.equals(trie));
	}

}
