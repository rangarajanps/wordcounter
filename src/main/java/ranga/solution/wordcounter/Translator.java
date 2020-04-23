package ranga.solution.wordcounter;

/**
 * Translator is a placeholder for testing purpose. This will be replaced with external class.
 */
public class Translator {
	
	/**
	   * This method is used to get an English translated word for a given word
	   * 
	   * @param input Word to be translated in English
	   * @return String This returns the input word as is as a stub and 
	   * 			provides valid values only to support hard coded string for testing 
	   */
	public static String translate(String input) {
		
		if (input.equalsIgnoreCase("blume") || input.equalsIgnoreCase("flor") ) {
			return "flower";
		}
		return input;
	}

}
