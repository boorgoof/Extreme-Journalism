package it.unipd.dei.dbdc.analysis.src_analyzers.MapSplitAnalyzer;

import it.unipd.dei.dbdc.analysis.Article;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class that tests {@link AnalyzerArticleThread}.
 */
@Order(7)
public class AnalyzerArticleThreadTest {

    /**
     * Tests of {@link AnalyzerArticleThread#run()} with valid and invalid inputs gave to the constructor.
     */
    @Test
    public void run()
    {
        //Tests with null
        AnalyzerArticleThread t = new AnalyzerArticleThread(null, null, null);
        assertThrows(IllegalArgumentException.class, t::run);

        t = new AnalyzerArticleThread(null, new TreeMap<>(), new Semaphore(1));
        assertThrows(IllegalArgumentException.class, t::run);

        t = new AnalyzerArticleThread(new Article(), null, new Semaphore(1));
        assertThrows(IllegalArgumentException.class, t::run);

        t = new AnalyzerArticleThread(new Article(), new TreeMap<>(), null);
        assertThrows(IllegalArgumentException.class, t::run);

        //Nothing to analyze
        t = new AnalyzerArticleThread(new Article(), new TreeMap<>(), new Semaphore(1));
        assertThrows(IllegalArgumentException.class, t::run);

        //Valid tests
        TreeMap<String, Integer> results = new TreeMap<>();
        TreeMap<String, Integer> expected = new TreeMap<>();

        String[] params = {"id", "url", "this is the headline", "this is the body", "date", null, null};
        expected.put("this", 1);
        expected.put("is", 1);
        expected.put("the", 1);
        expected.put("headline", 1);
        expected.put("body", 1);
        t = new AnalyzerArticleThread(new Article(params), results, new Semaphore(1));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);

        String[] params1 = {null, null, "this is the headline and not", "not", null, "null", "null"};
        expected.put("this", 2);
        expected.put("is", 2);
        expected.put("the", 2);
        expected.put("headline", 2);
        expected.put("and", 1);
        expected.put("not", 1);
        t = new AnalyzerArticleThread(new Article(params1), results, new Semaphore(1));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);

        String[] params2 = {null, null, "this the headline and not mine", "not headline", null, null, null};
        expected.put("this", 3);
        expected.put("the", 3);
        expected.put("headline", 3);
        expected.put("and", 2);
        expected.put("not", 2);
        expected.put("mine", 1);
        t = new AnalyzerArticleThread(new Article(params2), results, new Semaphore(1));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);

        String[] params3 = {null, null, "", "", null, null, null};
        t = new AnalyzerArticleThread(new Article(params3), results, new Semaphore(1));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);

        String[] params4 = {null, null, "headline  19856 -1 2 £/ ( ) ? ^ ! $ % & | \\ \"+* @#° ", "not headline", null, null, null};
        expected.put("headline", 4);
        expected.put("not", 3);
        t = new AnalyzerArticleThread(new Article(params4), results, new Semaphore(1));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);

        String[] params5 = {null, null, "??????????????", "not??and???", null, null, null};
        expected.put("not", 4);
        expected.put("and", 3);
        t = new AnalyzerArticleThread(new Article(params5), results, new Semaphore(1));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);

        String[] params6 = {null, null, "not null", "123", null, null, null};
        expected.put("not", 5);
        expected.put("null", 1);
        t = new AnalyzerArticleThread(new Article(params6), results, new Semaphore(1));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);

        String[] params7 = {null, null, "yes", null, null, null, null};
        t = new AnalyzerArticleThread(new Article(params7), results, new Semaphore(1));
        assertThrows(IllegalArgumentException.class, t::run);
        assertEquals(expected, results);

        String[] params8 = {null, null, null, "yes", null, null, null};
        t = new AnalyzerArticleThread(new Article(params8), results, new Semaphore(1));
        assertThrows(IllegalArgumentException.class, t::run);
        assertEquals(expected, results);

        //Test with a different semaphore
        String[] params9 = {null, null, "yes", "null", null, null, null};
        expected.put("yes", 1);
        expected.put("null", 2);
        t = new AnalyzerArticleThread(new Article(params9), results, new Semaphore(45));
        assertDoesNotThrow(t::run);
        assertEquals(expected, results);
    }
}
