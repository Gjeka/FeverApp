package com.fever.feverapp.objects;

import java.io.Serializable;
import java.util.UUID;


public class Song implements Serializable{

    //static list of all chats to be saved
    private static LinkedBag<Song> songList = new LinkedBag<>();
    // properties
    private String UID;
    public String name;
    public String artist;
    //public String genre;
    private String Link;
    public boolean hasSource;
    //todo: will specify where the link came from
    public boolean LinkisSpotify;
    public boolean LinkisYoutube;
    // private boolean gloabalRating; TODO: for a trending list and for stories

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.UID = UUID.randomUUID().toString();
        this.hasSource = false;
        this.Link = null;
    }
    //********************************************************************************************//
    /* Getters and Setters.
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getLink() {
        return Link;
    }
    // set the link of this song
    public void setLink(String link) {
        this.Link = link;
        // TODO: check if link is openable and downloadable
        this.hasSource = true;
    }
    public static LinkedBag<Song> getSongList() {
        return songList;
    }
    // get unique id of song
    public String getUID() {
        return UID;
    }

    public void setHasSource(boolean hasSource) {
        this.hasSource = hasSource;
    }

    public void setLinkisSpotify(boolean linkisSpotify) {
        LinkisSpotify = linkisSpotify;
    }

    public void setLinkisYoutube(boolean linkisYoutube) {
        LinkisYoutube = linkisYoutube;
    }

    // Check if two songs are equal
    public boolean equals(Song song){
        return this.getUID().equals(song.getUID());
    }
    @Override
    public String toString() {
        return (this.name + " - " +this.artist);
    }

    //********************************************************************************************//
    /* Methods needed by the class.
     */
    //TODO: TO PLAY THE SONG, PLAYER MIGHT BE HERE
    /*
    public boolean play(){
        if(!this.hasSource){
            return null
        }
        //call the player to play this song

    }*/
}
