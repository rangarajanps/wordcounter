package ranga.solution.wordcounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WordCounter provides methods to add words and get word count
 */
public class WordCounter {

	private HashMap<String, Integer> wcMap = new HashMap<String, Integer>();

	/**
	 * This method is used to add words to the store
	 * 
	 * @param input Words to add in the converter
	 * @return boolean This returns true for successful operation and false even if
	 *         one word is not added
	 */
	public boolean addWord(String[] strList) {

		boolean status = true;
		for (String word : strList) {
			if (!validateAndAddWord(word)) {
				status = false;
			}
		}

		return status;
	}

	/**
	 * This method is used to validate and add a given word to the store
	 * 
	 * @param input Word to add in the converter
	 * @return boolean This returns true for successful operation and false if there
	 *         is an non-alphanumeric character
	 */
	private synchronized boolean validateAndAddWord(String input) {

		if (!validateWord(input)) {
			return false;
		}

		int value = 1;
		if (wcMap.containsKey(input)) {
			value += wcMap.get(input);
		}
		wcMap.put(input, value);

		return true;
	}

	/**
	 * This method is used to verify if the given word contains alpha-numeric
	 * characters or not
	 * 
	 * @param input Word to validate
	 * @return boolean This returns true if all characters in the word are
	 *         alpha-numeric
	 */
	private boolean validateWord(String word) {

		List<Character> result = word.chars().mapToObj(c -> (char) c)
				.filter(chr -> Character.isLetterOrDigit(chr))
				.collect(Collectors.toList());

		if (word.length() == result.size()) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns the count of how many times a given word was added to the
	 * word counter
	 * 
	 * @param input Word for which add count is required
	 * @return int This returns the number of times a given word was added
	 */
	public int getWordCount(String input) {

		if (wcMap.isEmpty()) {
			return 0;
		}

		int count = 0;
		if (wcMap.containsKey(input)) {
			count += wcMap.get(input);
		}

		String translatedInput = Translator.translate(input);

		for (Map.Entry<String, Integer> entry : wcMap.entrySet()) {

			// call translate API and check if translated word is present in the Map or not
			// if present, add the count of the translated word
			String wordToCheck = Translator.translate(entry.getKey());
			if (wordToCheck.equalsIgnoreCase(translatedInput) && wcMap.containsKey(wordToCheck)
					&& (!entry.getKey().equalsIgnoreCase(wordToCheck))) {
				count += wcMap.get(input);
			}
		}

		return count;
	}

}
