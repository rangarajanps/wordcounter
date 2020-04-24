package ranga.solution.wordcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WordCounterTest {

	WordCounter wc = new WordCounter();

	@Test
	@DisplayName("Negative Test AddWords with an InValid input - null")
	public void testAddSingleInvalidInputNull() {

		String[] wordList = null;
		assertEquals(false, wc.addWords(wordList));

	}
	
	@Test
	@DisplayName("Negative Test AddWords with an InValid input - empty")
	public void testAddSingleInvalidInputEmpty() {

		String[] wordList = { };
		assertEquals(false, wc.addWords(wordList));

	}
	
	@Test
	@DisplayName("Negative Test AddWords with an InValid input 2 - empty")
	public void testAddSingleInvalidInputEmpty2() {

		String[] wordList = {""};
		assertEquals(false, wc.addWords(wordList));

	}

	@Test
	@DisplayName("Negative Test AddWords with an InValid single word")
	public void testAddSingleInvalidWord() {

		String[] wordList = { "Test!" };
		assertEquals(false, wc.addWords(wordList));

	}
	
	@Test
	@DisplayName("Negative Test AddWords with an InValid word in a list")
	public void testAddSingleInvalidWordInAList() {

		String[] wordList = { "This", "is:", "a", "test" };
		assertEquals(false, wc.addWords(wordList));

	}
	
	@Test
	@DisplayName("Negative Test call getWordCount without calling addWords")
	public void testGetWordCountWithoutAddingAWord() {

		assertEquals(0, wc.getWordCount("test"));

	}

	@Test
	@DisplayName("Test AddWords with a Valid single word")
	public void testAddSingleValidWord() {

		String[] wordList = { "Test" };
		assertEquals(true, wc.addWords(wordList));
	}

	@Test
	@DisplayName("Test AddWords with a Valid word list")
	public void testAddValidWords() {

		String[] wordList = { "This", "is", "a", "test" };
		assertEquals(true, wc.addWords(wordList));
	}

	@Test
	@DisplayName("Test getWordCount with a Valid word")
	public void testAddWordCount() {
		String[] wordList = { "Test" };
		assertEquals(true, wc.addWords(wordList));
		assertEquals(1, wc.getWordCount("Test"));
	}

	@Test
	@DisplayName("Test getWordCount with an InValid word in the list")
	public void testAddWordCountWithInvalidWord() {
		String[] wordList = { "This", "is:", "a", "test" };
		assertEquals(false, wc.addWords(wordList));
		assertEquals(1, wc.getWordCount("test"));
	}

	@Test
	@DisplayName("Test getWordCount with a Valid word list")
	public void testAddWordCountWithMultipleAdd() {
		String[] wordList = { "Test" };
		assertEquals(true, wc.addWords(wordList));
		assertEquals(true, wc.addWords(wordList));
		assertEquals(2, wc.getWordCount("Test"));
		assertEquals(true, wc.addWords(wordList));
		assertEquals(3, wc.getWordCount("Test"));
	}
	
	@Test
	@DisplayName("Test getWordCount with large data set - 1000 words")
	public void testAddWordsWithThousandWords() {
		String[] wordList = { "This", "is", "a", "test","with","testing","for","a","large","dataset" };
		for (int i=0;i<100;i++) {
			assertEquals(true, wc.addWords(wordList));
		}
		
		assertEquals(200, wc.getWordCount("a"));
	}
	
	@Test
	@DisplayName("Test getWordCount with large data set - Million words")
	public void testAddWordsWithMillionWords() {
		String[] wordList = { "This", "is", "a", "test","with","testing","for","a","large","dataset" };
		for (int i=0;i<100000;i++) {
			assertEquals(true, wc.addWords(wordList));
		}
		
		assertEquals(200000, wc.getWordCount("a"));
	}

	@Test
	@DisplayName("Test getWordCount with Translate")
	public void testAddWordTranslate() {

		String[] wordList = { "This", "is", "a", "translation", "test", "flower", "flor", "blume" };
		assertEquals(true, wc.addWords(wordList));
		assertEquals(3, wc.getWordCount("flor"));
		assertEquals(3, wc.getWordCount("flower"));
		assertEquals(3, wc.getWordCount("blume"));
	}
	
	@Test
	@DisplayName("Test getWordCount with Translate where translated words are not added")
	public void testAddWordTranslateWithoutTranslatedWordAdded() {

		String[] wordList = { "This", "is", "a", "translation", "test", "blume" };
		assertEquals(true, wc.addWords(wordList));
		assertEquals(1, wc.getWordCount("blume"));
		
	}

	@Test
	@DisplayName("Test getWordCount for concurrency")
	public void canAddWordsConcurrently() throws InterruptedException {

		Random random = new Random();
		String[] wordList = { "Test" };

		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++)
			es.execute(new Runnable() {

				@Override
				public void run() {
					try {
						wc.addWords(wordList);
						Thread.sleep(random.nextInt(10));
					} catch (InterruptedException ignored) {
					}
				}
			});
		es.shutdown();
		es.awaitTermination(1, TimeUnit.MINUTES);

		assertEquals(100, wc.getWordCount("Test"));
	}

}