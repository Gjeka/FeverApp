package com.fever.feverapp.objects;
import java.io.Serializable;
import java.util.Date;

public class chatSongRating implements Serializable {
    public String sentiment;
    private User creator;
    private Date created;
    private Song song;
    private int finishedRates;
    private int neededRates;
    private double tmpAvg;
    private double weights;
    private   boolean applied;
    private Chat inChat;

    public chatSongRating(String text, User madeBy, Song song1, Chat chat) {
        this.sentiment = text;
        this.creator = madeBy;
        this.created = new Date();
        this.song = song1;
        this.finishedRates = 0;
        this.neededRates = chat.getInvolved().getSize() -1 ;
        this.tmpAvg = 0;
        this.weights = 0;
        this.applied = false;
        this.inChat = chat;
    }
    //*******************************************************************************************//
    /* Getters and Setters.

     */
    // Returns the rating of the Song
    public double getTempRating() {
        return this.tmpAvg/this.weights;
    }
    // Sets the rating of the Song
    public double getTmpAvg() {
        return tmpAvg;
    }

    public void setTmpAvg(double tmpAvg) {
        this.tmpAvg = tmpAvg;
    }

    public double getWeights() {
        return weights;
    }

    public void setWeights(double weights) {
        this.weights = weights;
    }

    public boolean isComplete(){
        if(this.neededRates == this.finishedRates){
            return true;
        }
        return false;
    }
    // Get the percentage of completeness of the rating of this song
    public double howComplete(){
        return this.finishedRates/this.neededRates;
    }
    // Returns sentiment
    public String getSentiment() {
        return sentiment;
    }
    // Sets Sentiment
    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    // Returns Creator
    public User getCreator() {
        return creator;
    }
    // Return Date Created
    public Date getCreated() {
        return created;
    }
    // Returns the song this rating is about.
    public Song getSong(){
        return song;
    }
    // Return the chat that this rating is in.
    public Chat getInChat() {
        return inChat;
    }
    // Checks if the final rating of this song has been applied to the creators weight
    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public double getFinalrating(){
        if( this.isComplete()){
            return this.getTmpAvg()/this.getWeights();
        }
        System.err.print("This song has not been fully rated");
        return -1.0;
    }

    //********************************************************************************************//
    /* Methods needed by SongRating class.
     */
    // Checks if two ratings a equal.
    public boolean equals(chatSongRating rating){
         if(this.getSong().equals(rating.getSong()) & this.getInChat().equals(rating.getInChat())){
             return true;
         }
         return false;
    }
    // Adds a rating on the song based on the weight of the rater.
    public boolean addRating(double rate, User rater){
        //  FORMULA THAT WILL ADD RATE BESED ON RANK OF RATER AND SIZE OF CHAT GROUP
        //  also check if person adding rating has already rated this song, Maybe as the only way you can rate is if you have a rate request.
        if ((this.finishedRates < this.neededRates) & !this.getCreator().equals(rater)) {
            this.finishedRates++;
            int weight = 1;
            if (rater.getRank() < 25) {
                weight = 1;
            }
            if (rater.getRank() >= 25 & rater.getRank() < 50) {
                weight = 2;
            }
            if (rater.getRank() >= 50 & rater.getRank() < 75) {
                weight = 3;
            }
            if (rater.getRank() >= 75) {
                weight = 4;
            }
            this.setTmpAvg(this.getTmpAvg() + (rate*weight));
            this.setWeights(this.getWeights() + weight);
            return true;
        }return false;
    }
}
