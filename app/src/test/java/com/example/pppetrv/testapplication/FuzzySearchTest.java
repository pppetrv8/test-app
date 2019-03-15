package com.example.pppetrv.testapplication;

import com.example.pppetrv.testapplication.util.FuzzySearch;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class FuzzySearchTest {

    private static String TEST_STR = "cartwheel";

    private FuzzySearch fSearch = null;

// fuzzysearch("car", "cartwheel");        // true
// fuzzysearch("cwhl", "cartwheel");       // true
// fuzzysearch("cwheel", "cartwheel");     // true
// fuzzysearch("cartwheel", "cartwheel");  // true
// fuzzysearch("cwheeel", "cartwheel");    // false
// fuzzysearch("lw", "cartwheel");         // false

    @Before
    public void prepare() {
        fSearch = new FuzzySearch();
    }

    @Test
    public void testSearchNullOrEmpty() {
        assert(!fSearch.search(null, TEST_STR));
        assert(!fSearch.search("car", null));
        assert(!fSearch.search("", TEST_STR));
        assert(!fSearch.search("", ""));
    }

    @Test
    public void testSearch1() {
        assert(fSearch.search("car", TEST_STR));
    }

    @Test
    public void testSearch2() {
        assert(fSearch.search("cwhl", TEST_STR));
    }

    @Test
    public void testSearch3() {
        assert(fSearch.search("cwheel", TEST_STR));
    }

    @Test
    public void testSearch4() {
        assert(fSearch.search("cartwheel", TEST_STR));
    }


    @Test
    public void testSearch5() {
        assert(!fSearch.search("cwheeel", TEST_STR));
    }

    @Test
    public void testSearch6() {
        assert(!fSearch.search("lw", TEST_STR));
    }

//    @Test
//    public void testSearchAll() {
//        assert(fSearch.search("car", "cartwheel"));
//        assert(fSearch.search("cwhl", "cartwheel"));
//        assert(fSearch.search("cwheel", "cartwheel"));
//        assert(fSearch.search("cartwheel", "cartwheel"));
//        assert(!fSearch.search("cwheeel", "cartwheel"));
//        assert(!fSearch.search("lw", "cartwheel"));
//    }
}
