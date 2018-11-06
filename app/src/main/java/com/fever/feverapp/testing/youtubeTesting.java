package com.fever.feverapp.testing;

import com.fever.feverapp.handlers.SearchYoutubeActivity;
import com.fever.feverapp.objects.Admin;
import com.google.api.services.youtube.model.SearchResult;

import junit.framework.TestCase;

public class youtubeTesting extends TestCase{

    public Admin admin;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        SearchYoutubeActivity searchYoutube = new SearchYoutubeActivity();
        searchYoutube.searchYoutube("Joe Rogan");
    }

    public void testSearches(){
        assertNotNull(SearchYoutubeActivity.SEARCH_RESULTS);
        for(SearchResult item : SearchYoutubeActivity.SEARCH_RESULTS){
            System.out.print(item.getSnippet().getTitle().toString());
            System.out.print("\n");
        }
    }
}
