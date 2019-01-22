package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class SpellCorrector implements ISpellCorrector
{
    private Trie trie;

    public SpellCorrector()
    {
        this.trie = new Trie();
    }

    public void useDictionary(String dictionaryFileName) throws IOException
    {
       Scanner scanner = new Scanner(new File(dictionaryFileName));
       while (scanner.hasNext())
       {
           this.trie.add(scanner.next());
       }
    }

    public String suggestSimilarWord(String inputWord)
    {
        if (this.trie.find(inputWord) != null)
        {
            return this.trie.find(inputWord).getString();
        }
        else
        {
            ArrayList<String> candidates = new ArrayList<>();
            candidates.addAll(this.trie.deleteDistance(inputWord));
            candidates.addAll(this.trie.transposeDistance(inputWord));
            candidates.addAll(this.trie.alterDistance(inputWord));
            candidates.addAll(this.trie.insertDistance(inputWord));
            if (candidates.size() == 0)
            {
                candidates.addAll(this.trie.deleteDistanceTwo(inputWord));
                candidates.addAll(this.trie.transposeDistanceTwo(inputWord));
                candidates.addAll(this.trie.alterDistanceTwo(inputWord));
                candidates.addAll(this.trie.insertDistanceTwo(inputWord));
            }
            if (candidates.size() == 0)
            {
                return null;
            }
            if (candidates.size() > 1)
            {
                candidates = this.trie.selectHighCounts(candidates);
            }
            if (candidates.size() > 1)
            {
                Collections.sort(candidates);
            }
            return candidates.get(0);
        }
    }

    public Trie getTrie()
    {
        return this.trie;
    }
}
