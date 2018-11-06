package com.fever.feverapp.handlers;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

//NEEDED FOR YOUTUBE STUFF
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.youtube.model.*;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


import com.google.api.services.youtube.model.SearchResult;

// Specifically for finding songs off the internet, Youtube or spotify or locally;
public class
SearchYoutubeActivity extends ListActivity {

    public String text;

    public static List<SearchResult> SEARCH_RESULTS;
    //Youtube variables
    /** youtube: Define global instance of a Youtube object to search for vidoes on Youtube.
     * Then display the name and thumbnail image of each video.
     *
     */
    /** Application name. */
    private static final long NUM_VIDS_RETURNED = 25;

    private static YouTube youTube;

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/java-youtube-api-tests");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final Collection<String> SCOPES = Arrays.asList("YouTubeScopes.https://www.googleapis.com/auth/youtube.force-ssl YouTubeScopes.https://www.googleapis.com/auth/youtubepartner");

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // GET THE INTENET,
        Intent intent = getIntent();
        SEARCH_RESULTS = null;
        //TODO: may cause problems if you dont send the extra properly
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchYoutube(query);
        }
    }

    public void searchYoutube(String searchText){

        try {
            youTube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("Fever").build();
            // Define API request for retreiving  search results
            YouTube.Search.List search = youTube.search().list("id, snippet");

            // Set developer key from Google Cloud Consle for non-authenticated requests.
            String apiKey = DeveloperKey.getDeveloperKey();
            search.setKey(apiKey);
            search.setQ(searchText);

            //Restrict search results tp only include vidoes.
            search.setType("video");
            // Retreive the feilds that we need
            search.setFields("items(id/kind, id/videoId, snippet/title, snippet/thumbnails/default/url)");
            search.setMaxResults(NUM_VIDS_RETURNED);

            //search and print results
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResults = searchResponse.getItems();

            //Print results
            if (searchResults != null){
                //Intent intent = new Intent(this, VideoListDemoActivity.class);
                //intent.putExtra("adminClass", this.administrator);
                SEARCH_RESULTS = searchResults;
                //startActivity(intent);
            }
        }catch (GoogleJsonResponseException e){
            System.err.println("There was a service error: "+ e.getDetails().getCode()+ ", " + e.getDetails().getMessage());
        }catch (IOException e){
            System.err.println(" There was an IO error: " + e.getCause() + " : "+ e.getMessage());
        }catch (Throwable t){
            t.printStackTrace();
        }

    }
}
