package ranga.solution.wordcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class WordCounterTest {

	WordCounter wc = new WordCounter();

	@Test
	public void testAddSingleValidWord() {

		String[] wordList = { "Test" };
		assertEquals(true, wc.addWord(wordList));
	}

	@Test
	public void testAddValidWords() {

		String[] wordList = { "This", "is", "a", "test" };
		assertEquals(true, wc.addWord(wordList));
	}

	@Test
	public void testAddSingleInvalidWord() {

		String[] wordList = { "Test!" };
		assertEquals(false, wc.addWord(wordList));

	}

	@Test
	public void testAddSingleInvalidWordInAList() {

		String[] wordList = { "This", "is:", "a", "test" };
		assertEquals(false, wc.addWord(wordList));

	}

	@Test
	public void testAddWordCount() {
		String[] wordList = { "Test" };
		assertEquals(true, wc.addWord(wordList));
		assertEquals(1, wc.getWordCount("Test"));
	}

	@Test
	public void testAddWordCountWithMultipleAdd() {
		String[] wordList = { "Test" };
		assertEquals(true, wc.addWord(wordList));
		assertEquals(true, wc.addWord(wordList));
		assertEquals(2, wc.getWordCount("Test"));
		assertEquals(true, wc.addWord(wordList));
		assertEquals(3, wc.getWordCount("Test"));
	}

	@Test
	public void testAddWordTranslate() {

		String[] wordList = { "This", "is", "a", "translation", "test", "flower", "flor", "blume" };
		assertEquals(true, wc.addWord(wordList));
		assertEquals(3, wc.getWordCount("flor"));
		assertEquals(3, wc.getWordCount("flower"));
		assertEquals(3, wc.getWordCount("blume"));
	}

	@Test
	public void canAddWordsConcurrently() throws InterruptedException {

		Random random = new Random();
		String[] wordList = { "Test" };

		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++)
			es.execute(new Runnable() {

				@Override
				public void run() {
					try {
						wc.addWord(wordList);
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
