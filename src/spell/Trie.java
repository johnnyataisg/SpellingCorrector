package spell;

import java.util.ArrayList;

public class Trie implements ITrie
{
    static final int alphabet_length = 26;
    private Node root;
    private int uniqueWordCount;
    private int nodeCount;

    public Trie()
    {
        this.root = new Node();
        this.uniqueWordCount = 0;
        this.nodeCount = 1;
    }
    //Add a word in the dictionary
    public void add(String word)
    {
        int tier;
        int keyLength = word.length();
        int index;
        Node currentNode = this.root;
        for (tier = 0; tier < keyLength; tier++)
        {
            char currentLetter = word.charAt(tier);
            index = currentLetter - 'a';
            if (currentNode.nextLetters[index] == null)
            {
                currentNode.nextLetters[index] = new Node(currentLetter);
                this.nodeCount++;
            }
            currentNode = currentNode.nextLetters[index];
        }
        if (currentNode.isLastChar == false)
        {
            this.uniqueWordCount++;
        }
        currentNode.isLastChar = true;
        currentNode.representedWord = word;
        currentNode.addFrequency();
    }
    //Search for a word in the dictionary
    public Node find(String word)
    {
        int tier;
        int keyLength = word.length();
        int index;
        Node currentNode = this.root;
        for (tier = 0; tier < keyLength; tier++)
        {
            index = word.charAt(tier) - 'a';
            if (currentNode.nextLetters[index] == null)
            {
                return null;
            }
            currentNode = currentNode.nextLetters[index];
        }
        if (currentNode != null && currentNode.isLastChar == true)
        {
            return currentNode;
        }
        else
        {
            return null;
        }
    }
    //Return an arraylist of words that have the highest frequencies
    public ArrayList<String> selectHighCounts(ArrayList<String> wordList)
    {
        int largestNumber = 0;
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++)
        {
            int frequency = this.getCount(wordList.get(i));
            if (frequency > largestNumber)
            {
                largestNumber = frequency;
                newList.clear();
            }
            if (frequency == largestNumber)
            {
                newList.add(wordList.get(i));
            }
        }
        return newList;
    }
    //Return an arraylist of words that are 1 delete distance away from the target
    public ArrayList<String> deleteDistance(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> candidates = new ArrayList<>();
        if (wordLength > 1)
        {
            for (tier = 0; tier < wordLength; tier++)
            {
                StringBuilder modified = new StringBuilder(word);
                modified.deleteCharAt(tier);
                if (this.find(modified.toString()) != null)
                {
                    candidates.add(modified.toString());
                }
            }
        }
        return candidates;
    }
    //Return an arraylist of words that are 2 delete distance away from the target
    public ArrayList<String> deleteDistanceTwo(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> intermediates = new ArrayList<>();
        if (wordLength > 1)
        {
            for (tier = 0; tier < wordLength; tier++)
            {
                StringBuilder modified = new StringBuilder(word);
                modified.deleteCharAt(tier);
                intermediates.add(modified.toString());
            }
        }
        ArrayList<String> candidates = new ArrayList<>();
        for (String str : intermediates)
        {
            candidates.addAll(this.deleteDistance(str));
            candidates.addAll(this.transposeDistance(str));
            candidates.addAll(this.alterDistance(str));
            candidates.addAll(this.insertDistance(str));
        }
        return candidates;
    }
    //Return an arraylist of words that are 1 transpose distance away from the target
    public ArrayList<String> transposeDistance(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> candidates = new ArrayList<>();
        if (wordLength > 1)
        {
            for (tier = 0; tier < wordLength - 1; tier++)
            {
                StringBuilder modified = new StringBuilder(word);
                char firstChar = modified.charAt(tier);
                char secondChar = modified.charAt(tier + 1);
                modified.setCharAt(tier, secondChar);
                modified.setCharAt(tier + 1, firstChar);
                if (this.find(modified.toString()) != null)
                {
                    candidates.add(modified.toString());
                }
            }
        }
        return candidates;
    }
    //Return an arraylist of words that are 2 transpose distance away from the target
    public ArrayList<String> transposeDistanceTwo(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> intermediates = new ArrayList<>();
        if (wordLength > 1)
        {
            for (tier = 0; tier < wordLength - 1; tier++)
            {
                StringBuilder modified = new StringBuilder(word);
                char firstChar = modified.charAt(tier);
                char secondChar = modified.charAt(tier + 1);
                modified.setCharAt(tier, secondChar);
                modified.setCharAt(tier + 1, firstChar);
                intermediates.add(modified.toString());
            }
        }
        ArrayList<String> candidates = new ArrayList<>();
        for (String str : intermediates)
        {
            candidates.addAll(this.deleteDistance(str));
            candidates.addAll(this.transposeDistance(str));
            candidates.addAll(this.alterDistance(str));
            candidates.addAll(this.insertDistance(str));
        }
        return candidates;
    }
    //Return an arraylist of words that are 1 alter distance away from the target
    public ArrayList<String> alterDistance(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> candidates = new ArrayList<>();
        for (tier = 0; tier < wordLength; tier++)
        {
            for (char i = 'a'; i <= 'z'; i++)
            {
                char defaultChar = word.charAt(tier);
                StringBuilder modified = new StringBuilder(word);
                if (i != defaultChar)
                {
                    modified.setCharAt(tier, i);
                    if (this.find(modified.toString()) != null)
                    {
                        candidates.add(modified.toString());
                    }
                }
            }
        }
        return candidates;
    }
    //Return an arraylist of words that are 2 alter distance away from the target
    public ArrayList<String> alterDistanceTwo(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> intermediates = new ArrayList<>();
        for (tier = 0; tier < wordLength; tier++)
        {
            for (char i = 'a'; i <= 'z'; i++)
            {
                char defaultChar = word.charAt(tier);
                StringBuilder modified = new StringBuilder(word);
                if (i != defaultChar)
                {
                    modified.setCharAt(tier, i);
                    intermediates.add(modified.toString());
                }
            }
        }
        ArrayList<String> candidates = new ArrayList<>();
        for (String str : intermediates)
        {
            candidates.addAll(this.deleteDistance(str));
            candidates.addAll(this.transposeDistance(str));
            candidates.addAll(this.alterDistance(str));
            candidates.addAll(this.insertDistance(str));
        }
        return candidates;
    }
    //Return an arraylist of words that are 1 insert distance away from the target
    public ArrayList<String> insertDistance(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> candidate = new ArrayList<>();
        for (tier = 0; tier <= wordLength; tier++)
        {
            for (char i = 'a'; i <= 'z'; i++)
            {
                StringBuilder modified = new StringBuilder(word);
                modified.insert(tier, i);
                if (this.find(modified.toString()) != null)
                {
                    candidate.add(modified.toString());
                }
            }
        }
        return candidate;
    }
    //Return an arraylist of words that are 2 insert distance away from the target
    public ArrayList<String> insertDistanceTwo(String word)
    {
        int tier;
        int wordLength = word.length();
        ArrayList<String> intermediates = new ArrayList<>();
        for (tier = 0; tier <= wordLength; tier++)
        {
            for (char i = 'a'; i <= 'z'; i++)
            {
                StringBuilder modified = new StringBuilder(word);
                modified.insert(tier, i);
                intermediates.add(modified.toString());
            }
        }
        ArrayList<String> candidates = new ArrayList<>();
        for (String str : intermediates)
        {
            candidates.addAll(this.deleteDistance(str));
            candidates.addAll(this.transposeDistance(str));
            candidates.addAll(this.alterDistance(str));
            candidates.addAll(this.insertDistance(str));
        }
        return candidates;
    }

    public int getWordCount()
    {
        return this.uniqueWordCount;
    }

    public int getNodeCount()
    {
        return this.nodeCount;
    }

    //STARTER FUNCTION: List all words in the dictionary in alphabetical order
    public String toString()
    {
        StringBuilder output = new StringBuilder();
        Node startNode = this.root;
        this.toString(output, startNode);
        return output.toString();
    }

    //RECURSIVE FUNCTION: Build the list of words in the dictionary in a string
    public void toString(StringBuilder fullString, Node currentNode)
    {
        if (currentNode.isLastChar == true)
        {
            fullString.append(currentNode.representedWord + System.lineSeparator());
        }
        for (int i = 0; i < alphabet_length; i++)
        {
            if (currentNode.nextLetters[i] != null)
            {
                toString(fullString, currentNode.nextLetters[i]);
            }
        }
    }

    public int hashCode()
    {
        int[] hashValue = {1};
        Node startNode = this.root;
        this.hashCode(hashValue, startNode);
        hashValue[0] = hashValue[0] * 31;
        return hashValue[0];
    }

    public void hashCode(int[] value, Node currentNode)
    {
        if (currentNode != this.root)
        {
            value[0] += (currentNode.getChar() * currentNode.getValue());
            if (currentNode.isLastChar == true)
            {
                for (int i = 0; i < currentNode.representedWord.length(); i++)
                {
                    value[0] += currentNode.representedWord.charAt(i);
                }
            }
        }
        for (int i = 0; i < alphabet_length; i++)
        {
            if (currentNode.nextLetters[i] != null)
            {
                hashCode(value, currentNode.nextLetters[i]);
            }
        }
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }
        Trie newTrie = (Trie)obj;
        Node trie_1_node = this.root;
        Node trie_2_node = newTrie.root;
        return equals(trie_1_node, trie_2_node);
    }

    public boolean equals(Node first, Node second)
    {
        if (first.frequency != second.frequency)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < alphabet_length; i++)
            {
                if (first.nextLetters[i] == null && second.nextLetters[i] != null)
                {
                    return false;
                }
                else if (first.nextLetters[i] != null && second.nextLetters[i] == null)
                {
                    return false;
                }
                else if (first.nextLetters[i] != null && second.nextLetters[i] != null)
                {
                    if (equals(first.nextLetters[i], second.nextLetters[i]) == false)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int getCount(String word)
    {
        int tier;
        int wordLength = word.length();
        Node currentNode = this.root;
        for (tier = 0; tier < wordLength; tier++)
        {
            int index = word.charAt(tier) - 'a';
            currentNode = currentNode.nextLetters[index];
        }
        return currentNode.getValue();
    }

    public class Node implements ITrie.INode
    {
        //Sets every node's children array length to 26
        Node[] nextLetters = new Node[alphabet_length];
        private String representedWord;
        private char letter;
        private int frequency;
        boolean isLastChar;
        //Default constructor
        public Node()
        {
            this.frequency = 0;
            this.isLastChar = false;
            for (int i = 0; i < alphabet_length; i++)
            {
                this.nextLetters[i] = null;
            }
        }
        //Constructor for specific character
        public Node(char character)
        {
            this.frequency = 0;
            this.letter = character;
            this.isLastChar = false;
            for (int i = 0; i < alphabet_length; i++)
            {
                this.nextLetters[i] = null;
            }
        }
        //Increment the frequency of the word that ends with this node by 1
        public void addFrequency()
        {
            this.frequency++;
        }
        //Get the string represented by this node
        public String getString()
        {
            return this.representedWord;
        }
        //Get the frequency of the word that ends with this node
        public int getValue()
        {
            return this.frequency;
        }
        //Get the character represented by this node
        public char getChar()
        {
            return this.letter;
        }
    }
}
